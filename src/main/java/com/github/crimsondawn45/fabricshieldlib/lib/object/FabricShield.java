package com.github.crimsondawn45.fabricshieldlib.lib.object;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

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
    int getCoolDownTicks();

    /**
     * If shield supports banners. Used for enabling banner crafting.
     *
     * @return Whether a shield supports banners.
     */
    boolean supportsBanner();

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
    default void appendShieldTooltip(ItemStack stack, List<Text> tooltip, TooltipContext context) {
    }
}