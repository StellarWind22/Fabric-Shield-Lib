package com.github.crimsondawn45.fabricshieldlib.lib.object;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.RepairableComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.consume.UseAction;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Pre-made class for quickly making custom shields.
 */
public class FabricShieldItem extends Item implements FabricShield {
    /**
     * @param settings       item settings.
     * @param coolDownTicks  ticks shield will be disabled for when it with axe. Vanilla: 100
     * @param enchantability enchantability of shield. Vanilla: 14
     * @param repairItems    item(s) for repairing shield.
     */
    @SuppressWarnings("deprecation")
	public FabricShieldItem(Settings settings, int coolDownTicks, int enchantability, Item... repairItems) {
        this(settings, coolDownTicks, enchantability, RegistryEntryList.of(Arrays.stream(repairItems).map(Item::getRegistryEntry).collect(Collectors.toList())));
    }

    /**
     * @param settings      item settings.
     * @param coolDownTicks ticks shield will be disabled for when it with axe. Vanilla: 100
     * @param material      tool material.
     */
    public FabricShieldItem(Settings settings, int coolDownTicks, ToolMaterial material) {
    	this(settings.maxDamage(material.durability()), coolDownTicks, material.enchantmentValue(), material.repairItems());
    }

    /**
     * @param settings       item settings.
     * @param coolDownTicks  ticks shield will be disabled for when it with axe. Vanilla: 100
     * @param enchantability enchantability of shield. Vanilla: 14
     * @param repairItemTag  item tag for repairing shield.
     */
    public FabricShieldItem(Settings settings, int coolDownTicks, int enchantability, TagKey<Item> repairItemTag) {
        this(settings, coolDownTicks, enchantability, Registries.createEntryLookup(Registries.ITEM).getOrThrow(repairItemTag));
    }

    /**
     * @param settings       item settings.
     * @param coolDownTicks  ticks shield will be disabled for when it with axe. Vanilla: 100
     * @param enchantability enchantability of shield. Vanilla: 14
     * @param repairItems    list of items/tags for repairing shield.
     */
    public FabricShieldItem(Settings settings, int coolDownTicks, int enchantability, @Nullable RegistryEntryList<Item> repairItems) {
        super(
             attachRepairable(FabricShieldUtils.defaultShieldSettings(settings), repairItems)
            .enchantable(enchantability)
            .component(DataComponentTypes.BLOCKS_ATTACKS, FabricShieldUtils.makeNewComponent(coolDownTicks))
        );
    }

    @SuppressWarnings("deprecation")
    public FabricShieldItem(Settings settings, int enchantability, Item... repairItems) {
        this(settings, enchantability, RegistryEntryList.of(Arrays.stream(repairItems).map(Item::getRegistryEntry).collect(Collectors.toList())));
    }

    /**
     * @param settings      item settings.
     * @param material      tool material.
     */
    public FabricShieldItem(Settings settings, ToolMaterial material) {
        this(settings.maxDamage(material.durability()), material.enchantmentValue(), material.repairItems());
    }

    /**
     * @param settings       item settings.
     * @param enchantability enchantability of shield. Vanilla: 14
     * @param repairItemTag  item tag for repairing shield.
     */
    public FabricShieldItem(Settings settings, int enchantability, TagKey<Item> repairItemTag) {
        this(settings, enchantability, Registries.createEntryLookup(Registries.ITEM).getOrThrow(repairItemTag));
    }

    /**
     * @param settings       item settings.
     * @param enchantability enchantability of shield. Vanilla: 14
     * @param repairItems    list of items/tags for repairing shield.
     */
    public FabricShieldItem(Settings settings, int enchantability, @Nullable RegistryEntryList<Item> repairItems) {
        super(
            attachRepairable(FabricShieldUtils.defaultShieldSettings(settings), repairItems)
            .enchantable(enchantability)
        );
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return ActionResult.CONSUME;
    }

    public static Item.Settings attachRepairable(Item.Settings settings, @Nullable RegistryEntryList<Item> repairItems) {
        return (repairItems == null ? settings : settings.component(DataComponentTypes.REPAIRABLE, new RepairableComponent(repairItems)));
    }
}