package com.riverstone.unknown303.errorlib.api.abilities.ability.origin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

@FunctionalInterface
public interface AbilityOrigin {
    boolean isAvailable(Player player, Level level);
}
