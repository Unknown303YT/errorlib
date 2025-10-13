package com.riverstone.unknown303.errorlib.api.helpers.registry;

import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.general.ModInfo;
import com.riverstone.unknown303.errorlib.api.helpers.ErrorLibHelper;
import com.riverstone.unknown303.errorlib.items.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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

    public <T> IForgeRegistry<T> createRegistry(String registryId, Supplier<T> sup) {
        ResourceKey<Registry<T>> registryKey = key(registryId, sup);
        DeferredRegister<T> REGISTRY = DeferredRegister.create(registryKey, this.getModId());
        return REGISTRY.makeRegistry(RegistryBuilder::new).get();
    }

    private <T> ResourceKey<Registry<T>> key(String registryId, Supplier<T> sup) {
        return ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(this.getModId(), registryId));
    }

    public <T> List<T> getValidRegistrations(IForgeRegistry<T> reg, Supplier<T> sup) {
        return reg.getValues().stream().toList();
    }

    public <T> T getRegistration(IForgeRegistry<T> reg, String registryId, Supplier<T> sup) {
        return reg.getValue(ResourceLocation.fromNamespaceAndPath(this.getModId(), registryId));
    }
}
