package me.crimsondawn45.fabricshieldlib.entrypoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.crimsondawn45.fabricshieldlib.util.ShieldRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Items;

public class FabricShieldLibMod implements ModInitializer
{	
	public static final String MOD_ID = "fabricshieldlib";
	
	public static final Logger logger = LogManager.getLogger("FabricShieldLib");
	
	@Override
	public void onInitialize()
	{
		ShieldRegistry.register(Items.SHIELD);
		logger.info("Fabric Shield Lib Successfully Initialized!");
	}
}