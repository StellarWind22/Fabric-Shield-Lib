package me.crimsondawn45.fabricshieldlib;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.crimsondawn45.fabricshieldlib.object.FabricShield;
import me.crimsondawn45.fabricshieldlib.object.FabricShieldEnchantment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class FabricShieldLib implements ModInitializer
{	
	public static final String MOD_ID = "fabricshieldlib";
	
	public static final Logger logger = LogManager.getLogger("FabricShieldLib");
	
	private static final List<Item> allShields = new ArrayList<Item>();
	private static final List<FabricShield> fabricShields = new ArrayList<FabricShield>();
	public static final List<FabricShieldEnchantment> enchantments = new ArrayList<FabricShieldEnchantment>();
	
	@Override
	public void onInitialize()
	{	
		registerShield(Items.SHIELD);
		logger.info("Fabric Shield Lib Successfully Initialized!");
	}
	
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
	 * Check if the shield registry contains a particular item instance.
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
	 * Check if the shield registry contains a particular FabricShieldItem instance.
	 * 
	 * @param shield - Item instance to check for.
	 */
	public static boolean containsFabricShield(Item shield)
	{
		return fabricShields.contains(shield);
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
	 * @return Every registered shield instance at the time this is invoked.
	 */
	public static Item[] getAllShields()
	{
		return (Item[]) allShields.toArray();
	}
	
	/**
	 * getAllFabricShields
	 * 
	 * @return Every registered FabricShieldItem instance at the time this is invoked.
	 */
	public static FabricShield[] getAllFabricShields()
	{
		return (FabricShield[]) fabricShields.toArray();
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