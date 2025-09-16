package com.riverstone.unknown303.errorlib.api.abilities.ability.origin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class ArmorOrigin implements AbilityOrigin {
    private final Ingredient ingredient;

    public ArmorOrigin(ItemLike item) {
        this(Ingredient.of(item));
    }

    public ArmorOrigin(ItemStack stack) {
        this(Ingredient.of(stack));
    }

    public ArmorOrigin(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public boolean isAvailable(Player player, Level level) {
        for (ItemStack stack : player.getInventory().armor)
            if (ingredient.test(stack))
                return true;

        return false;
    }
}
