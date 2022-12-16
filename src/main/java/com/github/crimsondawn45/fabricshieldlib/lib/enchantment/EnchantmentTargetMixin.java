package com.github.crimsondawn45.fabricshieldlib.lib.enchantment;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("ALL") // Don't need this in mixins.json
@Mixin(EnchantmentTarget.class)
abstract class EnchantmentTargetMixin {

    @Shadow
    public abstract boolean isAcceptableItem(Item item);
}
