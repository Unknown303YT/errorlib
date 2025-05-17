package com.riverstone.unknown303.errorlib.events.api;

import com.riverstone.unknown303.errorlib.api.abilities.PlayerAbilitiesProvider;
import com.riverstone.unknown303.errorlib.api.misc.ErrorRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeEvents {
    public static class Client {

    }

    public static class Common {
        // Abilities

        @SubscribeEvent
        public static void playerTick(TickEvent.PlayerTickEvent event) {
            Player player = event.player;
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {
                ErrorRegistries.ABILITIES.getValues().forEach(ability -> {
                    if (ability.isAvailable(player, player.level())) abilities.unlockAbility(ability, player);
                    else abilities.lockAbility(ability, player);
                });
                abilities.tickAbilities(player);
            });
        }
    }
}
