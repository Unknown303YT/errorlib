package com.riverstone.unknown303.errorlib.api.helpers.horse_armor;

import com.riverstone.unknown303.errorlib.api.general.ModInfo;
import com.riverstone.unknown303.errorlib.api.helpers.ErrorLibHelper;
import com.riverstone.unknown303.errorlib.api.misc.CustomArmorMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class HorseArmorHelper extends ErrorLibHelper {
    public HorseArmorHelper(ModInfo modInfo) {
        super(modInfo);
    }
    public RegistryObject<Item> registerHorseArmor(CustomArmorMaterial material,
                                                   Item.Properties properties) {
        int protection;
        if (material.getDefenseForType(ArmorItem.Type.CHESTPLATE) +
                material.getDefenseForType(ArmorItem.Type.BOOTS) == 11) {
            protection = material.getDefenseForType(ArmorItem.Type.CHESTPLATE) +
                    material.getDefenseForType(ArmorItem.Type.BOOTS) + 2;
        } else {
            protection = material.getDefenseForType(ArmorItem.Type.CHESTPLATE) +
                    material.getDefenseForType(ArmorItem.Type.BOOTS);
        }
        return this.getRegister().register(material.getPath() + "_horse_armor",
                () -> new HorseArmorItem(protection, createHorseArmorTexture(material.getPath()), properties.stacksTo(1)));
    }

    public RegistryObject<Item> registerVanillaHorseArmor(ArmorMaterial material,
                                                          Item.Properties properties) {
        int protection;
        if (material.getDefenseForType(ArmorItem.Type.CHESTPLATE) +
                material.getDefenseForType(ArmorItem.Type.BOOTS) == 11) {
            protection = material.getDefenseForType(ArmorItem.Type.CHESTPLATE) +
                    material.getDefenseForType(ArmorItem.Type.BOOTS) + 2;
        } else {
            protection = material.getDefenseForType(ArmorItem.Type.CHESTPLATE) +
                    material.getDefenseForType(ArmorItem.Type.BOOTS);
        }
        return registerHorseArmor(material.getName(), protection, properties);
    }

    public RegistryObject<Item> registerHorseArmor(String armorType, int protection, Item.Properties properties) {
        return this.getRegister().register(armorType + "_horse_armor",
                () -> new HorseArmorItem(protection, createHorseArmorTexture(armorType), properties.stacksTo(1)));
    }

    private ResourceLocation createHorseArmorTexture(String armorId) {
        return ResourceLocation.fromNamespaceAndPath(this.getModId(), "textures/entity/horse/armor/horse_armor_" + armorId + ".png");
    }
}
