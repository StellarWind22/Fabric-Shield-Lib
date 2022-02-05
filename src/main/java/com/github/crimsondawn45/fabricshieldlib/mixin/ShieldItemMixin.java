package com.github.crimsondawn45.fabricshieldlib.mixin;

import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLib;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;

/**
 * Makes vanilla shield enchantable with an enchantability of 14.
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
		if(FabricShieldLib.config.allow_vanilla_shield_enchanting) {
			return !item.hasEnchantments();
		} else {
			return false;
		}
	}
	
	@Override
	public int getEnchantability() {
		return FabricShieldLib.config.vanilla_shield_enchantability;
	}
}
