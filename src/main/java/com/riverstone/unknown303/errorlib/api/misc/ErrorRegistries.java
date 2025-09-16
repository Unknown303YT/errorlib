package com.riverstone.unknown303.errorlib.api.misc;

import com.riverstone.unknown303.errorlib.ErrorHelpers;
import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.abilities.ability.Ability;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.List;

public class ErrorRegistries {
    public static final IForgeRegistry<Ability> ABILITIES =
            ErrorHelpers.REGISTRY_HELPER.createRegistry(Keys.ABILITIES);

    public static class Keys {
        public static final ResourceKey<Registry<Ability>> ABILITIES =
                ErrorHelpers.REGISTRY_HELPER.createRegistryKey(
                ResourceLocation.fromNamespaceAndPath(ErrorMod.MOD_ID, "abilities"));

        private static void load() {
            List.of(ABILITIES);
        }
    }

    public static void load() {
        Keys.load();
        List.of(ABILITIES);
    }
}
