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
    public static final EnchantmentTarget TARGET = ClassTinkerers.getEnum(EnchantmentTarget.class, "FABRIC_SHIELD");
    private final boolean isTreasure;
    private final boolean isCurse;

    /**
     * @param weight     rarity of enchantment.
     * @param isTreasure if enchantment is a treasure enchantment.
     */
    public FabricShieldEnchantment(Rarity weight, boolean isTreasure, boolean isCurse) {
        super(weight, TARGET, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
        this.isTreasure = isTreasure;
        this.isCurse = isCurse;
    }

    /**
     * @param weight     rarity of enchantment.
     * @param isTreasure if enchantment is a treasure enchantment.
     */
    public FabricShieldEnchantment(Rarity weight, boolean isTreasure) {
        this(weight, isTreasure, false);
    }

    /**
     * @param weight rarity of enchantment.
     */
    public FabricShieldEnchantment(Rarity weight) {
        this(weight, false, false);
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
