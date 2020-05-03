package me.crimsondawn45.fabricshieldlib.object;

import me.crimsondawn45.fabricshieldlib.FabricShieldLib;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;
import net.minecraft.util.Hand;
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
	 * @param durability - How much damage the shield can handle before it breaks.
	 * @param repairItem - Item that can be used to repair the shield.
	 */
	public FabricShield(Settings settings, int cooldownTicks, int durability, Item repairItem)
	{
		super(settings.maxDamage(durability));
		
		DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
		
		this.cooldownTicks = cooldownTicks;
		this.repairItem = repairItem;
		this.usesItemTag = false;
		
		FabricShieldLib.shields.add(this);
		FabricShieldLib.logger.info("Registered Instance of Shield: " + this.getClass().getSimpleName() + ".");
	}
	
	/**
	 * Fabric Shield Item
	 * 
	 * @param settings - Item settings
	 * @param cooldownTicks - How many ticks the shield will be disabled for when hit by an axe.
	 * @param durability - How much damage the shield can handle before it breaks.
	 * @param repairItemTag - Item that can be used to repair the shield.
	 */
	public FabricShield(Settings settings, int cooldownTicks, int durability, Tag.Identified<Item> repairItemTag)
	{
		super(settings.maxDamage(durability));
		
		DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
		
		this.cooldownTicks = cooldownTicks;
		this.repairItemTag = repairItemTag;
		this.usesItemTag = true;
		
		FabricShieldLib.shields.add(this);
		FabricShieldLib.logger.info("Registered Instance of Shield: " + this.getClass().getSimpleName() + ".");
	}
	
	/**
	 * onBlockMelee
	 * 
	 * Method called whenever the shield successfully blocks a melee attack.
	 * 
	 * @param player - The player.
	 * @param attacker - Entity attacking the player.
	 * @param shield - The shield.
	 * @param shieldHand - Hand holding the shield.
	 */
	public void onBlockMelee(World world, PlayerEntity player, LivingEntity attacker, ItemStack shield, Hand shieldHand){}
	
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
