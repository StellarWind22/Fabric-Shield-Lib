package com.github.crimsondawn45.fabricshieldlib.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

import com.github.crimsondawn45.fabricshieldlib.lib.config.FabricShieldLibConfig;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

/**
 * Makes vanilla shield enchantable with an enchantability of 14.
 */
@Mixin(Items.class)
public class ItemsMixin {
	@ModifyExpressionValue(
			method = "<clinit>",
			slice = @Slice(
					from = @At(value = "CONSTANT", args = "stringValue=shield"),
					to = @At(value = "FIELD", opcode = Opcodes.PUTSTATIC, target = "Lnet/minecraft/item/Items;SHIELD:Lnet/minecraft/item/Item;")),
			at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item$Settings;equippableUnswappable(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/Item$Settings;"))
	private static Item.Settings assignVanillaShieldEnchantability(Item.Settings settings) {
		return settings.enchantable(FabricShieldLibConfig.vanilla_shield_enchantability);
	}
}
