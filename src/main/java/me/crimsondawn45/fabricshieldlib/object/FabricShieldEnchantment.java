package me.crimsondawn45.fabricshieldlib.object;

import java.util.Arrays;
import java.util.List;

import me.crimsondawn45.fabricshieldlib.FabricShieldLib;
import me.crimsondawn45.fabricshieldlib.util.ItemListType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;

public class FabricShieldEnchantment extends Enchantment
{
	private Item acceptedItem;
	private Tag.Identified<Item> acceptedItemTag;
	private List<Item> acceptedItemList;
	private ItemListType itemListType;
	
	/**
	 * Fabric Shield Enchanement
	 * 
	 * @param weight - Rarity of enchantment.
	 * @param type - Type of enchantment.
	 * @param acceptedItem - Item that enchantments can be applied to.
	 */
	public FabricShieldEnchantment(Rarity weight, EnchantmentTarget type, Item acceptedItem)
	{
		super(weight, type, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
		
		this.acceptedItem = acceptedItem;
		this.itemListType = ItemListType.ITEM;
		
		FabricShieldLib.enchantments.add(this);
	}
	
	/**
	 * Fabric Shield Enchanement
	 * 
	 * @param weight - Rarity of enchantment.
	 * @param type - Type of enchantment.
	 * @param acceptedItemTag - Items that enchantments can be applied to.
	 */
	public FabricShieldEnchantment(Rarity weight, EnchantmentTarget type, Tag.Identified<Item> acceptedItemTag)
	{
		super(weight, type, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
		
		this.acceptedItemTag = acceptedItemTag;
		this.itemListType = ItemListType.TAG;
		
		FabricShieldLib.enchantments.add(this);
	}
	
	/**
	 * Fabric Shield Enchanement
	 * 
	 * @param weight - Rarity of enchantment.
	 * @param type - Type of enchantment.
	 * @param acceptedItems - Items that enchantments can be applied to.
	 */
	public FabricShieldEnchantment(Rarity weight, EnchantmentTarget type, Item...acceptedItems)
	{
		super(weight, type, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
		
		this.acceptedItemList = Arrays.asList(acceptedItems);
		this.itemListType = ItemListType.LIST;
		
		FabricShieldLib.enchantments.add(this);
	}
	
	/**
	 * onBlockDamage
	 * 
	 * Fired whenever a shield with this enchantment successfully blocks an attack.
	 * 
	 * @param defender - Entity using the enchanted shield.
	 * @param source - Source of the damage.
	 * @param amount - Amount of damage that was blocked.
	 */
	public void onBlockDamage(LivingEntity defender, DamageSource source, float amount){}
	
	/**
	 * onBlockingTick
	 * 
	 * Fired every tick that a shield with this enchantment is blocking.
	 * 
	 * @param defender - Entity using the enchanted shield.
	 */
	public void onBlockingTick(LivingEntity defender){}
	
	@Override
	public boolean isAcceptableItem(ItemStack item)
	{
		switch(this.itemListType)
		{
			case LIST:	return this.acceptedItemList.contains(item.getItem());
			case ITEM:	return this.acceptedItem == item.getItem();
			case TAG:	return this.acceptedItemTag.contains(item.getItem());
			
			default:	return false;
		}
	}
}
