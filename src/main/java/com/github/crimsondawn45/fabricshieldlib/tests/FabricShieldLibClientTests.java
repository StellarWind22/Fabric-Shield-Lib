package com.github.crimsondawn45.fabricshieldlib.tests;

import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLib;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldModelRenderer;
import com.mojang.serialization.MapCodec;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.item.model.special.SpecialModelTypes;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

/**
 * These are test codes used internally by FabricShieldLib developers.
 * <p>
 * Modders are not supposed to reference this class!
 */
public class FabricShieldLibClientTests {
	/**
	 * Will be made by user (dev code).
	 */
	protected static final EntityModelLayer FABRIC_BANNER_SHIELD_MODEL_LAYER = new EntityModelLayer(
		Identifier.of(FabricShieldLib.MOD_ID, "fabric_banner_shield"), "main");

	@SuppressWarnings("deprecation")
	public static final SpriteIdentifier FABRIC_BANNER_SHIELD_BASE = new SpriteIdentifier(
		SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE,
		Identifier.of(FabricShieldLib.MOD_ID, "entity/fabric_banner_shield_base")
	);

	@SuppressWarnings("deprecation")
	public static final SpriteIdentifier FABRIC_BANNER_SHIELD_BASE_NO_PATTERN = new SpriteIdentifier(
		SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE,
		Identifier.of(FabricShieldLib.MOD_ID, "entity/fabric_banner_shield_base_nopattern")
	);

	public static final Identifier FABRIC_BANNER_SHIELD_MODEL_TYPE =
		Identifier.of(FabricShieldLib.MOD_ID, "fabric_banner_shield");

	// private static ShieldEntityModel modelFabricShield;

	public static void runTests() {
		MapCodec<FabricShieldModelRenderer.UnbakedInstance.Unbaked> codec =
			new FabricShieldModelRenderer.UnbakedInstance(
				FABRIC_BANNER_SHIELD_BASE.getTextureId(),
				FABRIC_BANNER_SHIELD_BASE_NO_PATTERN.getTextureId(),
				FabricShieldLibClientTests.FABRIC_BANNER_SHIELD_MODEL_LAYER
			).codec;

		SpecialModelTypes.ID_MAPPER.put(FABRIC_BANNER_SHIELD_MODEL_TYPE, codec);

		// Registers sprite directories and model layer, will be done by player, dev code
		EntityModelLayerRegistry.registerModelLayer(FABRIC_BANNER_SHIELD_MODEL_LAYER,
			ShieldEntityModel::getTexturedModelData);

		/*
		// Set model
		ShieldSetModelCallback.EVENT.register((loader) -> {
			modelFabricShield = new ShieldEntityModel(
				loader.getModelPart(fabric_banner_shield_model_layer));
			return ActionResult.PASS;
		});
		*/
	}
}
