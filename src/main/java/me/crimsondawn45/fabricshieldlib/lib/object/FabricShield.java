package me.crimsondawn45.fabricshieldlib.lib.object;

import me.crimsondawn45.fabricshieldlib.lib.ItemListType;
import me.crimsondawn45.fabricshieldlib.lib.ShieldRegistry;
import me.crimsondawn45.fabricshieldlib.lib.event.ShieldEvent;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
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
	private Item[] repairItemArray;
	private ItemListType itemListType;
	private ShieldEvent event;
	
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
		FabricModelPredicateProviderRegistry.register(this, new Identifier("blocking"), null);		
		this.cooldownTicks = cooldownTicks;
		this.repairItem = repairItem;
		this.itemListType = ItemListType.ITEM;
		
		ShieldRegistry.register(this);
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
		FabricModelPredicateProviderRegistry.register(this, new Identifier("blocking"),(itemStack, clientWorld, livingEntity) -> {
			return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
		});
		this.cooldownTicks = cooldownTicks;
		this.repairItemTag = repairItemTag;
		this.itemListType = ItemListType.TAG;
		
		ShieldRegistry.register(this);
	}
	
	/**
	 * Fabric Shield Item
	 * 
	 * @param settings - Item settings
	 * @param cooldownTicks - How many ticks the shield will be disabled for when hit by an axe.
	 * @param durability - How much damage the shield can handle before it breaks.
	 * @param repairItemTag - Item that can be used to repair the shield.
	 */
	public FabricShield(Settings settings, int cooldownTicks, int durability, Item...repairItems)
	{
		super(settings.maxDamage(durability));
		
		DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
		FabricModelPredicateProviderRegistry.register(this, new Identifier("blocking"),(itemStack, clientWorld, livingEntity) -> {
			return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
		});		
		this.cooldownTicks = cooldownTicks;
		this.repairItemArray = repairItems;
		this.itemListType = ItemListType.ARRAY;
		
		ShieldRegistry.register(this);
	}
	
	/**
	 * Fabric Shield Item
	 * 
	 * @param settings - Item settings.
	 * @param cooldownTicks - How many ticks the shield will be disabled for when hit by an axe.
	 * @param durability - How much damage the shield can handle before it breaks.
	 * @param event - Shield event to be registered to the shield item.
	 * @param repairItem - Item that can be used to repair the shield.
	 */
	public FabricShield(Settings settings, int cooldownTicks, int durability, ShieldEvent event, Item repairItem)
	{
		super(settings.maxDamage(durability));
		
		DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
		FabricModelPredicateProviderRegistry.register(this, new Identifier("blocking"),(itemStack, clientWorld, livingEntity) -> {
			return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
		});		
		this.cooldownTicks = cooldownTicks;
		this.repairItem = repairItem;
		this.itemListType = ItemListType.ITEM;
		this.event = event;
		
		ShieldRegistry.register(this);
	}
	
	/**
	 * Fabric Shield Item
	 * 
	 * @param settings - Item settings
	 * @param cooldownTicks - How many ticks the shield will be disabled for when hit by an axe.
	 * @param durability - How much damage the shield can handle before it breaks.
	 * @param event - Shield event to be registered to the shield item.
	 * @param repairItemTag - Item that can be used to repair the shield.
	 */
	public FabricShield(Settings settings, int cooldownTicks, int durability, ShieldEvent event, Tag.Identified<Item> repairItemTag)
	{
		super(settings.maxDamage(durability));
		
		DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
		FabricModelPredicateProviderRegistry.register(this, new Identifier("blocking"),(itemStack, clientWorld, livingEntity) -> {
			return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
		});
		this.cooldownTicks = cooldownTicks;
		this.repairItemTag = repairItemTag;
		this.itemListType = ItemListType.TAG;
		this.event = event;
		
		ShieldRegistry.register(this);
	}
	
	/**
	 * Fabric Shield Item
	 * 
	 * @param settings - Item settings
	 * @param cooldownTicks - How many ticks the shield will be disabled for when hit by an axe.
	 * @param durability - How much damage the shield can handle before it breaks.
	 * @param event - Shield event to be registered to the shield item.
	 * @param repairItemTag - Item that can be used to repair the shield.
	 */
	public FabricShield(Settings settings, int cooldownTicks, int durability, ShieldEvent event, Item...repairItems)
	{
		super(settings.maxDamage(durability));
		
		DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
		FabricModelPredicateProviderRegistry.register(this, new Identifier("blocking"),(itemStack, clientWorld, livingEntity) -> {
			return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
		});		
		this.cooldownTicks = cooldownTicks;
		this.repairItemArray = repairItems;
		this.itemListType = ItemListType.ARRAY;
		this.event = event;
		
		ShieldRegistry.register(this);
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

	/**
	 * setCooldownTicks
	 * 
	 * @param cooldownTicks How many tick the shield goes into cooldown for after being disabled.
	 */
	public void setCooldownTicks(int cooldownTicks)
	{
		this.cooldownTicks = cooldownTicks;
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
		switch(this.itemListType)
		{
			case ITEM:	return this.repairItem == ingredient.getItem();
			case ARRAY:
				
				for(Item entry : this.repairItemArray)
				{
					if(entry == ingredient.getItem())
					{
						return true;
					}
				}
				return false;
				
			case TAG:	return this.repairItemTag.contains(ingredient.getItem());
			
			default:	return false;
		}
    }
	
	@Override
	public boolean isEnchantable(ItemStack item)
	{
		return !item.hasEnchantments();
	}
	
	@Override
	public int getEnchantability()
	{
		return 9;
	}
	
	public boolean hasEvent() {
		return this.event != null;
	}
	
	public ShieldEvent getEvent() {
		return this.event;
	}
}
