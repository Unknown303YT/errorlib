package com.riverstone.unknown303.errorlib.abilities;

import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.abilities.ability.Ability;
import com.riverstone.unknown303.errorlib.api.abilities.ability.misc.AbilityContext;
import com.riverstone.unknown303.errorlib.api.abilities.ability.origin.InHandOrigin;
import com.riverstone.unknown303.errorlib.api.abilities.ability.provided.CreativeFlightAbility;
import com.riverstone.unknown303.errorlib.api.abilities.ability.provided.FullInvisAbility;
import com.riverstone.unknown303.errorlib.api.misc.EasyTag;
import com.riverstone.unknown303.errorlib.api.misc.ErrorRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ErrorAbilities {
    public static final DeferredRegister<Ability> ABILITIES =
            DeferredRegister.createOptional(ErrorRegistries.Keys.ABILITIES,
                    ErrorMod.MOD_ID);

    public static final RegistryObject<FullInvisAbility> INVIS =
            ABILITIES.register("invis", () -> new FullInvisAbility(
                    new Ability.Properties().origin(new InHandOrigin(Items.GLASS))));

    public static final RegistryObject<Ability> FLIGHT = ABILITIES.register("flight" ,
            () -> new CreativeFlightAbility(new Ability.Properties().context(AbilityContext.HOLD)
                    .origin(new InHandOrigin(Items.FEATHER))));

    public static final RegistryObject<Ability> TP_UP = ABILITIES.register("tp_up", () ->
            new Ability(new Ability.Properties().context(AbilityContext.INSTANT)
                    .origin(new InHandOrigin(Items.ENDER_PEARL))) {
                @Override
                public void enable(Player player, Level level) {}

                @Override
                public void tick(Player player, Level level) {
                    player.setPos(new Vec3(player.position().x, player.position().y + 10,
                            player.position().z));
                }

                @Override
                public void disable(Player player, Level level) {}

                @Override
                public void saveAdditional(EasyTag tag) {}

                @Override
                public void loadAdditonal(EasyTag tag) {}
            });

    public static void register(IEventBus eventBus) {
        ABILITIES.register(eventBus);
    }
}