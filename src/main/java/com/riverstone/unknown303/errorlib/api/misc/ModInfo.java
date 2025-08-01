package com.riverstone.unknown303.errorlib.api.misc;

import com.riverstone.unknown303.errorlib.api.helpers.ErrorLibHelper;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.ArrayList;
import java.util.List;

public class ModInfo {
    private final List<ErrorLibHelper.Registrable> registrableHelpers = new ArrayList<>();
    private final String modId;

    public ModInfo(String modId) {
        this.modId = modId;
    }

    public <T extends ErrorLibHelper.Registrable> void registrableHelper(T helper) {
        registrableHelpers.add(helper);
    }

    public String getModId() {
        return this.modId;
    }

    public List<ErrorLibHelper.Registrable> getRegistrableHelpers() {
        return registrableHelpers;
    }

    public void register(IEventBus eventBus) {
        registrableHelpers.forEach(registrable ->
                registrable.register(eventBus));
    }
}
