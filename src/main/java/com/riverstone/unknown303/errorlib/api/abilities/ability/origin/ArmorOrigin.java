package com.riverstone.unknown303.errorlib.api.abilities.ability.origin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class ArmorOrigin implements AbilityOrigin {
    private final ItemLike item;

    public ArmorOrigin(ItemLike item) {
        this.item = item;
    }

    @Override
    public boolean isAvailable(Player player, Level level) {
        return player.getInventory().armor.contains(new ItemStack(item));
    }
}
