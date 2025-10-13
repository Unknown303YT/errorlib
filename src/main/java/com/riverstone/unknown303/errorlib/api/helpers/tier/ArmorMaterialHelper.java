package com.riverstone.unknown303.errorlib.api.helpers.tier;

import com.riverstone.unknown303.errorlib.api.general.ModInfo;
import com.riverstone.unknown303.errorlib.api.helpers.ErrorLibHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

import java.util.HashMap;

public class ArmorMaterialHelper extends ErrorLibHelper {
    public ArmorMaterialHelper(ModInfo modInfo) {
        super(modInfo);
    }

    public ErrorLibArmorMaterial createArmorMaterial(String name, ArmorInfo info) {
        return new ErrorLibArmorMaterial(ResourceLocation.fromNamespaceAndPath(modInfo.getModId(), name),
                info.getDurabilityMultiplier(), info.getProtection(), info.getEnchantmentValue(),
                info.getEquipSound(), info.getToughness(), info.getKnockbackResistance(),
                info.getRepairIngredient());
    }

    public ArmorInfo createArmorInfo(int durabilityMultiplier,
                                     HashMap<ArmorItem.Type, Integer> protPerPiece,
                                     float tougness, int enchantability) {
        return new ArmorInfo(durabilityMultiplier,
                new int[] {
                        protPerPiece.get(ArmorItem.Type.HELMET), protPerPiece.get(ArmorItem.Type.CHESTPLATE),
                protPerPiece.get(ArmorItem.Type.LEGGINGS), protPerPiece.get(ArmorItem.Type.BOOTS) },
                tougness, enchantability);
    }
}
