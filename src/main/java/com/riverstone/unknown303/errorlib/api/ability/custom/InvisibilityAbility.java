package com.riverstone.unknown303.errorlib.api.ability.custom;

import com.riverstone.unknown303.errorlib.api.ability.Ability;
import com.riverstone.unknown303.errorlib.api.ability.origin.AbilityOrigin;
import com.riverstone.unknown303.errorlib.api.helpers.component.ComponentHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class InvisibilityAbility extends Ability {
    public InvisibilityAbility(AbilityOrigin origin) {
        super(origin);
    }

    @Override
    public void enable(Level level, Player owner) {}

    @Override
    public void tick(Level level, Player owner) {}

    @Override
    public void disable(Level level, Player owner) {}
}
