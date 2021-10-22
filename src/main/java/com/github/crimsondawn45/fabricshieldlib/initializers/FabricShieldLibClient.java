package com.github.crimsondawn45.fabricshieldlib.initializers;

import com.mojang.blaze3d.platform.GlStateManager;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureCache;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Identifier;

public class FabricShieldLibClient implements ClientModInitializer {

    /**
     * Will be made by user (dev code)
     */
    @Override
    public void onInitializeClient() {
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {

            //Warn about dev code
            FabricShieldLib.logger.warn("FABRIC SHIELD LIB DEVELOPMENT CODE RAN!!!, if you are not in a development environment this is very bad! Client side banner code ran!");

            /*
             * Registers sprite directories and model layer, will be done by player, dev code
             */
            ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEX).register((atlasTexture, registry) -> {
                registry.register(new Identifier(FabricShieldLib.MOD_ID, "entity/fabric_banner_shield_base"));
                registry.register(new Identifier(FabricShieldLib.MOD_ID, "entity/fabric_banner_shield_base_nopattern"));
            });
        }
    }

    /**
     * Used to simplify the mixin on the user end to make their shield render banner
     *
     * Uses params from the mixin method, and the model and sprite identifiers made by the player
     */
    public static void renderBanner(ItemStack stack, ShieldEntityModel modelShield, Identifier base, Identifier base_nopattern){
        final BannerBlockEntity renderBanner = new BannerBlockEntity();

        if (stack.getSubTag("BlockEntityTag") != null) {
            renderBanner.readFrom(stack, ShieldItem.getColor(stack));
            MinecraftClient.getInstance().getTextureManager().bindTexture(TextureCache.SHIELD.get(renderBanner.getPatternCacheKey(), renderBanner.getPatterns(), renderBanner.getPatternColors()));
         } else {
            MinecraftClient.getInstance().getTextureManager().bindTexture(base_nopattern);
         }

         GlStateManager.pushMatrix();
         GlStateManager.scalef(1.0F, -1.0F, -1.0F);
         modelShield.renderItem();
         if (stack.hasEnchantmentGlint()) {
            ShieldEntityModel var10001 = modelShield;
            FabricShieldLibClient.renderEnchantmentGlint(var10001::renderItem);
         }

         GlStateManager.popMatrix();
    }

    private static void renderEnchantmentGlint(Runnable runnable) {
        GlStateManager.color3f(0.5019608F, 0.2509804F, 0.8F);
        MinecraftClient.getInstance().getTextureManager().bindTexture(ItemRenderer.ENCHANTMENT_GLINT_TEX);
        ItemRenderer.renderGlint(MinecraftClient.getInstance().getTextureManager(), runnable, 1);
    }
}