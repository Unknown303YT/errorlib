package com.riverstone.unknown303.errorlib.api.misc;

import com.mojang.logging.LogUtils;
import com.riverstone.unknown303.errorlib.api.ErrorAPI;
import org.slf4j.Logger;

import java.util.function.Consumer;

public class Debuggable {
    protected static void debug(Consumer<Logger> log) {
        if (ErrorAPI.debuggingEnabled())
            log.accept(LogUtils.getLogger());
    }

    protected static void log(Consumer<Logger> log) {
        log.accept(LogUtils.getLogger());
    }
}
