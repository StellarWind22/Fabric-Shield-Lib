package me.crimsondawn45.fabricshieldlib.entrypoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.crimsondawn45.fabricshieldlib.object.TestEnchantment;
import me.crimsondawn45.fabricshieldlib.util.FabricShieldLibRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FabricShieldLibMod implements ModInitializer
{	
	public static final String MOD_ID = "fabricshieldlib";
	
	public static final Logger logger = LogManager.getLogger("FabricShieldLib");
	
	@Override
	public void onInitialize()
	{
		FabricShieldLibRegistry.registerShield(Items.SHIELD);
		logger.info("Fabric Shield Lib Successfully Initialized!");

		Registry.register(Registry.ENCHANTMENT, new Identifier(MOD_ID, "test_enchantment"), new TestEnchantment(Rarity.COMMON));
	}
}