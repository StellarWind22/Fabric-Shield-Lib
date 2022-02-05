package com.github.crimsondawn45.fabricshieldlib.lib.object;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

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

    default List<Text> getCooldownTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, int cooldownTicks) {

        List<Text> advanced = new ArrayList<Text>();
        if(context.isAdvanced()) {

            if(stack.isDamaged()) {

                for(int i = 0; i < tooltip.size(); i++) {

                    Text text = tooltip.get(i);
                    String strText = text.getString();

                    if(strText.startsWith("Durability")) {
                        advanced.add(text);
                        tooltip.remove(i);
                    }
                }
            }

            for(int i = 0; i < tooltip.size(); i++) {

                Text text = tooltip.get(i);
                String strText = text.getString();

                if(Identifier.isValid(strText)) {
                    advanced.add(text);
                    tooltip.remove(i);
                }
            }
            
            
            if(stack.hasNbt()) {
                for(int i = 0; i < tooltip.size(); i++) {

                    Text text = tooltip.get(i);
                    String strText = text.getString();

                    if(strText.startsWith("NBT: ")) {
                        advanced.add(text);
                        tooltip.remove(i);
                    }
                }
            }
        }

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

        /**
         * Append advanced info
         */
        if(context.isAdvanced()) {
            tooltip.addAll(advanced);
        }
        return tooltip;
    }
}