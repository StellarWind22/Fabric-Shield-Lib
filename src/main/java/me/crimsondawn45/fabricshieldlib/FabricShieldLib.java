package me.crimsondawn45.fabricshieldlib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.crimsondawn45.fabricshieldlib.api.ShieldRegistry;
import me.crimsondawn45.fabricshieldlib.dev.TestShieldEvent;
import me.crimsondawn45.fabricshieldlib.object.BasicShield;
import me.crimsondawn45.fabricshieldlib.object.ShieldEnchantment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FabricShieldLib implements ModInitializer {	
	public static final String MOD_ID = "fabricshieldlib";
	public static final Logger logger = LogManager.getLogger("FabricShieldLib");
	
	public static Item test_shield;
	public static ShieldEnchantment test_enchantment;
	
	@Override
	public void onInitialize() {
		ShieldRegistry.register(Items.SHIELD);
		logger.info("Fabric Shield Lib Successfully Initialized!");
		
		//Development Environment Code
		// Warn people that Development code is going to run
		debugMsg("###########################################################################################################");
		debugMsg("#  WARNING: Fabric Shield Lib is running in dev mode! test_shield and test_enchantment will be ingame!!!  #");
		debugMsg("###########################################################################################################");
		if(FabricLoader.getInstance().isDevelopmentEnvironment()) {	
			test_shield = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "test_shield"), new BasicShield(new Item.Settings().group(ItemGroup.COMBAT), 20, 100, Items.OAK_PLANKS));			//Register Development Stuff
			test_enchantment = Registry.register(Registry.ENCHANTMENT, new Identifier(MOD_ID, "test_enchantment"), new ShieldEnchantment(Rarity.COMMON, new TestShieldEvent(true, true, true)));
		}
		debugMsg("test_shield        hasEvent: " + Boolean.toString(ShieldRegistry.hasEvent(test_shield)));
		debugMsg("test_enchantment   hasEvent: " + Boolean.toString(ShieldRegistry.hasEvent(test_enchantment)));
		debugMsg("###########################################################################################################");
		debugMsg("test_shield  isFabricShield: " + Boolean.toString(ShieldRegistry.isFabricShield(test_shield)));
		debugMsg("shield       isFabricShield: " + Boolean.toString(ShieldRegistry.isFabricShield(Items.SHIELD)));
		debugMsg("###########################################################################################################");
	}
	
	public static void debugMsg(String msg) {
		if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
			logger.warn("[DEBUG] " + msg);
		}
	}
}