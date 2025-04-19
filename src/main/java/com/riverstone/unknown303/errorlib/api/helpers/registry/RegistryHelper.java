package com.riverstone.unknown303.errorlib.api.helpers.registry;

import com.riverstone.unknown303.errorlib.api.helpers.ErrorLibHelper;
import com.riverstone.unknown303.errorlib.api.misc.ModInfo;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.ArrayList;
import java.util.List;

public class RegistryHelper extends ErrorLibHelper.Registrable {
    private final List<RegistryMaker<?>> registryMakers = new ArrayList<>();

    public RegistryHelper(ModInfo modInfo) {
        super(modInfo);
    }

    public <T> ErrorLibRegistry<T> createRegistry(ResourceKey<Registry<T>> registryKey) {
        RegistryMaker<T> registryMaker = new RegistryMaker<>(registryKey);
        registryMakers.add(registryMaker);
        return registryMaker.getRegistry();
    }

    public <T> ErrorLibRegistry<T> createRegistry(ResourceLocation registryId) {
        return createRegistry(createRegistryKey(registryId));
    }

    public <T> ResourceKey<Registry<T>> createRegistryKey(ResourceLocation registryKey) {
        return ResourceKey.createRegistryKey(registryKey);
    }

    @Override
    public void register(IEventBus eventBus) {
        registryMakers.forEach(registryMaker -> registryMaker.register(eventBus));
    }
}
