package com.github.crimsondawn45.fabricshieldlib.initializers;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class FabricShieldLibEarlyRiser implements Runnable {
    
    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();
        String enchantmentTarget = remapper.mapClassName("intermediary", "net.minecraft.class_1886");
        ClassTinkerers.enumBuilder(enchantmentTarget).addEnumSubclass("FABRIC_SHIELD", "com.github.crimsondawn45.fabricshieldlib.lib.enchantment.ShieldEnchantmentTarget").build();
    }
    
}
