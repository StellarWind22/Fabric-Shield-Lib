package me.crimsondawn45.fabricshieldlib;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.crimsondawn45.fabricshieldlib.object.FabricShield;
import me.crimsondawn45.fabricshieldlib.object.FabricShieldEnchantment;
import me.crimsondawn45.fabricshieldlib.test.CurseOfVulnerabilityEnchantment;
import me.crimsondawn45.fabricshieldlib.test.ReflectionEnchantment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FabricShieldLib implements ModInitializer
{	
	public static final String MOD_ID = "fabricshieldlib";
	
	public static final Logger logger = LogManager.getLogger("FabricShieldLib");
	
	public static final List<Item> allShields = new ArrayList<Item>();
	public static final List<FabricShield> fabricShields = new ArrayList<FabricShield>();
	public static final List<FabricShieldEnchantment> enchantments = new ArrayList<FabricShieldEnchantment>();
	
	@Override
	public void onInitialize()
	{	
		allShields.add(Items.SHIELD);
		
		Registry.register(Registry.ENCHANTMENT, new Identifier(MOD_ID, "reflection"), new ReflectionEnchantment(Rarity.COMMON, EnchantmentTarget.BREAKABLE, allShields));
		Registry.register(Registry.ENCHANTMENT, new Identifier(MOD_ID, "curse_of_vulnerability"), new CurseOfVulnerabilityEnchantment(Rarity.RARE, EnchantmentTarget.BREAKABLE, allShields));
		
		logger.info("Fabric Shield Lib Successfully Initialized!");
	}
}
