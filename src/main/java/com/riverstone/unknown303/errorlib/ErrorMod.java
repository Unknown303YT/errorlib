package com.riverstone.unknown303.errorlib;

import com.mojang.logging.LogUtils;
import com.riverstone.unknown303.errorlib.abilities.ErrorAbilities;
import com.riverstone.unknown303.errorlib.api.ErrorAPI;
import com.riverstone.unknown303.errorlib.api.misc.API;
import com.riverstone.unknown303.errorlib.api.misc.ErrorRegistries;
import com.riverstone.unknown303.errorlib.events.ForgeEvents;
import com.riverstone.unknown303.errorlib.events.ModEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ErrorMod.MOD_ID)
public class ErrorMod extends API {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "errorlib";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public ErrorMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        ErrorAPI.enableDebugging();

        ErrorAPI.init(modEventBus);

        modEventBus.register(ModEvents.Client.class);
        modEventBus.register(ModEvents.Common.class);

        FORGE_EVENT_BUS.register(ForgeEvents.Client.class);
        FORGE_EVENT_BUS.register(ForgeEvents.Common.class);

        ErrorAbilities.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        FORGE_EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Starting Common Setup for ErrorLib");

        LOGGER.info("Common Setup for ErrorLib complete");
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Starting Server Setup for ErrorLib");

        LOGGER.info("Server Setup for ErrorLib complete");
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("Starting Client Setup for ErrorLib");

            LOGGER.info("Client Setup for ErrorLib complete");
        }
    }
}
