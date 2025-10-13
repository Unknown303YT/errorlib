package com.riverstone.unknown303.errorlib.api.helpers.tier;

import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.misc.CustomArmorMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.util.Lazy;

import java.util.function.Supplier;

public class ErrorLibArmorMaterial implements CustomArmorMaterial {
    private final ResourceLocation armorTypeId;
    private final int durabilityMultiplier;
    private final int[] protection;
    private final int enchantability;
    private final Lazy<SoundEvent> equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = new int[]{ 11, 16, 15, 13};

    ErrorLibArmorMaterial(String name, int durabilityMultiplier, int[] protection, int enchantability,
                          Lazy<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this(ResourceLocation.fromNamespaceAndPath(ErrorMod.MOD_ID, name), durabilityMultiplier, protection, enchantability,
                equipSound, toughness, knockbackResistance, repairIngredient);
    }

    ErrorLibArmorMaterial(ResourceLocation name, int durabilityMultiplier, int[] protection, int enchantability,
                          Lazy<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.armorTypeId = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protection = protection;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public ResourceLocation getId() {
        return this.armorTypeId;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return BASE_DURABILITY[type.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return this.protection[type.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound.get();
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
