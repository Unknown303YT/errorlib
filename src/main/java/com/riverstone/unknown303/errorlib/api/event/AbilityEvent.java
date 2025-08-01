package com.riverstone.unknown303.errorlib.api.event;

import com.riverstone.unknown303.errorlib.api.abilities.Abilities;
import com.riverstone.unknown303.errorlib.api.abilities.IAbilities;
import com.riverstone.unknown303.errorlib.api.abilities.ability.Ability;
import com.riverstone.unknown303.errorlib.api.abilities.ability.misc.AbilityContext;
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
    private final IAbilities abilities;
    private final Ability ability;

    public AbilityEvent(Player player, IAbilities abilities, Ability ability) {
        super(player);
        this.abilities = abilities;
        this.ability = ability;
    }

    public IAbilities getAbilities() {
        return abilities;
    }

    public Ability getAbility() {
        return ability;
    }

    /**
     * This event is fired whenever an {@link Ability} is unlocked in<br>
     * {@link IAbilities#unlockAbility(Ability, Player)}.<br>
     * <br>
     * This event is {@link Cancelable}.<br>
     * This event has a result. {@link HasResult}<br>
     * DEFAULT: means the ErrorLib logic will determine if the ability should be unlocked (only added if it is not already there).<br>
     * DENY: The ability will not be unlocked.<br>
     * ALLOW: This ability is forced to be unlocked.<br>
     * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
     **/
    @HasResult
    @Cancelable
    public static class AbilityUnlockedEvent extends AbilityEvent {
        public AbilityUnlockedEvent(Player player, IAbilities abilities, Ability ability) {
            super(player, abilities, ability);
        }
    }

    /**
     * This event is fired whenever an {@link Ability} is locked in<br>
     * {@link IAbilities#lockAbility(Ability, Player)}.<br>
     * <br>
     * This event is {@link Cancelable}.<br>
     * This event does not have a result. {@link HasResult}<br>
     * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
     **/
    @Cancelable
    public static class AbilityLockedEvent extends AbilityEvent {
        public AbilityLockedEvent(Player player, IAbilities abilities, Ability ability) {
            super(player, abilities, ability);
        }
    }

    /**
     * This event is fired whenever an {@link Ability} is enabled in<br>
     * {@link IAbilities#enable(Ability, Player, int)}.<br>
     * <br>
     * This event is {@link Cancelable}.<br>
     * This event does not have a result. {@link HasResult}<br>
     * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
     **/
    @Cancelable
    public static class AbilityEnabledEvent extends AbilityEvent {
        public AbilityEnabledEvent(Player player, IAbilities abilities, Ability ability) {
            super(player, abilities, ability);
        }
    }

    /**
     * This event is fired whenever an {@link Ability} is disabled in<br>
     * {@link IAbilities#disable(Ability, Player)}.<br>
     * Due to the nature of {@link AbilityContext#HOLD}, cancelling this event has no effect on Ability's using {@link AbilityContext#HOLD}.<br>
     * <br>
     * This event is {@link Cancelable}.<br>
     * This event does not have a result. {@link HasResult}<br>
     * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
     **/
    @Cancelable
    public static class AbilityDisabledEvent extends AbilityEvent {
        public AbilityDisabledEvent(Player player, IAbilities abilities, Ability ability) {
            super(player, abilities, ability);
        }
    }

    /**
     * This event is fired whenever an {@link Ability} is ticked in<br>
     * {@link com.riverstone.unknown303.errorlib.events.api.ForgeEvents.Common#onPlayerTick(TickEvent.PlayerTickEvent)}.<br>
     * <br>
     * This event is {@link Cancelable}.<br>
     * This event does not have a result. {@link HasResult}<br>
     * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
     **/
    @Cancelable
    public static class AbilityTickEvent extends AbilityEvent {
        public AbilityTickEvent(Player player, IAbilities abilities, Ability ability) {
            super(player, abilities, ability);
        }
    }
}
