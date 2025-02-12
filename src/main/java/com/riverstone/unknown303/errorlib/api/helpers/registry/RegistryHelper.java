package com.riverstone.unknown303.errorlib.api.helpers.registry;

import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.ability.Ability;
import com.riverstone.unknown303.errorlib.api.general.ModInfo;
import com.riverstone.unknown303.errorlib.api.helpers.ErrorLibHelper;
import com.riverstone.unknown303.errorlib.items.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.List;
import java.util.function.Supplier;

public class RegistryHelper extends ErrorLibHelper {
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
        ResourceKey<Registry<T>> registryKey = key(registryId, type);
        DeferredRegister<T> REGISTRY_MAKER = DeferredRegister.create(registryKey, registryId.getNamespace());
        Supplier<IForgeRegistry<T>> REGISTRY = REGISTRY_MAKER.makeRegistry(RegistryBuilder::new);
        REGISTRY_MAKER.register(this.getEventBus());
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
     * @return all RegistryObjects registered to the {@link IForgeRegistry<T> IForgeRegistry} provided.
     *
     * <br>Intended Use:
     */
    public <T> List<T> getValidRegistrations(IForgeRegistry<T> registry) {
        return registry.getValues().stream().toList();
    }
}
