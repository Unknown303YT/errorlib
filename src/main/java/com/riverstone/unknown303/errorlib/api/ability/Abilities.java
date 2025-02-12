package com.riverstone.unknown303.errorlib.api.ability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.List;

public class Abilities {
    public static final int MAX_AVAILABLE_ABILITIES = 5;

    List<Ability> unlockedAbilities = new ArrayList<Ability>();
    List<Ability> availableAbilities = new ArrayList<Ability>();
    List<Ability> enabledAbilities = new ArrayList<>();

    public void scrollAbilities(ScrollDirection direction) {
        List<Ability> newUnlockedAbilities = new ArrayList<Ability>();
        List<Ability> newAvailableAbilities = new ArrayList<Ability>();
        switch (direction) {
            case DOWN -> {
                newAvailableAbilities.add(0, unlockedAbilities.get(unlockedAbilities.size() - 1));
                unlockedAbilities.remove(unlockedAbilities.size() - 1);

                newUnlockedAbilities.add(0, availableAbilities.get(availableAbilities.size() - 1));
                availableAbilities.remove(availableAbilities.size() - 1);

                newUnlockedAbilities.addAll(unlockedAbilities);
                newAvailableAbilities.addAll(availableAbilities);

                unlockedAbilities = newUnlockedAbilities;
                availableAbilities = newAvailableAbilities;
            } case UP -> {

            }
        }
    }

    public <T extends Ability> void toggleAbility(T ability) {
        if (availableAbilities.contains(ability)) {
            actuallyToggleAbility(ability);
        }
    }

    public <T extends Ability> void actuallyToggleAbility(T ability) {
        if (!enabledAbilities.contains(ability)) {
            enabledAbilities.add(ability);
        } else {
            enabledAbilities.remove(ability);
        }
    }

    public void addAdditionalSaveData(CompoundTag unlockedAbilitiesTag, CompoundTag availableAbilitiesTag, CompoundTag enabledAbilitiesTag) {
        saveUnlockedAbilities(unlockedAbilitiesTag);
        saveAvailableAbilities(availableAbilitiesTag);
        saveEnabledAbilities(enabledAbilitiesTag);
    }

    private void saveUnlockedAbilities(CompoundTag pTag) {
        pTag.putInt("count", unlockedAbilities.size());
        for (int i = 0; i < unlockedAbilities.size(); i++) {
            Ability ability = unlockedAbilities.get(i);
            pTag.putString("ability" + i, ability.getAbilityId().toString());
        }
    }

    private void saveAvailableAbilities(CompoundTag pTag) {
        pTag.putInt("count", availableAbilities.size());
        for (int i = 0; i < availableAbilities.size(); i++) {
            Ability ability = availableAbilities.get(i);
            pTag.putString("ability" + i, ability.getAbilityId().toString());
        }
    }

    private void saveEnabledAbilities(CompoundTag pTag) {
        pTag.putInt("count", enabledAbilities.size());
        for (int i = 0; i < enabledAbilities.size(); i++) {
            Ability ability = enabledAbilities.get(i);
            pTag.putString("ability" + i, ability.getAbilityId().toString());
        }
    }

    public void loadAdditionalSaveData(CompoundTag unlockedAbilitiesTag, CompoundTag availableAbilitiesTag, CompoundTag enabledAbilitiesTag) {
        loadUnlockedAbilities(unlockedAbilitiesTag);
        loadAvailableAbilities(availableAbilitiesTag);
        loadEnabledAbilities(enabledAbilitiesTag);
    }

    public void loadUnlockedAbilities(CompoundTag pTag) {
        int count = pTag.getInt("count");
        unlockedAbilities.clear();
        for (int i = 0; i < count; i++) {
            unlockedAbilities.add(i, Ability.fromString("ability" + i));
        }
    }

    public void loadAvailableAbilities(CompoundTag pTag) {
        int count = pTag.getInt("count");
        availableAbilities.clear();
        for (int i = 0; i < count; i++) {
            availableAbilities.add(i, Ability.fromString("ability" + i));
        }
    }

    public void loadEnabledAbilities(CompoundTag pTag) {
        int count = pTag.getInt("count");
        enabledAbilities.clear();
        for (int i = 0; i < count; i++) {
            enabledAbilities.add(i, Ability.fromString("ability" + i));
        }
    }

    public Abilities copyFrom(Abilities oldAbilities) {
        unlockedAbilities = oldAbilities.unlockedAbilities;
        availableAbilities = oldAbilities.availableAbilities;
        enabledAbilities = oldAbilities.enabledAbilities;
        return this;
    }

    public <T extends Ability> boolean isAbilityUnlocked(Class<T> pAbility) {
        for (Ability ability : unlockedAbilities) {
            if (ability.getClass().equals(pAbility)) {
                return true;
            }
        }

        return false;
    }

    public <T extends Ability> boolean isAbilityAvailable(Class<T> pAbility) {
        if (!isAbilityUnlocked(pAbility)) {
            return false;
        }

        for (Ability ability : availableAbilities) {
            if (ability.getClass().equals(pAbility)) {
                return true;
            }
        }

        return false;
    }

    public <T extends Ability> boolean isAbilityEnabled(Class<T> pAbility) {
        if (!isAbilityUnlocked(pAbility)) {
            return false;
        }

        for (Ability ability : enabledAbilities) {
            if (ability.getClass().equals(pAbility)) {
                return true;
            }
        }

        return false;
    }

    public <T extends Ability> void unlockAbility(T pAbility) {
        if (unlockedAbilities.contains(pAbility) || availableAbilities.contains(pAbility)) {
            return;
        }

        actuallyUnlockAbility(pAbility);
    }

    public <T extends Ability> void actuallyUnlockAbility(T pAbility) {
        if (availableAbilities.size() <= MAX_AVAILABLE_ABILITIES) {
            availableAbilities.add(pAbility);
        } else {
            unlockedAbilities.add(pAbility);
        }
    }

    public List<Ability> getEnabledAbilities() {
        return enabledAbilities;
    }

    public List<Ability> getAvailableAbilities() {
        return availableAbilities;
    }

    public enum ScrollDirection {
        UP,
        DOWN;
    }
}
