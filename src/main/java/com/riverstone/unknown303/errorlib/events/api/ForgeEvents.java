package com.riverstone.unknown303.errorlib.events.api;

import com.riverstone.unknown303.errorlib.api.abilities.Abilities;
import com.riverstone.unknown303.errorlib.api.abilities.PlayerAbilitiesProvider;
import com.riverstone.unknown303.errorlib.api.misc.ErrorRegistries;
import com.riverstone.unknown303.errorlib.api.networking.ErrorPacketHandler;
import com.riverstone.unknown303.errorlib.api.networking.packets.AbilitiesDataSyncS2CPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
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
                    event.addCapability(Abilities.ABILITIES_ID, new PlayerAbilitiesProvider());
                }
            }
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            Player player = event.player;
            if (event.phase != TickEvent.Phase.START)
                return;
            if (event.side.isServer() &&
                player instanceof ServerPlayer serverPlayer) {
                player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {
                    ErrorRegistries.ABILITIES.getValues().forEach(ability -> {
                        if (ability.isAvailable(player, player.level()))
                            abilities.unlockAbility(ability, player);
                        else if (abilities.contains(ability))
                            abilities.lockAbility(ability, player);
                    });
                    abilities.tickAbilities(player);
                    ErrorPacketHandler.sendToAll(new AbilitiesDataSyncS2CPacket(abilities));
                });
            }
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
