package com.github.crimsondawn45.fabricshieldlib.mixin;


import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldBannerRendering;
import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLib;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Handles rendering of shields with banners.
 */
@Mixin (BuiltinModelItemRenderer.class)
public class RendererMixin {
    final Identifier FABRIC_SHIELD_NOPATTERN = new Identifier("fabricshieldlib","textures/entity/fabric_banner_shield_base_nopattern.png");
    final Identifier FABRIC_SHIELD_BANNER = new Identifier("fabricshieldlib","textures/entity/fabric_banner_shield_base.png");

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void mainRender(ItemStack stack, CallbackInfo callbackInfo) {
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            if (stack.getItem() == FabricShieldLib.fabric_banner_shield) {
                FabricShieldBannerRendering.render(stack, FABRIC_SHIELD_NOPATTERN, FABRIC_SHIELD_BANNER);
                callbackInfo.cancel();
            }
        }
    }
}