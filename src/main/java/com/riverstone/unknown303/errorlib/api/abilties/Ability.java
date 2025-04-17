package com.riverstone.unknown303.errorlib.api.abilties;

import com.riverstone.unknown303.errorlib.api.abilties.origin.AbilityOrigin;
import com.riverstone.unknown303.errorlib.api.misc.ErrorRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class Ability {
    private final AbilityOrigin origin;

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

    public boolean isAvailable(Player player) {
        return origin.isAvailable(player, player.level());
    }
}
