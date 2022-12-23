package com.github.crimsondawn45.fabricshieldlib.mixin;


import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldSetModelCallback;

import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;

/**
 * Handles rendering of shields with banners.
 */
@Mixin(BuiltinModelItemRenderer.class)
public class RendererMixin {

    /**
     * This whole mixin is dev code and will be made by the player.
     */

    @Final
    @Shadow
    public EntityModelLoader entityModelLoader;

    @Inject(method = "reload", at = @At("HEAD"))
    private void setModelFabricShield(CallbackInfo ci){
        ShieldSetModelCallback.EVENT.invoker().setModel(this.entityModelLoader);
    }
}