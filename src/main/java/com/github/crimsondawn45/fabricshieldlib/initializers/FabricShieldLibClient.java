package com.github.crimsondawn45.fabricshieldlib.initializers;

import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricBannerShieldItem;
import com.mojang.blaze3d.platform.GlStateManager;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.TextureCache;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class FabricShieldLibClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
    }

    /**
     * Used in renderBanner method internally
     */
    private static void renderEnchantmentGlint(Runnable runnable) {
        GlStateManager.color3f(0.5019608F, 0.2509804F, 0.8F);
        MinecraftClient.getInstance().getTextureManager().bindTexture(ItemRenderer.ENCHANTMENT_GLINT_TEX);
        ItemRenderer.renderGlint(MinecraftClient.getInstance().getTextureManager(), runnable, 1);
    }

    /**
     * Used to simplify the mixin on the user end to make their shield render banner
     *
     * Uses the item being rendered, the identifiers for the default shield and the blank banner shield textures provided by the user
     */
    public static void renderBanner(ItemStack stack, Identifier nopattern_texture, Identifier banner_shield_texture){
        final ShieldEntityModel model = new ShieldEntityModel();
        final BannerBlockEntity renderBanner = new BannerBlockEntity();
        final TextureCache.Manager banner_texture = new TextureCache.Manager(banner_shield_texture.toString(), banner_shield_texture, "textures/entity/shield/");
        if (stack.getSubTag("BlockEntityTag") != null) {
            renderBanner.readFrom(stack, FabricBannerShieldItem.getColor(stack));
            MinecraftClient.getInstance().getTextureManager().bindTexture(banner_texture.get(renderBanner.getPatternCacheKey(), renderBanner.getPatterns(), renderBanner.getPatternColors()));
        } else {
            MinecraftClient.getInstance().getTextureManager().bindTexture(nopattern_texture);
        }
        GlStateManager.pushMatrix();
        GlStateManager.scalef(1.0F, -1.0F, -1.0F);
        model.renderItem();
        if (stack.hasEnchantmentGlint()) {
            renderEnchantmentGlint(model::renderItem);
        }
        GlStateManager.popMatrix();
    }
}