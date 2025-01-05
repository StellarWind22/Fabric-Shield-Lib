package com.github.crimsondawn45.fabricshieldlib.mixin;


import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldSetModelCallback;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.model.special.ShieldModelRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Handles rendering of shields with banners.
 */
@Mixin(ShieldModelRenderer.Unbaked.class)
public class RendererMixin {

    /**
     * This is now lib code and the player will not have to make this!
     */

    @Inject(method = "bake", at = @At("HEAD"))
    private void setModelFabricShield(LoadedEntityModels entityModels, CallbackInfoReturnable<SpecialModelRenderer<?>> cir){
        ShieldSetModelCallback.EVENT.invoker().setModel(entityModels);
    }
}