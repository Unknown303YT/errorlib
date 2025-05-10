package com.riverstone.unknown303.errorlib.api.helpers.registry;

import com.riverstone.unknown303.errorlib.api.helpers.ErrorLibHelper;
import com.riverstone.unknown303.errorlib.api.misc.ModInfo;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RegistryHelper extends ErrorLibHelper.Registrable {
    private final List<DeferredRegister<?>> registers = new ArrayList<>();

    public RegistryHelper(ModInfo modInfo) {
        super(modInfo);
    }

    /**
     * Used to create a custom {@linkplain IForgeRegistry} in the form of a {@linkplain DelegatedRegistry}.<br>
     * We use the {@linkplain DelegatedRegistry} to delegate all methods in case the proper {@linkplain IForgeRegistry} is never filled.<br>
     * It also is helpful for delaying errors until the proper {@linkplain IForgeRegistry} is filled.
     * @param registryKey The {@linkplain ResourceKey<Registry>} that contains our registry's {@link ResourceLocation ResourceLocation id}.
     * @param <T> The type of variable the {@linkplain IForgeRegistry} handles.
     * @return A {@linkplain DelegatedRegistry} that handles the {@linkplain IForgeRegistry}.
     */
    public <T> DelegatedRegistry<T> createRegistry(ResourceKey<Registry<T>> registryKey) {
        DeferredRegister<T> deferredRegister = DeferredRegister.create(registryKey, this.getModId());
        Supplier<IForgeRegistry<T>> reg =
                deferredRegister.makeRegistry(RegistryBuilder::new);
        registers.add(deferredRegister);
        return new DelegatedRegistry<>(registryKey, reg);
    }

    /**
     * Used to create a custom {@linkplain IForgeRegistry} in the form of a {@linkplain DelegatedRegistry}.<br>
     * We use the {@linkplain DelegatedRegistry} to delegate all methods in case the proper {@linkplain IForgeRegistry} is never filled.<br>
     * It also is helpful for delaying errors until the proper {@linkplain IForgeRegistry} is filled.
     * @param registryId The {@linkplain ResourceLocation} that we will turn into a {@linkplain ResourceKey<Registry>}.
     * @param <T> The type of variable the {@linkplain IForgeRegistry} handles.
     * @return A {@linkplain DelegatedRegistry} that handles the {@linkplain IForgeRegistry}.
     */
    public <T> DelegatedRegistry<T> createRegistry(ResourceLocation registryId) {
        return createRegistry(createRegistryKey(registryId));
    }

    /**
     * @param registryKey The {@linkplain ResourceLocation} that we will turn into a {@linkplain ResourceKey<Registry>}.
     * @param <T> The type of variable used by the {@linkplain Registry} or {@linkplain IForgeRegistry}.
     * @return A {@linkplain ResourceKey<Registry>} that can be used when creating a custom {@linkplain IForgeRegistry}.

     */
    public <T> ResourceKey<Registry<T>> createRegistryKey(ResourceLocation registryKey) {
        return ResourceKey.createRegistryKey(registryKey);
    }

    @Override
    public void register(IEventBus eventBus) {
        log(logger -> logger.info("Registering DeferredRegisters..."));
        debug(logger -> {
            if (registers.isEmpty())
                logger.warn("DeferredRegisters Empty! Variables may not be loaded yet.");
        });
        registers.forEach(register -> register.register(eventBus));
    }
}
