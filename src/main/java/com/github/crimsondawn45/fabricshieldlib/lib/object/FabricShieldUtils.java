package com.github.crimsondawn45.fabricshieldlib.lib.object;

import java.util.List;
import java.util.Optional;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundEvents;

public class FabricShieldUtils {
	public static final int VANILLA_SHIELD_DURABILITY = 336;
	public static final BlocksAttacksComponent VANILLA_SHIELD_BLOCKS_ATTACKS_COMPONENT =
		new BlocksAttacksComponent(
			0.25F,
			1.0F,
			List.of(new BlocksAttacksComponent.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)),
			new BlocksAttacksComponent.ItemDamage(3.0F, 1.0F, 1.0F),
			Optional.of(DamageTypeTags.BYPASSES_SHIELD),
			Optional.of(SoundEvents.ITEM_SHIELD_BLOCK),
			Optional.of(SoundEvents.ITEM_SHIELD_BREAK)
		);

	public static boolean isShieldItem(ItemStack itemStack) {
		return itemStack.contains(DataComponentTypes.BLOCKS_ATTACKS);
	}

	public static boolean isShieldItem(Item item) {
		return item.getComponents().contains(DataComponentTypes.BLOCKS_ATTACKS);
	}

	public static Item.Settings vanillaShieldSettings(Item.Settings settings) {
		return settings
			.maxDamage(FabricShieldUtils.VANILLA_SHIELD_DURABILITY)
			.equippableUnswappable(EquipmentSlot.OFFHAND)
			.component(DataComponentTypes.BLOCKS_ATTACKS, FabricShieldUtils.VANILLA_SHIELD_BLOCKS_ATTACKS_COMPONENT)
			.component(DataComponentTypes.BREAK_SOUND, SoundEvents.ITEM_SHIELD_BREAK);
	}
}
