package com.github.crimsondawn45.fabricshieldlib.lib.object;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

/**
 * Pre-made class for quickly making custom shields
 */
public class FabricShieldItem extends Item implements FabricShield {

    private int cooldownTicks;
    private ItemStack[] repairItems;
    private int enchantability;

    /**
     * @param settings item settings.
     * @param cooldownTicks ticks shield will be disabled for when it with axe. Vanilla: 100
     * @param enchantability enchantability of shield. Vanilla: 14
     * @param repairItem item for repairing shield.
     */
    public FabricShieldItem(Settings settings, int cooldownTicks, int enchantability, Item repairItem) {
        super(settings);

        //Register dispenser equip behavior
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);

        //Register that item has a blocking model
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            this.addPropertyGetter(new Identifier("blocking"), (itemStack, clientWorld, livingEntity) -> {
		         return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
		    });
		}

        this.cooldownTicks = cooldownTicks;

        ItemStack[] repairItems = {new ItemStack(repairItem)};

        this.repairItems = repairItems;
		this.enchantability = enchantability;
    }

    /**
     * @param settings item settings.
     * @param cooldownTicks ticks shield will be disabled for when it with axe. Vanilla: 100
     * @param material tool material.
     */
    public FabricShieldItem(Settings settings, int cooldownTicks, ToolMaterial material) {
        super(settings.maxDamage(material.getDurability())); //Make durability match material

        //Register dispenser equip behavior
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);

        //Register that item has a blocking model
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            this.addPropertyGetter(new Identifier("blocking"), (itemStack, clientWorld, livingEntity) -> {
		         return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
		    });
		}

        this.cooldownTicks = cooldownTicks;
        this.repairItems = material.getRepairIngredient().getMatchingStacksClient();
        this.enchantability = material.getEnchantability();
    }

    @Override
    public int getCooldownTicks() {
        return this.cooldownTicks;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
	public int getMaxUseTime(ItemStack stack) {
		return 72000;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
	    user.setCurrentHand(hand);
	    return new TypedActionResult<ItemStack>(ActionResult.SUCCESS, itemStack);
	}

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        for (ItemStack itemStack : repairItems) {
            if(itemStack.getItem() == ingredient.getItem()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return !stack.hasEnchantments();
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public boolean supportsBanner() {
        return false;
    }
}