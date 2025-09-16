package com.riverstone.unknown303.errorlib.api.misc;

import com.mojang.logging.LogUtils;
import com.riverstone.unknown303.errorlib.api.ErrorAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class Debuggable {
    private static final StackWalker STACK_WALKER = StackWalker.getInstance(
            StackWalker.Option.RETAIN_CLASS_REFERENCE);

    protected static void debug(Consumer<Logger> log) {
        if (ErrorAPI.debuggingEnabled())
            log.accept(LoggerFactory.getLogger(STACK_WALKER.getCallerClass()));
    }

    protected static void log(Consumer<Logger> log) {
        log.accept(LoggerFactory.getLogger(STACK_WALKER.getCallerClass()));
    }
}
