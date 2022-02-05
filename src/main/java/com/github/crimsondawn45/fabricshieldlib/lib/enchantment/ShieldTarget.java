package com.github.crimsondawn45.fabricshieldlib.lib.enchantment;

import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShield;
import com.github.crimsondawn45.fabricshieldlib.mixin.EnchantmentTargetMixin;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class ShieldTarget extends EnchantmentTargetMixin {

    @Override
    public boolean isAcceptableItem(Item item) {
        return item instanceof FabricShield || item.equals(Items.SHIELD);
    }
}