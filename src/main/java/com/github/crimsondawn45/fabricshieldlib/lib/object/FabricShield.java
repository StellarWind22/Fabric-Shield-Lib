package com.github.crimsondawn45.fabricshieldlib.lib.object;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Used to identify which items should be treated as shields.
 */
public interface FabricShield {

    /**
     * Vanilla shield: 100 ticks.
     *
     * @return how many ticks shield will be disabled for when it with axe.
     */
	default int getCoolDownTicks(@Nullable ItemStack itemStack) {
		return 100;
	}

    /**
     * If shield supports banners. Used for enabling banner crafting.
     *
     * @return Whether a shield supports banners.
     */
	default boolean supportsBanner() {
		return false;
	}

    /**
     * Whether the shield will have a tooltip showing cooldown when hit by an axe.
     */
    default boolean displayTooltip() {
        return true;
    }

    /**
     * Adds a tooltip immediately after the name & before the tooltip saying shield stats.
     *
     * @param stack   shield's item stack
     * @param tooltip current tooltip
     * @param context context
     */
    default void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
    }
}