package com.riverstone.unknown303.errorlib.api.abilities.ability.misc;

public enum AbilityContext {
    CONSTANT,
    TOGGLE,
    HOLD,
    INSTANT;

    @Override
    public String toString() {
        return switch (this) {
            case CONSTANT -> "CONSTANT";
            case TOGGLE -> "TOGGLE";
            case HOLD -> "HOLD";
            case INSTANT -> "INSTANT";
        };
    }
}
