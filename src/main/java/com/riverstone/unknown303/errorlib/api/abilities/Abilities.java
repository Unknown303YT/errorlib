package com.riverstone.unknown303.errorlib.api.abilities;

import net.minecraft.nbt.CompoundTag;

public class Abilities {
    private final AbilitiesHandler handler = new AbilitiesHandler();

    public CompoundTag saveData() {
        CompoundTag data = new CompoundTag();
        data.put("constant", handler.getConstantAbilitiesNBT());
        data.put("enabled", handler.getEnabledAbilitiesNBT());
        data.put("available", handler.getAvailableAbilitiesNBT());
        data.put("unlocked", handler.getUnlockedAbilitiesNBT());
        return data;
    }

    public Abilities loadData(CompoundTag data) {
        Abilities nbt = fromNBT(data);
        copyFrom(nbt);
        return this;
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
