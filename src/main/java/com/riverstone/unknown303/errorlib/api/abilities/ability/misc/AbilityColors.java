package com.riverstone.unknown303.errorlib.api.abilities.ability.misc;

import com.riverstone.unknown303.errorlib.ErrorMod;
import net.minecraft.resources.ResourceLocation;

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

    private final String name;
    private final ResourceLocation texture;

    AbilityColors(String tex) {
        this.name = tex.toUpperCase();
        this.texture = ResourceLocation.fromNamespaceAndPath(ErrorMod.MOD_ID,
                "textures/ability/border/%s.png".formatted(tex));
    }

    @Override
    public String colorName() {
        return this.name;
    }

    @Override
    public ResourceLocation textureLocation() {
        return this.texture;
    }
}
