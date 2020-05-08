package me.crimsondawn45.fabricshieldlib.render;

import java.util.List;

import com.mojang.datafixers.util.Pair;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRenderer;
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

public class FabricBannerShieldRenderer implements BuiltinItemRenderer
{
	private ShieldEntityModel modelShield;
	public SpriteIdentifier shield_base;
	public SpriteIdentifier shield_base_no_pattern;
	
	public FabricBannerShieldRenderer(String modId, String itemName)
	{
		this.shield_base = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX, new Identifier(modId + "entity/" + itemName + "base"));
		this.shield_base_no_pattern = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX, new Identifier(modId + "entity/" + itemName + "_base_nopattern"));
	}
	
	@Override
	public void render(ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay)
	{
		boolean bl = stack.getSubTag("BlockEntityTag") != null;
		matrices.push();
		matrices.scale(1.0F, -1.0F, -1.0F);
		SpriteIdentifier spriteIdentifier = bl ? shield_base : shield_base_no_pattern;
		VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getArmorVertexConsumer(vertexConsumers, this.modelShield.getLayer(spriteIdentifier.getAtlasId()), false, stack.hasEnchantmentGlint()));
		this.modelShield.method_23775().render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
		
        if (bl)
        {
        	List<Pair<BannerPattern, DyeColor>> list = BannerBlockEntity.method_24280(ShieldItem.getColor(stack), BannerBlockEntity.getPatternListTag(stack));
        	BannerBlockEntityRenderer.renderCanvas(matrices, vertexConsumers, light, overlay, this.modelShield.method_23774(), spriteIdentifier, false, list);
        }
        else
        {
        	this.modelShield.method_23774().render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        }
	}
}
