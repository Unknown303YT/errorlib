package com.riverstone.unknown303.errorlib.api.abilties;

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
    private final LazyOptional<Abilities> optional = LazyOptional.of(this::getPlayerAbilities);

    private Abilities getPlayerAbilities() {
        if (this.abilities == null)
            this.abilities = new Abilities();
        return this.abilities;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_ABILITIES)
            return optional.cast();
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {

    }
}
