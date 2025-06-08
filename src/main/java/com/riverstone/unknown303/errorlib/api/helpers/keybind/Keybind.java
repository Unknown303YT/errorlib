package com.riverstone.unknown303.errorlib.api.helpers.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.function.BiConsumer;

public class Keybind {
    public static final int CONTEXT_IN_GAME = 0;
    public static final int CONTEXT_IN_MENU = 1;
    public static final int CONTEXT_UNIVERSAL = 2;

    private final ResourceLocation name;
    private final KeyMapping keyMapping;

    private EventDispatcher modEventDispatcher = null;

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
                                        ResourceLocation category) {
        this(name, context, isKeyboard, key,
                createKeybindCategory(category));
    }

    public Keybind(ResourceLocation name, int context,
                                        boolean isKeyboard, int key, String category) {
        this.name = name;
        this.keyMapping = new KeyMapping(name.toLanguageKey("key"),
                switch (context) {
                    case CONTEXT_IN_GAME -> KeyConflictContext.IN_GAME;
                    case CONTEXT_IN_MENU -> KeyConflictContext.GUI;
                    default -> KeyConflictContext.UNIVERSAL;
                },
                isKeyboard ? InputConstants.Type.KEYSYM : InputConstants.Type.MOUSE,
                key, category);
    }

    public static String createKeybindCategory(ResourceLocation categoryID) {
        return categoryID.toLanguageKey("key.category");
    }

    public Keybind setOnClick(BiConsumer<InputEvent, KeyMapping> onClick) {
        this.onClick = onClick;
        return this;
    }

    public Keybind setOnHold(BiConsumer<InputEvent, KeyMapping> onHold) {
        this.onHold = onHold;
        return this;
    }

    public Keybind setOnRegister(BiConsumer<RegisterKeyMappingsEvent, KeyMapping> onRegister) {
        this.onRegister = onRegister;
        return this;
    }

    public ResourceLocation getName() {
        return this.name;
    }

    public KeyMapping getKeyMapping() {
        return this.keyMapping;
    }

    public void onInput(InputEvent event) {
        if (this.onClick != null) {
            if (this.keyMapping.consumeClick())
                this.onClick.accept(event, this.keyMapping);
        }
        if (this.onHold != null) {
            if (this.keyMapping.isDown())
                this.onHold.accept(event, this.keyMapping);
        }
    }

    private void onRegisterMappings(RegisterKeyMappingsEvent event) {
        event.register(keyMapping);
        if (onRegister != null)
            onRegister.accept(event, keyMapping);
    }

    public void eventDispatcher(IEventBus eventBus) {
        if (eventDispatcher == null)
            eventDispatcher = new EventDispatcher(this, eventBus);
    }

    public static class EventDispatcher {
        private final Keybind keybind;

        private EventDispatcher(Keybind keybind, IEventBus eventBus) {
            this.keybind = keybind;
            eventBus.addListener(this::onRegisterMappings);
            MinecraftForge.EVENT_BUS.addListener(this::onInput);
        }

        public void onRegisterMappings(RegisterKeyMappingsEvent event) {
            keybind.onRegisterMappings(event);
        }

        public void onInput(InputEvent event) {
            keybind.onInput(event);
        }
    }
}
