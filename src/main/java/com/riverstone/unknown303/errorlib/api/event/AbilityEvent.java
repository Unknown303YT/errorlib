package com.riverstone.unknown303.errorlib.api.event;

import com.riverstone.unknown303.errorlib.api.abilities.Abilities;
import com.riverstone.unknown303.errorlib.api.abilities.Ability;
import com.riverstone.unknown303.errorlib.api.abilities.misc.AbilityContext;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * AbilityEvent is fired whenever an event involving an {@link Ability} occurs. <br>
 * If a method utilizes this {@link net.minecraftforge.eventbus.api.Event} as its parameter, the method will
 * receive every child event of this class.<br>
 * <br>
 * All children of this event are fired on the {@link MinecraftForge#EVENT_BUS}.
 **/
public class AbilityEvent extends PlayerEvent {
    private final Abilities abilities;
    private final Ability ability;

    public AbilityEvent(Player player, Abilities abilities, Ability ability) {
        super(player);
        this.abilities = abilities;
        this.ability = ability;
    }

    public Abilities getAbilities() {
        return abilities;
    }

    public Ability getAbility() {
        return ability;
    }

    public AbilityContext getContext() {
        return ability.getContext();
    }

    /**
     * This event is fired whenever an {@link Ability} is unlocked in<br>
     * {@link Abilities#unlockAbility(Ability, Player)}.<br>
     * <br>
     * This event is {@link Cancelable}.<br>
     * <br>
     * This event has a result. {@link HasResult}<br>
     * DEFAULT: means the ErrorLib logic will determine if the ability should be unlocked (only added if it is not already there).<br>
     * DENY: The ability will not be unlocked.<br>
     * ALLOW: This ability is forced to be unlocked.<br>
     * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
     **/
    @HasResult
    @Cancelable
    public static class AbilityUnlockedEvent extends AbilityEvent {
        public AbilityUnlockedEvent(Player player, Abilities abilities, Ability ability) {
            super(player, abilities, ability);
        }
    }

    /**
     * This event is fired whenever an {@link Ability} is locked in<br>
     * {@link Abilities#lockAbility(Ability, Player)}.<br>
     * <br>
     * This event is {@link Cancelable}.<br>
     * <br>
     * This event does not have a result. {@link HasResult}<br>
     * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
     **/
    @Cancelable
    public static class AbilityLockedEvent extends AbilityEvent {
        public AbilityLockedEvent(Player player, Abilities abilities, Ability ability) {
            super(player, abilities, ability);
        }
    }

    /**
     * This event is fired whenever an {@link Ability} is enabled in<br>
     * {@link Abilities#enable(Ability, Player, int)}.<br>
     * <br>
     * This event is {@link Cancelable}.<br>
     * <br>
     * This event does not have a result. {@link HasResult}<br>
     * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
     **/
    @Cancelable
    public static class AbilityEnabledEvent extends AbilityEvent {
        public AbilityEnabledEvent(Player player, Abilities abilities, Ability ability) {
            super(player, abilities, ability);
        }
    }

    /**
     * This event is fired whenever an {@link Ability} is disabled in<br>
     * {@link Abilities#disable(Ability, Player)}.<br>
     * <br>
     * This event is {@link Cancelable}.<br>
     * <br>
     * This event does not have a result. {@link HasResult}<br>
     * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
     **/
    @Cancelable
    public static class AbilityDisabledEvent extends AbilityEvent {
        public AbilityDisabledEvent(Player player, Abilities abilities, Ability ability) {
            super(player, abilities, ability);
        }
    }

    /**
     * This event is fired whenever an {@link Ability} is ticked in<br>
     * {@link com.riverstone.unknown303.errorlib.events.api.ForgeEvents.Common#playerTick(TickEvent.PlayerTickEvent)}.<br>
     * <br>
     * This event is {@link Cancelable}.<br>
     * <br>
     * This event does not have a result. {@link HasResult}<br>
     * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
     **/
    @Cancelable
    public static class AbilityTickEvent extends AbilityEvent {
        public AbilityTickEvent(Player player, Abilities abilities, Ability ability) {
            super(player, abilities, ability);
        }
    }
}
