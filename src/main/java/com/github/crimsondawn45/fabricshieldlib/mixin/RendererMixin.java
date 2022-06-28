package com.github.crimsondawn45.fabricshieldlib.mixin;


import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLib;
import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLibClient;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

/**
 * Handles rendering of shields with banners.
 */
@Mixin (BuiltinModelItemRenderer.class)
public class RendererMixin {

    /**
     * This whole mixin is dev code and will be made by the player.
     */
    private static ShieldEntityModel modelFabricShield;
    @SuppressWarnings( "deprecation" )
    private static final SpriteIdentifier FABRIC_BANNER_SHIELD_BASE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(FabricShieldLib.MOD_ID,"entity/fabric_banner_shield_base"));
    @SuppressWarnings( "deprecation" )
    private static final SpriteIdentifier FABRIC_BANNER_SHIELD_BASE_NO_PATTERN = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(FabricShieldLib.MOD_ID,"entity/fabric_banner_shield_base_nopattern"));


    @Final
    @Shadow
    private EntityModelLoader entityModelLoader;


    @Inject(method = "reload", at = @At("HEAD"))
    private void setModelFabricShield(CallbackInfo ci){
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            modelFabricShield = new ShieldEntityModel(this.entityModelLoader.getModelPart(FabricShieldLibClient.fabric_banner_shield_model_layer));
        }
    }



    @Inject(method = "render", at = @At("HEAD"))
    private void mainRender(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            if (stack.isOf(FabricShieldLib.fabric_banner_shield)) {
                FabricShieldLibClient.renderBanner(stack, matrices, vertexConsumers, light, overlay, modelFabricShield, FABRIC_BANNER_SHIELD_BASE, FABRIC_BANNER_SHIELD_BASE_NO_PATTERN);
            }
        }
    }
}