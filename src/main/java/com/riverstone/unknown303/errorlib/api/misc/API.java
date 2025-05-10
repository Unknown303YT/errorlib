package com.riverstone.unknown303.errorlib.api.misc;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import org.jetbrains.annotations.ApiStatus;

public class API {
    protected static IEventBus FORGE_EVENT_BUS = MinecraftForge.EVENT_BUS;

    /**
     * OVERRIDE THIS:
     * @param modEventBus The mod api's {@linkplain IEventBus}.
     */
    @ApiStatus.OverrideOnly
    public static void init(IEventBus modEventBus) {}
}
