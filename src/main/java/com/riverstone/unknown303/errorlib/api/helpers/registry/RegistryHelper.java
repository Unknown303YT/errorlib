package com.riverstone.unknown303.errorlib.api.helpers.registry;

import com.riverstone.unknown303.errorlib.api.helpers.ErrorLibHelper;
import com.riverstone.unknown303.errorlib.api.misc.ModInfo;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public class RegistryHelper extends ErrorLibHelper.Registrable {
    private final List<RegistryMaker<?>> registryMakers = new ArrayList<>();

    public RegistryHelper(ModInfo modInfo) {
        super(modInfo);
    }

    /**
     * Used to create a custom {@linkplain IForgeRegistry} in the form of a {@linkplain ErrorLibRegistry}.<br>
     * We use the {@linkplain ErrorLibRegistry} in case the {@linkplain IForgeRegistry} is never filled.
     * @param registryKey The {@linkplain ResourceKey<Registry>} that contains our registry's {@link ResourceLocation ResourceLocation id}.
     * @param <T> The type of {@linkplain IForgeRegistry} we are making.
     * @return An {@linkplain ErrorLibRegistry} that handles the {@linkplain IForgeRegistry}
     */
    public <T> ErrorLibRegistry<T> createRegistry(ResourceKey<Registry<T>> registryKey) {
        RegistryMaker<T> registryMaker = new RegistryMaker<>(registryKey);
        registryMakers.add(registryMaker);
        debug((logger -> logger.debug("Added RegistryMaker {}.", registryMaker.getRegistryId().toString())));
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
        log(logger -> logger.info("Registering RegistryMakers..."));
        debug(logger -> {
            if (registryMakers.isEmpty())
                logger.warn("RegistryHelper Empty! Variables may not be loaded yet.");
        });
        registryMakers.forEach(registryMaker -> registryMaker.register(eventBus));
    }
}
