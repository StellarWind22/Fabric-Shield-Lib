package me.crimsondawn45.fabricshieldlib.lib;

import me.crimsondawn45.fabricshieldlib.lib.event.ShieldEvent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;

public class ShieldEnchantment extends Enchantment
{
    private Item acceptedItem;
    private Tag.Identified<Item> acceptedItemTag;
    private Item[] acceptedItemArray;
    private ItemListType itemListType;

    /**
     * Fabric Shield Enchantment
     * 
     * @param weight       - Rarity of enchantment.
     * @param acceptedItem - Item that enchantment can be applied to.
     */
    public ShieldEnchantment(Rarity weight, ShieldEvent event, Item acceptedItem)
    {
        super(weight, EnchantmentTarget.BREAKABLE, new EquipmentSlot[] { EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND });

        this.acceptedItem = acceptedItem;
        this.itemListType = ItemListType.ITEM;

        //ShieldRegistry.registerEnchantmentEvent(this, event);
    }

    /**
     * Fabric Shield Enchantment
     * 
     * @param weight          - Rarity of enchantment.
     * @param acceptedItemTag - Items that enchantment can be applied to.
     */
    public ShieldEnchantment(Rarity weight, ShieldEvent event, Tag.Identified<Item> acceptedItemTag)
    {
        super(weight, EnchantmentTarget.BREAKABLE, new EquipmentSlot[] { EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND });

        this.acceptedItemTag = acceptedItemTag;
        this.itemListType = ItemListType.TAG;

        ShieldRegistry.registerEnchantmentEvent(this, event);
    }

    /**
     * Fabric Shield Enchantment
     * 
     * @param weight        - Rarity of enchantment.
     * @param acceptedItems - Items that enchantment can be applied to.
     */
    public ShieldEnchantment(Rarity weight, ShieldEvent event, Item... acceptedItemArray)
    {
        super(weight, EnchantmentTarget.BREAKABLE, new EquipmentSlot[] { EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND });

        this.acceptedItemArray = acceptedItemArray;
        this.itemListType = ItemListType.ARRAY;

        ShieldRegistry.registerEnchantmentEvent(this, event);
    }

    /**
     * Fabric Shield Enchantment
     * 
     * @param weight Rarity of the enchantment.
     */
    public ShieldEnchantment(Rarity weight, ShieldEvent event)
	{
		super(weight, EnchantmentTarget.BREAKABLE, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
		
        this.itemListType = ItemListType.REGISTRY;
        
        ShieldRegistry.registerEnchantmentEvent(this, event);
	}
	
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
				for(Item entry : ShieldRegistry.getAllShields())
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