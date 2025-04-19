package com.riverstone.unknown303.errorlib.abilities;

import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.abilities.Ability;
import com.riverstone.unknown303.errorlib.api.misc.ErrorRegistries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class ErrorAbilities {
    public static final DeferredRegister<Ability> ABILITIES =
            ErrorRegistries.ABILITIES.createRegister(ErrorMod.MOD_ID);

    public static void register(IEventBus eventBus) {
        ABILITIES.register(eventBus);
    }
}
