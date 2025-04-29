package com.riverstone.unknown303.errorlib.api.helpers.registry;

import com.riverstone.unknown303.errorlib.api.misc.Debuggable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

public class RegistryMaker<T> extends Debuggable {
    private final ResourceKey<Registry<T>> registryKey;
    private final ErrorLibRegistry<T> registry;

    public RegistryMaker(ResourceKey<Registry<T>> registryKey) {
        this.registryKey = registryKey;
        this.registry = new ErrorLibRegistry<>(registryKey);
    }

    public ErrorLibRegistry<T> getRegistry() {
        return this.registry;
    }

    public ResourceKey<Registry<T>> getRegistryKey() {
        return this.registryKey;
    }

    public ResourceLocation getRegistryId() {
        return getRegistryKey().location();
    }

    public void register(IEventBus eventBus) {
        log(logger -> logger.info("Registering RegistryMaker {}.", getRegistryId()));
        eventBus.addListener(this::createRegistry);
    }

    private void createRegistry(NewRegistryEvent event) {
        RegistryBuilder<T> builder = new RegistryBuilder<T>()
                .setName(this.registryKey.location())
                .setDefaultKey(this.registryKey.location());
        this.registry.createRegistry(event, builder);
    }
}
