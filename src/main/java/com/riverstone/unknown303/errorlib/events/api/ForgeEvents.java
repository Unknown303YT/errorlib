package com.riverstone.unknown303.errorlib.events.api;

import com.riverstone.unknown303.errorlib.api.abilities.Abilities;
import com.riverstone.unknown303.errorlib.api.abilities.ClientAbilitiesData;
import com.riverstone.unknown303.errorlib.api.abilities.PlayerAbilitiesProvider;
import com.riverstone.unknown303.errorlib.api.misc.ErrorRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeEvents {
    public static class Client {

    }

    public static class Common {
        // Abilities

        @SubscribeEvent
        public static void onAttachCaps(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player player) {
                if (!player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).isPresent()) {
                    PlayerAbilitiesProvider prov = new PlayerAbilitiesProvider();
                    prov.getOrCreateAbilities().setOwner(player);
                    event.addCapability(Abilities.ABILITIES_ID, new PlayerAbilitiesProvider());
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            Player player = event.player;
            if (ClientAbilitiesData.get(player) == null)
                player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {
                    ClientAbilitiesData.set(player, abilities);
                });
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {
                if (abilities != ClientAbilitiesData.get(player))
                    abilities.copyFrom(ClientAbilitiesData.get(player));
            });
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {
                ErrorRegistries.ABILITIES.getValues().forEach(ability -> {
                    if (ability.isAvailable(player, player.level()))
                        abilities.unlockAbility(ability, player);
                    else if (abilities.contains(ability))
                        abilities.lockAbility(ability, player);
                });
                abilities.tickAbilities(player);
            });
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            event.getEntity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES)
                    .ifPresent(newStore -> event.getOriginal().getCapability(
                            PlayerAbilitiesProvider.PLAYER_ABILITIES)
                            .ifPresent(newStore::copyFrom));
        }
    }
}
