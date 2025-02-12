package com.riverstone.unknown303.errorlib.events;

import com.riverstone.unknown303.errorlib.ErrorHelpers;
import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.ability.Abilities;
import com.riverstone.unknown303.errorlib.api.ability.Ability;
import com.riverstone.unknown303.errorlib.api.misc.ErrorRegistries;
import com.riverstone.unknown303.errorlib.api.ability.PlayerAbilitiesProvider;
import com.riverstone.unknown303.errorlib.api.ability.custom.InvisibilityAbility;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ErrorMod.MOD_ID)
public class CommonEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (!player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).isPresent()) {
                event.addCapability(new ResourceLocation(ErrorMod.MOD_ID, "abilities"), new PlayerAbilitiesProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(Abilities.class);
    }

    @SubscribeEvent
    public static void playerVisibility(LivingEvent.LivingVisibilityEvent event) {
        if (event.getEntity() instanceof Player player) {
            LazyOptional<Abilities> ABILITIES_CAP = player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES);
            if (!ABILITIES_CAP.isPresent()) {
                return;
            }
            IllegalStateException exception = new IllegalStateException("Abilities Player Capability not available but is marked as present.");
            Abilities playerAbilities = ABILITIES_CAP.orElseThrow(() -> exception);

            if (playerAbilities.isAbilityEnabled(InvisibilityAbility.class)) {
                event.modifyVisibility(0D);
            }
        }
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Level level = player.level();

        player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {
            for (Ability ability : ErrorHelpers.REGISTRY_HELPER.getValidRegistrations(ErrorRegistries.ABILITIES.get())) {
                if (ability.isAvailable(player)) {
                    abilities.unlockAbility(ability);
                }
            }

            for (Ability ability : abilities.getEnabledAbilities()) {
                ability.tick(level, player);
            }
        });
    }
}
