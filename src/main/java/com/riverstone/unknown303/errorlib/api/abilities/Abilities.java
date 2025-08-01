package com.riverstone.unknown303.errorlib.api.abilities;

import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.abilities.ability.Ability;
import com.riverstone.unknown303.errorlib.api.abilities.ability.misc.AbilityContext;
import com.riverstone.unknown303.errorlib.api.event.AbilityEvent;
import com.riverstone.unknown303.errorlib.api.misc.ErrorRegistries;
import com.riverstone.unknown303.errorlib.misc.ErrorKeybinds;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.util.HashMap;

public class Abilities implements IAbilities {
    public static final ResourceLocation ABILITIES_ID =
            ResourceLocation.fromNamespaceAndPath(ErrorMod.MOD_ID, "abilities_properties");

    private final AbilitiesHandler handler = new AbilitiesHandler();
    private String owner = "";

    private final HashMap<ResourceLocation, Runnable> queue = new HashMap<>();

    @Override
    public AbilitiesHandler getHandler() {
        return this.handler;
    }

    @Override
    public IAbilities setOwner(Player player) {
        this.owner = player.getStringUUID();
        return this;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public boolean contains(Ability ability) {
        return handler.getAvailableAbilities().contains(ability) ||
                handler.getUnlockedAbilities().contains(ability) ||
                handler.getConstantAbilities().contains(ability);
    }

    @ApiStatus.Internal
    @Override
    public void tickAbilities(Player player) {
        handler.getEnabledAbilities().forEach(ability -> {
            if (ability == null)
                return;
            if (!MinecraftForge.EVENT_BUS.post(new AbilityEvent.AbilityTickEvent(
                    player, this, ability)))
                ability.tick(player, player.level());
            queue(ability.getId(), () -> {
                if (ability.getContext() == AbilityContext.HOLD) {
                    int slot = handler.getEnabledAbilities().indexOf(ability);
                    if (slot == -1) {
                        disable(ability, player);
                        return;
                    }
                    if (!ErrorKeybinds.getAbilitySlotKeybind(slot).getKeyMapping()
                            .isDown())
                        disable(ability, player);
                }
            });
        });
        handler.getConstantAbilities().forEach(ability -> {
            if (!MinecraftForge.EVENT_BUS.post(new AbilityEvent.AbilityTickEvent(
                    player, this, ability)))
                ability.tick(player, player.level());
        });
        runAll();
    }

    private void queue(ResourceLocation key, Runnable value) {
        queue.put(key, value);
    }

    private void runFromQueue(ResourceLocation key) {
        queue.get(key).run();
    }

    private void runAll() {
        queue.forEach((key, value) -> {
            value.run();
        });
    }

    @Override
    public void unlockAbility(Ability ability, Player player) {
        AbilityEvent.AbilityUnlockedEvent abilityUnlockedEvent = new AbilityEvent.AbilityUnlockedEvent(player, this, ability);
        if (!MinecraftForge.EVENT_BUS.post(abilityUnlockedEvent)) {
            if (ability.getContext() == AbilityContext.CONSTANT){
                handler.unlockConstant(ability, abilityUnlockedEvent);
                if (!MinecraftForge.EVENT_BUS.post(new AbilityEvent.AbilityEnabledEvent(player, this, ability)))
                    ability.enable(player, player.level());
                return;
            }
            handler.unlockAbility(ability, abilityUnlockedEvent);
        }
    }

    @Override
    public void lockAbility(Ability ability, Player player) {
        if (!MinecraftForge.EVENT_BUS.post(new AbilityEvent.AbilityLockedEvent(player, this, ability))) {
            if (ability.getContext() == AbilityContext.CONSTANT ||
                handler.getEnabledAbilities().contains(ability)) {
                if (!MinecraftForge.EVENT_BUS.post(new AbilityEvent.AbilityDisabledEvent(
                        player, this, ability))) {
                    ability.disable(player, player.level());
                    handler.getConstantAbilities().remove(ability);
                    handler.getEnabledAbilities().remove(ability);
                }
            }
            if (ability.getContext() == AbilityContext.CONSTANT)
                handler.lockAbility(ability);
        }
    }

    @ApiStatus.Internal
    @Override
    public void pressAbilityKeybind(int keybindSlot, Player player) {
        if (!handler.getAvailableAbilities().isEmpty()) {
            if (handler.getAvailableAbilities().size() > keybindSlot) {
                Ability ability = handler.getAvailableAbilities().get(keybindSlot);
                if (ability != null) {
                    player.sendSystemMessage(Component.literal("Ability Context: " + ability.getContext().toString()));
                    if (!handler.getEnabledAbilities().contains(ability))
                        enable(ability, player, keybindSlot);
                    else if (ability.getContext() == AbilityContext.TOGGLE)
                        disable(ability, player);
                }
            } else
                player.sendSystemMessage(Component.literal("There are not that many Abilities!"));
            player.sendSystemMessage(Component.literal("You pressed slot %s".formatted(keybindSlot)));
        } else
            player.sendSystemMessage(Component.literal("No Abilities!"));
        player.sendSystemMessage(Component.literal("Available Abilities: " + handler.getAvailableAbilities().size()));
        player.sendSystemMessage(Component.literal("Unlocked Abilities: " + handler.getUnlockedAbilities().size()));
        player.sendSystemMessage(Component.literal("Constant Abilities: " + handler.getConstantAbilities().size()));
        player.sendSystemMessage(Component.literal("Registered Abilities: " + ErrorRegistries.ABILITIES.getValues().size()));
    }

    @Override
    public void enable(Ability ability, Player player, int slot) {
        if (!MinecraftForge.EVENT_BUS.post(new AbilityEvent.AbilityEnabledEvent(player, this, ability))) {
            switch (ability.getContext()) {
                case INSTANT -> enableInstant(ability, player);
                case HOLD, TOGGLE -> enableDefault(ability, player, slot);
            }
        }
    }

    @Override
    public void enableDefault(Ability ability, Player player, int slot) {
        handler.getEnabledAbilities().set(slot, ability);
        ability.enable(player, player.level());
    }

    @Override
    public void enableInstant(Ability ability, Player player) {
        ability.enable(player, player.level());
        ability.tick(player, player.level());
        ability.disable(player, player.level());
    }

    @Override
    public void disable(Ability ability, Player player) {
        if (!MinecraftForge.EVENT_BUS.post(new AbilityEvent.AbilityDisabledEvent(player, this, ability))) {
            handler.getEnabledAbilities().remove(ability);
            ability.disable(player, player.level());
        }
    }

    @Override
    public CompoundTag saveData() {
        CompoundTag data = new CompoundTag();
        data.putString("owner", this.owner == null ? "" : this.owner);
        data.put("constant", handler.getConstantAbilitiesNBT());
        data.put("enabled", handler.getEnabledAbilitiesNBT());
        data.put("available", handler.getAvailableAbilitiesNBT());
        data.put("unlocked", handler.getUnlockedAbilitiesNBT());
        return data;
    }

    @Override
    public IAbilities loadData(CompoundTag data) {
        IAbilities fromNBT = fromNBT(data);
        return copyFrom(fromNBT);
    }

    public static IAbilities fromNBT(CompoundTag data) {
        Abilities abilities = new Abilities();
        abilities.owner = data.getString("owner");
        abilities.handler.loadConstantAbilitiesNBT(data.getCompound("constant"));
        abilities.handler.loadEnabledAbilitiesNBT(data.getCompound("enabled"));
        abilities.handler.loadAvailableAbilitiesNBT(data.getCompound("available"));
        abilities.handler.loadUnlockedAbilitiesNBT(data.getCompound("unlocked"));
        return abilities;
    }

    @Override
    public IAbilities copyFrom(IAbilities oldAbilities) {
        handler.loadAbilities(oldAbilities.getHandler());
        return this;
    }

    @Override
    public void encode(FriendlyByteBuf buf) throws IOException {
        NbtIo.write(saveData(), new ByteBufOutputStream(buf));
    }

    public static IAbilities decode(FriendlyByteBuf buf) throws IOException {
        CompoundTag nbt = NbtIo.read(new ByteBufInputStream(buf));
        return fromNBT(nbt);
    }
}
