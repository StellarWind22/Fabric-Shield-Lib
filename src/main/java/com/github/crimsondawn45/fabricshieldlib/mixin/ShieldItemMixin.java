package com.github.crimsondawn45.fabricshieldlib.mixin;

import java.util.List;

import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLib;

import org.spongepowered.asm.mixin.Mixin;

import blue.endless.jankson.annotation.Nullable;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BannerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

/**
 * Makes vanilla shield enchantable with an enchantability of 14.
 */
@Mixin(ShieldItem.class)
public class ShieldItemMixin extends Item {

	/**
	 * @param settings item settings
	 */
    public ShieldItemMixin(Settings settings) {
		super(settings);
	}

	@Override
	public boolean isEnchantable(ItemStack item) {
		if(FabricShieldLib.config.allow_vanilla_shield_enchanting) {
			return !item.hasEnchantments();
		} else {
			return false;
		}
	}
	
	@Override
	public int getEnchantability() {
		return FabricShieldLib.config.vanilla_shield_enchantability;
	}
	
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		BannerItem.appendBannerTooltip(stack, tooltip);
        tooltip.add(new LiteralText(""));
        tooltip.add(new TranslatableText("fabricshieldlib.shield_tooltip.start").formatted(Formatting.GRAY));
        tooltip.add(new LiteralText(" 5s ").formatted(Formatting.DARK_GREEN).append(new TranslatableText("fabricshieldlib.shield_tooltip.end")));
    }
}
