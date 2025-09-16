package com.riverstone.unknown303.errorlib.api;

import com.mojang.logging.LogUtils;
import com.riverstone.unknown303.errorlib.ErrorHelpers;
import com.riverstone.unknown303.errorlib.api.misc.ErrorRegistries;
import com.riverstone.unknown303.errorlib.api.networking.ErrorPacketHandler;
import com.riverstone.unknown303.errorlib.api.subscribers.ErrorAPISubscriberHandler;
import com.riverstone.unknown303.errorlib.events.api.ForgeEvents;
import com.riverstone.unknown303.errorlib.events.api.ModEvents;
import com.riverstone.unknown303.errorlib.misc.ErrorKeybinds;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.MarkerFactory;

public class ErrorAPI {
    public static final org.slf4j.Marker ERROR_API_MARKER =
            MarkerFactory.getMarker("ERROR_API");


    private static final Logger LOGGER = LogUtils.getLogger();

    private static boolean shouldDebug = false;

    public static void enableDebugging() {
        shouldDebug = true;
    }

    public static boolean debuggingEnabled() {
        return shouldDebug;
    }

    @ApiStatus.Internal
    public static void init(IEventBus modEventBus) {
        ErrorHelpers.ERRORLIB_INFO.register(modEventBus);

        modEventBus.register(ModEvents.Common.class);
        modEventBus.register(ModEvents.Client.class);

        MinecraftForge.EVENT_BUS.register(ForgeEvents.Common.class);
        MinecraftForge.EVENT_BUS.register(ForgeEvents.Client.class);
        ErrorAPISubscriberHandler.gatherErrorLibSubscribers();
    }

    public static void load() {
        ErrorKeybinds.load();
        ErrorRegistries.load();
    }

    public static void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info(ERROR_API_MARKER, "Starting Common Setup for ErrorAPI");
        event.enqueueWork(ErrorPacketHandler::register);
        LOGGER.info(ERROR_API_MARKER, "Queued ErrorAPI Networking and Packets for Registration");
        LOGGER.info(ERROR_API_MARKER, "Common Setup for ErrorAPI complete");
    }

    public static void serverSetup(ServerStartingEvent event) {
        LOGGER.info(ERROR_API_MARKER, "Starting Server Setup for ErrorAPI");

        LOGGER.info(ERROR_API_MARKER, "Server Setup for ErrorAPI complete");
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        LOGGER.info(ERROR_API_MARKER, "Starting Client Setup for ErrorAPI");

        LOGGER.info(ERROR_API_MARKER, "Client Setup for ErrorAPI complete");
    }
}
