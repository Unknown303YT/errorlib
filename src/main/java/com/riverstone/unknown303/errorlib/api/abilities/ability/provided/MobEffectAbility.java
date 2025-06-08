package com.riverstone.unknown303.errorlib.api.abilities.ability.provided;

import com.riverstone.unknown303.errorlib.api.abilities.ability.Ability;
import com.riverstone.unknown303.errorlib.api.abilities.ability.origin.AbilityOrigin;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class MobEffectAbility extends Ability {
    private final MobEffectInstance effect;
    private final boolean immediatelyCancel;

    public MobEffectAbility(Properties properties,
                            MobEffectInstance effect, boolean immediatelyCancel) {
        super(properties);
        this.effect = effect;
        this.immediatelyCancel = immediatelyCancel;
    }

    @Override
    public void enable(Player player, Level level) {
        player.addEffect(effect);
    }

    @Override
    public void tick(Player player, Level level) {
        player.addEffect(effect);
    }

    @Override
    public void disable(Player player, Level level) {
        if (immediatelyCancel)
            player.removeEffect(effect.getEffect());
    }
}
