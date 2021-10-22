package com.github.crimsondawn45.fabricshieldlib.initializers;

import java.util.List;

import com.mojang.datafixers.util.Pair;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.DyeColor;
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
    public static void renderBanner(ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ShieldEntityModel model, SpriteIdentifier base, SpriteIdentifier base_nopattern){
        boolean bl = stack.getSubTag("BlockEntityTag") != null;
        matrices.push();
        matrices.scale(1.0F, -1.0F, -1.0F);
        SpriteIdentifier spriteIdentifier = bl ? base : base_nopattern;
        VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getArmorVertexConsumer(vertexConsumers, model.getLayer(spriteIdentifier.getAtlasId()), false, stack.hasEnchantmentGlint()));
        model.method_23775().render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        if (bl) {
            List<Pair<BannerPattern, DyeColor>> list = BannerBlockEntity.method_24280(ShieldItem.getColor(stack), BannerBlockEntity.method_24281(stack));
            BannerBlockEntityRenderer.method_23802(matrices, vertexConsumers, light, overlay, model.method_23774(), spriteIdentifier, false, list);
        } else {
            model.method_23774().render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        matrices.pop();
    }
}