package me.crimsondawn45.fabricshieldlib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.crimsondawn45.fabricshieldlib.object.FabricShield;
import me.crimsondawn45.fabricshieldlib.object.TestEnchantment;
import me.crimsondawn45.fabricshieldlib.util.FabricShieldLibRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FabricShieldLib implements ModInitializer
{	
	public static final String MOD_ID = "fabricshieldlib";
	
	public static final Logger logger = LogManager.getLogger("FabricShieldLib");
	
	@Override
	public void onInitialize()
	{
		FabricShieldLibRegistry.registerShield(Items.SHIELD);
		logger.info("Fabric Shield Lib Successfully Initialized!");
		
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "test_shield"), new FabricShield(new Item.Settings(), 100, 337, ItemTags.PLANKS));
		Registry.register(Registry.ENCHANTMENT, new Identifier("test_enchantment"), new TestEnchantment(Enchantment.Weight.COMMON));
	}
}