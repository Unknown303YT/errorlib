package com.riverstone.unknown303.errorlib.api.helpers;

import com.riverstone.unknown303.errorlib.api.misc.ModInfo;
import net.minecraftforge.eventbus.api.IEventBus;

public abstract class ErrorLibHelper {
    private final ModInfo modInfo;

    public ErrorLibHelper(ModInfo modInfo) {
        this.modInfo = modInfo;
    }

    protected String getModId() {
        return this.modInfo.getModId();
    }

    public static abstract class Registrable extends ErrorLibHelper {
        public Registrable(ModInfo modInfo) {
            super(modInfo);
            modInfo.registrableHelper(this);
        }

        public abstract void register(IEventBus eventBus);
    }
}
