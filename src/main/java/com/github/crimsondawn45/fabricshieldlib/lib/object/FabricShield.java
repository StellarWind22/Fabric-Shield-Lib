package com.github.crimsondawn45.fabricshieldlib.lib.object;

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
}