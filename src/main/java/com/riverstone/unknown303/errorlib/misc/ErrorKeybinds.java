package com.riverstone.unknown303.errorlib.misc;

import com.riverstone.unknown303.errorlib.ErrorHelpers;
import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.abilities.PlayerAbilitiesProvider;
import com.riverstone.unknown303.errorlib.api.helpers.keybind.Keybind;
import com.riverstone.unknown303.errorlib.api.helpers.keybind.KeybindHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

public class ErrorKeybinds {
    public static final String ABILITIES_KEY_CATEGORY = Keybind.createKeybindCategory(
            ResourceLocation.fromNamespaceAndPath(ErrorMod.MOD_ID, "abilities"));

    public static final Keybind ABILITIES_SLOT_0 = ErrorHelpers.KEYBIND_HELPER.add(
            new Keybind(ResourceLocation
                    .fromNamespaceAndPath(ErrorMod.MOD_ID, "ability_slot_0"),
                    Keybind.CONTEXT_IN_GAME, true, GLFW.GLFW_KEY_X,
                    ABILITIES_KEY_CATEGORY, (event, keybind) -> {
                if (Minecraft.getInstance().player != null)
                    Minecraft.getInstance().player
                            .getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(
                                    abilities -> abilities.pressAbilityKeybind(keybind));
            }));

    public static int getAbilitySlot(Keybind keybind) {
        String path = keybind.getName().getPath();
        String id = path.replace("ability_slot_", "");
        return Integer.parseInt(id);
    }
}
