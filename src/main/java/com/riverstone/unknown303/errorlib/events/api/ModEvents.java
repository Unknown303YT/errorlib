package com.riverstone.unknown303.errorlib.events.api;

import com.riverstone.unknown303.errorlib.api.abilities.Ability;
import com.riverstone.unknown303.errorlib.api.misc.ErrorRegistries;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegisterEvent;

public class ModEvents {
    public static class Client {

    }

    public static class Common {
        @SubscribeEvent
        public static void registerEvent(RegisterEvent event) {
            if (event.getRegistryKey() == ErrorRegistries.Keys.ABILITIES) {
                IForgeRegistry<Ability> reg = event.getForgeRegistry();
                if (reg != null) {
                    reg.getValues().forEach(ability -> {
                        if (!ability.getForgeEvents().isEmpty())
                            ability.getForgeEvents().forEach(eventContainer -> eventContainer.add(MinecraftForge.EVENT_BUS));
                        if (!ability.getModEvents().isEmpty())
                            ability.getModEvents().forEach(eventContainer -> eventContainer.add(ability.getModEventBus()));
                    });
                }
            }
        }
    }
}
