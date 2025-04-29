package com.riverstone.unknown303.errorlib.api.misc;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;

public class API {
    protected static IEventBus FORGE_EVENT_BUS = MinecraftForge.EVENT_BUS;

    /**
     * OVERRIDE THIS:
     */
    public static void init(IEventBus eventBus) {}
}
