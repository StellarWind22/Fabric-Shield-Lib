package me.crimsondawn45.fabricshieldlib.object;

import me.crimsondawn45.fabricshieldlib.util.FabricShieldLibRegistry;
import me.crimsondawn45.fabricshieldlib.util.ItemListType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;
import net.minecraft.util.Hand;

public class FabricShieldEnchantment extends Enchantment
{
	private Item acceptedItem;
	private Tag.Identified<Item> acceptedItemTag;
	private Item[] acceptedItemArray;
	private ItemListType itemListType;
	
	/**
	 * Fabric Shield Enchanement
	 * 
	 * @param weight - Rarity of enchantment.
	 * @param acceptedItem - Item that enchantments can be applied to.
	 */
	public FabricShieldEnchantment(Rarity weight, Item acceptedItem)
	{
		super(weight, EnchantmentTarget.BREAKABLE, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
		
		this.acceptedItem = acceptedItem;
		this.itemListType = ItemListType.ITEM;
		
		FabricShieldLibRegistry.registerShieldEnchantment(this);
	}
	
	/**
	 * Fabric Shield Enchanement
	 * 
	 * @param weight - Rarity of enchantment.
	 * @param acceptedItemTag - Items that enchantments can be applied to.
	 */
	public FabricShieldEnchantment(Rarity weight, Tag.Identified<Item> acceptedItemTag)
	{
		super(weight, EnchantmentTarget.BREAKABLE, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
		
		this.acceptedItemTag = acceptedItemTag;
		this.itemListType = ItemListType.TAG;
		
		FabricShieldLibRegistry.registerShieldEnchantment(this);
	}
	
	/**
	 * Fabric Shield Enchanement
	 * 
	 * @param weight - Rarity of enchantment.
	 * @param acceptedItems - Items that enchantments can be applied to.
	 */
	public FabricShieldEnchantment(Rarity weight, Item...acceptedItemArray)
	{
		super(weight, EnchantmentTarget.BREAKABLE, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
		
		this.acceptedItemArray = acceptedItemArray;
		this.itemListType = ItemListType.ARRAY;
		
		FabricShieldLibRegistry.registerShieldEnchantment(this);
	}
	
	/**
	 * Fabric Shield Enchantment
	 * 
	 * @param weight Rarity of the enchantment.
	 */
	public FabricShieldEnchantment(Rarity weight)
	{
		super(weight, EnchantmentTarget.BREAKABLE, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
		
		this.itemListType = ItemListType.REGISTRY;
		
		FabricShieldLibRegistry.registerShieldEnchantment(this);
	}
	
	/**
	 * onBlockDamage
	 * 
	 * Fired whenever a shield with this enchantment successfully blocks an attack.
	 * 
	 * @param defender - Entity using the enchanted shield.
	 * @param source - Source of the damage.
	 * @param amount - Amount of damage that was blocked.
	 * @param enchantmentLevel - Level of the enchantment.
	 */
	public void onBlockDamage(LivingEntity defender, DamageSource source, float amount, int enchantmentLevel, Hand hand, ItemStack shield){}
	
	/**
	 * whileBlockingTick
	 * 
	 * Fired every tick that a shield with this enchantment is blocking.
	 * 
	 * @param defender - Entity using the enchanted shield.
	 */
	public void whileBlocking(LivingEntity defender, int enchantmentLevel, Hand hand, ItemStack shield){}
	
	/**
	 * whileHoldingShieldTick
	 * 
	 * Fired every tick this shield is held
	 * 
	 * @param defender - Entity that is using this shield.
	 * @param isBlocking - If the shield is currently blocking.
	 * @param enchantmentLevel - Level of the enchantment.
	 */
	public void whileHolding(LivingEntity defender, boolean isBlocking, int enchantmentLevel, Hand hand, ItemStack shield){}
	
	@Override
	public boolean isAcceptableItem(ItemStack item)
	{
		switch(this.itemListType)
		{
			case ARRAY:
				for(Item entry : this.acceptedItemArray)
				{
					if(entry == item.getItem())
					{
						return true;
					}
				}
				return false;

			case ITEM:	return this.acceptedItem == item.getItem();
			case TAG:	return this.acceptedItemTag.contains(item.getItem());
			
			case REGISTRY:
				for(Item entry : FabricShieldLibRegistry.getAllShields())
				{
					if(entry == item.getItem())
					{
						return true;
					}
				}
				return false;
			
			default:	return false;
		}
	}
	
	/**
	 * hasEnchantment
	 * 
	 * @param item - Item to look for this enchantment on.
	 * 
	 * @return Whether or not the item has this enchantment.
	 */
	public boolean hasEnchantment(ItemStack item)
	{
		return EnchantmentHelper.getLevel(this, item) > 0;
	}
}
