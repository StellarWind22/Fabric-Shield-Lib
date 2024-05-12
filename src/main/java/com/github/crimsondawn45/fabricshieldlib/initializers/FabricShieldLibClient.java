package com.github.crimsondawn45.fabricshieldlib.initializers;

import com.github.crimsondawn45.fabricshieldlib.lib.config.FabricShieldLibConfig;
import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldSetModelCallback;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShield;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.item.TooltipType;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FabricShieldLibClient implements ClientModInitializer {

    /**
     * Will be made by user (dev code).
     */
    public static final EntityModelLayer fabric_banner_shield_model_layer = new EntityModelLayer(new Identifier(FabricShieldLib.MOD_ID, "fabric_banner_shield"),"main");
    public static ShieldEntityModel modelFabricShield;
    @SuppressWarnings("deprecation")
    public static final SpriteIdentifier FABRIC_BANNER_SHIELD_BASE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(FabricShieldLib.MOD_ID, "entity/fabric_banner_shield_base"));
    @SuppressWarnings("deprecation")
    public static final SpriteIdentifier FABRIC_BANNER_SHIELD_BASE_NO_PATTERN = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(FabricShieldLib.MOD_ID, "entity/fabric_banner_shield_base_nopattern"));
    
    @Override
    public void onInitializeClient() {

        /*
         * Register tooltip callback this is the same as mixing into the end of:
         * ItemStack.getTooltip()
         */
        ItemTooltipCallback.EVENT.register((stack, context, type, tooltip) -> {

            if(FabricShieldLibConfig.enable_tooltips) {
                
                if(stack.getItem() instanceof FabricShield) {

                    FabricShield shield = (FabricShield) stack.getItem();

                    //Add any custom tooltips
                    shield.appendTooltip(stack, context, tooltip, type);

                    //Add cooldown tooltip
                    if(shield.displayTooltip()) {
                        getCooldownTooltip(stack, type ,tooltip, shield.getCoolDownTicks());
                    }
                }

                //Display tooltip for vanilla shield
                if(stack.getItem().equals(Items.SHIELD)) {
                    getCooldownTooltip(stack, type,tooltip, 100);
                }
            }
        });

        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {

            //Warn about dev code
            FabricShieldLib.logger.warn("FABRIC SHIELD LIB DEVELOPMENT CODE RAN!!!, if you are not in a development environment this is very bad! Client side banner code ran!");

            //Registers sprite directories and model layer, will be done by player, dev code
            EntityModelLayerRegistry.registerModelLayer(fabric_banner_shield_model_layer, ShieldEntityModel::getTexturedModelData);

            //Set model
            ShieldSetModelCallback.EVENT.register((loader) -> {
                modelFabricShield = new ShieldEntityModel(loader.getModelPart(FabricShieldLibClient.fabric_banner_shield_model_layer));
                return ActionResult.PASS;
            });

            //Register renderer
            BuiltinItemRendererRegistry.INSTANCE.register(FabricShieldLib.fabric_banner_shield, (stack, mode, matrices, vertexConsumers, light, overlay) -> {
                renderBanner(stack, matrices, vertexConsumers, light, overlay, modelFabricShield, FABRIC_BANNER_SHIELD_BASE, FABRIC_BANNER_SHIELD_BASE_NO_PATTERN);
            });
        }
    }

    /**
     * Used to simplify the mixin on the user end to make their shield render banner.
     *
     * Uses params from the mixin method, and the model and sprite identifiers made by the player.
     */
    public static void renderBanner(ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ShieldEntityModel model, SpriteIdentifier base, SpriteIdentifier base_nopattern){
        BannerPatternsComponent bannerPatternsComponent = (BannerPatternsComponent)stack.getOrDefault(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT);
        DyeColor dyeColor2 = (DyeColor)stack.get(DataComponentTypes.BASE_COLOR);
        boolean bl = !bannerPatternsComponent.layers().isEmpty() || dyeColor2 != null;
        matrices.push();
        matrices.scale(1.0F, -1.0F, -1.0F);
        SpriteIdentifier spriteIdentifier = bl ? base : base_nopattern;
        VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, model.getLayer(spriteIdentifier.getAtlasId()), true, stack.hasGlint()));
        model.getHandle().render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        if (bl) {
            BannerBlockEntityRenderer.renderCanvas(matrices, vertexConsumers, light, overlay, model.getPlate(), spriteIdentifier, false, (DyeColor) Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasGlint());
        } else {
            model.getPlate().render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        matrices.pop();
    }

    /**
     * Shield tooltip thing.
     */
    public static List<Text> getCooldownTooltip(ItemStack stack, TooltipType type, List<Text> tooltip, int cooldownTicks) {

        List<Text> advanced = new ArrayList<Text>();

        /*
         * These all loop in reverse to grab the first instance of a match
         * at the end of the tooltip
         */
        if(type.isAdvanced()) {

            //Grab durability
            if(stack.isDamaged()) {

                for(int i = tooltip.size() - 1; i > 0; i--) {

                    Text text = tooltip.get(i);
                    String strText = text.getString();

                    if(strText.startsWith("Durability")) {
                        advanced.add(text);
                        tooltip.remove(i);
                        break;
                    }
                }
            }

            //Grab item id
            for(int i = tooltip.size() - 1; i > 0; i--) {

                Text text = tooltip.get(i);
                String strText = text.getString().trim();

                if(Identifier.isValid(strText)) {
                    advanced.add(text);
                    tooltip.remove(i);
                    break;
                }
            }
            
            //Grab nbt string
            if(!stack.getComponents().isEmpty()) {
                for(int i = tooltip.size() - 1; i > 0; i--) {

                    Text text = tooltip.get(i);
                    String strText = text.getString();

                    if(strText.startsWith("NBT: ")) {
                        advanced.add(text);
                        tooltip.remove(i);
                        break;
                    }
                }
            }
        }

        //Add disabled cooldown tooltip
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("fabricshieldlib.shield_tooltip.start").append(Text.literal(":")).formatted(Formatting.GRAY));

        /*
         * All of this is so if there is a .0 instead of there being a need for a 
         * decimal remove the .0
         */
        String cooldown = String.valueOf((Double)(cooldownTicks / 20.0));
        char[] splitCooldown = cooldown.toCharArray();
        if(splitCooldown.length >= 3) {

            if(splitCooldown[2] == '0') {

                if(!(splitCooldown.length >= 4)) {
                    cooldown = String.valueOf(splitCooldown[0]);
                }
            }
        }

        tooltip.add(Text.literal(" " + cooldown)
                        .formatted(Formatting.DARK_GREEN)
                        .append(Text.translatable("fabricshieldlib.shield_tooltip.unit"))
                        .append(Text.literal(" "))
                        .append(Text.translatable("fabricshieldlib.shield_tooltip.end")));

        //Append advanced info
        if(type.isAdvanced()) {
            tooltip.addAll(advanced);
        }
        return tooltip;
    }
}