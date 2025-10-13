package com.riverstone.unknown303.errorlib.api.helpers.component;

import com.riverstone.unknown303.errorlib.api.general.ModInfo;
import com.riverstone.unknown303.errorlib.api.helpers.ErrorLibHelper;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class ComponentHelper extends ErrorLibHelper {
    public ComponentHelper(ModInfo modInfo) {
        super(modInfo);
    }

    public Component createTranslatableComponent(String type, String id) {
        return Component.translatable(Util.makeDescriptionId(type,
                ResourceLocation.fromNamespaceAndPath(this.getModId(), id)));
    }

    public Component combineComponents(MutableComponent base, MutableComponent addition) {
        return base.append(addition);
    }

    public Component combineComponents(MutableComponent base, List<MutableComponent> additions) {
        MutableComponent toReturn = base;
        for (MutableComponent addition : additions) {
            toReturn.append(addition);
        }

        return toReturn;
    }
}
