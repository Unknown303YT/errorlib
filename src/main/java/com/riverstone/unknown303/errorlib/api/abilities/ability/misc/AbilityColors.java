package com.riverstone.unknown303.errorlib.api.abilities.ability.misc;

import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.abilities.ability.Ability;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public enum AbilityColors implements AbilityColor {
    NO_COLOR("no_color"),
    RED("red"),
    ORANGE("orange"),
    YELLOW("yellow"),
    LIGHT_GREEN("light_green"),
    DARK_GREEN("dark_green"),
    CYAN("cyan"),
    LIGHT_BLUE("light_blue"),
    DARK_BLUE("dark_blue"),
    PURPLE("purple"),
    MAGENTA("magenta"),
    PINK("pink"),
    BROWN("brown"),
    WHITE("white"),
    LIGHT_GREY("light_grey"),
    DARK_GREY("dark_grey"),
    BLACK("black");

    @Nullable
    private final ResourceLocation texture;

    AbilityColors(@Nullable String tex) {
        this.texture = ResourceLocation.fromNamespaceAndPath(ErrorMod.MOD_ID,
                "textures/ability/border/%s.png".formatted(tex));
    }

    @Nullable
    @Override
    public ResourceLocation textureLocation() {
        return this.texture;
    }
}
