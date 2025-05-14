package com.github.crimsondawn45.fabricshieldlib.lib.object;

import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLib;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class FabricShieldModelRenderer implements SpecialModelRenderer<ComponentMap> {
    private final LoadedEntityModels loadedEntityModels;

    public FabricShieldModelRenderer(LoadedEntityModels loadedEntityModels) {
        this.loadedEntityModels = loadedEntityModels;
    }

    @Nullable
    public ComponentMap getData(ItemStack itemStack) {
        return itemStack.getImmutableComponents();
    }

    public void render(@Nullable ComponentMap componentMap, ItemDisplayContext modelTransformationMode, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, boolean bl) {
        BannerPatternsComponent bannerPatternsComponent = componentMap != null ? (BannerPatternsComponent)componentMap.getOrDefault(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT) : BannerPatternsComponent.DEFAULT;
        DyeColor dyeColor = componentMap != null ? (DyeColor)componentMap.get(DataComponentTypes.BASE_COLOR) : null;
        boolean bl2 = !bannerPatternsComponent.layers().isEmpty() || dyeColor != null;
        matrixStack.push();
        matrixStack.scale(1.0F, -1.0F, -1.0F);
        FabricShieldModelComponent modelComponent = componentMap.get(FabricShieldLib.MODEL_COMPONENT);
        EntityModelLayer EML = new EntityModelLayer(getEMLID(modelComponent.layer()), "main");
        ShieldEntityModel model = new ShieldEntityModel(loadedEntityModels.getModelPart(EML));
        @SuppressWarnings("deprecation")
        SpriteIdentifier spriteIdentifier = bl2 ? new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, modelComponent.baseModel()) : new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, modelComponent.baseModelNoPat());
        VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getItemGlintConsumer(vertexConsumerProvider, model.getLayer(spriteIdentifier.getAtlasId()), modelTransformationMode == ItemDisplayContext.GUI, bl));
        model.getHandle().render(matrixStack, vertexConsumer, i, j);
        if (bl2) {
            BannerBlockEntityRenderer.renderCanvas(matrixStack, vertexConsumerProvider, i, j, model.getPlate(), spriteIdentifier, false, (DyeColor)Objects.requireNonNullElse(dyeColor, DyeColor.WHITE), bannerPatternsComponent, bl, false);
        } else {
            model.getPlate().render(matrixStack, vertexConsumer, i, j);
        }

        matrixStack.pop();
    }

    @Environment(EnvType.CLIENT)
    public record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final FabricShieldModelRenderer.Unbaked INSTANCE = new FabricShieldModelRenderer.Unbaked();
        public static final MapCodec<FabricShieldModelRenderer.Unbaked> CODEC;

        public MapCodec<FabricShieldModelRenderer.Unbaked> getCodec() {
            return CODEC;
        }


        public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
            return new FabricShieldModelRenderer(entityModels);
        }

        static {
            CODEC = MapCodec.unit(INSTANCE);
        }
    }

    @Nullable
    public static Identifier getEMLID(String IdWithVariant) {
        return Identifier.tryParse(IdWithVariant.split("#")[0]);
    }
}

