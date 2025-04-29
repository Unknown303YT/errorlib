package com.riverstone.unknown303.errorlib.api.abilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerAbilitiesProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<Abilities> PLAYER_ABILITIES = CapabilityManager.get(new CapabilityToken<>(){});

    private Abilities abilities = null;
    private final LazyOptional<Abilities> HOLDER = LazyOptional.of(this::getOrCreateAbilities);

    private Abilities getOrCreateAbilities() {
        if (this.abilities == null)
            this.abilities = new Abilities();
        return this.abilities;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return PLAYER_ABILITIES.orEmpty(cap, HOLDER);
    }

    @Override
    public CompoundTag serializeNBT() {
        return getOrCreateAbilities().saveData();
    }

    @Override
    public void deserializeNBT(CompoundTag data) {
        abilities = getOrCreateAbilities().loadData(data);
    }
}
