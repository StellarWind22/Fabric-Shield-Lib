package me.crimsondawn45.fabricshieldlib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.crimsondawn45.fabricshieldlib.dev.TestShieldEvent;
import me.crimsondawn45.fabricshieldlib.lib.ShieldRegistry;
import me.crimsondawn45.fabricshieldlib.lib.object.FabricShield;
import me.crimsondawn45.fabricshieldlib.lib.object.ShieldEnchantment;
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
	
	public static FabricShield test_shield;
	public static ShieldEnchantment test_enchantment;
	
	@Override
	public void onInitialize() {
		logger.info("Fabric Shield Lib Successfully Initialized!");
		
		//Development Environment Code
		// Warn people that Development code is going to run
		if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
			debugMsg("###########################################################################################################");
			debugMsg("#  WARNING: Fabric Shield Lib is running in dev mode! test_shield and test_enchantment will be ingame!!!  #");
			debugMsg("###########################################################################################################");
			
			test_shield = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "test_shield"), new FabricShield(new Item.Settings().group(ItemGroup.COMBAT), 20, 100, 9, Items.OAK_PLANKS));			//Register Development Stuff
			test_enchantment = Registry.register(Registry.ENCHANTMENT, new Identifier(MOD_ID, "test_enchantment"), new ShieldEnchantment(Rarity.COMMON, new TestShieldEvent(true, true, true)));
			
			debugMsg("test_shield        hasEvent: " + Boolean.toString(test_shield.hasEvent()));
			debugMsg("test_enchantment   hasEvent: " + Boolean.toString(test_enchantment.hasEvent()));
			debugMsg("###########################################################################################################");
			debugMsg("test_shield  isFabricShield: " + Boolean.toString(ShieldRegistry.isFabricShield(test_shield)));
			debugMsg("shield       isFabricShield: " + Boolean.toString(ShieldRegistry.isFabricShield(Items.SHIELD)));
			debugMsg("###########################################################################################################");
		}
	}
	
	public static void debugMsg(String msg) {
		logger.warn("[DEBUG] " + msg);
	}
}