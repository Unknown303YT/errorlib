package com.riverstone.unknown303.errorlib.api.abilities.origin;

import com.riverstone.unknown303.errorlib.api.abilities.misc.AbilityColor;
import com.riverstone.unknown303.errorlib.api.abilities.misc.AbilityContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class ArmorOrigin implements AbilityOrigin {
    private final AbilityColor color;
    private final AbilityContext context;
    private final ItemLike item;

    public ArmorOrigin(AbilityColor color, AbilityContext context,
                       ItemLike item) {
        this.color = color;
        this.context = context;
        this.item = item;
    }

    @Override
    public AbilityContext getContext() {
        return this.context;
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
