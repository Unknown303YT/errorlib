package com.riverstone.unknown303.errorlib.api.abilities;

import com.riverstone.unknown303.errorlib.api.abilities.misc.AbilityColor;
import com.riverstone.unknown303.errorlib.api.abilities.misc.AbilityContext;
import com.riverstone.unknown303.errorlib.api.abilities.origin.AbilityOrigin;
import com.riverstone.unknown303.errorlib.api.misc.ErrorRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Ability {
    private final AbilityOrigin origin;
    private Supplier<IEventBus> modEventBus = null;

    public Ability(AbilityOrigin origin) {
        this.origin = origin;
    }

    public abstract void enable(Player player, Level level);
    public abstract void tick(Player player, Level level);
    public abstract void disable(Player player, Level level);

    @Override
    public String toString() {
        return getId().toString();
    }

    public ResourceLocation getId() {
        return ErrorRegistries.ABILITIES.getKey(this);
    }

    public Component getName() {
        return Component.translatable(getId().toLanguageKey("ability"));
    }

    public Component getDescription() {
        return Component.translatable(getId().toLanguageKey("ability", "description"));
    }

    public boolean isAvailable(Player player, Level level) {
        return origin.isAvailable(player, level);
    }

    public Ability setModEventBus(Supplier<IEventBus> modEventBus) {
        this.modEventBus = modEventBus;
        return this;
    }

    public List<EventContainer<?>> getForgeEvents() {
        return List.of();
    }

    public List<EventContainer<?>> getModEvents() {
        Objects.requireNonNull(modEventBus);
        return List.of();
    }

    @ApiStatus.Internal
    public IEventBus getModEventBus() {
        Objects.requireNonNull(modEventBus);
        return modEventBus.get();
    }

    public AbilityColor getColor() {
        return origin.getColor();
    }

    public AbilityContext getContext() {
        return origin.getContext();
    }

    public static Ability fromID(String id) {
        return fromID(ResourceLocation.parse(id));
    }

    public static Ability fromID(ResourceLocation id) {
        return ErrorRegistries.ABILITIES.getValue(id);
    }

    public static class EventContainer<T extends Event> {
        private final Consumer<T> event;
        private EventPriority eventPriority = null;
        private boolean receiveCancelled = false;

        public EventContainer(Consumer<T> event) {
            this.event = event;
        }

        public EventContainer(Consumer<T> event, EventPriority eventPriority) {
            this.event = event;
            this.eventPriority = eventPriority;
        }

        public EventContainer(Consumer<T> event, boolean receiveCancelled, EventPriority eventPriority) {
            this.event = event;
            this.receiveCancelled = receiveCancelled;
            this.eventPriority = eventPriority;
        }

        public void add(IEventBus eventBus) {
            if (eventPriority == null) {
                if (receiveCancelled) eventBus.addListener(EventPriority.NORMAL, true, event);
                else eventBus.addListener(event);
            } else eventBus.addListener(eventPriority, receiveCancelled, event);
        }
    }
}
