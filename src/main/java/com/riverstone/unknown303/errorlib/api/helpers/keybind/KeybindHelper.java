package com.riverstone.unknown303.errorlib.api.helpers.keybind;

import com.riverstone.unknown303.errorlib.api.helpers.ErrorLibHelper;
import com.riverstone.unknown303.errorlib.api.misc.ModInfo;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class KeybindHelper extends ErrorLibHelper.Registrable {
    private final List<Keybind> keybinds = new ArrayList<>();

    public KeybindHelper(ModInfo modInfo) {
        super(modInfo);
    }

    public Keybind setConsumers(Keybind keybind, BiConsumer<InputEvent, KeyMapping> onClick,
                                BiConsumer<InputEvent, KeyMapping> onHold,
                                BiConsumer<RegisterKeyMappingsEvent, KeyMapping> onRegister) {
        keybind.setOnClick(onClick, false);
        keybind.setOnHold(onHold, false);
        keybind.setOnRegister(onRegister);
        return keybind;
    }

    public Keybind add(Keybind keybind) {
        keybinds.add(keybind);
        return keybind;
    }

    public static ResourceLocation getKeybindID(KeyMapping keybind) {
        String id = keybind.getName()
                .replace("key.", "").replace('.', ':');
        return ResourceLocation.parse(id);
    }

    @Override
    public void register(IEventBus eventBus) {
        keybinds.forEach(eventBus::register);
        keybinds.forEach(keybind -> eventBus.register(keybind.getEventDispatcher()));
    }
}
