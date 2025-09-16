package com.riverstone.unknown303.errorlib.api.abilities.ability.misc;

import net.minecraft.resources.ResourceLocation;

public interface AbilityColor {
    String colorName();
    ResourceLocation textureLocation();

    static AbilityColor of(String modId, String colorName) {
        if (colorName == null) return of();
        return new AbilityColor() {
            @Override
            public String colorName() {
                return colorName;
            }

            @Override
            public ResourceLocation textureLocation() {
                return ResourceLocation.fromNamespaceAndPath(modId,
                        "textures/ability/border/%s.png".formatted(colorName));
            }
        };
    }

    static AbilityColor of() {
        return new AbilityColor() {
            @Override
            public String colorName() {
                return "";
            }

            @Override
            public ResourceLocation textureLocation() {
                return null;
            }
        };
    }
}
