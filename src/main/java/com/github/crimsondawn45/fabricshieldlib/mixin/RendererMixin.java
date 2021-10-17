package com.github.crimsondawn45.fabricshieldlib.mixin;


import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLib;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldEntityModel;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@SuppressWarnings("deprecation")
@Mixin (BuiltinModelItemRenderer.class)
public class RendererMixin {
    private FabricShieldEntityModel modelFabricShield;
    private static final SpriteIdentifier FABRIC_SHIELD_BASE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(FabricShieldLib.MOD_ID,"entity/fabric_shield_base"));
    private static final SpriteIdentifier FABRIC_SHIELD_BASE_NO_PATTERN = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(FabricShieldLib.MOD_ID,"entity/fabric_shield_base_nopattern"));

    @Final
    @Shadow
    private EntityModelLoader entityModelLoader;


    @Inject(method = "reload", at = @At("HEAD"))
    private void setModelFabricShield(CallbackInfo ci){
        this.modelFabricShield = new FabricShieldEntityModel(this.entityModelLoader.getModelPart(FabricShieldLib.fabric_shield_model_layer));
    }



    @Inject(method = "render", at = @At("HEAD"))
    private void mainRender(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            if (stack.isOf(FabricShieldLib.fabric_shield)) {
                boolean bl = stack.getSubNbt("BlockEntityTag") != null;
                matrices.push();
                matrices.scale(1.0F, -1.0F, -1.0F);
                SpriteIdentifier spriteIdentifier = bl ? FABRIC_SHIELD_BASE : FABRIC_SHIELD_BASE_NO_PATTERN;
                VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.modelFabricShield.getLayer(spriteIdentifier.getAtlasId()), true, stack.hasGlint()));
                this.modelFabricShield.getHandle().render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                if (bl) {
                    List<Pair<BannerPattern, DyeColor>> list = BannerBlockEntity.getPatternsFromNbt(FabricShieldItem.getColor(stack), BannerBlockEntity.getPatternListTag(stack));
                    BannerBlockEntityRenderer.renderCanvas(matrices, vertexConsumers, light, overlay, this.modelFabricShield.getPlate(), spriteIdentifier, false, list, stack.hasGlint());
                } else {
                    this.modelFabricShield.getPlate().render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                }

                matrices.pop();
            }
        }
    }

}

