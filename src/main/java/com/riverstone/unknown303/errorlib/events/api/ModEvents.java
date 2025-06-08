package com.riverstone.unknown303.errorlib.events.api;

import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.abilities.Abilities;
import com.riverstone.unknown303.errorlib.api.abilities.ability.Ability;
import com.riverstone.unknown303.errorlib.api.misc.ErrorRegistries;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegisterEvent;

import java.util.function.Consumer;

public class ModEvents {
    public static class Client {

    }

    public static class Common {
        @SubscribeEvent
        public static void onRegisterCaps(RegisterCapabilitiesEvent event) {
            event.register(Abilities.class);
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void onRegister(RegisterEvent event) {
            if (event.getRegistryKey() == ErrorRegistries.Keys.ABILITIES) {
                IForgeRegistry<Ability> reg = event.getForgeRegistry();
                if (reg != null) {
                    reg.getValues().forEach(ability -> {
                        Consumer<IEventBus> registerForgeEvents =
                                ability.registerForgeEvents();
                        Consumer<IEventBus> registerModEvents =
                                ability.registerModEvents();
                        if (registerForgeEvents != null)
                            registerForgeEvents.accept(MinecraftForge.EVENT_BUS);
                        if (registerModEvents != null)
                            registerModEvents.accept(ErrorMod.eventBus());
                    });
                }
            }
        }
    }
}
