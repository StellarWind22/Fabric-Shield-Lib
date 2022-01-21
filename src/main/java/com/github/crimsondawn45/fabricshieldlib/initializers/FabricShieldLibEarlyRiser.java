package com.github.crimsondawn45.fabricshieldlib.initializers;

import com.chocohead.mm.api.ClassTinkerers;

import net.fabricmc.loader.api.FabricLoader;

public class FabricShieldLibEarlyRiser implements Runnable {

    @Override
    public void run() {
        var remapper = FabricLoader.getInstance().getMappingResolver();
        String enchantmentTarget = remapper.mapClassName("intermediary", "net.minecraft.class_1886");
        ClassTinkerers.enumBuilder(enchantmentTarget, new Class[0]).addEnumSubclass("FABRIC_SHIELD", "com.github.crimsondawn45.fabricshieldlib.lib.enchantment.ShieldTarget").build();
    }
}
