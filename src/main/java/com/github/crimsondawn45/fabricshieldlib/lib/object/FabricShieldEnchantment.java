package com.github.crimsondawn45.fabricshieldlib.lib.object;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;

import java.util.Optional;

/**
 * Enchantment that works on fabric shields and vanilla shield(Needs events to do anything).
 */
public class FabricShieldEnchantment extends Enchantment {
    private final boolean isTreasure;
    private final boolean isCurse;

    public FabricShieldEnchantment(int weight, int maxLevel, Enchantment.Cost minCost, Enchantment.Cost maxCost, int anvilCost, boolean isCurse, boolean isTreasure) {
        super(new Enchantment.Properties(ConventionalItemTags.SHIELDS_TOOLS, Optional.empty(), weight, maxLevel, minCost, maxCost, anvilCost, FeatureFlags.DEFAULT_ENABLED_FEATURES, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
        this.isCurse = isCurse;
        this.isTreasure = isTreasure;
    }

    @Override
    public boolean isTreasure() {
        return this.isTreasure;
    }

    @Override
    public boolean isCursed() {
        return this.isCurse;
    }

    /**
     * @param stack item stack.
     * @return if item has this enchantment.
     */
    public boolean hasEnchantment(ItemStack stack) {
        return EnchantmentHelper.getLevel(this, stack) > 0;
    }


}
