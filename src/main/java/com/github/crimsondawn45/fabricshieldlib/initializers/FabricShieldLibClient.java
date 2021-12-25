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
    //public static final EntityModelLayer fabric_banner_shield_model_layer = new EntityModelLayer(new Identifier(FabricShieldLib.MOD_ID, "fabric_banner_shield"),"main");
    
    @Override
    public void onInitializeClient() {
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {

            //Warn about dev code
            FabricShieldLib.logger.warn("FABRIC SHIELD LIB DEVELOPMENT CODE RAN!!!, if you are not in a development environment this is very bad! Client side banner code ran!");

            /*
             * Registers sprite directories and model layer, will be done by player, dev code
             */
            //EntityModelLayerRegistry.registerModelLayer(fabric_banner_shield_model_layer, ShieldEntityModel::getTexturedModelData);
            ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEX).register((atlasTexture, registry) -> {
                registry.register(new Identifier(FabricShieldLib.MOD_ID, "entity/fabric_banner_shield_base"));
                registry.register(new Identifier(FabricShieldLib.MOD_ID, "entity/fabric_banner_shield_base_nopattern"));
            });
        }
    }

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
        final TextureCache.Manager banner_texture = new TextureCache.Manager("shield_", banner_shield_texture, "textures/entity/shield/");
        if (stack.getSubTag("BlockEntityTag") != null) {
            renderBanner.readFrom(stack, ShieldItem.getColor(stack));
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