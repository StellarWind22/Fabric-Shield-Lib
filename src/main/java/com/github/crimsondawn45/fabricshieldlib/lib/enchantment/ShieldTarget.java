package com.github.crimsondawn45.fabricshieldlib.lib.enchantment;

import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShield;
import com.github.crimsondawn45.fabricshieldlib.mixin.EnchantmentTargetMixin;

import net.minecraft.item.Item;
import net.minecraft.item.ShieldItem;

public class ShieldTarget extends EnchantmentTargetMixin {

    @Override
    public boolean isAcceptableItem(Item item) {

        if(item instanceof ShieldItem) {
            return true;
        }

        if(item instanceof FabricShield) {
            FabricShield shield = (FabricShield) item;
            return shield.acceptsShieldEnchantments();
        }

        return false;
    }
}