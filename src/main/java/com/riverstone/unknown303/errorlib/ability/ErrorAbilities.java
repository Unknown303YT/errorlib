package com.riverstone.unknown303.errorlib.ability;

import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.ability.Ability;
import com.riverstone.unknown303.errorlib.api.misc.ErrorRegistries;
import com.riverstone.unknown303.errorlib.api.ability.custom.InvisibilityAbility;
import com.riverstone.unknown303.errorlib.api.ability.origin.AbilityOrigin;
import com.riverstone.unknown303.errorlib.api.ability.origin.InHandOrigin;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ErrorAbilities {
    public static final DeferredRegister<Ability> ABILITIES =
            DeferredRegister.create(ErrorRegistries.ABILITIES.get(), ErrorMod.MOD_ID);

    public static final RegistryObject<Ability> INVISIBILTY_BARRIER = ABILITIES.register("invis_barrier",
            () -> new InvisibilityAbility(new InHandOrigin(Items.BARRIER, AbilityOrigin.Color.BLACK)));

    public static void register(IEventBus eventBus) {
        ABILITIES.register(eventBus);
    }
}
