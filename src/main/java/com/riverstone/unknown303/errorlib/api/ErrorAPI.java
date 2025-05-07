package com.riverstone.unknown303.errorlib.api;

import com.riverstone.unknown303.errorlib.ErrorHelpers;
import com.riverstone.unknown303.errorlib.api.misc.API;
import com.riverstone.unknown303.errorlib.api.misc.ErrorRegistries;
import com.riverstone.unknown303.errorlib.events.api.ForgeEvents;
import com.riverstone.unknown303.errorlib.events.api.ModEvents;
import net.minecraftforge.eventbus.api.IEventBus;

public class ErrorAPI extends API {
    private static boolean shouldDebug = false;

    public static void enableDebugging() {
        shouldDebug = true;
    }

    public static boolean debuggingEnabled() {
        return shouldDebug;
    }

    public static void init(IEventBus modEventBus) {
        ErrorHelpers.ERRORLIB_INFO.modEventBus(modEventBus);

        ErrorRegistries.load();

        modEventBus.register(ModEvents.Common.class);
        modEventBus.register(ModEvents.Client.class);

        FORGE_EVENT_BUS.register(ForgeEvents.Common.class);
        FORGE_EVENT_BUS.register(ForgeEvents.Client.class);

        ErrorHelpers.ERRORLIB_INFO.register();
    }
}
