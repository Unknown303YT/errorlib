package com.riverstone.unknown303.errorlib;

import com.riverstone.unknown303.errorlib.api.helpers.keybind.KeybindHelper;
import com.riverstone.unknown303.errorlib.api.misc.ModInfo;
import com.riverstone.unknown303.errorlib.api.helpers.registry.RegistryHelper;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ErrorHelpers {
    public static final ModInfo ERRORLIB_INFO = new ModInfo(ErrorMod.MOD_ID);

    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(ERRORLIB_INFO);

    public static final KeybindHelper KEYBIND_HELPER = new KeybindHelper(ERRORLIB_INFO);
}
