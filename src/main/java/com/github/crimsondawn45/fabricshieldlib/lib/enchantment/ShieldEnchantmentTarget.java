package com.github.crimsondawn45.fabricshieldlib.lib.enchantment;

import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShield;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class ShieldEnchantmentTarget extends EnchantmentTargetMixin {

    @Override
    public boolean isAcceptableItem(Item item) {
        return item == Items.SHIELD || item instanceof FabricShield;
    }
}


