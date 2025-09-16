package com.riverstone.unknown303.errorlib.api.abilities.ability.origin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class InHandOrigin implements AbilityOrigin {
    private final Ingredient ingredient;

    public InHandOrigin(ItemLike item) {
        this(Ingredient.of(item));
    }

    public InHandOrigin(ItemStack stack) {
        this(Ingredient.of(stack));
    }

    public InHandOrigin(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public boolean isAvailable(Player player, Level level) {
        return player.isHolding(stack -> ingredient.test(stack));
    }
}
