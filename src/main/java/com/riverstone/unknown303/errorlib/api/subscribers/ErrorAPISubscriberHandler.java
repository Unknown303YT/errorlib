package com.riverstone.unknown303.errorlib.api.subscribers;

import com.mojang.logging.LogUtils;
import com.riverstone.unknown303.errorlib.api.ErrorAPI;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.ApiStatus;
import org.objectweb.asm.Type;
import org.slf4j.Logger;

import java.lang.reflect.Constructor;
import java.util.*;

public class ErrorAPISubscriberHandler {
    private static final Type ERRORLIB_SUBSCRIBER = Type.getType(ErrorLibSubscriber.class);

    private static final List<IErrorLibSubscriber> errorLibSubscribers = new ArrayList<>();

    private static final Logger LOGGER = LogUtils.getLogger();

    public static void gatherErrorLibSubscribers() {
        errorLibSubscribers.clear();
        errorLibSubscribers.addAll(getInstances(ErrorLibSubscriber.class,
                IErrorLibSubscriber.class));
    }

    public static void autoRegisterSubscribers() {
        errorLibSubscribers.forEach(subscriber -> {
            if (!subscriber.autoRegister())
                return;
            subscriber.modInfo().register(subscriber.eventBus());
        });
    }

    private static <T> List<T> getInstances(Class<?> annotationClass, Class<T> instanceClass) {
        Type annotationType = Type.getType(annotationClass);
        List<ModFileScanData> allScanData = ModList.get().getAllScanData();
        Set<String> pluginClassNames = new LinkedHashSet<>();
        for (ModFileScanData scanData : allScanData) {
            Iterable<ModFileScanData.AnnotationData> annotations = scanData.getAnnotations();
            for (ModFileScanData.AnnotationData a : annotations) {
                if (Objects.equals(a.annotationType(), annotationType)) {
                    String memberName = a.memberName();
                    pluginClassNames.add(memberName);
                }
            }
        }
        List<T> instances = new ArrayList<>();
        for (String className : pluginClassNames) {
            try {
                Class<?> asmClass = Class.forName(className);
                Class<? extends T> asmInstanceClass = asmClass.asSubclass(instanceClass);
                Constructor<? extends T> constructor = asmInstanceClass.getDeclaredConstructor();
                T instance = constructor.newInstance();
                instances.add(instance);
            } catch (ReflectiveOperationException | LinkageError e) {
                LOGGER.error("Failed to load: {}", className, e);
            }
        }
        return instances;
    }
}
