package com.riverstone.unknown303.errorlib.api.abilities.ability.origin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class InHandOrigin implements AbilityOrigin {
    private final ItemLike item;

    public InHandOrigin(ItemLike item) {
        this.item = item;
    }

    @Override
    public boolean isAvailable(Player player, Level level) {
        return player.isHolding(item.asItem());
    }
}
