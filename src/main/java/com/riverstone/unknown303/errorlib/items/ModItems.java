package com.riverstone.unknown303.errorlib.items;

import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.tools.DaggerItem;
import net.minecraft.world.item.*;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.item.armortrim.TrimPatterns;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ErrorMod.MOD_ID);

//    public static final RegistryObject<Item> CORRUPTED_HEART = ITEMS.register("corrupted_heart",
//            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)
//                    .stacksTo(1).fireResistant()));

    public static final RegistryObject<Item> WOOD_DAGGER = ITEMS.register("wood_dagger",
            () -> new DaggerItem(Tiers.WOOD, 3, -2F, new Item.Properties()));

    public static final RegistryObject<Item> STONE_DAGGER = ITEMS.register("stone_dagger",
            () -> new DaggerItem(Tiers.STONE, 3, -2F, new Item.Properties()));

    public static final RegistryObject<Item> IRON_DAGGER = ITEMS.register("iron_dagger",
            () -> new DaggerItem(Tiers.IRON, 3, -2F, new Item.Properties()));

    public static final RegistryObject<Item> GOLD_DAGGER = ITEMS.register("gold_dagger",
            () -> new DaggerItem(Tiers.GOLD, 3, -2F, new Item.Properties()));

    public static final RegistryObject<Item> DIAMOND_DAGGER = ITEMS.register("diamond_dagger",
            () -> new DaggerItem(Tiers.DIAMOND, 3, -2F, new Item.Properties()));

    public static final RegistryObject<Item> NETHERITE_DAGGER = ITEMS.register("netherite_dagger",
            () -> new DaggerItem(Tiers.NETHERITE, 3, -2F, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
