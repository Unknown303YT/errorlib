package com.riverstone.unknown303.errorlib;

import com.mojang.logging.LogUtils;
import com.riverstone.unknown303.errorlib.abilities.ErrorAbilities;
import com.riverstone.unknown303.errorlib.api.ErrorAPI;
import com.riverstone.unknown303.errorlib.events.ForgeEvents;
import com.riverstone.unknown303.errorlib.events.ModEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;

@Mod(ErrorMod.MOD_ID)
public class ErrorMod {
    public static final String MOD_ID = "errorlib";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static IEventBus modEventBus;

    public ErrorMod(FMLJavaModLoadingContext context) {
        modEventBus = context.getModEventBus();
        ErrorAPI.enableDebugging();

        ErrorAPI.load();

        LOGGER.debug("LIST OF REGISTRABLE HELPERS: " +
                ErrorHelpers.ERRORLIB_INFO.getRegistrableHelpers().size());


        ErrorAPI.init(modEventBus);

        modEventBus.register(ModEvents.Client.class);
        modEventBus.register(ModEvents.Common.class);

        MinecraftForge.EVENT_BUS.register(ForgeEvents.Client.class);
        MinecraftForge.EVENT_BUS.register(ForgeEvents.Common.class);

        ErrorAbilities.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.addListener(this::serverSetup);
    }

    @ApiStatus.Internal
    public static IEventBus eventBus() {
        return modEventBus;
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        ErrorAPI.commonSetup(event);
        LOGGER.info("Starting Common Setup for ErrorLib");

        LOGGER.info("Common Setup for ErrorLib complete");
    }

    private void serverSetup(ServerStartingEvent event) {
        ErrorAPI.serverSetup(event);
        LOGGER.info("Starting Server Setup for ErrorLib");

        LOGGER.info("Server Setup for ErrorLib complete");
    }

    private void clientSetup(FMLClientSetupEvent event) {
        ErrorAPI.clientSetup(event);
        LOGGER.info("Starting Client Setup for ErrorLib");

        LOGGER.info("Client Setup for ErrorLib complete");
    }
}
