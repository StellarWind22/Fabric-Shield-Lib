package me.crimsondawn45.fabricshieldlib;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.crimsondawn45.fabricshieldlib.object.FabricShield;
import me.crimsondawn45.fabricshieldlib.object.FabricShieldEnchantment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

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
		logger.info("Fabric Shield Lib Successfully Initialized!");
	}
}
