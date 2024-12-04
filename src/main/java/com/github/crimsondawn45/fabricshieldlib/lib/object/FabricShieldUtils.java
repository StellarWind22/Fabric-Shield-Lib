package com.github.crimsondawn45.fabricshieldlib.lib.object;

import net.minecraft.item.Item;
import net.minecraft.item.ShieldItem;

public class FabricShieldUtils {
	public static boolean isShieldItem(Item item) {
		return item instanceof ShieldItem || item instanceof FabricShield;
	}
}
