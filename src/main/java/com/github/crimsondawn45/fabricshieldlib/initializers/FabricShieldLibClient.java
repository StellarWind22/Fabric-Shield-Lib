package com.github.crimsondawn45.fabricshieldlib.initializers;

import com.github.crimsondawn45.fabricshieldlib.lib.config.FabricShieldLibConfig;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShield;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldModelRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.item.model.special.SpecialModelTypes;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class FabricShieldLibClient implements ClientModInitializer {
	@SuppressWarnings("deprecation")
	public static final SpriteIdentifier FABRIC_BANNER_SHIELD_BASE = new SpriteIdentifier(
		SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE,
		Identifier.of(FabricShieldLib.MOD_ID, "entity/fabric_banner_shield_base")
	);

	@SuppressWarnings("deprecation")
	public static final SpriteIdentifier FABRIC_BANNER_SHIELD_BASE_NO_PATTERN = new SpriteIdentifier(
		SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE,
		Identifier.of(FabricShieldLib.MOD_ID, "entity/fabric_banner_shield_base_nopattern")
	);

	public static final Identifier FABRIC_BANNER_SHIELD_MODEL_TYPE =
		Identifier.of(FabricShieldLib.MOD_ID, "fabric_banner_shield");

	@Override
	public void onInitializeClient() {
		SpecialModelTypes.ID_MAPPER.put(
			FABRIC_BANNER_SHIELD_MODEL_TYPE,
			FabricShieldModelRenderer.Unbaked.CODEC
		);

		/*
		 * Register tooltip callback this is the same as mixing into the end of:
		 * ItemStack.getTooltip()
		 */
		ItemTooltipCallback.EVENT.register((stack, context, type, tooltip) -> {
			if (!FabricShieldLibConfig.enable_tooltips)
				return;

			boolean displayTooltip = true;
			if (stack.getItem() instanceof FabricShield) {
				FabricShield shield = (FabricShield) stack.getItem();
				displayTooltip = shield.displayTooltip();
			}

			// Display tooltip for shields
			BlocksAttacksComponent shield = stack.get(DataComponentTypes.BLOCKS_ATTACKS);
			if (shield != null && displayTooltip) {
				getCooldownTooltip(stack, type, tooltip, (int) (100.0F * shield.disableCooldownScale()));
			}
		});

		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			// Warn about dev code
			FabricShieldLib.logger.warn(
				"FABRIC SHIELD LIB DEVELOPMENT CODE RAN!!!, if you are not in a development environment this is very bad! Client side banner code ran!");

			FabricShieldLibClientTests.runTests();
		}
	}

	/**
	 * Shield tooltip thing.
	 */
	public static List<Text> getCooldownTooltip(ItemStack stack, TooltipType type, List<Text> tooltip, int cooldownTicks) {
		List<Text> advanced = new ArrayList<Text>();

		/*
		 * These all loop in reverse to grab the first instance of a match at the end of
		 * the tooltip
		 */
		if (type.isAdvanced()) {
			// Grab durability
			if (stack.isDamaged()) {
				for (int i = tooltip.size() - 1; i > 0; i--) {

					Text text = tooltip.get(i);
					String strText = text.getString();

					if (strText.startsWith("Durability")) {
						advanced.add(text);
						tooltip.remove(i);
						break;
					}
				}
			}

			// Grab item id
			for (int i = tooltip.size() - 1; i > 0; i--) {

				Text text = tooltip.get(i);
				String strText = text.getString().trim();

				if (Identifier.isNamespaceValid(strText)) { // not sure if isnamespacevalid or ispathvalid
					advanced.add(text);
					tooltip.remove(i);
					break;
				}
			}

			// Grab nbt string
			if (!stack.getComponents().isEmpty()) {
				for (int i = tooltip.size() - 1; i > 0; i--) {

					Text text = tooltip.get(i);
					String strText = text.getString();

					if (strText.startsWith("NBT: ")) {
						advanced.add(text);
						tooltip.remove(i);
						break;
					}
				}
			}
		}

		// Add disabled cooldown tooltip
		tooltip.add(Text.literal(""));
		tooltip.add(Text.translatable("fabricshieldlib.shield_tooltip.start")
			.append(Text.literal(":"))
			.formatted(Formatting.GRAY)
		);

		/*
		 * All of this is so if there is a .0 instead of there being a need for a
		 * decimal remove the .0
		 */
		String cooldown = String.valueOf((Double) (cooldownTicks / 20.0));
		char[] splitCooldown = cooldown.toCharArray();
		if (splitCooldown.length >= 3) {
			if (splitCooldown[2] == '0') {
				if (!(splitCooldown.length >= 4)) {
					cooldown = String.valueOf(splitCooldown[0]);
				}
			}
		}

		tooltip.add(Text.literal(" " + cooldown)
			.formatted(Formatting.DARK_GREEN)
			.append(Text.translatable("fabricshieldlib.shield_tooltip.unit"))
			.append(" ")
			.append(Text.translatable("fabricshieldlib.shield_tooltip.end"))
		);

		// Append advanced info
		if (type.isAdvanced()) {
			tooltip.addAll(advanced);
		}

		return tooltip;
	}
}