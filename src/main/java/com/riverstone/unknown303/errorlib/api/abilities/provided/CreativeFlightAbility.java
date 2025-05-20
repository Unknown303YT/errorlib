package com.riverstone.unknown303.errorlib.api.abilities.provided;

import com.riverstone.unknown303.errorlib.api.abilities.Ability;
import com.riverstone.unknown303.errorlib.api.abilities.origin.AbilityOrigin;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;

public class CreativeFlightAbility extends Ability {
    private final boolean letDownSlowly;

    public CreativeFlightAbility(AbilityOrigin origin, boolean letDownSlowly) {
        super(origin);
        this.letDownSlowly = letDownSlowly;
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
        if (player.getServer().getDefaultGameType() != GameType.CREATIVE ||
                player.getServer().getDefaultGameType() != GameType.SPECTATOR
                ) {
            if (letDownSlowly)
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 120));
            player.getAbilities().flying = false;
            player.getAbilities().mayfly = false;
            player.onUpdateAbilities();
        }
    }
}
