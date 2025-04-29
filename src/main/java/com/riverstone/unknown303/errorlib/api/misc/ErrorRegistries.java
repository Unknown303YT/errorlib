package com.riverstone.unknown303.errorlib.api.misc;

import com.riverstone.unknown303.errorlib.ErrorHelpers;
import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.abilities.Ability;
import com.riverstone.unknown303.errorlib.api.helpers.registry.ErrorLibRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class ErrorRegistries extends StaticLoadable {
    static {
        init();
    }
    
    public static final ErrorLibRegistry<Ability> ABILITIES = ErrorHelpers.REGISTRY_HELPER.createRegistry(Keys.ABILITIES);

    public static class Keys {
        public static final ResourceKey<Registry<Ability>> ABILITIES = ErrorHelpers.REGISTRY_HELPER.createRegistryKey(
                ResourceLocation.fromNamespaceAndPath("abilities", ErrorMod.MOD_ID));

        private static void init() {
        }
    }
    
    private static void init() {
        Keys.init();
    }
}
