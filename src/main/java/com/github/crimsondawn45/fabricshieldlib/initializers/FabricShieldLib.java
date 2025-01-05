package com.github.crimsondawn45.fabricshieldlib.initializers;

import com.github.crimsondawn45.fabricshieldlib.lib.config.FabricShieldLibConfig;
import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldBlockCallback;
import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldDisabledCallback;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricBannerShieldItem;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldDecoratorRecipe;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldLibDataGenerator;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShieldDecorationRecipe;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * Main class for Fabric Shield Lib.
 */
@SuppressWarnings("ALL")
public class FabricShieldLib implements ModInitializer {

    /**
     * Fabric Shield Lib's mod id.
     */
    public static final String MOD_ID = "fabricshieldlib";

    /**
     * Fabric Shield Lib's logger.
     */
    public static final Logger logger = LoggerFactory.getLogger(MOD_ID);

    /**
     * Test shield item.
     */
    public static FabricBannerShieldItem fabric_banner_shield;

    /**
     * Test shield item that does not support banners.
     */
    public static FabricShieldItem fabric_shield;


    /**
     * Recipe type and serializer for banner decoration recipe.
     */

    public static final SpecialCraftingRecipe.SpecialRecipeSerializer<ShieldDecorationRecipe> FABRIC_SHIELD_DECORATION_SERIALIZER;
    public static final RecipeType<FabricShieldDecoratorRecipe> FABRIC_SHIELD_DECORATION;

    static {
        //Registering Banner Recipe (Lib only)
        FABRIC_SHIELD_DECORATION = Registry.register(Registries.RECIPE_TYPE, Identifier.of(MOD_ID, "fabric_shield_decoration"), new RecipeType<FabricShieldDecoratorRecipe>() {
            @Override
            public String toString() {return "fabric_shield_decoration";}
        });
        FABRIC_SHIELD_DECORATION_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(MOD_ID, "fabric_shield_decoration"), new SpecialCraftingRecipe.SpecialRecipeSerializer<>(FabricShieldDecoratorRecipe::new));
        }


    @Override
    public void onInitialize() {

        //Register Config
        MidnightConfig.init(MOD_ID, FabricShieldLibConfig.class);

        //Dev environment code.
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {

            //Warn about dev code
            logger.warn("FABRIC SHIELD LIB DEVELOPMENT CODE RAN!!!, if you are not in a development environment this is very bad! Test items and test enchantments will be ingame!");

            //Register Custom Shield
            fabric_banner_shield = registerItem("fabric_banner_shield", (props) -> new FabricBannerShieldItem(props.maxDamage(336), 85, 9, Items.OAK_PLANKS, Items.SPRUCE_PLANKS));

            fabric_shield = registerItem("fabric_shield", (props) -> new FabricShieldItem(props.maxDamage(336), 100, 9, Items.OAK_PLANKS, Items.SPRUCE_PLANKS));

            ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
                entries.addAfter(Items.SHIELD,fabric_banner_shield);
                entries.addAfter(fabric_banner_shield,fabric_shield);
            });

            //Test event: makes any shield with new enchantment reflect a 1/3rd of damage back to attacker
            ShieldBlockCallback.EVENT.register((defender, source, amount, hand, shield) -> {

                RegistryKey<Enchantment> key = FabricShieldLibDataGenerator.EnchantmentGenerator.REFLECTION;
                RegistryEntry<Enchantment> entry = defender.getWorld().getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).getEntry(key.getValue()).get();
                int reflectNumber = EnchantmentHelper.getLevel(entry, shield);

                if(reflectNumber > 0) {
                    Entity attacker = source.getAttacker();

                    if(attacker.equals(null)) {
                        return ActionResult.CONSUME;
                    }
                    if(defender.blockedByShield(source)){
                        World world = attacker.getWorld();
                        if(defender instanceof PlayerEntity) {  //Defender should always be a player, but check anyway
                            attacker.sidedDamage(world.getDamageSources().playerAttack((PlayerEntity) defender), Math.round(amount * 0.33F));
                        } else {
                            attacker.sidedDamage(world.getDamageSources().mobAttack(defender), Math.round(amount * 0.33F));
                        }
                    }
                }

                return ActionResult.PASS;
            });

            //Test Event: if your shield gets disabled, give player speed
            ShieldDisabledCallback.EVENT.register((defender, hand, shield) -> {
                defender.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10, 1, true, false));
                return ActionResult.PASS;
            });
        }
        //Announce having finished starting up
        logger.info("Fabric Shield Lib Initialized!");
    }

    private static <T extends Item> T registerItem(String name, Function<Item.Settings, T> constructor) {
    	RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, name));
    	Item.Settings settings = new Item.Settings();
    	settings = settings.registryKey(key);
    	T item = constructor.apply(settings);
    	return Registry.register(Registries.ITEM, key, item);
    }
}
