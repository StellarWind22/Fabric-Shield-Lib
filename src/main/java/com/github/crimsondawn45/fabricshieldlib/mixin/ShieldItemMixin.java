package com.github.crimsondawn45.fabricshieldlib.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;

import com.github.crimsondawn45.fabricshieldlib.lib.config.FabricShieldLibConfig;

/**
 * Makes vanilla shield enchantable with an enchantability of 14.
 */
@Mixin(ShieldItem.class)
public class ShieldItemMixin extends Item {
    /**
     * @param settings item settings.
     */
    public ShieldItemMixin(Settings settings) {
        super(settings.enchantable(FabricShieldLibConfig.vanilla_shield_enchantability));
    }
}
