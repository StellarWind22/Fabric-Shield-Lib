package me.crimsondawn45.fabricshieldlib.object;

import me.crimsondawn45.fabricshieldlib.util.ItemListType;
import me.crimsondawn45.fabricshieldlib.util.ShieldRegistry;
import me.crimsondawn45.fabricshieldlib.util.event.ShieldEvent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;

public final class ShieldEnchantment extends Enchantment
{
    private ItemListType listType;

    private Item acceptedItem;
    private Tag.Identified<Item> acceptedItemTag;
    private Item[] acceptedItemArray;

    /**
     * ShieldEnchantment
     * 
     * Class for easily making shield enchantments.
     * 
     * @param rarity - Rarity of the enchantment.
     * @param event - ShieldEvent for the enchantment.
     * @param acceptedItem - Accepted item.
     */
    public ShieldEnchantment(Rarity rarity, ShieldEvent event, Item acceptedItem)
    {
        super(rarity, EnchantmentTarget.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});

        this.listType = ItemListType.ITEM;
        this.acceptedItem = acceptedItem;

        ShieldRegistry.registerEnchantmentEvent(this, event);
    }

    /**
     * ShieldEnchantment
     * 
     * Class for easily making shield enchantments.
     * 
     * @param rarity - Rarity of the enchantment.
     * @param event - ShieldEvent for the enchantment.
     * @param acceptedItemTag - Accepted item tag.
     */
    public ShieldEnchantment(Rarity rarity, ShieldEvent event, Tag.Identified<Item> acceptedItemTag)
    {
        super(rarity, EnchantmentTarget.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});

        this.listType = ItemListType.TAG;
        this.acceptedItemTag = acceptedItemTag;

        ShieldRegistry.registerEnchantmentEvent(this, event);
    }

    public ShieldEnchantment(Rarity rarity, ShieldEvent event, Item...acceptedItemArray)
    {
        super(rarity, EnchantmentTarget.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});

        this.listType = ItemListType.ARRAY;
        this.acceptedItemArray = acceptedItemArray;

        ShieldRegistry.registerEnchantmentEvent(this, event);
    }

    public ShieldEnchantment(Rarity rarity, ShieldEvent event)
    {
        super(rarity, EnchantmentTarget.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});

        this.listType = ItemListType.REGISTRY;

        ShieldRegistry.registerEnchantmentEvent(this, event);
    }

    @Override
	public boolean isAcceptableItem(ItemStack item)
	{
		switch(this.listType)
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
}