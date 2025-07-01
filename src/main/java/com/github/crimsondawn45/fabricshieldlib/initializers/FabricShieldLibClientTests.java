package com.github.crimsondawn45.fabricshieldlib.initializers;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.util.Identifier;

public class FabricShieldLibClientTests {
	/**
	 * Will be made by user (dev code).
	 */
	protected static final EntityModelLayer fabric_banner_shield_model_layer = new EntityModelLayer(
		Identifier.of(FabricShieldLib.MOD_ID, "fabric_banner_shield"), "main");

	protected static void runTests() {
		// Registers sprite directories and model layer, will be done by player, dev code
		EntityModelLayerRegistry.registerModelLayer(fabric_banner_shield_model_layer,
			ShieldEntityModel::getTexturedModelData);
	}
}
