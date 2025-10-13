package com.riverstone.unknown303.errorlib.api.helpers.trim.material;

import com.riverstone.unknown303.errorlib.api.general.ModInfo;
import com.riverstone.unknown303.errorlib.api.helpers.ErrorLibHelper;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;

import java.util.LinkedHashMap;
import java.util.Map;

public class TrimMaterialHelper extends ErrorLibHelper {
    private static final LinkedHashMap<ResourceKey<TrimMaterial>, Item> trimMaterialItems = new LinkedHashMap<>();
    private static final LinkedHashMap<ResourceKey<TrimMaterial>, TextColor> trimMaterialTextColors = new LinkedHashMap<>();
    private static final LinkedHashMap<ResourceKey<TrimMaterial>, TrimMaterialColor> trimMaterialColors = new LinkedHashMap<>();

    public TrimMaterialHelper(ModInfo modInfo) {
        super(modInfo);
    }

    public void registerTrimMaterial(String name, Item item, TextColor textColor, TrimMaterialColor color) {
        ResourceKey<TrimMaterial> trimKey = ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(this.getModId(), name));
        trimMaterialItems.put(trimKey, item);
        trimMaterialTextColors.put(trimKey, textColor);
        trimMaterialColors.put(trimKey, color);
    }

    public void bootstrap(BootstapContext<TrimMaterial> context) {
        for (ResourceKey<TrimMaterial> trimMaterial : trimMaterialItems.keySet()) {
            Item item = trimMaterialItems.get(trimMaterial);
            TextColor textColor = trimMaterialTextColors.get(trimMaterial);
            TrimMaterialColor color = trimMaterialColors.get(trimMaterial);
            register(context, trimMaterial, item, Style.EMPTY.withColor(textColor), color);
        }
    }

    public void register(BootstapContext<TrimMaterial> context, ResourceKey<TrimMaterial> trimKey, Item item,
                         Style style, TrimMaterialColor itemModelIndex) {
        TrimMaterial trimMaterial = TrimMaterial.create(trimKey.location().getPath(), item, TrimMaterialColor.getItemModelIndex(itemModelIndex),
                Component.translatable(Util.makeDescriptionId("trim_material", trimKey.location())).withStyle(style), Map.of());
        context.register(trimKey, trimMaterial);
    }

    public enum TrimMaterialColor {
        QUARTZ,
        IRON,
        NETHERITE,
        REDSTONE,
        COPPER,
        GOLD,
        EMERALD,
        DIAMOND,
        LAPIS,
        AMETHYST;

        public static float getItemModelIndex(TrimMaterialColor materialColor) {
            if (materialColor == TrimMaterialColor.QUARTZ) {
                return 0.1F;
            } if (materialColor == TrimMaterialColor.IRON) {
                return 0.2F;
            } if (materialColor == TrimMaterialColor.NETHERITE) {
                return 0.3F;
            } if (materialColor == TrimMaterialColor.REDSTONE) {
                return 0.4F;
            } if (materialColor == TrimMaterialColor.COPPER) {
                return 0.5F;
            } if (materialColor == TrimMaterialColor.GOLD) {
                return 0.6F;
            } if (materialColor == TrimMaterialColor.EMERALD) {
                return 0.7F;
            } if (materialColor == TrimMaterialColor.DIAMOND) {
                return 0.8F;
            } if (materialColor == TrimMaterialColor.LAPIS) {
                return 0.9F;
            }
            return 1.0F;
        }
    }
}
