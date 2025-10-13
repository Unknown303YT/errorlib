package com.riverstone.unknown303.errorlib.api.helpers.tier;

import com.riverstone.unknown303.errorlib.ErrorMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.TierSortingRegistry;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class TierInfo {
    private final Logger logger;

    private final int durability;
    private final float attackSpeed;
    private final float tierAttackDamage;
    private final int enchantability;
    @NotNull private TagKey<Block> miningTag;
    @Nullable private Ingredient repairIngredient;

    @Nullable private Tier lowerRank;
    @Nullable private Tier higherRank;

    private boolean isEquivalent = false;

    private static final TagKey<Block> EMPTY = TagKey.create(Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(ErrorMod.MOD_ID, "empty"));
    private static final TagKey<Block> EQUAL_RANK = TagKey.create(Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(ErrorMod.MOD_ID, "equal"));

    public TierInfo(Logger logger, int durability, float attackSpeed,
                    float tierAttackDamage, int enchantability) {
        this.logger = logger;
        this.durability = durability;
        this.attackSpeed = attackSpeed;
        this.tierAttackDamage = tierAttackDamage;
        this.enchantability = enchantability;
        this.miningTag = EMPTY;
        this.repairIngredient = Ingredient.EMPTY;
        this.lowerRank = null;
        this.higherRank = null;
    }

    public TierInfo miningTag(TagKey<Block> miningTag) {
        if (isEquivalent) {
            IllegalStateException exception
                    = new IllegalStateException("Error: TierInfo is using an equivalent rank");
            this.logger.error("Error: TierInfo is using an equivalent rank", exception);
        }

        this.miningTag = miningTag;
        return this;
    }

    public TierInfo repairIngredient(ItemLike repairIngredient) {
        this.repairIngredient = Ingredient.of(repairIngredient);
        return this;
    }

    public TierInfo lowerRank(Tier lowerRank) {
        if (isEquivalent) {
            IllegalStateException exception =
                    new IllegalStateException("Error: TierInfo is using an equivalent rank");
            this.logger.error("Error: TierInfo is using an equivalent rank", exception);
        } if (lowerRank == null) {
            IllegalStateException exception =
                    new IllegalStateException("Error: Lower Rank must be valid");
            this.logger.error("Error: Lower Rank must be valid", exception);
        }

        this.lowerRank = lowerRank;
        return this;
    }

    public TierInfo higherRank(Tier higherRank) {
        if (isEquivalent) {
            IllegalStateException exception =
                    new IllegalStateException("Error: TierInfo is using an equivalent rank");
            this.logger.error("Error: TierInfo is using an equivalent rank", exception);
        } if (higherRank == null) {
            IllegalStateException exception =
                    new IllegalStateException("Error: Higher Rank must be valid");
            this.logger.error("Error: Higher Rank must be valid", exception);
        }

        this.higherRank = higherRank;
        return this;
    }

    public TierInfo equalRank(Tier equalRank) {
        this.lowerRank = equalRank;
        this.higherRank = null;
        boolean nextIsHigher = false;
        for (Tier tier : TierSortingRegistry.getSortedTiers()) {
            if (nextIsHigher) {
                this.higherRank = tier;
                break;
            } if (tier.equals(equalRank)) {
                nextIsHigher = true;
            }
        }

        if (equalRank.getTag() != null) {
            this.miningTag = equalRank.getTag();
        } else {
            this.miningTag = EQUAL_RANK;
        }

        return this;
    }

    public int getLevel() {
        if (isEquivalent) {
            return lowerRank.getLevel();
        }

        if (this.lowerRank != null && this.higherRank != null) {
            return (this.lowerRank.getLevel() + this.higherRank.getLevel()) / 2;
        } else {
            return 0;
        }
    }

    public int getDurability() {
        return this.durability;
    }

    public float getAttackSpeed() {
        return this.attackSpeed;
    }

    public float getTierAttackDamage() {
        return this.tierAttackDamage;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    @NotNull
    public TagKey<Block> getMiningTag() {
        return this.miningTag;
    }

    @NotNull
    public Supplier<Ingredient> getRepairIngredient() {
        return () -> this.repairIngredient;
    }

    public List<Object> getLowerRank() {
        if (this.lowerRank != null) {
            return List.of(this.lowerRank);
        } else {
            return List.of();
        }
    }

    public List<Object> getHigherRank() {
        if (this.higherRank != null) {
            return List.of(this.higherRank);
        } else {
            return List.of();
        }
    }
}
