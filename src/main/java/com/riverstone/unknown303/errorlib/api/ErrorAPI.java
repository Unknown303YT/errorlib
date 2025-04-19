package com.riverstone.unknown303.errorlib.api;

import com.riverstone.unknown303.errorlib.ErrorHelpers;
import com.riverstone.unknown303.errorlib.api.misc.ErrorRegistries;
import net.minecraftforge.eventbus.api.IEventBus;

public class ErrorAPI {
    public static void init(IEventBus eventBus) {
        ErrorRegistries.load();

        ErrorHelpers.REGISTRY_HELPER.register(eventBus);
    }
}
