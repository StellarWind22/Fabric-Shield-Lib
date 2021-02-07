package me.crimsondawn45.fabricshieldlib.lib;

import java.util.HashSet;
import java.util.Set;

import me.crimsondawn45.fabricshieldlib.lib.object.FabricShield;
import me.crimsondawn45.fabricshieldlib.lib.object.ShieldEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

public class ShieldRegistry {
	//Shield item stuff
	private static final Set<FabricShield> shields = new HashSet<FabricShield>();
	private static final Set<ShieldEnchantment> enchantments = new HashSet<ShieldEnchantment>();

	/**
	 * registerShield
	 * 
	 * Registers an instance of FabricShieldItem or Item into the shield registry.
	 * 
	 * @param shield - Instance of shield to register.
	 */
	public static void register(FabricShield shield) {
		shields.add(shield);
	}
	
	public static void register(ShieldEnchantment enchantment) {
		enchantments.add(enchantment);
	}
	
	/**
	 * containsShield
	 * 
	 * Check if the shield registry contains a particular Item instance.
	 * 
	 * @param shield - Item instance to check for.
	 */
	public static boolean containsShield(FabricShield shield) {
		return shields.contains(shield);
	}

	/**
	 * getAllFabricShields
	 * 
	 * @return Every registered FabricShield instance at the time this is invoked.
	 */
	public static FabricShield[] getAllFabricShields() {
		FabricShield[] result = new FabricShield[shields.size()];
		result = shields.toArray(result);
		return result;
	}
	
	public static ShieldEnchantment[] getAllShieldEnchantments() {
		ShieldEnchantment[] result = new ShieldEnchantment[enchantments.size()];
		result = enchantments.toArray(result);
		return result;
	}
	
	public static boolean isFabricShield(Item shield) {
		return shield instanceof FabricShield;
	}
	
	public static boolean isShieldEnchantment(Enchantment enchantment) {
		return enchantment instanceof ShieldEnchantment;
	}
}