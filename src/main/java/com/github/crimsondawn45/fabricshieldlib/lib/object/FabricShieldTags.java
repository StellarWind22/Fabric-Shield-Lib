package com.github.crimsondawn45.fabricshieldlib.lib.object;

import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLib;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class FabricShieldTags {
	public static final TagKey<Item> SHOW_TOOLTIP =
		TagKey.of(RegistryKeys.ITEM, Identifier.of(FabricShieldLib.MOD_ID, "show_tooltip"));
	public static final TagKey<Item> SUPPORTS_BANNER =
		TagKey.of(RegistryKeys.ITEM, Identifier.of(FabricShieldLib.MOD_ID, "supports_banner"));
}
