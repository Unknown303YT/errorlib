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
                LazyOptional<Abilities> ABILITIES_CAP = player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES);
                if (!ABILITIES_CAP.isPresent()) {
                    return;
                }
                IllegalStateException exception = new IllegalStateException("Abilities Player Capability not available but is marked as present.");
                Abilities playerAbilities = ABILITIES_CAP.orElseThrow(() -> exception);

                if (playerAbilities.isAbilityEnabled(InvisibilityAbility.class)) {
                    event.setCanceled(true);
                }
            }
        }

        @SubscribeEvent
        public static void keyPressed(InputEvent.Key event) {
            Player player = Objects.requireNonNull(Minecraft.getInstance().player);
            Abilities playerAbilities = player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES)
                    .orElseThrow(() -> new IllegalStateException("Player " + player.getName().getString() + " has no Abilities"));

            switch (playerAbilities.getEnabledAbilities().size()) {
                case 1 -> {
                    if (ErrorKeybinds.KEY_ABILITY_SLOT_0.consumeClick()) {
                        playerAbilities.toggleAbility(playerAbilities.getAvailableAbilities().get(0));
                    }
                } case 2 -> {
                    if (ErrorKeybinds.KEY_ABILITY_SLOT_0.consumeClick()) {
                        playerAbilities.toggleAbility(playerAbilities.getAvailableAbilities().get(0));
                    } else if (ErrorKeybinds.KEY_ABILITY_SLOT_1.consumeClick()) {
                        playerAbilities.toggleAbility(playerAbilities.getAvailableAbilities().get(1));
                    }
                } case 3 -> {
                    if (ErrorKeybinds.KEY_ABILITY_SLOT_0.consumeClick()) {
                        playerAbilities.toggleAbility(playerAbilities.getAvailableAbilities().get(0));
                    } else if (ErrorKeybinds.KEY_ABILITY_SLOT_1.consumeClick()) {
                        playerAbilities.toggleAbility(playerAbilities.getAvailableAbilities().get(1));
                    } else if (ErrorKeybinds.KEY_ABILITY_SLOT_2.consumeClick()) {
                        playerAbilities.toggleAbility(playerAbilities.getAvailableAbilities().get(2));
                    }
                } case 4 -> {
                    if (ErrorKeybinds.KEY_ABILITY_SLOT_0.consumeClick()) {
                        playerAbilities.toggleAbility(playerAbilities.getAvailableAbilities().get(0));
                    } else if (ErrorKeybinds.KEY_ABILITY_SLOT_1.consumeClick()) {
                        playerAbilities.toggleAbility(playerAbilities.getAvailableAbilities().get(1));
                    } else if (ErrorKeybinds.KEY_ABILITY_SLOT_2.consumeClick()) {
                        playerAbilities.toggleAbility(playerAbilities.getAvailableAbilities().get(2));
                    } else if (ErrorKeybinds.KEY_ABILITY_SLOT_3.consumeClick()) {
                        playerAbilities.toggleAbility(playerAbilities.getAvailableAbilities().get(3));
                    }
                } case 5 -> {
                    if (ErrorKeybinds.KEY_ABILITY_SLOT_0.consumeClick()) {
                        playerAbilities.toggleAbility(playerAbilities.getAvailableAbilities().get(0));
                    } else if (ErrorKeybinds.KEY_ABILITY_SLOT_1.consumeClick()) {
                        playerAbilities.toggleAbility(playerAbilities.getAvailableAbilities().get(1));
                    } else if (ErrorKeybinds.KEY_ABILITY_SLOT_2.consumeClick()) {
                        playerAbilities.toggleAbility(playerAbilities.getAvailableAbilities().get(2));
                    } else if (ErrorKeybinds.KEY_ABILITY_SLOT_3.consumeClick()) {
                        playerAbilities.toggleAbility(playerAbilities.getAvailableAbilities().get(3));
                    } else if (ErrorKeybinds.KEY_ABILITY_SLOT_4.consumeClick()) {
                        playerAbilities.toggleAbility(playerAbilities.getAvailableAbilities().get(4));
                    }
                }
            }
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
