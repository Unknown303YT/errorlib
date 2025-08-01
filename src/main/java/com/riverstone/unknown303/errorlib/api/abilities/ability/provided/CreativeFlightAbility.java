package com.riverstone.unknown303.errorlib.api.abilities.ability.provided;

import com.riverstone.unknown303.errorlib.api.abilities.ability.Ability;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class CreativeFlightAbility extends Ability {
    public CreativeFlightAbility(Properties properties) {
        super(properties);
    }

    @Override
    public void enable(Player player, Level level) {
        player.getAbilities().mayfly = true;
        player.getAbilities().flying = true;
        player.onUpdateAbilities();
    }

    @Override
    public void tick(Player player, Level level) {
        player.getAbilities().mayfly = true;
        player.getAbilities().flying = true;
        player.onUpdateAbilities();
    }

    @Override
    public void disable(Player player, Level level) {
        MinecraftServer server = player.getServer();
        if (server == null) return;
        player.getAbilities().flying = false;
        player.getAbilities().mayfly = false;
        player.onUpdateAbilities();
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.setGameMode(serverPlayer.gameMode.getGameModeForPlayer());
        }
        player.onUpdateAbilities();
    }
}
