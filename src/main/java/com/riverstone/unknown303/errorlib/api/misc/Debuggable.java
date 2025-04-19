package com.riverstone.unknown303.errorlib.api.misc;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public class Debuggable {
    private static void log(DebugLog log) {
        log.run(LogUtils.getLogger());
    }

    @FunctionalInterface
    public interface DebugLog {
        void run(Logger logger);
    }
}
