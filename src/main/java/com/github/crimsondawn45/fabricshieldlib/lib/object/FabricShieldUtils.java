package com.github.crimsondawn45.fabricshieldlib.lib.object;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.List;
import java.util.Optional;

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

	/**
	 * @param itemStack
	 * @return true if the itemStack can be considered as a shield.
	 */
	public static boolean isShieldItem(ItemStack itemStack) {
		return itemStack.contains(DataComponentTypes.BLOCKS_ATTACKS);
	}

	/**
	 * @param item
	 * @return true if the item can be considered as a shield.
	 */
	public static boolean isShieldItem(Item item) {
		return item.getComponents().contains(DataComponentTypes.BLOCKS_ATTACKS);
	}

	/**
	 * @param itemStack
	 * @return true if the itemStack supports banner.
	 */
	public static boolean supportsBanner(ItemStack itemStack) {
		return itemStack.isIn(FabricShieldTags.SUPPORTS_BANNER);
	}

	/**
	 * @param item
	 * @return true if the item supports banner.
	 */
	@SuppressWarnings("deprecation")
	public static boolean supportsBanner(Item item) {
		return item.getRegistryEntry().isIn(FabricShieldTags.SUPPORTS_BANNER);
	}

	public static Item.Settings vanillaShieldSettings(Item.Settings settings) {
		return defaultShieldSettings(settings
			.maxDamage(FabricShieldUtils.VANILLA_SHIELD_DURABILITY)
			.component(DataComponentTypes.BLOCKS_ATTACKS, FabricShieldUtils.VANILLA_SHIELD_BLOCKS_ATTACKS_COMPONENT)
		);
	}

	public static Item.Settings defaultShieldSettings(Item.Settings settings) {
		return settings
			.equippableUnswappable(EquipmentSlot.OFFHAND)
			.component(DataComponentTypes.BREAK_SOUND, SoundEvents.ITEM_SHIELD_BREAK);
	}

	public static BlocksAttacksComponent withCooldownTicks(int cooldownTicks) {
		return withCooldownTicks(FabricShieldUtils.VANILLA_SHIELD_BLOCKS_ATTACKS_COMPONENT, cooldownTicks);
	}

	/**
	 * Replaces FabricShild::getCoolDownTicks
	 * @param in
	 * @param cooldownTicks
	 * @return
	 */
	public static BlocksAttacksComponent withCooldownTicks(BlocksAttacksComponent in, int cooldownTicks) {
		return new BlocksAttacksComponent(
				in.blockDelaySeconds(),
				(float)cooldownTicks / 100.0F,
				in.damageReductions(),
				in.itemDamage(),
				in.bypassedBy(),
				in.blockSound(),
				in.disableSound()
		);
	}

	@Deprecated
	public static BlocksAttacksComponent withBlockDelaySeconds(BlocksAttacksComponent in, float blockDelaySeconds) {
		return new BlocksAttacksComponent(
				blockDelaySeconds,
				in.disableCooldownScale(),
				in.damageReductions(),
				in.itemDamage(),
				in.bypassedBy(),
				in.blockSound(),
				in.disableSound()
		);
	}




	//TODO:maybe make seperate methods for the 4 variables in damageReductions: type, base, factor, blocking angle
	@Deprecated
	public static BlocksAttacksComponent withDamageReductions(BlocksAttacksComponent in, BlocksAttacksComponent.DamageReduction damageReductions) {
		return new BlocksAttacksComponent(
				in.blockDelaySeconds(),
				in.disableCooldownScale(),
				List.of(damageReductions),
				in.itemDamage(),
				in.bypassedBy(),
				in.blockSound(),
				in.disableSound()
		);
	}

	//TODO: Same concern as above: threshold, base, factor
	@Deprecated
	public static BlocksAttacksComponent withItemDamage(BlocksAttacksComponent in, BlocksAttacksComponent.ItemDamage itemDamage) {
		return new BlocksAttacksComponent(
				in.blockDelaySeconds(),
				in.disableCooldownScale(),
				in.damageReductions(),
				itemDamage,
				in.bypassedBy(),
				in.blockSound(),
				in.disableSound()
		);
	}

	@Deprecated
	public static BlocksAttacksComponent withBypassedDamageTypes(BlocksAttacksComponent in, TagKey<DamageType> damageType) {
		return new BlocksAttacksComponent(
				in.blockDelaySeconds(),
				in.disableCooldownScale(),
				in.damageReductions(),
				in.itemDamage(),
				Optional.of(damageType),
				in.blockSound(),
				in.disableSound()
		);
	}

	@Deprecated
	public static BlocksAttacksComponent withBlocSound(BlocksAttacksComponent in, RegistryEntry<SoundEvent> blockSound) {
		return new BlocksAttacksComponent(
				in.blockDelaySeconds(),
				in.disableCooldownScale(),
				in.damageReductions(),
				in.itemDamage(),
				in.bypassedBy(),
				Optional.of(blockSound),
				in.disableSound()
		);
	}

	@Deprecated
	public static BlocksAttacksComponent withDisableSound(BlocksAttacksComponent in, RegistryEntry<SoundEvent> disableSound) {
		return new BlocksAttacksComponent(
				in.blockDelaySeconds(),
				in.disableCooldownScale(),
				in.damageReductions(),
				in.itemDamage(),
				in.bypassedBy(),
				in.blockSound(),
				Optional.of(disableSound)
		);
	}

}
