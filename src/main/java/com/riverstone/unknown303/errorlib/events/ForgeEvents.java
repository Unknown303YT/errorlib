package com.riverstone.unknown303.errorlib.events;

import com.riverstone.unknown303.errorlib.commands.DebugCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeEvents {
    public static class Client {

    }

    public static class Common {
        @SubscribeEvent
        public static void onCommandRegistered(RegisterCommandsEvent event) {
            DebugCommand.register(event.getDispatcher());
        }
    }
}
