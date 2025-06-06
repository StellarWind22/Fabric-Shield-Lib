package com.github.crimsondawn45.fabricshieldlib.lib.object;

/**
 * Used to identify which items should be treated as shields.
 */
public interface FabricShield {
    /**
     * Whether the shield will have a tooltip showing cooldown when hit by an axe.
     */
    default boolean displayTooltip() {
        return true;
    }
}