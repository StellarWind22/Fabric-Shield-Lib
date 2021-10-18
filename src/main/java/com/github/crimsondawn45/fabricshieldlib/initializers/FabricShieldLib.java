package com.github.crimsondawn45.fabricshieldlib.initializers;

import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldBlockCallback;
import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldDisabledCallback;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricBannerShieldItem;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldDecoratorRecipe;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldEnchantment;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

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
    public static FabricBannerShieldItem fabric_shield;

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
    public static final EntityModelLayer fabric_shield_model_layer;
    /**
     * Test shield enchantment.
     */
    public static FabricShieldEnchantment shield_enchantment;

    /**
     * Used to simplify the mixin on the user end to make their shield render banner
     *
     * Uses params from the mixin method, and the model and sprite identifiers made by the player
     */
    public static void renderBanner(ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ShieldEntityModel model, SpriteIdentifier base, SpriteIdentifier base_nopattern){
        boolean bl = stack.getSubNbt("BlockEntityTag") != null;
        matrices.push();
        matrices.scale(1.0F, -1.0F, -1.0F);
        SpriteIdentifier spriteIdentifier = bl ? base : base_nopattern;
        VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, model.getLayer(spriteIdentifier.getAtlasId()), true, stack.hasGlint()));
        model.getHandle().render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        if (bl) {
            List<Pair<BannerPattern, DyeColor>> list = BannerBlockEntity.getPatternsFromNbt(FabricBannerShieldItem.getColor(stack), BannerBlockEntity.getPatternListTag(stack));
            BannerBlockEntityRenderer.renderCanvas(matrices, vertexConsumers, light, overlay, model.getPlate(), spriteIdentifier, false, list, stack.hasGlint());
        } else {
            model.getPlate().render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        matrices.pop();
    }

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
            fabric_shield = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "fabric_shield"), new FabricBannerShieldItem(new Item.Settings().maxDamage(336).group(ItemGroup.COMBAT), 100, 9, Items.OAK_PLANKS));
            fabric_shield_no_banner = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "fabric_shield_no_banner"), new FabricShieldItem(new Item.Settings().maxDamage(336).group(ItemGroup.COMBAT), 100, 9, Items.OAK_PLANKS));			//Register Development Stuff
            shield_enchantment = Registry.register(Registry.ENCHANTMENT, new Identifier(MOD_ID, "shield_enchantment"), new FabricShieldEnchantment(Rarity.COMMON));
            fabric_shield_model_layer = new EntityModelLayer(new Identifier(MOD_ID, "fabric_shield"),"main");
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
