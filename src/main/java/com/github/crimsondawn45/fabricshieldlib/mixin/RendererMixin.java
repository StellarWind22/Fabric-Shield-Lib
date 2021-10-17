package com.github.crimsondawn45.fabricshieldlib.mixin;


import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLib;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("ALL")
@Mixin (BuiltinModelItemRenderer.class)
public class RendererMixin {
    /**
     * This whole mixin is dev code and will be made by the player
     */

    private static final SpriteIdentifier FABRIC_SHIELD_BASE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(FabricShieldLib.MOD_ID,"entity/fabric_shield_base"));
    private static final SpriteIdentifier FABRIC_SHIELD_BASE_NO_PATTERN = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(FabricShieldLib.MOD_ID,"entity/fabric_shield_base_nopattern"));


    @Final
    @Shadow
    private EntityModelLoader entityModelLoader;


    @Inject(method = "reload", at = @At("HEAD"))
    private void setModelFabricShield(CallbackInfo ci){
        FabricShieldLib.modelFabricShield = new ShieldEntityModel(this.entityModelLoader.getModelPart(FabricShieldLib.fabric_shield_model_layer));
    }



    @Inject(method = "render", at = @At("HEAD"))
    private void mainRender(ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            if (stack.isOf(FabricShieldLib.fabric_shield)) {
            FabricShieldItem.renderBanner(stack, matrices, vertexConsumers, light, overlay, FabricShieldLib.modelFabricShield, FABRIC_SHIELD_BASE, FABRIC_SHIELD_BASE_NO_PATTERN);
            }
        }
    }

}

