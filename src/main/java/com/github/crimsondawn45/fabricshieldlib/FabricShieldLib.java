package com.github.crimsondawn45.fabricshieldlib;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldBlockCallback;
import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldDisabledCallback;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldEnchantment;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main class for Fabric Shield Lib
 */
public class FabricShieldLib  implements ModInitializer {

    /**
     * Fabric Shield Lib's modid.
     */
    public static final String MOD_ID = "fabricshieldlib";
    /**
     * Fabric Shield Lib's logger.
     */
    public static final Logger logger = LogManager.getLogger(MOD_ID);

    /**
     * Test shield item.
     */
    public static FabricShieldItem fabric_shield;
    /**
     * Test shield enchantment.
     */
    public static FabricShieldEnchantment shield_enchantment;

    @Override
    public void onInitialize() {
        logger.info("Fabric Shield Lib Initialized!");
        
        /**
         * Dev environment code.
         */
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {

            //Warn about dev code
            logger.warn("FABRIC SHIELD LIB DEVELOPMENT CODE RAN!!!, if you are not in a development environment this is very bad! Test items and test enchantments will be ingame!");

            //Register Custom Shield
            fabric_shield = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "fabric_shield"), new FabricShieldItem(new Item.Settings().maxDamage(336).group(ItemGroup.COMBAT), 100, 9, Items.OAK_PLANKS));
            shield_enchantment = Registry.register(Registry.ENCHANTMENT, new Identifier(MOD_ID, "shield_enchantment"), new FabricShieldEnchantment(Rarity.COMMON));

            /**
             * Test event: makes any shield with new enchantment reflect 1/3rd of damage back to attacker
             */
            ShieldBlockCallback.EVENT.register((defender, source, amount, hand, shield) -> {

                if(shield_enchantment.hasEnchantment(shield)) {
                    Entity attacker = source.getAttacker();

                    if(defender instanceof PlayerEntity) {  //Defender should always be a player, but check anyways
                        attacker.damage(DamageSource.player((PlayerEntity) defender), (int)Math.round(amount * 0.33F));
                    } else {
                        attacker.damage(DamageSource.mob(defender), (int)Math.round(amount * 0.33F));
                    }
                }

                return ActionResult.PASS;
            });

            /**
             * Test Event: if your shield get's disabled give player speed
             */
            ShieldDisabledCallback.EVENT.register((defender, hand, shield) -> {
                defender.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10, 1, true, false));
                return ActionResult.PASS;
            });
        }
    }
}