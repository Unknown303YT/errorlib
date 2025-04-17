package com.riverstone.unknown303.errorlib.api.helpers.registry;

import com.mojang.logging.LogUtils;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class ErrorLibRegistry<T> {
    private final ResourceKey<Registry<T>> registryKey;

    private Supplier<IForgeRegistry<T>> registry = () -> null;

    public ErrorLibRegistry(ResourceKey<Registry<T>> registryKey) {
        this.registryKey = registryKey;
    }

    void createRegistry(NewRegistryEvent event, RegistryBuilder<T> builder) {
        registry = event.create(builder, this::onFill);
    }

    private void onFill(IForgeRegistry<T> registry) {
        if (registryKey.equals(registry.getRegistryKey())) {
            this.registry = () -> registry;
        } else {
            String error = "Was provided filled IForgeRegistry that didn't match ErrorLibRegistry. Provided IForgeRegistry ID: "
                    + registry.getRegistryKey().registry() + " ErrorLibRegistryID: " + registryKey.registry();
            IllegalStateException exception = new IllegalStateException(error);
            LogUtils.getLogger().error(LogUtils.FATAL_MARKER, error, exception);
            Minecraft.crash(CrashReport.forThrowable(exception, error));
            throw exception;
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
