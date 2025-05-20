package com.riverstone.unknown303.errorlib.api.abilities;

import com.riverstone.unknown303.errorlib.api.helpers.keybind.Keybind;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.io.Serializable;

public interface IAbilities extends Serializable {
    @ApiStatus.Internal
    void tickAbilities(Player player);

    void unlockAbility(Ability ability, Player player);

    void lockAbility(Ability ability, Player player);

    @ApiStatus.Internal
    void pressAbilityKeybind(Keybind keybind, Player player);

    void enable(Ability ability, Player player, int slot);

    void enableToggle(Ability ability, Player player, int slot);

    void enableHold(Ability ability, Player player, int slot);

    void enableInstant(Ability ability, Player player);

    void disable(Ability ability, Player player);

    CompoundTag saveData();

    Abilities loadData(CompoundTag data);

    Abilities copyFrom(Abilities oldAbilities);

    @ApiStatus.Internal
    byte[] encode() throws IOException;
}
