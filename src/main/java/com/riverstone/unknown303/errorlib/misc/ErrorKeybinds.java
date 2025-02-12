package com.riverstone.unknown303.errorlib.misc;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class ErrorKeybinds {
    public static final String KEY_CATEGORY_ABILITIES = "key.category.errorlib.abilities";

    public static final KeyMapping KEY_ABILITY_SLOT_0 = new KeyMapping("key.errorlib.ability_slot_0", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X, KEY_CATEGORY_ABILITIES);
    public static final KeyMapping KEY_ABILITY_SLOT_1 = new KeyMapping("key.errorlib.ability_slot_1", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, KEY_CATEGORY_ABILITIES);
    public static final KeyMapping KEY_ABILITY_SLOT_2 = new KeyMapping("key.errorlib.ability_slot_2", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY_ABILITIES);
    public static final KeyMapping KEY_ABILITY_SLOT_3 = new KeyMapping("key.errorlib.ability_slot_3", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_B, KEY_CATEGORY_ABILITIES);
    public static final KeyMapping KEY_ABILITY_SLOT_4 = new KeyMapping("key.errorlib.ability_slot_4", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_N, KEY_CATEGORY_ABILITIES);
}
