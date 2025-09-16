package com.riverstone.unknown303.errorlib.api.abilities.ability;

import com.riverstone.unknown303.errorlib.api.abilities.PlayerAbilitiesProvider;
import com.riverstone.unknown303.errorlib.api.abilities.ability.misc.AbilityColor;
import com.riverstone.unknown303.errorlib.api.abilities.ability.misc.AbilityColors;
import com.riverstone.unknown303.errorlib.api.abilities.ability.misc.AbilityContext;
import com.riverstone.unknown303.errorlib.api.abilities.ability.origin.AbilityOrigin;
import com.riverstone.unknown303.errorlib.api.misc.EasyTag;
import com.riverstone.unknown303.errorlib.api.misc.ErrorRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class Ability {
    private static boolean returned;

    private final AbilityColor color;
    private final AbilityContext context;
    private final AbilityOrigin origin;

    public Ability(Properties properties) {
        this.color = properties.abilityColor;
        this.context = properties.abilityContext;
        this.origin = properties.abilityOrigin;
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

    /**
     * A {@link Consumer<IEventBus>} for registering {@link Event} listeners. This way the {@link Ability} can fire events.
     * @return A {@link Consumer<IEventBus>} for the {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS}
     */
    @Nullable
    public Consumer<IEventBus> registerForgeEvents() {
        return null;
    }

    /**
     * A {@link Consumer<IEventBus>} for registering {@link Event} listeners. This way the {@link Ability} can fire events.
     * @return A {@link Consumer<IEventBus>} for the ErrorLib Mod {@link IEventBus}
     */
    @Nullable
    public Consumer<IEventBus> registerModEvents() {
        return null;
    }

    public AbilityColor getColor() {
        return color;
    }

    public AbilityContext getContext() {
        return context;
    }

    public final EasyTag save() {
        EasyTag tag = new EasyTag();
        tag.putString("id", getId().toString());
        EasyTag additional = new EasyTag();
        saveAdditional(additional);
        tag.put("additional", additional);
        return tag;
    }

    public static Ability load(EasyTag tag) {
        Ability value = fromID(tag.getString("id"));
        value.loadAdditonal((EasyTag) tag.getCompound("additional"));
        return value;
    }

    protected abstract void saveAdditional(EasyTag tag);

    protected abstract void loadAdditonal(EasyTag tag);

    public static Ability fromID(String id) {
        if (Objects.equals(id, ""))
            return null;
        return fromID(ResourceLocation.parse(id));
    }

    public static Ability fromID(ResourceLocation id) {
        return ErrorRegistries.ABILITIES.getValue(id);
    }

    public static boolean isEnabled(Player player, Ability ability) {
        returned = false;
        player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES)
                .ifPresent(abilities ->
                        returned = abilities.getHandler().getEnabledAbilities()
                                .contains(ability) || abilities.getHandler()
                                .getConstantAbilities().contains(ability));
        return returned;
    }

    public boolean isEnabled(Player player) {
        return isEnabled(player, this);
    }

    public static class Properties {
        private AbilityColor abilityColor;
        private AbilityContext abilityContext;
        private AbilityOrigin abilityOrigin;

        public Properties() {
            this.abilityColor = AbilityColors.NO_COLOR;
            this.abilityContext = AbilityContext.TOGGLE;
            this.abilityOrigin = AbilityOrigin.empty();
        }

        public Properties color(AbilityColor color) {
            this.abilityColor = color;
            return this;
        }

        public Properties context(AbilityContext context) {
            this.abilityContext = context;
            return this;
        }

        public Properties origin(AbilityOrigin origin) {
            this.abilityOrigin = origin;
            return this;
        }

        public static Properties copy(Ability ability) {
            return new Properties().color(ability.color)
                    .context(ability.context)
                    .origin(ability.origin);
        }

        public static Properties copy(Properties properties) {
            return new Properties().color(properties.abilityColor)
                    .context(properties.abilityContext)
                    .origin(properties.abilityOrigin);
        }
    }
}
