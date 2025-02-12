package com.riverstone.unknown303.errorlib.api.ability;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerAbilitiesProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<Abilities> PLAYER_ABILITIES = CapabilityManager.get(new CapabilityToken<Abilities>() {});

    private Abilities abilities = null;
    private final LazyOptional<Abilities> optional = LazyOptional.of(this::getPlayerAbilities);

    private Abilities getPlayerAbilities() {
        if (this.abilities == null) {
            this.abilities = new Abilities();
        }

        return this.abilities;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_ABILITIES) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        CompoundTag unlocked = new CompoundTag();
        CompoundTag available = new CompoundTag();
        CompoundTag enabled = new CompoundTag();
        getPlayerAbilities().addAdditionalSaveData(unlocked, available, enabled);
        tag.put("unlocked", unlocked);
        tag.put("available", available);
        tag.put("enabled", enabled);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        CompoundTag unlocked = tag.getCompound("unlocked");
        CompoundTag available = tag.getCompound("available");
        CompoundTag enabled = tag.getCompound("enabled");
        getPlayerAbilities().loadAdditionalSaveData(unlocked, available, enabled);
    }
}
