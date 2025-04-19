package com.riverstone.unknown303.errorlib.api.helpers.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

public class RegistryMaker<T> {
    private final ResourceKey<Registry<T>> registryKey;
    private final ErrorLibRegistry<T> registry;

    public RegistryMaker(ResourceKey<Registry<T>> registryKey) {
        this.registryKey = registryKey;
        this.registry = new ErrorLibRegistry<>(registryKey);
    }

    public ErrorLibRegistry<T> getRegistry() {
        return registry;
    }

    public void register(IEventBus eventBus) {
        eventBus.addListener(this::createRegistry);
    }

    private void createRegistry(NewRegistryEvent event) {
        RegistryBuilder<T> builder = new RegistryBuilder<T>()
                .setName(registryKey.location()).setDefaultKey(registryKey.location());
        this.registry.createRegistry(event, builder);
    }
}
