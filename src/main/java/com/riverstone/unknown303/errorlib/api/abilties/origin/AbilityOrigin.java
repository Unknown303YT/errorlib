package com.riverstone.unknown303.errorlib.api.abilties.origin;

import com.riverstone.unknown303.errorlib.api.abilties.AbilityColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface AbilityOrigin {
    AbilityColor getColor();

    boolean isAvailable(Player player, Level level);
}
