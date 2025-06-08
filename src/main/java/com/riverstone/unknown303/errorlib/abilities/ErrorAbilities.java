package com.riverstone.unknown303.errorlib.abilities;

import com.mojang.logging.LogUtils;
import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.abilities.ability.Ability;
import com.riverstone.unknown303.errorlib.api.abilities.ability.origin.InHandOrigin;
import com.riverstone.unknown303.errorlib.api.abilities.ability.provided.FullInvisAbility;
import com.riverstone.unknown303.errorlib.api.misc.ErrorRegistries;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Marker;

public class ErrorAbilities {
    public static final DeferredRegister<Ability> ABILITIES =
            DeferredRegister.createOptional(ErrorRegistries.Keys.ABILITIES, ErrorMod.MOD_ID);

    public static final RegistryObject<Ability> INVIS = ABILITIES.register("invis",
            () -> new FullInvisAbility(new Ability.Properties().origin(new InHandOrigin(Blocks.GLASS))));

    public static void register(IEventBus eventBus) {
        LogUtils.getLogger().error(LogUtils.FATAL_MARKER, "ABILITIES REGISTERED: {}", ABILITIES.getEntries().size());
        ABILITIES.register(eventBus);
    }
}