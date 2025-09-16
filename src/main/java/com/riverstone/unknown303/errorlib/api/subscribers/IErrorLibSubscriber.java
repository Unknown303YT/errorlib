package com.riverstone.unknown303.errorlib.api.subscribers;

import com.riverstone.unknown303.errorlib.api.misc.ModInfo;
import net.minecraftforge.eventbus.api.IEventBus;

public interface IErrorLibSubscriber {
    String modId();
    boolean autoRegister();
    IEventBus eventBus();
    ModInfo modInfo();
}
