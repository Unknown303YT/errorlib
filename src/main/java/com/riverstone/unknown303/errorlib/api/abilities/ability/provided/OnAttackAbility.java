package com.riverstone.unknown303.errorlib.api.abilities.ability.provided;

import com.riverstone.unknown303.errorlib.api.abilities.ability.Ability;
import com.riverstone.unknown303.errorlib.api.misc.EasyTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.function.Consumer;

public class OnAttackAbility extends Ability {
    private final OnAttack onAttack;

    public OnAttackAbility(Properties properties, OnAttack onAttack) {
        super(properties);
        this.onAttack = onAttack;
    }

    @Override
    public void enable(Player player, Level level) {}

    @Override
    public void tick(Player player, Level level) {}

    @Override
    public void disable(Player player, Level level) {}

    @Override
    public Consumer<IEventBus> registerForgeEvents() {
        return eventBus ->
                eventBus.addListener(this::onAttack);
    }

    public void onAttack(AttackEntityEvent event) {
        onAttack.run(event.getEntity().level(),
                event.getEntity(), event.getTarget());
    }

    @Override
    public void saveAdditional(EasyTag tag) {}

    @Override
    public void loadAdditonal(EasyTag tag) {}

    @FunctionalInterface
    public interface OnAttack {
        void run(Level level, Player attacker, Entity target);
    }
}
