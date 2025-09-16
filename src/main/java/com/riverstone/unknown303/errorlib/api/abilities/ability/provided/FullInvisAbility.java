package com.riverstone.unknown303.errorlib.api.abilities.ability.provided;

import com.riverstone.unknown303.errorlib.api.abilities.ability.Ability;
import com.riverstone.unknown303.errorlib.api.abilities.ability.origin.AbilityOrigin;
import com.riverstone.unknown303.errorlib.api.misc.EasyTag;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.function.Consumer;

public class FullInvisAbility extends Ability {
    public FullInvisAbility(Properties properties) {
        super(properties);
    }

    @Override
    public void enable(Player player, Level level) {}

    @Override
    public void tick(Player player, Level level) {}

    @Override
    public void disable(Player player, Level level) {}

    @Override
    public Consumer<IEventBus> registerForgeEvents() {
        return eventBus -> {
            eventBus.addListener(this::renderEntity);
            eventBus.addListener(this::entityVisibility);
            eventBus.addListener(this::renderArm);
            eventBus.addListener(this::renderHand);
        };
    }

    private void renderEntity(RenderLivingEvent<?, ?> event) {
        if (event.getEntity() instanceof Player player)
            if (Ability.isEnabled(player, this)) event.setCanceled(true);
    }

    private void entityVisibility(LivingEvent.LivingVisibilityEvent event) {
        if (event.getEntity() instanceof Player player)
            if (isEnabled(player)) event.modifyVisibility(0D);
    }

    private void renderArm(RenderArmEvent event) {
        if (isEnabled(event.getPlayer())) event.setCanceled(true);
    }

    private void renderHand(RenderHandEvent event) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            if (isEnabled(player)) event.setCanceled(true);
        }
    }

    @Override
    public void saveAdditional(EasyTag tag) {}

    @Override
    public void loadAdditonal(EasyTag tag) {}
}
