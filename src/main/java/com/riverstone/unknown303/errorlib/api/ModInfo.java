package com.riverstone.unknown303.errorlib.api;

import net.minecraftforge.eventbus.api.IEventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModInfo {
    private final List<ErrorLibHelper.Registrable> registrableHelpers = new ArrayList<>();
    private final String modId;
    private Supplier<IEventBus> eventBus = () -> null;

    public ModInfo(String modId) {
        this.modId = modId;
    }

    public ModInfo modEventBus(IEventBus eventBus) {
        this.eventBus = () -> eventBus;
        return this;
    }

    <T extends ErrorLibHelper.Registrable> void registrableHelper(T helper) {
        registrableHelpers.add(helper);
    }

    public String getModId() {
        return this.modId;
    }

    public void register() {
        registrableHelpers.forEach(registrable -> registrable.register(eventBus.get()));
    }
}
