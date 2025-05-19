package com.github.crimsondawn45.fabricshieldlib.lib.object;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;

/**
 * Pre-made class for quickly making custom shields which support banners.
 */
public class FabricBannerShieldItem extends FabricShieldItem {
    /**
     * @param settings       item settings.
     * @param coolDownTicks  ticks shield will be disabled for when it with axe. Vanilla: 100
     * @param enchantability enchantability of shield. Vanilla: 9
     * @param repairItems    item(s) for repairing shield.
     */
    public FabricBannerShieldItem(Settings settings, int coolDownTicks, int enchantability, Item... repairItems) {
        super(settings, coolDownTicks, enchantability, repairItems);
    }

    /**
     * @param settings      item settings.
     * @param coolDownTicks ticks shield will be disabled for when it with axe. Vanilla: 100
     * @param material      tool material.
     */
    public FabricBannerShieldItem(Settings settings, int coolDownTicks, ToolMaterial material) {
        super(settings, coolDownTicks, material);
    }

    /**
     * @param settings       item settings.
     * @param coolDownTicks  ticks shield will be disabled for when it with axe. Vanilla: 100
     * @param enchantability enchantability of shield. Vanilla: 9
     * @param repairItemTag  item tag for repairing shield.
     */
    public FabricBannerShieldItem(Settings settings, int coolDownTicks, int enchantability, TagKey<Item> repairItemTag) {
        super(settings, coolDownTicks, enchantability, repairItemTag);
    }

    /**
     * @param settings       item settings.
     * @param enchantability enchantability of shield. Vanilla: 9
     * @param repairItems    item(s) for repairing shield.
     */
    public FabricBannerShieldItem(Settings settings, int enchantability, Item... repairItems) {
        super(settings, enchantability, repairItems);
    }

    /**
     * @param settings      item settings.
     * @param material      tool material.
     */
    public FabricBannerShieldItem(Settings settings, ToolMaterial material) {
        super(settings, material);
    }

    /**
     * @param settings       item settings.
     * @param enchantability enchantability of shield. Vanilla: 9
     * @param repairItemTag  item tag for repairing shield.
     */
    public FabricBannerShieldItem(Settings settings, int enchantability, TagKey<Item> repairItemTag) {
        super(settings, enchantability, repairItemTag);
    }

    @Override
    public Text getName(ItemStack stack) {
        DyeColor dyeColor = (DyeColor)stack.get(DataComponentTypes.BASE_COLOR);
        if (dyeColor != null) {
            String key = this.getTranslationKey();
            return Text.translatable(key + "." + dyeColor.name());
        } else {
            return super.getName(stack);
        }
    }
}