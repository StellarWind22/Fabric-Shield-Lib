package com.github.crimsondawn45.fabricshieldlib.lib.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class FabricShieldLibConfig extends MidnightConfig {

    @Entry
    public static boolean enable_tooltips = true;

    @Entry
    public static int vanilla_shield_enchantability = 14;

    @Comment
    public static Comment enchantability_convention;

    @Entry
    public static boolean universal_disable = false;

    @Comment
    public static Comment universal_disable_description;
}
