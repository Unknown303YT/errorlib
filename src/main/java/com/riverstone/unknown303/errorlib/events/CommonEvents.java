package com.riverstone.unknown303.errorlib.events;

import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.abilities.PlayerAbilitiesProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class CommonEvents {
    @Mod.EventBusSubscriber(modid = ErrorMod.MOD_ID)
    public static class Forge {
        @SubscribeEvent
        public static void onPlayerAttacks(AttackEntityEvent event) {
            Player player = event.getEntity();
            Entity entity = event.getTarget();
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {

            });
        }
    }
}
