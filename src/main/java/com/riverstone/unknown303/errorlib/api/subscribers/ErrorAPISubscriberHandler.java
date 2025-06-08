package com.riverstone.unknown303.errorlib.api.subscribers;

import com.mojang.logging.LogUtils;
import com.riverstone.unknown303.errorlib.api.ErrorAPI;
import com.riverstone.unknown303.errorlib.api.misc.ModInfo;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;
import org.objectweb.asm.Type;

import java.lang.reflect.Modifier;
import java.util.*;

public class ErrorAPISubscriberHandler {
    private static final Type ERRORLIB_SUBSCRIBER = Type.getType(ErrorLibSubscriber.class);

    private static final HashMap<String, Class<?>> errorLibSubscribers = new HashMap<>();

    private static final Logger LOGGER = LogManager.getLogger();

    @ApiStatus.Internal
    public static void gatherErrorAPISubscribers() {
        LOGGER.debug(ErrorAPI.ERROR_API, "Attempting to gather @ErrorLibSubscriber classes");
        List<ModFileScanData.AnnotationData> targets = ModList.get().getAllScanData()
                .stream().map(ModFileScanData::getAnnotations)
                .flatMap(Collection::stream)
                .filter(data ->
                        ERRORLIB_SUBSCRIBER.equals(data.annotationType()))
                .toList();

        targets.forEach(data -> {
            try {
                LOGGER.debug(ErrorAPI.ERROR_API, "Auto-subscribing {} to ErrorLib", data.clazz().getClassName());
                errorLibSubscribers.put((String) data.annotationData().get("modId"), Class.forName(data.clazz().getClassName()));
            } catch (ClassNotFoundException e) {
                String description = "Failed to load ErrorAPI Subscriber class %s for @ErrorLibSubscriber annotation".formatted(data.clazz().getClassName());
                LOGGER.fatal(ErrorAPI.ERROR_API, description);
                Minecraft.crash(CrashReport.forThrowable(e, description));
            }
        });
    }

    public static void initializeErrorAPISubscribers() {
        errorLibSubscribers.forEach((modId, clazz) ->
                Arrays.stream(clazz.getFields())
                .filter(f -> Modifier.isStatic(f.getModifiers()))
                .filter(f ->
                        f.isAnnotationPresent(ErrorLibSubscriber.ModInfo.class))
                .forEach(field -> {
                    try {
                        if (field.get(null) instanceof ModInfo modInfo)
                            modInfo.register(Mod.EventBusSubscriber.Bus.MOD.bus().get());
                        else {
                            String description = "Field " + field.getName() + " is not a ModInfo but was annotated with @ModInfo!";
                            IllegalStateException exception = new IllegalStateException(description);
                            Minecraft.crash(CrashReport.forThrowable(exception, description));
                            throw new RuntimeException(exception);
                        }
                    } catch (IllegalAccessException e) {
                        Minecraft.crash(CrashReport.forThrowable(e, e.getMessage()));
                        throw new RuntimeException(e);
                    }
                }));
    }
}
