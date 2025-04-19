package com.riverstone.unknown303.errorlib.api.abilities.origin;

import com.riverstone.unknown303.errorlib.api.abilities.misc.AbilityColor;
import com.riverstone.unknown303.errorlib.api.abilities.misc.AbilityContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface AbilityOrigin {
    AbilityContext getContext();
    AbilityColor getColor();

    boolean isAvailable(Player player, Level level);
}
