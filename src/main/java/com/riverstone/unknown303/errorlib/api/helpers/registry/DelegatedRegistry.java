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

public class DelegatedRegistry<T> implements IForgeRegistry<T> {
    private final ResourceKey<Registry<T>> registryKey;
    private final Supplier<IForgeRegistry<T>> properRegistry;
    private final EmptyRegistry<T> emptyRegistry;

    public DelegatedRegistry(ResourceKey<Registry<T>> registryKey,
                             Supplier<IForgeRegistry<T>> properRegistry) {
        this(registryKey, null, properRegistry);
    }

    public DelegatedRegistry(ResourceKey<Registry<T>> registryKey, T defaultValue,
                             Supplier<IForgeRegistry<T>> properRegistry) {
        this.registryKey = registryKey;
        this.properRegistry = properRegistry;
        this.emptyRegistry = new EmptyRegistry<>(registryKey, defaultValue);
    }

    public DeferredRegister<T> createRegister(String modId) {
        return DeferredRegister.createOptional(this.registryKey, modId);
    }

    @Override
    public ResourceKey<Registry<T>> getRegistryKey() {
        if (properRegistry.get() != null) return properRegistry.get().getRegistryKey();
        return emptyRegistry.getRegistryKey();
    }

    @Override
    public ResourceLocation getRegistryName() {
        if (properRegistry.get() != null) return properRegistry.get().getRegistryName();
        return emptyRegistry.getRegistryName();
    }

    @Override
    public void register(String key, T value) {
        if (properRegistry.get() != null) properRegistry.get().register(key, value);
        else emptyRegistry.register(key, value);
    }

    @Override
    public void register(ResourceLocation key, T value) {
        if (properRegistry.get() != null) properRegistry.get().register(key, value);
        else emptyRegistry.register(key, value);
    }

    @Override
    public boolean containsKey(ResourceLocation key) {
        if (properRegistry.get() != null) return properRegistry.get().containsKey(key);
        return emptyRegistry.containsKey(key);
    }

    @Override
    public boolean containsValue(T value) {
        if (properRegistry.get() != null) return properRegistry.get().containsValue(value);
        return emptyRegistry.containsValue(value);
    }

    @Override
    public boolean isEmpty() {
        if (properRegistry.get() != null) return properRegistry.get().isEmpty();
        return emptyRegistry.isEmpty();
    }

    @Override
    public @Nullable T getValue(ResourceLocation key) {
        if (properRegistry.get() != null) return properRegistry.get().getValue(key);
        return emptyRegistry.getValue(key);
    }

    @Override
    public @Nullable ResourceLocation getKey(T value) {
        if (properRegistry.get() != null) return properRegistry.get().getKey(value);
        return emptyRegistry.getKey(value);
    }

    @Override
    public @Nullable ResourceLocation getDefaultKey() {
        if (properRegistry.get() != null) return properRegistry.get().getDefaultKey();
        return emptyRegistry.getDefaultKey();
    }

    @Override
    public @NotNull Optional<ResourceKey<T>> getResourceKey(T value) {
        if (properRegistry.get() != null) return properRegistry.get().getResourceKey(value);
        return emptyRegistry.getResourceKey(value);
    }

    @Override
    public @NotNull Set<ResourceLocation> getKeys() {
        if (properRegistry.get() != null) return properRegistry.get().getKeys();
        return emptyRegistry.getKeys();
    }

    @Override
    public @NotNull Collection<T> getValues() {
        if (properRegistry.get() != null) return properRegistry.get().getValues();
        return emptyRegistry.getValues();
    }

    @Override
    public @NotNull Set<Map.Entry<ResourceKey<T>, T>> getEntries() {
        if (properRegistry.get() != null) return properRegistry.get().getEntries();
        return emptyRegistry.getEntries();
    }

    @Override
    public @NotNull Codec<T> getCodec() {
        if (properRegistry.get() != null) return properRegistry.get().getCodec();
        return emptyRegistry.getCodec();
    }

    @Override
    public @NotNull Optional<Holder<T>> getHolder(ResourceKey<T> key) {
        if (properRegistry.get() != null) return properRegistry.get().getHolder(key);
        return emptyRegistry.getHolder(key);
    }

    @Override
    public @NotNull Optional<Holder<T>> getHolder(ResourceLocation location) {
        if (properRegistry.get() != null) return properRegistry.get().getHolder(location);
        return emptyRegistry.getHolder(location);
    }

    @Override
    public @NotNull Optional<Holder<T>> getHolder(T value) {
        if (properRegistry.get() != null) return properRegistry.get().getHolder(value);
        return emptyRegistry.getHolder(value);
    }

    @Override
    public @Nullable ITagManager<T> tags() {
        if (properRegistry.get() != null) return properRegistry.get().tags();
        return emptyRegistry.tags();
    }

    @Override
    public @NotNull Optional<Holder.Reference<T>> getDelegate(ResourceKey<T> key) {
        if (properRegistry.get() != null) return properRegistry.get().getDelegate(key);
        return emptyRegistry.getDelegate(key);
    }

    @Override
    public Holder.@NotNull Reference<T> getDelegateOrThrow(ResourceKey<T> key) {
        if (properRegistry.get() != null) return properRegistry.get().getDelegateOrThrow(key);
        return emptyRegistry.getDelegateOrThrow(key);
    }

    @Override
    public @NotNull Optional<Holder.Reference<T>> getDelegate(ResourceLocation key) {
        if (properRegistry.get() != null) return properRegistry.get().getDelegate(key);
        return emptyRegistry.getDelegate(key);
    }

    @Override
    public Holder.@NotNull Reference<T> getDelegateOrThrow(ResourceLocation key) {
        if (properRegistry.get() != null) return properRegistry.get().getDelegateOrThrow(key);
        return emptyRegistry.getDelegateOrThrow(key);
    }

    @Override
    public @NotNull Optional<Holder.Reference<T>> getDelegate(T value) {
        if (properRegistry.get() != null) return properRegistry.get().getDelegate(value);
        return emptyRegistry.getDelegate(value);
    }

    @Override
    public Holder.@NotNull Reference<T> getDelegateOrThrow(T value) {
        if (properRegistry.get() != null) return properRegistry.get().getDelegateOrThrow(value);
        return emptyRegistry.getDelegateOrThrow(value);
    }

    @Override
    public <T1> T1 getSlaveMap(ResourceLocation slaveMapName, Class<T1> type) {
        if (properRegistry.get() != null) return properRegistry.get().getSlaveMap(slaveMapName, type);
        return emptyRegistry.getSlaveMap(slaveMapName, type);
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        if (properRegistry.get() != null) return properRegistry.get().iterator();
        return emptyRegistry.iterator();
    }
}
