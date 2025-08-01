package com.riverstone.unknown303.errorlib.misc;

import com.mojang.logging.LogUtils;
import com.riverstone.unknown303.errorlib.ErrorHelpers;
import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.helpers.keybind.Keybind;
import com.riverstone.unknown303.errorlib.api.networking.ErrorPacketHandler;
import com.riverstone.unknown303.errorlib.api.networking.packets.AbilityKeybindPressedC2SPacket;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import org.lwjgl.glfw.GLFW;

import java.util.List;

@ApiStatus.Internal
public class ErrorKeybinds {
    public static final String ABILITIES_KEY_CATEGORY = Keybind.createKeybindCategory(
            ResourceLocation.fromNamespaceAndPath(ErrorMod.MOD_ID, "abilities"));

    public static final Keybind ABILITIES_SLOT_0 = ErrorHelpers.KEYBIND_HELPER.add(new Keybind(
            ResourceLocation.fromNamespaceAndPath(ErrorMod.MOD_ID, "ability_slot_0"),
                    Keybind.CONTEXT_IN_GAME, true, GLFW.GLFW_KEY_X,
                    ABILITIES_KEY_CATEGORY).setOnClick((event, mapping) ->
            ErrorPacketHandler.sendToServer(new AbilityKeybindPressedC2SPacket(
                    0))));
    public static final Keybind ABILITIES_SLOT_1 = ErrorHelpers.KEYBIND_HELPER.add(new Keybind(
            ResourceLocation.fromNamespaceAndPath(ErrorMod.MOD_ID, "ability_slot_1"),
            Keybind.CONTEXT_IN_GAME, true, GLFW.GLFW_KEY_C,
            ABILITIES_KEY_CATEGORY).setOnClick((event, mapping) ->
                    ErrorPacketHandler.sendToServer(new AbilityKeybindPressedC2SPacket(
                            0))));
    public static final Keybind ABILITIES_SLOT_2 = ErrorHelpers.KEYBIND_HELPER.add(new Keybind(
            ResourceLocation.fromNamespaceAndPath(ErrorMod.MOD_ID, "ability_slot_2"),
            Keybind.CONTEXT_IN_GAME, true, GLFW.GLFW_KEY_V,
            ABILITIES_KEY_CATEGORY).setOnClick((event, mapping) ->
            ErrorPacketHandler.sendToServer(new AbilityKeybindPressedC2SPacket(
                    0))));
    public static final Keybind ABILITIES_SLOT_3 = ErrorHelpers.KEYBIND_HELPER.add(new Keybind(
            ResourceLocation.fromNamespaceAndPath(ErrorMod.MOD_ID, "ability_slot_3"),
            Keybind.CONTEXT_IN_GAME, true, GLFW.GLFW_KEY_B,
            ABILITIES_KEY_CATEGORY).setOnClick((event, mapping) ->
            ErrorPacketHandler.sendToServer(new AbilityKeybindPressedC2SPacket(
                    0))));
    public static final Keybind ABILITIES_SLOT_4 = ErrorHelpers.KEYBIND_HELPER.add(new Keybind(
            ResourceLocation.fromNamespaceAndPath(ErrorMod.MOD_ID, "ability_slot_4"),
            Keybind.CONTEXT_IN_GAME, true, GLFW.GLFW_KEY_N,
            ABILITIES_KEY_CATEGORY).setOnClick((event, mapping) ->
            ErrorPacketHandler.sendToServer(new AbilityKeybindPressedC2SPacket(
                    0))));

    public static void load() {
        List<Keybind> loader = List.of(
        ABILITIES_SLOT_0,
        ABILITIES_SLOT_1,
        ABILITIES_SLOT_2,
        ABILITIES_SLOT_3,
        ABILITIES_SLOT_4
        );
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
