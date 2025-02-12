package com.riverstone.unknown303.errorlib.api.helpers;

import com.riverstone.unknown303.errorlib.api.general.ModInfo;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import org.slf4j.Logger;

public class ErrorLibHelper {
    protected final ModInfo modInfo;

    public ErrorLibHelper(ModInfo modInfo) {
        this.modInfo = modInfo;
    }

    public String getModId() {
        return this.modInfo.getModId();
    }

    public Logger getModLogger() {
        return this.modInfo.getLogger();
    }

    public DeferredRegister<Item> getRegister() {
        return this.modInfo.getRegister();
    }

    public IEventBus getEventBus() {
        return this.modInfo.getEventBus();
    }
}
