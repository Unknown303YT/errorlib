package com.riverstone.unknown303.errorlib.api.abilities.ability.misc;

import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

@FunctionalInterface
public interface AbilityColor {
    @Nullable
    ResourceLocation textureLocation();

    static AbilityColor of(String modId, String colorId) {
        if (colorId == null) return of();
        return () -> ResourceLocation.fromNamespaceAndPath(modId,
                "textures/ability/border/%s.png".formatted(colorId));
    }

    static AbilityColor of() {
        return () -> null;
    }
}
