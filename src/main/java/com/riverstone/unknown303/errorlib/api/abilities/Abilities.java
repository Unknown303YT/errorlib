package com.riverstone.unknown303.errorlib.api.abilities;

import com.riverstone.unknown303.errorlib.api.abilities.misc.AbilityContext;
import com.riverstone.unknown303.errorlib.api.event.AbilityEvent;
import com.riverstone.unknown303.errorlib.api.helpers.keybind.Keybind;
import com.riverstone.unknown303.errorlib.misc.ErrorKeybinds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.ApiStatus;

import java.util.Objects;

public class Abilities {
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
                handler.unlockConstant(ability, player, this, abilityUnlockedEvent);
                if (!MinecraftForge.EVENT_BUS.post(new AbilityEvent.AbilityEnabledEvent(player, this, ability)))
                    ability.enable(player, player.level());
                return;
            }
            handler.unlockAbility(ability, player, this, abilityUnlockedEvent);
        }
    }

    public void lockAbility(Ability ability, Player player) {
        if (!MinecraftForge.EVENT_BUS.post(new AbilityEvent.AbilityLockedEvent(player, this, ability))) {
            if (ability.getContext() == AbilityContext.CONSTANT) {
                handler.lockConstant(ability);
                if (!MinecraftForge.EVENT_BUS.post(new AbilityEvent.AbilityDisabledEvent(player, this, ability)))
                    ability.disable(player, player.level());
            } else handler.lockAbility(ability);
        }
    }

    @ApiStatus.Internal
    public void pressAbilityKeybind(Keybind keybind, Player player) {
        int slot = ErrorKeybinds.getAbilitySlot(keybind);
        Ability ability = handler.getAvailableAbilities().get(slot);
        if (!handler.getEnabledAbilities().contains(ability)) enable(ability, player, slot);
        else disable(ability, player);
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

    public Abilities loadData(CompoundTag data) {
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

    public Abilities copyFrom(Abilities oldAbilities) {
        handler.loadAbilities(oldAbilities.handler);
        return this;
    }
}
