package com.riverstone.unknown303.errorlib.api.abilities.provided;

import com.riverstone.unknown303.errorlib.api.abilities.Ability;
import com.riverstone.unknown303.errorlib.api.abilities.origin.AbilityOrigin;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class CodeOnAttackAbility extends Ability {
    private final OnAttack onAttack;

    public CodeOnAttackAbility(AbilityOrigin origin, OnAttack onAttack) {
        super(origin);
        this.onAttack = onAttack;
    }

    @Override
    public void enable(Player player, Level level) {}

    @Override
    public void tick(Player player, Level level) {}

    @Override
    public void disable(Player player, Level level) {}

    public OnAttack getOnAttack() {
        return onAttack;
    }

    @FunctionalInterface
    public interface OnAttack {
        void run(Player attacker, Entity target);
    }
}
