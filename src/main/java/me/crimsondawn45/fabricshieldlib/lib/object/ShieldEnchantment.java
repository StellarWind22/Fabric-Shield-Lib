package me.crimsondawn45.fabricshieldlib.lib.object;

import me.crimsondawn45.fabricshieldlib.lib.ItemListType;
import me.crimsondawn45.fabricshieldlib.lib.ShieldRegistry;
import me.crimsondawn45.fabricshieldlib.lib.event.ShieldEvent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.Tag;

public class ShieldEnchantment extends Enchantment {
    private Item acceptedItem;
    private Tag.Identified<Item> acceptedItemTag;
    private Item[] acceptedItemArray;
    private ItemListType itemListType;
    private ShieldEvent event;

    /**
     * Fabric Shield Enchantment
     * 
     * @param weight - Rarity of enchantment.
     * @param event - ShieldEvent to be fired for this enchantment
     * @param acceptedItem - Item that enchantment can be applied to.
     */
    public ShieldEnchantment(Rarity weight, ShieldEvent event, Item acceptedItem) {
        super(weight, EnchantmentTarget.BREAKABLE, new EquipmentSlot[] { EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND });

        this.acceptedItem = acceptedItem;
        this.itemListType = ItemListType.ITEM;

        this.event = event;
        ShieldRegistry.register(this);
    }

    /**
     * Fabric Shield Enchantment
     * 
     * @param weight - Rarity of enchantment.
     * @param event - ShieldEvent to be fired for this enchantment
     * @param acceptedItemTag - Items that enchantment can be applied to.
     */
    public ShieldEnchantment(Rarity weight, ShieldEvent event, Tag.Identified<Item> acceptedItemTag) {
        super(weight, EnchantmentTarget.BREAKABLE, new EquipmentSlot[] { EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND });

        this.acceptedItemTag = acceptedItemTag;
        this.itemListType = ItemListType.TAG;

        this.event = event;
        ShieldRegistry.register(this);
    }

    /**
     * Fabric Shield Enchantment
     * 
     * @param weight - Rarity of enchantment.
     * @param event - ShieldEvent to be fired for this enchantment
     * @param acceptedItems - Items that enchantment can be applied to.
     */
    public ShieldEnchantment(Rarity weight, ShieldEvent event, Item... acceptedItemArray) {
        super(weight, EnchantmentTarget.BREAKABLE, new EquipmentSlot[] { EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND });

        this.acceptedItemArray = acceptedItemArray;
        this.itemListType = ItemListType.ARRAY;

        this.event = event;
        ShieldRegistry.register(this);
    }

    /**
     * Fabric Shield Enchantment
     * 
     * @param weight - Rarity of the enchantment.
     * @param event - ShieldEvent to be fired for this enchantment
     */
    public ShieldEnchantment(Rarity weight, ShieldEvent event) {
		super(weight, EnchantmentTarget.BREAKABLE, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
		
        this.itemListType = ItemListType.REGISTRY;
        
        this.event = event;
        ShieldRegistry.register(this);
	}
	
	@Override
	public boolean isAcceptableItem(ItemStack item) {
		switch(this.itemListType) {
			case ARRAY:
				for(Item entry : this.acceptedItemArray) {
					if(entry == item.getItem()) {
						return true;
					}
				}
				return false;

			case ITEM:	return this.acceptedItem == item.getItem();
			case TAG:	return this.acceptedItemTag.contains(item.getItem());
			case REGISTRY:
				for(Item entry : ShieldRegistry.getAllFabricShields()) {
					if(entry == item.getItem() || item.getItem() == Items.SHIELD) {
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
	 * @return Whether or not the item has this enchantment.
	 */
	public boolean hasEnchantment(ItemStack item) {
		return EnchantmentHelper.getLevel(this, item) > 0;
	}
	
	public boolean hasEvent() {
		return this.event != null;
	}
	
	public ShieldEvent getEvent() {
		return this.event;
	}
}