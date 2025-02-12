package com.riverstone.unknown303.errorlib.api.general;

import com.riverstone.unknown303.errorlib.ErrorMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import org.slf4j.Logger;

public class ModInfo {
    private final String modId;
    private boolean hasLogger;
    private Logger logger;
    private boolean hasItemRegister;
    private DeferredRegister<Item> register;
    private boolean hasEventBus;
    private IEventBus eventBus;

    public ModInfo(String modId) {
        this.modId = modId;
        this.hasLogger = false;
        this.hasItemRegister = false;
        this.hasEventBus = false;
    }

    public ModInfo logger(Logger logger) {
        this.hasLogger = true;
        this.logger = logger;
        return this;
    }

    public ModInfo itemRegister(DeferredRegister<Item> itemRegister) {
        this.hasItemRegister = true;
        this.register = itemRegister;
        return this;
    }

    public ModInfo eventBus(IEventBus eventBus) {
        this.hasEventBus = true;
        this.eventBus = eventBus;
        return this;
    }

    public String getModId() {
        return this.modId;
    }

    public Logger getLogger() {
        if (hasLogger) {
            return this.logger;
        } else {
            IllegalStateException exception = new IllegalStateException("Logger not provided to ModInfo");
            ErrorMod.LOGGER.error("Logger not provided to ModInfo", exception);
            throw exception;
        }
    }

    public DeferredRegister<Item> getRegister() {
        if (hasItemRegister) {
            return this.register;
        } else {
            IllegalStateException exception = new IllegalStateException("Item Register not provided to ModInfo");
            ErrorMod.LOGGER.error("Item Register not provided to ModInfo", exception);
            throw exception;
        }
    }

    public IEventBus getEventBus() {
        if (hasEventBus) {
            return this.eventBus;
        } else {
            IllegalStateException exception = new IllegalStateException("IEventBus not provided to ModInfo");
            ErrorMod.LOGGER.error("IEventBus not provided to ModInfo", exception);
            throw exception;
        }
    }
}
