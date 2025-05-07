package com.riverstone.unknown303.errorlib.api.helpers.registry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.tags.ITagManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class EmptyRegistry<T> implements IForgeRegistry<T> {
    private final BiMap<ResourceKey<T>, T> VALUES = HashBiMap.create();

    private final ResourceKey<Registry<T>> registryKey;
    @Nullable private final T toProvide;

    public EmptyRegistry(ResourceKey<Registry<T>> registryKey) {
        this(registryKey, null);
    }

    public EmptyRegistry(ResourceKey<Registry<T>> registryKey, @Nullable T toProvide) {
        this.registryKey = registryKey;
        this.toProvide = toProvide;
    }

    private ResourceKey<T> key(ResourceLocation id) {
        return ResourceKey.create(registryKey, id);
    }

    @Override
    public ResourceKey<Registry<T>> getRegistryKey() {
        return this.registryKey;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.registryKey.location();
    }

    @Override
    public void register(String key, T value) {
        register(ResourceLocation.parse(key), value);
    }

    @Override
    public void register(ResourceLocation key, T value) {
        VALUES.put(key(key), value);
    }

    @Override
    public boolean containsKey(ResourceLocation key) {
        return VALUES.containsKey(key(key));
    }

    @Override
    public boolean containsValue(T value) {
        return VALUES.containsValue(value);
    }

    @Override
    public boolean isEmpty() {
        return VALUES.isEmpty();
    }

    @Override
    public @Nullable T getValue(ResourceLocation key) {
        return VALUES.getOrDefault(key(key), toProvide);
    }

    @Override
    public @Nullable ResourceLocation getKey(T value) {
        return VALUES.inverse().get(value).location();
    }

    @Override
    public @Nullable ResourceLocation getDefaultKey() {
        return ResourceLocation.fromNamespaceAndPath(registryKey.location().getNamespace(), "null_" + registryKey.location().getPath());
    }

    @Override
    public @NotNull Optional<ResourceKey<T>> getResourceKey(T value) {
        return Optional.ofNullable(VALUES.inverse().get(value));
    }

    @Override
    public @NotNull Set<ResourceLocation> getKeys() {
        Set<ResourceLocation> keys = new HashSet<>();
        VALUES.forEach((resourceKey, value) -> keys.add(resourceKey.location()));
        return keys;
    }

    @Override
    public @NotNull Collection<T> getValues() {
        return VALUES.values();
    }

    @Override
    public @NotNull Set<Map.Entry<ResourceKey<T>, T>> getEntries() {
        return VALUES.entrySet();
    }

    @Override
    public @NotNull Codec<T> getCodec() {
        return null;
    }

    @Override
    public @NotNull Optional<Holder<T>> getHolder(ResourceKey<T> key) {
        return Optional.empty();
    }

    @Override
    public @NotNull Optional<Holder<T>> getHolder(ResourceLocation location) {
        return Optional.empty();
    }

    @Override
    public @NotNull Optional<Holder<T>> getHolder(T value) {
        return Optional.empty();
    }

    @Override
    public @Nullable ITagManager<T> tags() {
        return null;
    }

    @Override
    public @NotNull Optional<Holder.Reference<T>> getDelegate(ResourceKey<T> rkey) {
        return Optional.empty();
    }

    @Override
    public Holder.@NotNull Reference<T> getDelegateOrThrow(ResourceKey<T> rkey) {
        return null;
    }

    @Override
    public @NotNull Optional<Holder.Reference<T>> getDelegate(ResourceLocation key) {
        return Optional.empty();
    }

    @Override
    public Holder.@NotNull Reference<T> getDelegateOrThrow(ResourceLocation key) {
        return null;
    }

    @Override
    public @NotNull Optional<Holder.Reference<T>> getDelegate(T value) {
        return Optional.empty();
    }

    @Override
    public Holder.@NotNull Reference<T> getDelegateOrThrow(T value) {
        return null;
    }

    @Override
    public <T1> T1 getSlaveMap(ResourceLocation slaveMapName, Class<T1> type) {
        return null;
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return VALUES.values().iterator();
    }
}
