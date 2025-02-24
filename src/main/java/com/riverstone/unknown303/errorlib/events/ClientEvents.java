package com.riverstone.unknown303.errorlib.events;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.ability.Abilities;
import com.riverstone.unknown303.errorlib.api.ability.PlayerAbilitiesProvider;
import com.riverstone.unknown303.errorlib.api.ability.custom.InvisibilityAbility;
import com.riverstone.unknown303.errorlib.misc.ErrorKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.checkerframework.checker.units.qual.A;

import javax.swing.text.JTextComponent;
import java.util.Objects;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = ErrorMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void renderEntity(RenderLivingEvent.Pre<?, ?> event) {
            if (event.getEntity() instanceof Player player) {
                player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {
                    if (abilities.isAbilityEnabled(InvisibilityAbility.class)) {
                        event.setCanceled(true);
                    }
                });
            }
        }

        @SubscribeEvent
        public static void keyPressed(InputEvent.Key event) {
            Player player = Objects.requireNonNull(Minecraft.getInstance().player);
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {
                abilities.delegateAbilityKeybinds(player);
            });
        }
    }

    @Mod.EventBusSubscriber(modid = ErrorMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerKeys(RegisterKeyMappingsEvent event) {
            event.register(ErrorKeybinds.KEY_ABILITY_SLOT_0);
            event.register(ErrorKeybinds.KEY_ABILITY_SLOT_1);
            event.register(ErrorKeybinds.KEY_ABILITY_SLOT_2);
            event.register(ErrorKeybinds.KEY_ABILITY_SLOT_4);
        }
    }
}
