package com.github.crimsondawn45.fabricshieldlib.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;

/**
 * Makes vanilla shield enchantable with an enchantability of 9.
 */
@Mixin(ShieldItem.class)
public class ShieldItemMixin extends Item {

	/**
	 * @param settings item settings
	 */
    public ShieldItemMixin(Settings settings) {
		super(settings);
	}

	@Override
	public boolean isEnchantable(ItemStack item) {
		return !item.hasEnchantments();
	}
	
	@Override
	public int getEnchantability() {
		return 9;
	}
}
