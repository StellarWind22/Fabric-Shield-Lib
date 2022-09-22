package com.github.crimsondawn45.fabricshieldlib.lib.object;

import com.chocohead.mm.api.ClassTinkerers;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 * Enchantment that works on fabric shields and vanilla shield(Needs events to do anything).
 */
public class FabricShieldEnchantment extends Enchantment {

    private boolean isTreasure;
    private boolean isCurse;

    /**
     * @param weight rarity of enchantment.
     */
    public FabricShieldEnchantment(Rarity weight) {
        super(weight, ClassTinkerers.getEnum(EnchantmentTarget.class, "FABRIC_SHIELD"), new EquipmentSlot[] { EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND });
        this.isTreasure = false;
        this.isCurse = false;
    }

    /**
     * @param weight rarity of enchantment.
     * @param isTreasure if enchantment is a treasure enchantment.
     */
    public FabricShieldEnchantment(Rarity weight, boolean isTreasure) {
        super(weight, ClassTinkerers.getEnum(EnchantmentTarget.class, "FABRIC_SHIELD"), new EquipmentSlot[] { EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND });
        this.isTreasure = isTreasure;
        this.isCurse = false;
    }

    /**
     * @param weight rarity of enchantment.
     * @param isTreasure if enchantment is a treasure enchantment.
     */
    public FabricShieldEnchantment(Rarity weight, boolean isTreasure, boolean isCurse) {
        super(weight, ClassTinkerers.getEnum(EnchantmentTarget.class, "FABRIC_SHIELD"), new EquipmentSlot[] { EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND });
        this.isTreasure = isTreasure;
        this.isCurse = isCurse;
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