package com.riverstone.unknown303.errorlib.api.abilities.ability.provided;

import com.riverstone.unknown303.errorlib.api.abilities.ability.Ability;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class AttributeAbility extends Ability {
    private final Attribute targetAttribute;
    private final AttributeModifier modifier;

    public AttributeAbility(Properties properties, Attribute targetAttribute,
                            AttributeModifier modifier) {
        super(properties);
        this.targetAttribute = targetAttribute;
        this.modifier = modifier;
    }

    @Override
    public void enable(Player player, Level level) {
        AttributeInstance instance = player.getAttribute(targetAttribute);
        if (instance != null)
            instance.addPermanentModifier(modifier);
    }

    @Override
    public void tick(Player player, Level level) {
        AttributeInstance instance = player.getAttribute(targetAttribute);
        if (instance != null) {
            if (!instance.hasModifier(modifier))
                instance.addPermanentModifier(modifier);
        }
    }

    @Override
    public void disable(Player player, Level level) {
        AttributeInstance instance = player.getAttribute(targetAttribute);
        if (instance != null)
            instance.removePermanentModifier(modifier.getId());
    }
}
