package com.riverstone.unknown303.errorlib.api.helpers.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Keybind {
    public static final int CONTEXT_IN_GAME = 0;
    public static final int CONTEXT_IN_MENU = 1;
    public static final int CONTEXT_UNIVERSAL = 2;

    private final ResourceLocation name;
    private final KeyMapping keyMapping;

    private EventDispatcher eventDispatcher = null;

    private BiConsumer<InputEvent, Keybind> onInput;
    private BiConsumer<InputEvent, KeyMapping> onClick =
            null;
    private BiConsumer<InputEvent, KeyMapping> onHold =
            null;
    private BiConsumer<RegisterKeyMappingsEvent, KeyMapping> onRegister =
            null;

    public Keybind(ResourceLocation name, int context,
                                        boolean isKeyboard, int key,
                                        ResourceLocation category,
                   BiConsumer<InputEvent, Keybind> onInput) {
        this(name, context, isKeyboard, key,
                createKeybindCategory(category), onInput);
    }

    public Keybind(ResourceLocation name, int context,
                                        boolean isKeyboard, int key, String category,
                   BiConsumer<InputEvent, Keybind> onInput) {
        this.name = name;
        this.keyMapping = new KeyMapping(name.toLanguageKey("key"),
                switch (context) {
                    case CONTEXT_IN_GAME -> KeyConflictContext.IN_GAME;
                    case CONTEXT_IN_MENU -> KeyConflictContext.GUI;
                    default -> KeyConflictContext.UNIVERSAL;
                },
                isKeyboard ? InputConstants.Type.KEYSYM : InputConstants.Type.MOUSE,
                key, category);
        this.onInput = onInput;
    }

    public static String createKeybindCategory(ResourceLocation categoryID) {
        return categoryID.toLanguageKey("key.category");
    }

    public void setOnClick(BiConsumer<InputEvent, KeyMapping> onClick, boolean disableOnInput) {
        if (disableOnInput)
            this.onInput = null;
        this.onClick = onClick;
    }

    public void setOnHold(BiConsumer<InputEvent, KeyMapping> onHold, boolean disableOnInput) {
        if (disableOnInput)
            this.onInput = null;
        this.onHold = onHold;
    }

    public void setOnRegister(BiConsumer<RegisterKeyMappingsEvent, KeyMapping> onRegister) {
        this.onRegister = onRegister;
    }

    public ResourceLocation getName() {
        return this.name;
    }

    public KeyMapping getKeyMapping() {
        return this.keyMapping;
    }

    @SubscribeEvent
    private void onInput(InputEvent event) {
        if (this.onInput != null)
            this.onInput.accept(event, this);
        if (this.onClick != null) {
            if (this.keyMapping.consumeClick())
                this.onClick.accept(event, this.keyMapping);
        }
        if (this.onHold != null) {
            if (this.keyMapping.isDown())
                this.onHold.accept(event, this.keyMapping);
        }
    }

    public EventDispatcher getEventDispatcher() {
        if (eventDispatcher == null)
            eventDispatcher = new EventDispatcher(this);
        return eventDispatcher;
    }

    public static class EventDispatcher {
        private final Keybind keybind;

        private EventDispatcher(Keybind keybind) {
            this.keybind = keybind;
        }

        @SubscribeEvent
        private void onRegisterMappings(RegisterKeyMappingsEvent event) {
            event.register(keybind.keyMapping);
            if (keybind.onRegister != null)
                keybind.onRegister.accept(event, keybind.keyMapping);
        }
    }
}
