package com.github.crimsondawn45.fabricshieldlib.lib.object;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;

/**
 * Enchantment that works on fabric shields and vanilla shield(Needs events to do anything).
 */
public class FabricShieldEnchantment extends Enchantment {

    /**
     * @param weight rarity of enchantment.
     */
    public FabricShieldEnchantment(Weight weight) {
        super(weight, EnchantmentTarget.BREAKABLE, new EquipmentSlot[] { EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND });
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof FabricShield || stack.getItem() instanceof ShieldItem;
    }

    /**
     * @param stack item stack.
     * @return if item has this enchantment.
     */
    public boolean hasEnchantment(ItemStack stack) {
        return EnchantmentHelper.getLevel(this, stack) > 0;
    }
}