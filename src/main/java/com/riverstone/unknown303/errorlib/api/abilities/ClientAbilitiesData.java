package com.riverstone.unknown303.errorlib.api.abilities;

import net.minecraft.world.entity.player.Player;

import java.util.HashMap;

public class ClientAbilitiesData {
    private static final HashMap<String, IAbilities> DATA = new HashMap<>();

    public static void set(Player player, IAbilities abilities) {
        set(player.getStringUUID(), abilities);
    }

    public static IAbilities get(Player player) {
        return get(player.getStringUUID());
    }

    public static void set(String uuid, IAbilities abilities) {
        DATA.put(uuid, abilities);
    }

    public static IAbilities get(String uuid) {
        return DATA.get(uuid);
    }
}
