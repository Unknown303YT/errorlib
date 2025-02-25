package com.riverstone.unknown303.errorlib.api.ability;

import com.riverstone.unknown303.errorlib.misc.ErrorKeybinds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class Abilities {
    public static final int MAX_AVAILABLE_ABILITIES = 5;

    List<Ability> unlockedAbilities = new ArrayList<>();
    List<Ability> availableAbilities = new ArrayList<>();
    List<Ability> enabledAbilities = new ArrayList<>();

    public void scrollAbilities(ScrollDirection direction) {
        List<Ability> newUnlockedAbilities = new ArrayList<>();
        List<Ability> newAvailableAbilities = new ArrayList<>();
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

    public <T extends Ability> void toggleAbility(T ability, Player owner) {
        if (availableAbilities.contains(ability)) {
            actuallyToggleAbility(ability, owner);
        }
    }

    public <T extends Ability> void actuallyToggleAbility(T ability, Player owner) {
        if (!enabledAbilities.contains(ability)) {
            enabledAbilities.add(ability);
            ability.enable(owner, owner.level());
        } else {
            enabledAbilities.remove(ability);
            ability.disable(owner, owner.level());
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
            unlockedAbilities.add(i, Ability.fromString(pTag.getString("ability" + i)));
        }
    }

    public void loadAvailableAbilities(CompoundTag pTag) {
        int count = pTag.getInt("count");
        availableAbilities.clear();
        for (int i = 0; i < count; i++) {
            availableAbilities.add(i, Ability.fromString(pTag.getString("ability" + i)));
        }
    }

    public void loadEnabledAbilities(CompoundTag pTag) {
        int count = pTag.getInt("count");
        enabledAbilities.clear();
        for (int i = 0; i < count; i++) {
            enabledAbilities.add(i, Ability.fromString(pTag.getString("ability" + i)));
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
        for (Ability ability : availableAbilities) {
            if (ability.getClass().equals(pAbility)) {
                return true;
            }
        }

        return false;
    }

    public <T extends Ability> boolean isAbilityEnabled(Class<T> pAbility) {
        for (Ability ability : enabledAbilities) {
            if (ability.getClass().equals(pAbility)) {
                return true;
            }
        }

        return false;
    }

    public <T extends Ability> boolean isAbilityUnlocked(T pAbility) {
        return unlockedAbilities.contains(pAbility);
    }

    public <T extends Ability> boolean isAbilityAvailable(T pAbility) {
        return availableAbilities.contains(pAbility);
    }

    public <T extends Ability> boolean isAbilityEnabled(T pAbility) {
        return enabledAbilities.contains(pAbility);
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

    public <T extends Ability> void lockAbility(T pAbility) {
        if (availableAbilities.contains(pAbility)) {
            availableAbilities.remove(pAbility);
        } else unlockedAbilities.remove(pAbility);
    }

    public void tickAbilities(Player player, Level level) {
        for (Ability ability : enabledAbilities) {
            ability.tick(player, level);
        }
    }

    public void delegateAbilityKeybinds(Player owner) {
        switch (enabledAbilities.size()) {
            case 1 -> {
                if (ErrorKeybinds.KEY_ABILITY_SLOT_0.consumeClick()) {
                    toggleAbility(availableAbilities.get(0), owner);
                }
            } case 2 -> {
                if (ErrorKeybinds.KEY_ABILITY_SLOT_0.consumeClick()) {
                    toggleAbility(availableAbilities.get(0), owner);
                } else if (ErrorKeybinds.KEY_ABILITY_SLOT_1.consumeClick()) {
                    toggleAbility(availableAbilities.get(1), owner);
                }
            } case 3 -> {
                if (ErrorKeybinds.KEY_ABILITY_SLOT_0.consumeClick()) {
                    toggleAbility(availableAbilities.get(0), owner);
                } else if (ErrorKeybinds.KEY_ABILITY_SLOT_1.consumeClick()) {
                    toggleAbility(availableAbilities.get(1), owner);
                } else if (ErrorKeybinds.KEY_ABILITY_SLOT_2.consumeClick()) {
                    toggleAbility(availableAbilities.get(2), owner);
                }
            } case 4 -> {
                if (ErrorKeybinds.KEY_ABILITY_SLOT_0.consumeClick()) {
                    toggleAbility(availableAbilities.get(0), owner);
                } else if (ErrorKeybinds.KEY_ABILITY_SLOT_1.consumeClick()) {
                    toggleAbility(availableAbilities.get(1), owner);
                } else if (ErrorKeybinds.KEY_ABILITY_SLOT_2.consumeClick()) {
                    toggleAbility(availableAbilities.get(2), owner);
                } else if (ErrorKeybinds.KEY_ABILITY_SLOT_3.consumeClick()) {
                    toggleAbility(availableAbilities.get(3), owner);
                }
            } case 5 -> {
                if (ErrorKeybinds.KEY_ABILITY_SLOT_0.consumeClick()) {
                    toggleAbility(availableAbilities.get(0), owner);
                } else if (ErrorKeybinds.KEY_ABILITY_SLOT_1.consumeClick()) {
                    toggleAbility(availableAbilities.get(1), owner);
                } else if (ErrorKeybinds.KEY_ABILITY_SLOT_2.consumeClick()) {
                    toggleAbility(availableAbilities.get(2), owner);
                } else if (ErrorKeybinds.KEY_ABILITY_SLOT_3.consumeClick()) {
                    toggleAbility(availableAbilities.get(3), owner);
                } else if (ErrorKeybinds.KEY_ABILITY_SLOT_4.consumeClick()) {
                    toggleAbility(availableAbilities.get(4), owner);
                }
            }
        }
    }



    public enum ScrollDirection {
        UP,
        DOWN;
    }
}
