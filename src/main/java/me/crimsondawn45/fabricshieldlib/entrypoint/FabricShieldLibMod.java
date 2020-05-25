package me.crimsondawn45.fabricshieldlib.entrypoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.crimsondawn45.fabricshieldlib.object.BasicShield;
import me.crimsondawn45.fabricshieldlib.object.ShieldEnchantment;
import me.crimsondawn45.fabricshieldlib.object.TestEvent;
import me.crimsondawn45.fabricshieldlib.util.ShieldRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FabricShieldLibMod implements ModInitializer
{	
	public static final String MOD_ID = "fabricshieldlib";
	
	public static final Logger logger = LogManager.getLogger("FabricShieldLib");
	
	@Override
	public void onInitialize()
	{
		ShieldRegistry.register(Items.SHIELD);
		logger.info("Fabric Shield Lib Successfully Initialized!");

		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "basic_shield"), new BasicShield(new Item.Settings(), 100, 377, ItemTags.PLANKS));
		Registry.register(Registry.ENCHANTMENT, new Identifier(MOD_ID, "test_enchantment"), new ShieldEnchantment(Rarity.COMMON, new TestEvent()));

		ShieldRegistry.registerItemEvent(Items.SHIELD, new TestEvent());
	}
}