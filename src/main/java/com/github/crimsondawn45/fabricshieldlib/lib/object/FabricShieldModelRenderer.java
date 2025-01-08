package com.github.crimsondawn45.fabricshieldlib.lib.object;

import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLibClient;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.DyeColor;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class FabricShieldModelRenderer implements SpecialModelRenderer<ComponentMap> {
    private final ShieldEntityModel model;

    public FabricShieldModelRenderer(ShieldEntityModel model) {
        this.model = model;
    }

    @Nullable
    public ComponentMap getData(ItemStack itemStack) {
        return itemStack.getImmutableComponents();
    }

    public void render(@Nullable ComponentMap componentMap, ModelTransformationMode modelTransformationMode, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, boolean bl) {
        BannerPatternsComponent bannerPatternsComponent = componentMap != null ? (BannerPatternsComponent)componentMap.getOrDefault(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT) : BannerPatternsComponent.DEFAULT;
        DyeColor dyeColor = componentMap != null ? (DyeColor)componentMap.get(DataComponentTypes.BASE_COLOR) : null;
        boolean bl2 = !bannerPatternsComponent.layers().isEmpty() || dyeColor != null;
        matrixStack.push();
        matrixStack.scale(1.0F, -1.0F, -1.0F);
        SpriteIdentifier spriteIdentifier = bl2 ? FabricShieldLibClient.FABRIC_BANNER_SHIELD_BASE : FabricShieldLibClient.FABRIC_BANNER_SHIELD_BASE_NO_PATTERN;
        VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getItemGlintConsumer(vertexConsumerProvider, this.model.getLayer(spriteIdentifier.getAtlasId()), modelTransformationMode == ModelTransformationMode.GUI, bl));
        this.model.getHandle().render(matrixStack, vertexConsumer, i, j);
        if (bl2) {
            BannerBlockEntityRenderer.renderCanvas(matrixStack, vertexConsumerProvider, i, j, this.model.getPlate(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor, DyeColor.WHITE), bannerPatternsComponent, bl, false);
        } else {
            this.model.getPlate().render(matrixStack, vertexConsumer, i, j);
        }

        matrixStack.pop();
    }

    @Environment(EnvType.CLIENT)
    public static record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final FabricShieldModelRenderer.Unbaked INSTANCE = new FabricShieldModelRenderer.Unbaked();
        public static final MapCodec<FabricShieldModelRenderer.Unbaked> CODEC;

        public Unbaked() {
        }

        public MapCodec<FabricShieldModelRenderer.Unbaked> getCodec() {
            return CODEC;
        }

        public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
            return new FabricShieldModelRenderer(new ShieldEntityModel(entityModels.getModelPart(FabricShieldLibClient.fabric_banner_shield_model_layer)));
        }

        static {
            CODEC = MapCodec.unit(INSTANCE);
        }
    }
}

