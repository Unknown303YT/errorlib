package com.riverstone.unknown303.errorlib.api.abilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

public class AbilitiesHandler {
    private List<Ability> constantAbilities = new ArrayList<>();

    private List<Ability> unlockedAbilities = new ArrayList<>();
    private List<Ability> availableAbilities = new ArrayList<>();
    private List<Ability> enabledAbilities = new ArrayList<>();

    @ApiStatus.Internal
    public void unlockConstant(Ability ability) {
        constantAbilities.add(ability);
    }

    @ApiStatus.Internal
    public void unlockAbility(Ability ability) {
        if (!contains(ability))
            forceUnlockAbility(ability);
    }

    @ApiStatus.Internal
    public void forceUnlockAbility(Ability ability) {
        if (useAvailable()) availableAbilities.add(ability);
        else unlockedAbilities.add(ability);
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
        enabledAbilities.removeIf(ability -> !availableAbilities.contains(ability));
    }

    // SAVING

    public CompoundTag getConstantAbilitiesNBT() {
        CompoundTag nbt = new CompoundTag();

        nbt.putInt("count", constantAbilities.size());
        for (int i = 0; i < constantAbilities.size(); i++)
            nbt.putString("ability" + i, constantAbilities.get(i).toString());

        return nbt;
    }

    public CompoundTag getUnlockedAbilitiesNBT() {
        CompoundTag nbt = new CompoundTag();

        nbt.putInt("count", unlockedAbilities.size());
        for (int i = 0; i < unlockedAbilities.size(); i++)
            nbt.putString("ability" + i, unlockedAbilities.get(i).toString());

        return nbt;
    }

    public CompoundTag getEnabledAbilitiesNBT() {
        CompoundTag nbt = new CompoundTag();

        int count = Math.min(enabledAbilities.size(), 5);
        nbt.putInt("count", count);
        for (int i = 0; i < count; i++)
            nbt.putString("ability" + i, enabledAbilities.get(i).toString());

        return nbt;
    }

    public CompoundTag getAvailableAbilitiesNBT() {
        CompoundTag nbt = new CompoundTag();

        int count = Math.min(enabledAbilities.size(), 5);
        nbt.putInt("count", count);
        for (int i = 0; i < count; i++)
            nbt.putString("ability" + i, availableAbilities.get(i).toString());

        return nbt;
    }

    // LOADING

    public void loadConstantAbilitiesNBT(CompoundTag nbt) {
        for (int i = 0; i < nbt.getInt("count"); i++) {
            addOrSet(constantAbilities, i, Ability.fromID(
                    nbt.getString("ability" + i)));
        }
    }

    public void loadUnlockedAbilitiesNBT(CompoundTag nbt) {
        for (int i = 0; i < nbt.getInt("count"); i++) {
            addOrSet(unlockedAbilities, i, Ability.fromID(
                    nbt.getString("ability" + i)));
        }
    }

    public void loadEnabledAbilitiesNBT(CompoundTag nbt) {
        for (int i = 0; i < nbt.getInt("count"); i++) {
            addOrSet(enabledAbilities, i, Ability.fromID(
                    nbt.getString("ability" + i)));
        }
    }

    public void loadAvailableAbilitiesNBT(CompoundTag nbt) {
        for (int i = 0; i < nbt.getInt("count"); i++) {
            addOrSet(availableAbilities, i, Ability.fromID(
                    nbt.getString("ability" + i)));
        }
    }

    public void loadAbilities(AbilitiesHandler old) {
        constantAbilities = old.constantAbilities;
        unlockedAbilities = old.unlockedAbilities;
        availableAbilities = old.availableAbilities;
        enabledAbilities = old.enabledAbilities;

        loadConstantAbilitiesNBT(old.getConstantAbilitiesNBT());
        loadUnlockedAbilitiesNBT(old.getUnlockedAbilitiesNBT());
        loadAvailableAbilitiesNBT(old.getAvailableAbilitiesNBT());
        loadEnabledAbilitiesNBT(old.getEnabledAbilitiesNBT());
    }

    private <T> void addOrSet(List<T> addTo, int index, T value) {
        if (index >= addTo.size() || addTo.get(index) != null) addTo.add(index, value);
        else addTo.set(index, value);
    }



    public enum ScrollDirection {
        UP,
        DOWN;
    }
}
