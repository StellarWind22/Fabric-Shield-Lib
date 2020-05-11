package me.crimsondawn45.fabricshieldlib.render;

import java.util.List;

import com.mojang.datafixers.util.Pair;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRenderer;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FabricBannerShieldRenderer implements BuiltinItemRenderer
{
	private FabricShieldModel modelShield = new FabricShieldModel();
	public Sprite shield_base;
	public Sprite shield_base_no_pattern;
	
	@Override
	public void render(ItemStack stack, MatrixStack matricies, VertexConsumerProvider vertexConsumers, int light, int overlay)
	{	
		String nameSpace = Registry.ITEM.getId(stack.getItem()).getNamespace();
		String itemName = stack.getItem().toString();
		
		MinecraftClient mc = MinecraftClient.getInstance();
		
		this.shield_base = mc.getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier(nameSpace, "entity/" + itemName + "_base"));
		this.shield_base_no_pattern = mc.getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier(nameSpace, "entity/" + itemName + "_base_no_pattern"));
		
		mc.getTextureManager().
		
		
		boolean bl = stack.getSubTag("BlockEntityTag") != null;
        matricies.push();
        matricies.scale(1.0F, -1.0F, -1.0F);
         
        //Selects a sprite
        Sprite sprite = bl ? this.shield_base : this.shield_base_no_pattern;
         
        //Grabs vertext consumer for the sprite that was selected
        VertexConsumer vertexConsumer = sprite.getTextureSpecificVertexConsumer(ItemRenderer.getArmorVertexConsumer(vertexConsumers, this.modelShield.getLayer(SpriteAtlasTexture.BLOCK_ATLAS_TEX), false, stack.hasEnchantmentGlint()));
         
        //Renders the handle
        this.modelShield.getShieldHandle().render(matricies, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
         
        //Renders the flat part of the shield based on the bool
        if (bl)
        {
           List<Pair<BannerPattern, DyeColor>> list = BannerBlockEntity.method_24280(ShieldItem.getColor(stack), BannerBlockEntity.getPatternListTag(stack));
           BannerBlockEntityRenderer.renderCanvas(matricies, vertexConsumers, light, overlay, this.modelShield.getShieldFlat(), new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX, new Identifier(nameSpace, "entity/" + itemName + "_base")), false, list);
        }
        else
        {
           this.modelShield.getShieldFlat().render(matricies, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        matricies.pop();
        
        sprite.close();
	}
}
