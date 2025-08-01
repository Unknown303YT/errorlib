package com.riverstone.unknown303.errorlib.api.abilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerAbilitiesProvider implements ICapabilitySerializable<CompoundTag> {
    public static Capability<IAbilities> PLAYER_ABILITIES = CapabilityManager.get(new CapabilityToken<>(){});

    private IAbilities abilities = null;
    private final LazyOptional<IAbilities> HOLDER = LazyOptional.of(this::getOrCreateAbilities);

    @ApiStatus.Internal
    public IAbilities getOrCreateAbilities() {
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
