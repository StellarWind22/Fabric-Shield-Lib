package com.github.crimsondawn45.fabricshieldlib.lib.object;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.resource.featuretoggle.FeatureFlags;

import java.util.Optional;

/**
 * Enchantment that works on fabric shields and vanilla shield(Needs events to do anything).
 */
public class FabricShieldEnchantment extends Enchantment {
    private final boolean isTreasure;
    private final boolean isCurse;
    private final boolean vanillaShieldAllow;


    public FabricShieldEnchantment(int weight, int maxLevel, Enchantment.Cost minCost, Enchantment.Cost maxCost, int anvilCost, boolean isCurse, boolean isTreasure, boolean allowOnVanillaShields) {
        super(new Enchantment.Properties(ConventionalItemTags.SHIELDS_TOOLS, Optional.empty(), weight, maxLevel, minCost, maxCost, anvilCost, FeatureFlags.DEFAULT_ENABLED_FEATURES, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
        this.isCurse = isCurse;
        this.isTreasure = isTreasure;
        this.vanillaShieldAllow = allowOnVanillaShields;
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

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        if(vanillaShieldAllow) {
            return super.isAcceptableItem(stack) || stack.getItem() instanceof FabricShield || stack.getItem() instanceof ShieldItem;
        }
        return stack.getItem() instanceof FabricShield;
    }
}
