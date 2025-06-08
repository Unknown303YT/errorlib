package com.riverstone.unknown303.errorlib.api.abilities;

import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.abilities.ability.Ability;
import com.riverstone.unknown303.errorlib.api.abilities.ability.misc.AbilityContext;
import com.riverstone.unknown303.errorlib.api.event.AbilityEvent;
import com.riverstone.unknown303.errorlib.api.helpers.keybind.Keybind;
import com.riverstone.unknown303.errorlib.api.misc.ErrorRegistries;
import com.riverstone.unknown303.errorlib.misc.ErrorKeybinds;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.ApiStatus;

import java.io.*;

public class Abilities implements IAbilities {
    public static final ResourceLocation ABILITIES_ID =
            ResourceLocation.fromNamespaceAndPath(ErrorMod.MOD_ID, "abilities_properties");

    private final AbilitiesHandler handler = new AbilitiesHandler();
    private volatile boolean isDown;

    public AbilitiesHandler getHandler() {
        return this.handler;
    }

    @ApiStatus.Internal
    public void tickAbilities(Player player) {
        handler.getEnabledAbilities().forEach(ability -> {
            if (!MinecraftForge.EVENT_BUS.post(new AbilityEvent.AbilityTickEvent(player, this, ability)))
                ability.tick(player, player.level());
        });
    }

    public void unlockAbility(Ability ability, Player player) {
        AbilityEvent.AbilityUnlockedEvent abilityUnlockedEvent = new AbilityEvent.AbilityUnlockedEvent(player, this, ability);
        if (!MinecraftForge.EVENT_BUS.post(abilityUnlockedEvent)) {
            if (ability.getContext() == AbilityContext.CONSTANT){
                handler.unlockConstant(ability, abilityUnlockedEvent);
                if (!MinecraftForge.EVENT_BUS.post(new AbilityEvent.AbilityEnabledEvent(player, this, ability)))
                    ability.enable(player, player.level());
                return;
            }
            handler.unlockAbility(ability);
        }
    }

    public void lockAbility(Ability ability, Player player) {
        if (!MinecraftForge.EVENT_BUS.post(new AbilityEvent.AbilityLockedEvent(player, this, ability))) {
            if (ability.getContext() == AbilityContext.CONSTANT) {
                handler.lockConstant(ability);
                if (!MinecraftForge.EVENT_BUS.post(new AbilityEvent.AbilityDisabledEvent(player, this, ability)))
                    ability.disable(player, player.level());
            } else {
                if (handler.getEnabledAbilities().contains(ability))
                    disable(ability, player);
                handler.lockAbility(ability);
            }
        }
    }

    @ApiStatus.Internal
    public void pressAbilityKeybind(int keybindSlot, Player player) {
        if (!handler.getAvailableAbilities().isEmpty()) {
            if (handler.getAvailableAbilities().size() > keybindSlot) {
                Ability ability = handler.getAvailableAbilities().get(keybindSlot);
                if (!handler.getEnabledAbilities().contains(ability)) enable(ability, player, keybindSlot);
                else disable(ability, player);
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

    public void enable(Ability ability, Player player, int slot) {
        if (!MinecraftForge.EVENT_BUS.post(new AbilityEvent.AbilityEnabledEvent(player, this, ability))) {
            switch (ability.getContext()) {
                case TOGGLE -> enableToggle(ability, player, slot);
                case HOLD -> enableHold(ability, player, slot);
                case INSTANT -> enableInstant(ability, player);
            }
        }
    }

    public void enableToggle(Ability ability, Player player, int slot) {
        handler.getEnabledAbilities().add(slot, ability);
        ability.enable(player, player.level());
    }

    public void enableHold(Ability ability, Player player, int slot) {
        handler.getEnabledAbilities().add(slot, ability);
        ability.enable(player, player.level());
        do isDown = ErrorKeybinds.getAbilitySlotKeybind(slot).getKeyMapping().isDown();
        while (isDown);
        if (!MinecraftForge.EVENT_BUS.post(new AbilityEvent.AbilityDisabledEvent(player, this, ability))) {
            ability.disable(player, player.level());
            handler.getEnabledAbilities().remove(ability);
        }
    }

    public void enableInstant(Ability ability, Player player) {
        ability.enable(player, player.level());
        if (MinecraftForge.EVENT_BUS.post(new AbilityEvent.AbilityTickEvent(player, this, ability)))
            ability.tick(player, player.level());
        if (MinecraftForge.EVENT_BUS.post(new AbilityEvent.AbilityDisabledEvent(player, this, ability)))
            ability.disable(player, player.level());
    }

    public void disable(Ability ability, Player player) {
        if (!MinecraftForge.EVENT_BUS.post(new AbilityEvent.AbilityDisabledEvent(player, this, ability))) {
            handler.getEnabledAbilities().remove(ability);
            ability.disable(player, player.level());
        }
    }

    public CompoundTag saveData() {
        CompoundTag data = new CompoundTag();
        data.put("constant", handler.getConstantAbilitiesNBT());
        data.put("enabled", handler.getEnabledAbilitiesNBT());
        data.put("available", handler.getAvailableAbilitiesNBT());
        data.put("unlocked", handler.getUnlockedAbilitiesNBT());
        return data;
    }

    public IAbilities loadData(CompoundTag data) {
        Abilities fromNBT = fromNBT(data);
        return copyFrom(fromNBT);
    }

    public static Abilities fromNBT(CompoundTag data) {
        Abilities abilities = new Abilities();
        abilities.handler.loadConstantAbilitiesNBT(data.getCompound("constant"));
        abilities.handler.loadEnabledAbilitiesNBT(data.getCompound("enabled"));
        abilities.handler.loadAvailableAbilitiesNBT(data.getCompound("available"));
        abilities.handler.loadUnlockedAbilitiesNBT(data.getCompound("unlocked"));
        return abilities;
    }

    public IAbilities copyFrom(IAbilities oldAbilities) {
        handler.loadAbilities(oldAbilities.getHandler());
        return this;
    }

    @Override
    public byte[] encode() throws IOException {
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
        objectOutput.writeObject(this);
        objectOutput.close();
        return byteOutput.toByteArray();
    }

    public static Abilities decode(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteInput = new ByteArrayInputStream(data);
        ObjectInputStream objectInput = new ObjectInputStream(byteInput);
        if (objectInput.readObject() instanceof Abilities abilities)
            return abilities;
        IllegalArgumentException exception = new IllegalArgumentException("Byte Array provided not Abilities!");
        Minecraft.crash(CrashReport.forThrowable(exception, exception.getMessage()));
        throw new RuntimeException(exception);
    }
}
