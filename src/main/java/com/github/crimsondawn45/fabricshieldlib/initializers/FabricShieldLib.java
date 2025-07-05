package com.github.crimsondawn45.fabricshieldlib.initializers;

import com.github.crimsondawn45.fabricshieldlib.lib.config.FabricShieldLibConfig;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldDecoratorRecipe;
import com.github.crimsondawn45.fabricshieldlib.tests.FabricShieldLibTests;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShieldDecorationRecipe;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for Fabric Shield Lib.
 */
public class FabricShieldLib implements ModInitializer {
	/**
	 * Fabric Shield Lib's mod id.
	 */
	public static final String MOD_ID = "fabricshieldlib";

	/**
	 * Fabric Shield Lib's logger.
	 */
	public static final Logger logger = LoggerFactory.getLogger(MOD_ID);

	/**
	 * Recipe type and serializer for banner decoration recipe.
	 */

	public static final SpecialCraftingRecipe.SpecialRecipeSerializer<ShieldDecorationRecipe> FABRIC_SHIELD_DECORATION_SERIALIZER;
	public static final RecipeType<FabricShieldDecoratorRecipe> FABRIC_SHIELD_DECORATION;

	static {
		// Registering Banner Recipe (Lib only)
		FABRIC_SHIELD_DECORATION = Registry.register(Registries.RECIPE_TYPE,
			Identifier.of(MOD_ID, "fabric_shield_decoration"), new RecipeType<FabricShieldDecoratorRecipe>() {
				@Override
				public String toString() {
					return "fabric_shield_decoration";
				}
			});
		FABRIC_SHIELD_DECORATION_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER,
			Identifier.of(MOD_ID, "fabric_shield_decoration"),
			new SpecialCraftingRecipe.SpecialRecipeSerializer<>(FabricShieldDecoratorRecipe::new));
	}

	@Override
	public void onInitialize() {
		// Register Config
		MidnightConfig.init(MOD_ID, FabricShieldLibConfig.class);

		// Dev environment code.
		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			// Warn about dev code
			logger.warn("FABRIC SHIELD LIB DEVELOPMENT CODE RAN!!!, if you are not in a development environment this is very bad! Test items and test enchantments will be ingame!");

			FabricShieldLibTests.runTests();
		}

		// Announce having finished starting up
		logger.info("Fabric Shield Lib Initialized!");
	}
}
