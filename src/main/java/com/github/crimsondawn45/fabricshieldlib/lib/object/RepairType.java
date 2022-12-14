package com.github.crimsondawn45.fabricshieldlib.lib.object;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;

import java.util.Collection;

public class RepairType {
    static boolean getRepairType(
            ItemStack ingredient,
            RepairItemType repairType,
            Item[] repairItems,
            TagKey<Item> repairTag,
            Ingredient repairIngredients,
            Collection<TagKey<Item>> repairTags
    ) {
        switch (repairType) {
            case ARRAY:
                for (Item item : repairItems) {
                    if (item.equals(ingredient.getItem())) {
                        return true;
                    }
                }
                return false;
            case TAG:
                return ingredient.isIn(repairTag);
            case INGREDIENT:
                return repairIngredients.test(ingredient);
            case TAG_ARRAY:
                for (TagKey<Item> tag : repairTags) {
                    if (ingredient.isIn(tag)) {
                        return true;
                    }
                }
            default:
                return false;
        }
    }
}
