package com.riverstone.unknown303.errorlib.api.abilties.origin;

import com.riverstone.unknown303.errorlib.api.abilties.AbilityColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class ArmorOrigin implements AbilityOrigin {
    private final AbilityColor color;
    private final ItemLike item;

    public ArmorOrigin(AbilityColor color, ItemLike item) {
        this.color = color;
        this.item = item;
    }

    @Override
    public AbilityColor getColor() {
        return this.color;
    }

    @Override
    public boolean isAvailable(Player player, Level level) {
        return player.getInventory().armor.contains(new ItemStack(item));
    }
}
