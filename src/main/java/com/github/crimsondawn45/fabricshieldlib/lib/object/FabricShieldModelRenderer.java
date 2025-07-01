package com.github.crimsondawn45.fabricshieldlib.lib.object;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Objects;
import java.util.Set;

@Environment(EnvType.CLIENT)
public class FabricShieldModelRenderer implements SpecialModelRenderer<ComponentMap> {
	private final Identifier baseModel, baseModelNoPat;
	private final ShieldEntityModel model;

	public FabricShieldModelRenderer(Identifier baseModel, Identifier baseModelNoPat, ShieldEntityModel model) {
		this.baseModel = baseModel;
		this.baseModelNoPat = baseModelNoPat;
		this.model = model;
	}

	@Nullable
	public ComponentMap getData(ItemStack itemStack) {
		return itemStack.getImmutableComponents();
	}

	public void render(
		@Nullable ComponentMap componentMap, ItemDisplayContext displayContext, MatrixStack matrixStack,
		VertexConsumerProvider vertexConsumerProvider, int i, int j, boolean bl
	) {
		BannerPatternsComponent bannerPatternsComponent = componentMap == null ? BannerPatternsComponent.DEFAULT:
			(BannerPatternsComponent) componentMap.getOrDefault(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT);

		DyeColor dyeColor = componentMap == null ? null : (DyeColor) componentMap.get(DataComponentTypes.BASE_COLOR);

		boolean bl2 = !bannerPatternsComponent.layers().isEmpty() || dyeColor != null;

		matrixStack.push();
		matrixStack.scale(1.0F, -1.0F, -1.0F);

		try {
			@SuppressWarnings("deprecation")
			SpriteIdentifier spriteIdentifier = bl2
				? new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, this.baseModel)
				: new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, this.baseModelNoPat);
			VertexConsumer vertexConsumer = spriteIdentifier.getSprite()
				.getTextureSpecificVertexConsumer(ItemRenderer.getItemGlintConsumer(vertexConsumerProvider,
					model.getLayer(spriteIdentifier.getAtlasId()), displayContext == ItemDisplayContext.GUI, bl));
			model.getHandle().render(matrixStack, vertexConsumer, i, j);
			if (bl2) {
				BannerBlockEntityRenderer.renderCanvas(matrixStack, vertexConsumerProvider, i, j, model.getPlate(),
					spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor, DyeColor.WHITE),
					bannerPatternsComponent, bl, false);
			} else {
				model.getPlate().render(matrixStack, vertexConsumer, i, j);
			}
		} finally {
			matrixStack.pop();
		}
	}

	@Override
	public void collectVertices(Set<Vector3f> vertices) {
		MatrixStack matrixStack = new MatrixStack();
		matrixStack.scale(1.0F, -1.0F, -1.0F);
		this.model.getRootPart().collectVertices(matrixStack, vertices);
	}

	public static class UnbakedInstance {
		public final Identifier baseModel, baseModelNoPat;
		public final EntityModelLayer entityModelLayer;
		public final Unbaked instance;
		public final MapCodec<UnbakedInstance.Unbaked> codec;

		public UnbakedInstance(Identifier baseModel, Identifier baseModelNoPat, EntityModelLayer entityModelLayer) {
			this.baseModel = baseModel;
			this.baseModelNoPat = baseModelNoPat;
			this.entityModelLayer = entityModelLayer;
			this.instance = new Unbaked();
			this.codec = MapCodec.unit(this.instance);
		}

		public class Unbaked implements SpecialModelRenderer.Unbaked {
			private Unbaked() {}

			@Override
			public MapCodec<Unbaked> getCodec() {
				return UnbakedInstance.this.codec;
			}

			@Override
			public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
				ModelPart modelPart = entityModels.getModelPart(UnbakedInstance.this.entityModelLayer);
				ShieldEntityModel model = new ShieldEntityModel(modelPart);
				return new FabricShieldModelRenderer(
					UnbakedInstance.this.baseModel,
					UnbakedInstance.this.baseModelNoPat,
					model
				);
			}
		}
	}
}
