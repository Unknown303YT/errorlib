package com.riverstone.unknown303.errorlib.api.ability;

import com.riverstone.unknown303.errorlib.ErrorHelpers;
import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.ability.origin.AbilityOrigin;
import com.riverstone.unknown303.errorlib.api.misc.ErrorRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Arrays;
import java.util.Objects;

public abstract class Ability {
    private final AbilityOrigin origin;

    public Ability(AbilityOrigin origin) {
        this.origin = origin;
    }

    public abstract void enable(Level level, Player owner);

    public abstract void tick(Level level, Player owner);

    public abstract void disable(Level level, Player owner);

    public boolean isAvailable(Player player) {
        return this.origin.isAvailable(player);
    }

    public Component getAbilityName() {
        return Component.translatable(getAbilityId().toLanguageKey("ability", "name"));
    }

    public Component getAbilityDescription() {
        return Component.translatable(getAbilityId().toLanguageKey("ability", "description"));
    }

    public ResourceLocation getAbilityId() {
        return ErrorRegistries.ABILITIES.get().getKey(this);
    }

    public static Ability fromString(String abilityId) {
        for (Ability ability : ErrorHelpers.REGISTRY_HELPER.getValidRegistrations(ErrorRegistries.ABILITIES.get())) {
            if (Objects.equals(abilityId, ability.getAbilityId().toString())) {
                return ability;
            }
        }

        IllegalStateException exception = new IllegalStateException("Attempted to access unregistered Ability.");
        ErrorMod.LOGGER.error(exception.toString(), exception);
        ErrorMod.LOGGER.trace(Arrays.toString(exception.getStackTrace()), exception);
        throw exception;
    }

    public static enum AbilityType {
        INSTANT,
        HELD,
        TOGGLE,
        CONSTANT;
    }

    public enum AbilityContext {
        SUPERPOWER,
        ARMOR,
        IN_HAND;

        public static AbilityContext fromString(String name) {
            for (AbilityContext context : AbilityContext.values()) {
                if (context.toString().equals(name)) {
                    return context;
                }
            }

            return null;
        }
    }
}
