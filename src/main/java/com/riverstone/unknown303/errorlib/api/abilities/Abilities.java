package com.riverstone.unknown303.errorlib.api.abilities;

import com.riverstone.unknown303.errorlib.api.abilities.misc.AbilityContext;
import com.riverstone.unknown303.errorlib.api.helpers.keybind.Keybind;
import com.riverstone.unknown303.errorlib.misc.ErrorKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.ApiStatus;

public class Abilities {
    private final AbilitiesHandler handler = new AbilitiesHandler();

    public void unlockAbility(Ability ability, Player player) {
        if (ability.getContext() == AbilityContext.CONSTANT) {
            handler.unlockConstant(ability);
            ability.enable(player, player.level());
            return;
        }
        handler.unlockAbility(ability);
    }

    @ApiStatus.Internal
    public void pressAbilityKeybind(Keybind keybind, Player player) {
        int slot = ErrorKeybinds.getAbilitySlot(keybind);
        enable(handler.getAvailableAbilities().get(slot), Minecraft.getInstance().player);
    }

    public void enable(Ability ability, Player player) {
        handler.getEnabledAbilities().add(handler.getAvailableAbilities().indexOf(ability),
                ability);
        ability.enable(player, player.level());
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
