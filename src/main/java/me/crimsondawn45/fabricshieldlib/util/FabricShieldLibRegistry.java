package me.crimsondawn45.fabricshieldlib.util;

import java.util.ArrayList;
import java.util.List;

import me.crimsondawn45.fabricshieldlib.object.FabricBannerShield;
import me.crimsondawn45.fabricshieldlib.object.FabricShield;
import me.crimsondawn45.fabricshieldlib.object.FabricShieldEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

public class FabricShieldLibRegistry
{
	private static final List<Item> allShields = new ArrayList<Item>();
	private static final List<FabricShield> fabricShields = new ArrayList<FabricShield>();
	private static final List<FabricBannerShield> fabricBannerShields = new ArrayList<FabricBannerShield>();
	public static final List<FabricShieldEnchantment> enchantments = new ArrayList<FabricShieldEnchantment>();
	
	/**
	 * registerShield
	 * 
	 * Registers an instance of FabricShieldItem or Item into the shield registry.
	 * 
	 * @param shield - Instance of shield to register.
	 */
	public static void registerShield(Item shield)
	{
		allShields.add(shield);
		
		if(shield instanceof FabricShield)
		{
			fabricShields.add((FabricShield) shield);
			
			if(shield instanceof FabricBannerShield)
			{
				fabricBannerShields.add((FabricBannerShield) shield);
			}
		}
	}
	
	/**
	 * registerShieldEnchantment
	 * 
	 * Registers an instance of FabricShieldEnchantment into the shield registry.
	 * 
	 * @param enchantment - Instance of FabricShieldEnchantment to register.
	 */
	public static void registerShieldEnchantment(FabricShieldEnchantment enchantment)
	{
		enchantments.add(enchantment);
	}
	
	/**
	 * containsShield
	 * 
	 * Check if the shield registry contains a particular Item instance.
	 * 
	 * @param shield - Item instance to check for.
	 */
	public static boolean containsShield(Item shield)
	{
		return allShields.contains(shield);
	}
	
	/**
	 * containsFabricShield
	 * 
	 * Check if the shield registry contains a particular FabricShield instance.
	 * 
	 * @param shield - Item instance to check for.
	 */
	public static boolean containsFabricShield(Item shield)
	{
		return fabricShields.contains(shield);
	}
	
	/**
	 * containsFabricBannerShield
	 * 
	 * Check if the shield registry contains a particular FabricBannerShield instance.
	 * 
	 * @param shield - Item instance to check for.
	 */
	public static boolean containsFabricBannerShield(Item shield)
	{
		return fabricBannerShields.contains(shield);
	}
	
	/**
	 * containsShieldEnchantment
	 * 
	 * Check if the enchantment registry contains a particular FabricShieldEnchantment instance.
	 * 
	 * @param shield - Item instance to check for.
	 */
	public static boolean containsShieldEnchantment(Enchantment enchantment)
	{
		return enchantments.contains(enchantment);	
	}
	
	/**
	 * getAllShields
	 * 
	 * @return Every registered Item instance at the time this is invoked.
	 */
	public static Item[] getAllShields()
	{
		return (Item[]) allShields.toArray();
	}
	
	/**
	 * getAllFabricShields
	 * 
	 * @return Every registered FabricShield instance at the time this is invoked.
	 */
	public static FabricShield[] getAllFabricShields()
	{
		return (FabricShield[]) fabricShields.toArray();
	}
	
	/**
	 * getAllFabricBannerShields
	 * 
	 * @return Every registered FabricBannerShield instance at the time this is invoked.
	 */
	public static FabricBannerShield[] getAllFabricBannerShields()
	{
		return (FabricBannerShield[]) fabricBannerShields.toArray();
	}
	
	/**
	 * getAllShieldEnchantments
	 * 
	 * @return Every registered FabricShieldEnchantment instance at the time this is invoked.
	 */
	public static FabricShieldEnchantment[] getAllShieldEnchantments()
	{
		return (FabricShieldEnchantment[]) enchantments.toArray();
	}
}
