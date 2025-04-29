package com.riverstone.unknown303.errorlib.api.helpers.registry;

import com.riverstone.unknown303.errorlib.api.misc.Debuggable;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.List;
import java.util.function.Supplier;

public class ErrorLibRegistry<T> extends Debuggable {
    private final ResourceKey<Registry<T>> registryKey;

    private Supplier<IForgeRegistry<T>> registry = () -> null;

    public ErrorLibRegistry(ResourceKey<Registry<T>> registryKey) {
        this.registryKey = registryKey;
    }

    void createRegistry(NewRegistryEvent event, RegistryBuilder<T> builder) {
        registry = event.create(builder, this::onFill);
        debug(logger -> logger.debug("NewRegistryEvent called."));
    }

    private void onFill(IForgeRegistry<T> registry) {
        if (registryKey.equals(registry.getRegistryKey())) {
            this.registry = () -> registry;
            debug(logger -> logger.debug("Registry {} filled.", registryKey.location()));
        } else {
            debug(logger -> {
                String baseDescription = "Provided Registry's ResourceKey doesn't match correct ResourceKey!";
                String extraDescription = "Provided Registry's ID: %s. Correct Registry ID: %s".formatted(registry.getRegistryName(), registryKey.location());
                IllegalArgumentException exception = new IllegalArgumentException("%s %s".formatted(baseDescription, extraDescription));
                logger.error(baseDescription, exception);
                Minecraft.crash(CrashReport.forThrowable(exception, "%s %s".formatted(baseDescription, extraDescription)));
                throw exception;
            });
        }
    }

    public DeferredRegister<T> createRegister(String modId) {
        return DeferredRegister.createOptional(registryKey.location(), modId);
    }

    public T get(ResourceLocation key) {
        return registry.get().getValue(key);
    }

    public List<T> getValues() {
        return (List<T>) registry.get().getValues();
    }

    public ResourceLocation getKey(T value) {
        return registry.get().getKey(value);
    }

    @Override
    public String toString() {
        return registryKey.location().toString();
    }
}
