package com.github.crimsondawn45.fabricshieldlib.mixin;


import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLib;
import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLibClient;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

@Mixin (BuiltinModelItemRenderer.class)
public class RendererMixin {

    /**
     * This whole mixin is dev code and will be made by the player
     */
    private static final ShieldEntityModel modelFabricShield = new ShieldEntityModel();
    private static final Identifier FABRIC_BANNER_SHIELD_BASE = new Identifier(FabricShieldLib.MOD_ID,"entity/fabric_banner_shield_base");
    private static final Identifier FABRIC_BANNER_SHIELD_BASE_NO_PATTERN = new Identifier(FabricShieldLib.MOD_ID,"entity/fabric_banner_shield_base_nopattern");



    @Inject(method = "render", at = @At("HEAD"))
    private void mainRender(ItemStack stack, CallbackInfo ci) {
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            if (stack.getItem() == (FabricShieldLib.fabric_banner_shield)) {
                FabricShieldLibClient.renderBanner(stack, modelFabricShield, FABRIC_BANNER_SHIELD_BASE, FABRIC_BANNER_SHIELD_BASE_NO_PATTERN);
            }
        }
    }
}