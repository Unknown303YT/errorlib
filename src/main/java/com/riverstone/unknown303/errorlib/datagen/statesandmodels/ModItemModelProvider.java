package com.riverstone.unknown303.errorlib.datagen.statesandmodels;

import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.blocks.ModBlocks;
import com.riverstone.unknown303.errorlib.items.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ErrorMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
//        simpleItem(ModItems.CORRUPTED_HEART);
//        simpleBlockItem(ModBlocks.COMBO_BLOCK);
        handheldItem(ModItems.WOOD_DAGGER);
        handheldItem(ModItems.STONE_DAGGER);
        handheldItem(ModItems.IRON_DAGGER);
        handheldItem(ModItems.GOLD_DAGGER);
        handheldItem(ModItems.DIAMOND_DAGGER);
        handheldItem(ModItems.NETHERITE_DAGGER);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(ErrorMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> block) {
        return withExistingParent(block.getId().getPath(),
                new ResourceLocation(ErrorMod.MOD_ID, "block/" + block.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(ErrorMod.MOD_ID, "item/" + item.getId().getPath()));
    }
}
