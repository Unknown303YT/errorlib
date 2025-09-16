package com.riverstone.unknown303.errorlib.api.misc;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.riverstone.unknown303.errorlib.ErrorHelpers;
import com.riverstone.unknown303.errorlib.misc.GsonHelper;
import net.minecraft.nbt.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class EasyTag extends CompoundTag {
    public void putItem(String pKey, Item pValue) {
        putString(pKey, ForgeRegistries.ITEMS.getKey(pValue).toString());
    }

    public void putBlock(String pKey, Block pValue) {
        putString(pKey, ForgeRegistries.BLOCKS.getKey(pValue).toString());
    }

    public void putItemStack(String pKey, ItemStack pValue) {
        putIngredient(pKey, Ingredient.of(pValue));
    }

    public void putIngredient(String pKey, Ingredient pValue) {
        putJson(pKey, pValue.toJson());
    }

    public void putJson(String pKey, JsonElement pValue) {
        putString(pKey, GsonHelper.toJsonString(pValue));
    }

    public <T> void putObject(String pKey, T pValue) {
        putJson(pKey, GsonHelper.fromObject(pValue));
    }

    public Item getItem(String pKey) {
        return ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(getString(pKey)));
    }

    public Block getBlock(String pKey) {
        return ForgeRegistries.BLOCKS.getValue(ResourceLocation.parse(getString(pKey)));
    }

    public ItemStack getItemStack(String pKey) {
        return getIngredient(pKey).getItems()[0];
    }

    public Ingredient getIngredient(String pKey) {
        return Ingredient.fromJson(GsonHelper.fromJsonString(getString(pKey)));
    }

    public JsonElement getJson(String pKey) {
        return GsonHelper.fromJsonString(getString(pKey));
    }

    public <T> T getObject(String pKey, Class<? super T> type) {
        return GsonHelper.toObject(getJson(pKey), type);
    }
}
