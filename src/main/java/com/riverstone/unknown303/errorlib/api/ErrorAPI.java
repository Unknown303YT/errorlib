package com.riverstone.unknown303.errorlib.api;

import com.riverstone.unknown303.errorlib.ErrorHelpers;
import com.riverstone.unknown303.errorlib.api.misc.API;
import com.riverstone.unknown303.errorlib.api.misc.ErrorRegistries;
import com.riverstone.unknown303.errorlib.events.api.ForgeEvents;
import com.riverstone.unknown303.errorlib.events.api.ModEvents;
import com.riverstone.unknown303.errorlib.misc.ErrorKeybinds;
import net.minecraftforge.eventbus.api.IEventBus;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.jetbrains.annotations.ApiStatus;

public class ErrorAPI extends API {
    public static final Marker ERROR_API = MarkerManager.getMarker("ERROR_API");

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

        FORGE_EVENT_BUS.register(ForgeEvents.Common.class);
        FORGE_EVENT_BUS.register(ForgeEvents.Client.class);
    }

    public static void load() {
        ErrorKeybinds.load();
        ErrorRegistries.load();
    }
}
