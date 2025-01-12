package com.riverstone.unknown303.errorlib;

import com.mojang.logging.LogUtils;
import com.riverstone.unknown303.errorlib.blocks.ModBlocks;
import com.riverstone.unknown303.errorlib.items.ModCreativeTabs;
import com.riverstone.unknown303.errorlib.items.ModItems;
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

import java.util.Random;
import java.util.function.Supplier;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ErrorMod.MOD_ID)
public class ErrorMod {
    public static final String MOD_ID = "errorlib";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ErrorMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        Random random = new Random();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModCreativeTabs.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {
        // Common Setup
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        //Creative Mod Tabs Here
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
        }
    }
}
