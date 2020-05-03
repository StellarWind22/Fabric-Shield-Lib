package me.crimsondawn45.fabricshieldlib;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.crimsondawn45.fabricshieldlib.object.FabricShield;
import net.fabricmc.api.ModInitializer;

public class FabricShieldLib implements ModInitializer
{	
	public static final String MOD_ID = "fabricshieldlib";
	
	public static final Logger logger = LogManager.getLogger(MOD_ID);
	
	public static final ArrayList<FabricShield> shields = new ArrayList<FabricShield>();
	
	@Override
	public void onInitialize()
	{
		logger.info("Fabric Shield Lib Successfully Initialized!");
	}
}
