package com.riverstone.unknown303.errorlib.api.helpers.registry;

import com.riverstone.unknown303.errorlib.api.general.ModInfo;
import com.riverstone.unknown303.errorlib.api.helpers.ErrorLibHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RegistryHelper extends ErrorLibHelper {
    List<DeferredRegister<?>> deferredRegisters = new ArrayList<>();

    public RegistryHelper(ModInfo modInfo) {
        super(modInfo);
    }

    /**
     * Used if you want to make mods be able to register variables and later access those variables.
     * Intended Use (replace ExampleObject with your {@link IForgeRegistry IForgeRegistry} type): <pre><code>createRegistry("ability", ExampleObject.class)</code></pre>
     * @param registryId The {@link ResourceLocation ResourceLocation} id of your custom registry, not including the namespace.
     * @param type Used to set the class the {@link IForgeRegistry<T> IForgeRegistry} handles.
     */
    public <T> Supplier<IForgeRegistry<T>> createRegistry(ResourceLocation registryId, Class<T> type) {
//        ResourceKey<Registry<T>> registryKey = key(registryId, type);
        DeferredRegister<T> REGISTRY_MAKER = DeferredRegister.create(registryId, registryId.getNamespace());
        Supplier<IForgeRegistry<T>> REGISTRY = REGISTRY_MAKER.makeRegistry(RegistryBuilder::new);
        deferredRegisters.add(REGISTRY_MAKER);
        return REGISTRY;
    }

    /**
     * Used in {@link #createRegistry(ResourceLocation, Class<T>) createRegistry} to make the ResourceKey for the {@link IForgeRegistry<T> IForgeRegistry}.
     */
    private <T> ResourceKey<Registry<T>> key(ResourceLocation registryId, Class<T> type) {
        return ResourceKey.createRegistryKey(registryId);
    }

    /**
     * Intended Use (replace EXAMPLE_REGISTRY with your {@link IForgeRegistry IForgeRegistry}): <pre><code>getValidRegistrations(EXAMPLE_REGISTRY)</code></pre>
     * @param registry The {@link IForgeRegistry<T> IForgeRegistry} we are getting the values from.
     * @return {@link RegistryObject RegistryObject}s registered to the {@link IForgeRegistry<T> IForgeRegistry} provided.
     */
    public <T> List<T> getValidRegistrations(IForgeRegistry<T> registry) {
        return registry.getValues().stream().toList();
    }

    public void register(IEventBus eventBus) {
        for (DeferredRegister<?> register : deferredRegisters) {
            register.register(eventBus);
        }
    }
}
