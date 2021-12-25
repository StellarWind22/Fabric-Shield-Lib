package com.github.crimsondawn45.fabricshieldlib.mixin;


import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLib;
import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLibClient;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.texture.TextureCache;
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
    final TextureCache.Manager FABRIC_SHIELD_BANNER = new TextureCache.Manager("shield_", new Identifier("fabricshieldlib","textures/entity/fabric_banner_shield_base.png"), "textures/entity/shield/");

    @Inject(method = "render", at = @At("HEAD"))
    private void mainRender(ItemStack stack, CallbackInfo callbackInfo) {
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            if (stack.getItem() == FabricShieldLib.fabric_banner_shield) {
                FabricShieldLibClient.renderBanner(stack, FABRIC_SHIELD_NOPATTERN, FABRIC_SHIELD_BANNER);
            }
        }
    }
}