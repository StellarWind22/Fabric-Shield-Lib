package com.github.crimsondawn45.fabricshieldlib.lib.object;

/**
 * Define FabricShieldLib specific callback functions.
 * <p>
 * Implementing this interface in your shield item class is not
 * compulsory.
 * <p>
 * Since 1.21.5, any {@link net.minecraft.item.Item} that has
 * data component {@link net.minecraft.component.type.BlocksAttacksComponent}
 * will be treated as a shield. In addition,
 * {@link net.minecraft.component.type.BlocksAttacksComponent} defines shield
 * specs. See {@link FabricShieldUtils}.
 */
public interface FabricShield {
	/**
	 * Whether the shield will have a tooltip showing cooldown when hit by an axe.
	 */
	default boolean displayTooltip() {
		return true;
	}
}