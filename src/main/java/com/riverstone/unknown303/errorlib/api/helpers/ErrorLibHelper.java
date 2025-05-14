package com.riverstone.unknown303.errorlib.api.helpers;

import com.riverstone.unknown303.errorlib.api.misc.Debuggable;
import com.riverstone.unknown303.errorlib.api.misc.ModInfo;
import net.minecraftforge.eventbus.api.IEventBus;
import org.jetbrains.annotations.ApiStatus;

public class ErrorLibHelper extends Debuggable {
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

        @ApiStatus.Internal
        public abstract void register(IEventBus eventBus);
    }
}
