package com.riverstone.unknown303.errorlib.misc;

import com.riverstone.unknown303.errorlib.ErrorHelpers;
import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.abilities.PlayerAbilitiesProvider;
import com.riverstone.unknown303.errorlib.api.helpers.keybind.Keybind;
import com.riverstone.unknown303.errorlib.api.helpers.keybind.KeybindHelper;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;

public class ErrorKeybinds {
    public static final String ABILITIES_KEY_CATEGORY = Keybind.createKeybindCategory(
            ResourceLocation.fromNamespaceAndPath(ErrorMod.MOD_ID, "abilities"));

    public static final Keybind ABILITIES_SLOT_0 = ErrorHelpers.KEYBIND_HELPER.add(new Keybind(
            ResourceLocation.fromNamespaceAndPath(ErrorMod.MOD_ID, "ability_slot_0"),
                    Keybind.CONTEXT_IN_GAME, true, GLFW.GLFW_KEY_X,
                    ABILITIES_KEY_CATEGORY, (event, keybind) -> {
                Player player = Minecraft.getInstance().player;
                if (player != null)
                    player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(
                                    abilities -> abilities.pressAbilityKeybind(keybind, player));
            }));
    public static final Keybind ABILITIES_SLOT_1 = ErrorHelpers.KEYBIND_HELPER.add(new Keybind(
            ResourceLocation.fromNamespaceAndPath(ErrorMod.MOD_ID, "ability_slot_1"),
            Keybind.CONTEXT_IN_GAME, true, GLFW.GLFW_KEY_C,
            ABILITIES_KEY_CATEGORY, (event, keybind) -> {
        Player player = Minecraft.getInstance().player;
        if (player != null)
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(
                    abilities -> abilities.pressAbilityKeybind(keybind, player));
    }));
    public static final Keybind ABILITIES_SLOT_2 = ErrorHelpers.KEYBIND_HELPER.add(new Keybind(
            ResourceLocation.fromNamespaceAndPath(ErrorMod.MOD_ID, "ability_slot_2"),
            Keybind.CONTEXT_IN_GAME, true, GLFW.GLFW_KEY_V,
            ABILITIES_KEY_CATEGORY, (event, keybind) -> {
        Player player = Minecraft.getInstance().player;
        if (player != null)
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(
                    abilities -> abilities.pressAbilityKeybind(keybind, player));
    }));
    public static final Keybind ABILITIES_SLOT_3 = ErrorHelpers.KEYBIND_HELPER.add(new Keybind(
            ResourceLocation.fromNamespaceAndPath(ErrorMod.MOD_ID, "ability_slot_3"),
            Keybind.CONTEXT_IN_GAME, true, GLFW.GLFW_KEY_B,
            ABILITIES_KEY_CATEGORY, (event, keybind) -> {
        Player player = Minecraft.getInstance().player;
        if (player != null)
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(
                    abilities -> abilities.pressAbilityKeybind(keybind, player));
    }));
    public static final Keybind ABILITIES_SLOT_4 = ErrorHelpers.KEYBIND_HELPER.add(new Keybind(
            ResourceLocation.fromNamespaceAndPath(ErrorMod.MOD_ID, "ability_slot_4"),
            Keybind.CONTEXT_IN_GAME, true, GLFW.GLFW_KEY_N,
            ABILITIES_KEY_CATEGORY, (event, keybind) -> {
        Player player = Minecraft.getInstance().player;
        if (player != null)
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(
                    abilities -> abilities.pressAbilityKeybind(keybind, player));
    }));

    public static int getAbilitySlot(Keybind keybind) {
        String path = keybind.getName().getPath();
        String id = path.replace("ability_slot_", "");
        return Integer.parseInt(id);
    }

    public static Keybind getAbilitySlotKeybind(int slot) {
        return switch (slot) {
            case 0 -> ABILITIES_SLOT_0;
            case 1 -> ABILITIES_SLOT_1;
            case 2 -> ABILITIES_SLOT_2;
            case 3 -> ABILITIES_SLOT_3;
            case 4 -> ABILITIES_SLOT_4;
            default -> {
                IllegalArgumentException exception = new IllegalArgumentException("Expected slot id of 0-4, got " + slot);
                Minecraft.crash(CrashReport.forThrowable(exception, exception.getMessage()));
                throw new RuntimeException(exception);
            }
        };
    }
}
