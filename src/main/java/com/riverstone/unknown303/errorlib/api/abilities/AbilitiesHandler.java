package com.riverstone.unknown303.errorlib.api.abilities;

import com.riverstone.unknown303.errorlib.api.abilities.ability.Ability;
import com.riverstone.unknown303.errorlib.api.event.AbilityEvent;
import com.riverstone.unknown303.errorlib.api.misc.EasyTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.ApiStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AbilitiesHandler implements Serializable {
    private List<Ability> constantAbilities = new ArrayList<>();

    private List<Ability> unlockedAbilities = new ArrayList<>();
    private List<Ability> availableAbilities = new ArrayList<>();
    private List<Ability> enabledAbilities = new ArrayList<>();

    private boolean contains = false;

    public AbilitiesHandler() {
        for (int i = 0; i < 5; i++) {
            availableAbilities.add(i, null);
            enabledAbilities.add(i, null);
        }
    }

    @ApiStatus.Internal
    public void unlockConstant(Ability ability, AbilityEvent.AbilityUnlockedEvent abilityUnlockedEvent) {
        if (abilityUnlockedEvent.getResult() == Event.Result.ALLOW ||
                (abilityUnlockedEvent.getResult() == Event.Result.DEFAULT &&
                        !contains(ability))) constantAbilities.add(ability);
    }

    @ApiStatus.Internal
    public void unlockAbility(Ability ability, AbilityEvent.AbilityUnlockedEvent abilityUnlockedEvent) {
        if (abilityUnlockedEvent.getResult() == Event.Result.ALLOW ||
                (abilityUnlockedEvent.getResult() == Event.Result.DEFAULT &&
                        !contains(ability))) forceUnlockAbility(ability);
    }

    @ApiStatus.Internal
    public void forceUnlockAbility(Ability ability) {
        if (useAvailable()) availableAbilities.set(getNextAvailable(), ability);
        else unlockedAbilities.add(ability);
    }

    private int getNextAvailable() {
        int slot = 0;
        for (Ability ability : availableAbilities) {
            if (ability == null) {
                return slot;
            }
            slot++;
        }
        return -1;
    }

    @ApiStatus.Internal
    public void lockAbility(Ability ability) {
        if (contains(ability)) forceLockAbility(ability);
    }

    @ApiStatus.Internal
    public void forceLockAbility(Ability ability) {
        availableAbilities.remove(ability);
        enabledAbilities.remove(ability);
        unlockedAbilities.remove(ability);
    }

    private boolean useAvailable() {
        return availableAbilities.size() <= 5;
    }

    public boolean contains(Ability ability) {
        return constantAbilities.contains(ability) ||
                availableAbilities.contains(ability) ||
                unlockedAbilities.contains(ability);
    }

    public boolean contains(Class<? extends Ability> abilityClass) {
        contains = false;
        constantAbilities.forEach(ability -> {
            if (ability.getClass() == abilityClass)
                contains = true;
        });
        unlockedAbilities.forEach(ability -> {
            if (ability.getClass() == abilityClass)
                contains = true;
        });
        availableAbilities.forEach(ability -> {
            if (ability.getClass() == abilityClass)
                contains = true;
        });
        return contains;
    }

    /**
     * Modders: Use {@link AbilitiesHandler#getUnmodifiableConstantAbilities()} instead
     */
    @ApiStatus.Internal
    public List<Ability> getConstantAbilities() {
        return constantAbilities;
    }

    public List<Ability> getUnmodifiableConstantAbilities() {
        return Collections.unmodifiableList(getConstantAbilities());
    }

    /**
     * Modders: Use {@link AbilitiesHandler#getUnmodifiableUnlockedAbilities()} instead
     */
    @ApiStatus.Internal
    public List<Ability> getUnlockedAbilities() {
        return unlockedAbilities;
    }

    public List<Ability> getUnmodifiableUnlockedAbilities() {
        return Collections.unmodifiableList(getUnlockedAbilities());
    }

    /**
     * Modders: Use {@link AbilitiesHandler#getUnmodifiableAvailableAbilities()} instead
     */
    @ApiStatus.Internal
    public List<Ability> getAvailableAbilities() {
        return availableAbilities;
    }

    public List<Ability> getUnmodifiableAvailableAbilities() {
        return Collections.unmodifiableList(getAvailableAbilities());
    }

    /**
     * Modders: Use {@link AbilitiesHandler#getUnmodifiableEnabledAbilities()} instead
     */
    @ApiStatus.Internal
    public List<Ability> getEnabledAbilities() {
        return enabledAbilities;
    }
    public List<Ability> getUnmodifiableEnabledAbilities() {
        return Collections.unmodifiableList(getEnabledAbilities());
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

    private String abilityId(Ability ability) {
        if (ability == null)
            return "null";
        return ability.toString();
    }

    public CompoundTag getConstantAbilitiesNBT() {
        CompoundTag nbt = new CompoundTag();

        nbt.putInt("count", constantAbilities.size());
        for (int i = 0; i < constantAbilities.size(); i++)
            nbt.put("ability" + i, constantAbilities.get(i).save());

        return nbt;
    }

    public CompoundTag getUnlockedAbilitiesNBT() {
        CompoundTag nbt = new CompoundTag();

        nbt.putInt("count", unlockedAbilities.size());
        for (int i = 0; i < unlockedAbilities.size(); i++)
            nbt.put("ability" + i, unlockedAbilities.get(i).save());

        return nbt;
    }

    public CompoundTag getEnabledAbilitiesNBT() {
        CompoundTag nbt = new CompoundTag();

        int count = Math.min(enabledAbilities.size(), 5);
        nbt.putInt("count", count);
        for (int i = 0; i < count; i++)
            nbt.put("ability" + i, enabledAbilities.get(i).save());

        return nbt;
    }

    public CompoundTag getAvailableAbilitiesNBT() {
        CompoundTag nbt = new CompoundTag();

        int count = Math.min(enabledAbilities.size(), 5);
        nbt.putInt("count", count);
        for (int i = 0; i < count; i++)
            nbt.put("ability" + i, availableAbilities.get(i).save());

        return nbt;
    }

    // LOADING

    public void loadConstantAbilitiesNBT(CompoundTag nbt) {
        for (int i = 0; i < nbt.getInt("count"); i++)
            constantAbilities.add(i, Ability.load((EasyTag)
                    nbt.getCompound("ability" + i)));
    }

    public void loadUnlockedAbilitiesNBT(CompoundTag nbt) {
        for (int i = 0; i < nbt.getInt("count"); i++)
            unlockedAbilities.add(i, Ability.load((EasyTag)
                    nbt.getCompound("ability" + i)));
    }

    public void loadEnabledAbilitiesNBT(CompoundTag nbt) {
        for (int i = 0; i < nbt.getInt("count"); i++)
            enabledAbilities.add(i, Ability.load((EasyTag)
                    nbt.getCompound("ability" + i)));
    }

    public void loadAvailableAbilitiesNBT(CompoundTag nbt) {
        for (int i = 0; i < nbt.getInt("count"); i++)
            availableAbilities.add(i, Ability.load((EasyTag)
                    nbt.getCompound("ability" + i)));
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

    public enum ScrollDirection {
        UP,
        DOWN;
    }
}
