package com.github.crimsondawn45.fabricshieldlib.initializers;

import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldBlockCallback;
import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldDisabledCallback;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldDecoratorRecipe;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldEnchantment;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    public static FabricShieldItem fabric_shield;

    /**
     * Test shield item that does not support banners
     */
    public static FabricShieldItem fabric_shield_no_banner;

    /**
     * Recipe type and serializer for banner decoration recipe
     */
    public static final SpecialRecipeSerializer<FabricShieldDecoratorRecipe> FABRIC_SHIELD_DECORATION_SERIALIZER;
    public static final RecipeType<FabricShieldDecoratorRecipe> FABRIC_SHIELD_DECORATION;

    /**
     * Will be made by user (dev code)
     */
    public static final EntityModelLayer fabric_shield_model_layer = new EntityModelLayer(new Identifier(MOD_ID, "fabric_shield"),"main");

    /**
     * Makes fabric shield
     */
    public static ShieldEntityModel modelFabricShield;
    /**
     * Test shield enchantment.
     */
    public static FabricShieldEnchantment shield_enchantment;

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
            fabric_shield = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "fabric_shield"), new FabricShieldItem(new Item.Settings().maxDamage(336).group(ItemGroup.COMBAT), 100, 9, Items.OAK_PLANKS, true));
            fabric_shield_no_banner = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "fabric_shield_no_banner"), new FabricShieldItem(new Item.Settings().maxDamage(336).group(ItemGroup.COMBAT), 100, 9, Items.OAK_PLANKS, false));			//Register Development Stuff
            shield_enchantment = Registry.register(Registry.ENCHANTMENT, new Identifier(MOD_ID, "shield_enchantment"), new FabricShieldEnchantment(Rarity.COMMON));

            /*
             * Test event: makes any shield with new enchantment reflect 1/3rd of damage back to attacker
             */
            ShieldBlockCallback.EVENT.register((defender, source, amount, hand, shield) -> {

                if(shield_enchantment.hasEnchantment(shield)) {
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

            /*
             * Test Event: if your shield gets disabled give player speed
             */
            ShieldDisabledCallback.EVENT.register((defender, hand, shield) -> {
                defender.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10, 1, true, false));
                return ActionResult.PASS;
            });
        }
    }
}