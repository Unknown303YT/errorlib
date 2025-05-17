package com.riverstone.unknown303.errorlib.api.abilities.provided;

import com.riverstone.unknown303.errorlib.api.abilities.Ability;
import com.riverstone.unknown303.errorlib.api.abilities.origin.AbilityOrigin;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import java.util.List;

public class OnAttackAbility extends Ability {
    private final OnAttack onAttackCode;

    public OnAttackAbility(AbilityOrigin origin, OnAttack onAttack) {
        super(origin);
        this.onAttackCode = onAttack;
    }

    @Override
    public void enable(Player player, Level level) {}

    @Override
    public void tick(Player player, Level level) {}

    @Override
    public void disable(Player player, Level level) {}

    @Override
    public List<EventContainer<?>> getForgeEvents() {
        return List.of(new EventContainer<>(this::onAttack));
    }

    public void onAttack(AttackEntityEvent event) {
        onAttackCode.run(event.getEntity().level(), event.getEntity(), event.getTarget());
    }

    @FunctionalInterface
    public interface OnAttack {
        void run(Level level, Player attacker, Entity target);
    }
}
