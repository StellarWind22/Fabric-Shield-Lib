package com.github.crimsondawn45.fabricshieldlib.lib.object;

/**
 * used to identify which items should be treated as shields.
 */
public interface FabricShield {

    /**
     * Vanilla shield: 100 ticks.
     * @return how many ticks shield will be disabled for when it with axe.
     */
    public int getCooldownTicks();
}