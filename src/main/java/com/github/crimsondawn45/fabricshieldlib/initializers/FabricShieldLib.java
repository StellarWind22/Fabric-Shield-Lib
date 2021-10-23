package com.github.crimsondawn45.fabricshieldlib.initializers;

import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldBlockCallback;
import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldDisabledCallback;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricBannerShieldItem;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldDecoratorRecipe;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldEnchantment;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Main class for Fabric Shield Lib
 */
public class FabricShieldLib implements ModInitializer {

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
    public static FabricBannerShieldItem fabric_banner_shield;

    /**
     * Test shield item that does not support banners
     */
    public static FabricShieldItem fabric_shield;

    /**
     * Recipe type and serializer for banner decoration recipe
     */
    public static final SpecialRecipeSerializer<FabricShieldDecoratorRecipe> FABRIC_SHIELD_DECORATION_SERIALIZER;
    public static final RecipeType<FabricShieldDecoratorRecipe> FABRIC_SHIELD_DECORATION;

    /**
     * Test shield enchantment.
     */
    public static FabricShieldEnchantment reflect_enchantment;
    public static FabricShieldEnchantment curse_enchantment;

    static {
        //Registering Banner Recipe (Lib only)
        FABRIC_SHIELD_DECORATION = Registry.register(Registry.RECIPE_TYPE, new Identifier("fabricshieldlib", "fabric_shield_decoration"), new RecipeType<FabricShieldDecoratorRecipe>() {
            @Override
            public String toString() {return "test_recipe";}
        });
        FABRIC_SHIELD_DECORATION_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("fabricshieldlib", "fabric_shield_decoration"), new SpecialRecipeSerializer<>(FabricShieldDecoratorRecipe::new));
    }

    @Override
    public void onInitialize() {
        logger.info("Fabric Shield Lib Initialized!");

        /*
         * Dev environment code.
         */
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {

            //Warn about dev code
            logger.warn("FABRIC SHIELD LIB DEVELOPMENT CODE RAN!!!, if you are not in a development environment this is very bad! Test items and test enchantments will be ingame!");

            //Register Custom Shield
            fabric_banner_shield = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "fabric_banner_shield"), new FabricBannerShieldItem(new Item.Settings().maxDamage(336).group(ItemGroup.COMBAT), 100, 9, Items.OAK_PLANKS, Items.SPRUCE_PLANKS));
            fabric_shield = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "fabric_shield"), new FabricShieldItem(new Item.Settings().maxDamage(336).group(ItemGroup.COMBAT), 100, 9, Items.OAK_PLANKS, Items.SPRUCE_PLANKS));			//Register Development Stuff
            reflect_enchantment = Registry.register(Registry.ENCHANTMENT, new Identifier(MOD_ID, "reflect_enchantment"), new FabricShieldEnchantment(Rarity.COMMON));

            /*
             * Test event: makes any shield with new enchantment reflect 1/3rd of damage back to attacker
             */
            ShieldBlockCallback.EVENT.register((defender, source, amount, hand, shield) -> {

                if(reflect_enchantment.hasEnchantment(shield)) {
                    Entity attacker = source.getAttacker();

                    assert attacker != null;
                    if(defender instanceof PlayerEntity) {  //Defender should always be a player, but check anyways
                        attacker.damage(DamageSource.player((PlayerEntity) defender), (int)Math.round(amount * 0.33F));
                    } else {
                        attacker.damage(DamageSource.mob(defender), (int)Math.round(amount * 0.33F));
                    }
                }

                return ActionResult.PASS;
            });

            /**
             * Makes any shield with curse enchantment unable to blocks.
             */
            ShieldBlockCallback.EVENT.register((defender, source, amount, hand, shield) -> {

                if(curse_enchantment.hasEnchantment(shield)) {
                    logger.info("Canceled shield block event!");
                    return ActionResult.FAIL;
                }

                return ActionResult.PASS;
            });

            /*
             * Test Event: if your shield gets disabled give player speed
             */
            ShieldDisabledCallback.EVENT.register((defender, hand, shield) -> {
                defender.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10, 1, true, false));
                return ActionResult.PASS;
            });

            /**
             * Curse enchantment can't be disabled
             */
            ShieldDisabledCallback.EVENT.register((defender, hand, shield) -> {
                if(curse_enchantment.hasEnchantment(shield)) {
                    return ActionResult.FAIL;
                }
                return ActionResult.PASS;
            });
        }
    }
}
