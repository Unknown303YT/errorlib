package com.riverstone.unknown303.errorlib.api.abilities;

import com.riverstone.unknown303.errorlib.api.abilities.misc.AbilityContext;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class AbilitiesHandler {
    private List<Ability> constantAbilities = new ArrayList<>();

    private List<Ability> unlockedAbilities = new ArrayList<>();
    private List<Ability> availableAbilities = new ArrayList<>();
    private List<Ability> enabledAbilities = new ArrayList<>();

    public void unlockAbility(Ability ability, Player player) {
        if (!contains(ability))
            forceUnlockAbility(ability, player);
    }

    public void forceUnlockAbility(Ability ability, Player player) {
        switch (ability.getContext()) {
            case TOGGLE -> {
                if (useAvailable()) availableAbilities.add(ability);
                else unlockedAbilities.add(ability);
                ability.enable(player, player.level());
            } case CONSTANT -> {

            }
        }
    }

    private boolean useAvailable() {
        return availableAbilities.size() <= 5;
    }

    public boolean contains(Ability ability) {
        return constantAbilities.contains(ability) ||
                availableAbilities.contains(ability) ||
                unlockedAbilities.contains(ability);
    }

    public void scroll(ScrollDirection direction) {
        List<Ability> newUnlockedAbilities = new ArrayList<>();
        List<Ability> newAvailableAbilities = new ArrayList<>();
        switch (direction) {
            case UP -> {
                // CODE HERE
            } case DOWN -> {
                newAvailableAbilities.add(0, unlockedAbilities.get(unlockedAbilities.size() - 1));
                unlockedAbilities.remove(unlockedAbilities.size() - 1);

                newUnlockedAbilities.add(0, availableAbilities.get(availableAbilities.size() - 1));
                availableAbilities.remove(availableAbilities.size() - 1);

                newUnlockedAbilities.addAll(unlockedAbilities);
                newAvailableAbilities.addAll(availableAbilities);

                unlockedAbilities = newUnlockedAbilities;
                availableAbilities = newAvailableAbilities;
            }
        }
    }

    public enum ScrollDirection {
        UP,
        DOWN;
    }
}
