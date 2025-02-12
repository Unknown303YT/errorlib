package com.riverstone.unknown303.errorlib.api.misc;

import com.riverstone.unknown303.errorlib.ErrorHelpers;
import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.ability.Ability;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.MissingMappingsEvent;
import net.minecraftforge.registries.NewRegistryEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = ErrorMod.MOD_ID)
public class ErrorRegistries {
    public static final Supplier<IForgeRegistry<Ability>> ABILITIES =
            ErrorHelpers.REGISTRY_HELPER.createRegistry(new ResourceLocation(ErrorMod.MOD_ID, "ability"), Ability.class);
}
