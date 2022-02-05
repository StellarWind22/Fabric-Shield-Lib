package com.github.crimsondawn45.fabricshieldlib.lib.object;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

/**
 * used to identify which items should be treated as shields.
 */
public interface FabricShield {

    /**
     * Vanilla shield: 100 ticks.
     * @return how many ticks shield will be disabled for when it with axe.
     */
    int getCooldownTicks();

    /**
     * If shield supports banners. Used for enabling banner crafting
     * @return Whether a shield supports banners.
     */
    boolean supportsBanner();

    default List<Text> getCooldownTooltip(ItemStack stack, List<Text> tooltip, int cooldownTicks) {

        /**
         * Add disabled cooldown tooltip
         */
        tooltip.add(new LiteralText(""));
        tooltip.add(new TranslatableText("fabricshieldlib.shield_tooltip.start").formatted(Formatting.GRAY));

        /**
         * All of this is so if there is a .0 instead of there being a need for a 
         * decimal remove the .0
         */
        String cooldown = String.valueOf((Double)(cooldownTicks / 20.0));
        char[] splitCooldown = cooldown.toCharArray();
        if(splitCooldown.length >= 3) {

            if(splitCooldown[2] == '0') {

                if(!(splitCooldown.length >= 4)) {
                    cooldown = String.valueOf(splitCooldown[0]);
                }
            }
        }

        tooltip.add(new LiteralText(" " + cooldown +"s ").formatted(Formatting.DARK_GREEN).append(new TranslatableText("fabricshieldlib.shield_tooltip.end")));
        return tooltip;
    }
}