package me.crimsondawn45.fabricshieldlib.object;

import me.crimsondawn45.fabricshieldlib.FabricShieldLib;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class FabricShield extends Item
{
	private int cooldownTicks;
	private Item repairItem;
	private Tag.Identified<Item> repairItemTag;
	private boolean usesItemTag;
	
	/**
	 * Fabric Shield Item
	 * 
	 * @param settings - Item settings.
	 * @param cooldownTicks - How many ticks the shield will be disabled for when hit by an axe.
	 * @param repairItem - Item that can be used to repair the shield.
	 */
	public FabricShield(Settings settings, int cooldownTicks, Item repairItem)
	{
		super(settings);
		
		this.addPropertyGetter(new Identifier("blocking"), (stack, world, entity) -> {return entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F;});
		DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
		
		this.cooldownTicks = cooldownTicks;
		this.repairItem = repairItem;
		this.usesItemTag = false;
		
		FabricShieldLib.shields.add(this);
	}
	
	/**
	 * Fabric Shield Item
	 * 
	 * @param settings - Item settings
	 * @param cooldownTicks - How many ticks the shield will be disabled for when hit by an axe.
	 * @param repairItemTag - Item that can be used to repair the shield.
	 */
	public FabricShield(Settings settings, int cooldownTicks, Tag.Identified<Item> repairItemTag)
	{
		super(settings);
		
		this.addPropertyGetter(new Identifier("blocking"), (stack, world, entity) -> {return entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F;});
		DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
		
		this.cooldownTicks = cooldownTicks;
		this.repairItemTag = repairItemTag;
		this.usesItemTag = true;
		
		FabricShieldLib.shields.add(this);
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack)
	{
		return UseAction.BLOCK;
    }

	@Override
	public int getMaxUseTime(ItemStack stack)
	{
		return 72000;
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
	{
      ItemStack itemStack = user.getStackInHand(hand);
      user.setCurrentHand(hand);
      return TypedActionResult.consume(itemStack);
	}
	
	@Override
	public boolean canRepair(ItemStack stack, ItemStack ingredient)
	{
		if(this.usesItemTag)
		{
			return this.repairItemTag.contains(ingredient.getItem()) || super.canRepair(stack, ingredient);
		}
		else
		{
			return this.repairItem == ingredient.getItem() || super.canRepair(stack, ingredient);
		}
    }
	
	/**
	 * getCoolDownTicks
	 * 
	 * @return How many ticks the shield goes into cooldown for after being disabled.
	 */
	public int getCooldownTicks()
	{
		return this.cooldownTicks;
	}
}
