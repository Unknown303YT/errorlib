package com.riverstone.unknown303.errorlib.api.helpers.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.tags.ITagManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Supplier;

class DelegatedRegistry<T> implements IForgeRegistry<T> {
    private final ResourceKey<Registry<T>> registryKey;
    private final Supplier<IForgeRegistry<T>> properRegistry;

    public DelegatedRegistry(ResourceKey<Registry<T>> registryKey,
                             Supplier<IForgeRegistry<T>> properRegistry) {
        this(registryKey, null, properRegistry);
    }

    public DelegatedRegistry(ResourceKey<Registry<T>> registryKey, T defaultValue,
                             Supplier<IForgeRegistry<T>> properRegistry) {
        this.registryKey = registryKey;
        this.properRegistry = properRegistry;
    }

    public DeferredRegister<T> createRegister(String modId) {
        return DeferredRegister.createOptional(this.registryKey, modId);
    }

    @Override
    public ResourceKey<Registry<T>> getRegistryKey() {
        if (properRegistry.get() != null) return properRegistry.get().getRegistryKey();
        return registryKey;
    }

    @Override
    public ResourceLocation getRegistryName() {
        if (properRegistry.get() != null) return properRegistry.get().getRegistryName();
        return registryKey.location();
    }

    @Override
    public void register(String key, T value) {
        properRegistry.get().register(key, value);
    }

    @Override
    public void register(ResourceLocation key, T value) {
        properRegistry.get().register(key, value);
    }

    @Override
    public boolean containsKey(ResourceLocation key) {
        return properRegistry.get().containsKey(key);
    }

    @Override
    public boolean containsValue(T value) {
        return properRegistry.get().containsValue(value);
    }

    @Override
    public boolean isEmpty() {
        return properRegistry.get().isEmpty();
    }

    @Override
    public @Nullable T getValue(ResourceLocation key) {
        return properRegistry.get().getValue(key);
    }

    @Override
    public @Nullable ResourceLocation getKey(T value) {
        return properRegistry.get().getKey(value);
    }

    @Override
    public @Nullable ResourceLocation getDefaultKey() {
        return properRegistry.get().getDefaultKey();
    }

    @Override
    public @NotNull Optional<ResourceKey<T>> getResourceKey(T value) {
        return properRegistry.get().getResourceKey(value);
    }

    @Override
    public @NotNull Set<ResourceLocation> getKeys() {
        return properRegistry.get().getKeys();
    }

    @Override
    public @NotNull Collection<T> getValues() {
        return properRegistry.get().getValues();
    }

    @Override
    public @NotNull Set<Map.Entry<ResourceKey<T>, T>> getEntries() {
        return properRegistry.get().getEntries();
    }

    @Override
    public @NotNull Codec<T> getCodec() {
        return properRegistry.get().getCodec();
    }

    @Override
    public @NotNull Optional<Holder<T>> getHolder(ResourceKey<T> key) {
        return properRegistry.get().getHolder(key);
    }

    @Override
    public @NotNull Optional<Holder<T>> getHolder(ResourceLocation location) {
        return properRegistry.get().getHolder(location);
    }

    @Override
    public @NotNull Optional<Holder<T>> getHolder(T value) {
        return properRegistry.get().getHolder(value);
    }

    @Override
    public @Nullable ITagManager<T> tags() {
        return properRegistry.get().tags();
    }

    @Override
    public @NotNull Optional<Holder.Reference<T>> getDelegate(ResourceKey<T> key) {
        return properRegistry.get().getDelegate(key);
    }

    @Override
    public Holder.@NotNull Reference<T> getDelegateOrThrow(ResourceKey<T> key) {
        return properRegistry.get().getDelegateOrThrow(key);
    }

    @Override
    public @NotNull Optional<Holder.Reference<T>> getDelegate(ResourceLocation key) {
        return properRegistry.get().getDelegate(key);
    }

    @Override
    public Holder.@NotNull Reference<T> getDelegateOrThrow(ResourceLocation key) {
        return properRegistry.get().getDelegateOrThrow(key);
    }

    @Override
    public @NotNull Optional<Holder.Reference<T>> getDelegate(T value) {
        return properRegistry.get().getDelegate(value);
    }

    @Override
    public Holder.@NotNull Reference<T> getDelegateOrThrow(T value) {
        return properRegistry.get().getDelegateOrThrow(value);
    }

    @Override
    public <T1> T1 getSlaveMap(ResourceLocation slaveMapName, Class<T1> type) {
        return properRegistry.get().getSlaveMap(slaveMapName, type);
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return properRegistry.get().iterator();
    }
}
