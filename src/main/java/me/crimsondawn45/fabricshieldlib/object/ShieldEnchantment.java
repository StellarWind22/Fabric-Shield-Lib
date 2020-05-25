package me.crimsondawn45.fabricshieldlib.object;

import me.crimsondawn45.fabricshieldlib.util.ItemListType;
import me.crimsondawn45.fabricshieldlib.util.ShieldRegistry;
import me.crimsondawn45.fabricshieldlib.util.event.ShieldEvent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;

public final class ShieldEnchantment extends Enchantment
{
    private ItemListType listType;

    private Item acceptedItem;
    private Tag.Identified<Item> acceptedItemTag;
    private Item[] acceptedItemArray;

    public ShieldEnchantment(Rarity weight, ShieldEvent event, Item acceptedItem)
    {
        super(weight, EnchantmentTarget.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});

        this.listType = ItemListType.ITEM;
        this.acceptedItem = acceptedItem;

        ShieldRegistry.registerEnchantmentEvent(this, event);
    }

    public ShieldEnchantment(Rarity weight, ShieldEvent event, Tag.Identified<Item> acceptedItemTag)
    {
        super(weight, EnchantmentTarget.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});

        this.listType = ItemListType.TAG;
        this.acceptedItemTag = acceptedItemTag;

        ShieldRegistry.registerEnchantmentEvent(this, event);
    }

    public ShieldEnchantment(Rarity weight, ShieldEvent event, Item...acceptedItemArray)
    {
        super(weight, EnchantmentTarget.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});

        this.listType = ItemListType.ARRAY;
        this.acceptedItemArray = acceptedItemArray;

        ShieldRegistry.registerEnchantmentEvent(this, event);
    }

    public ShieldEnchantment(Rarity weight, ShieldEvent event)
    {
        super(weight, EnchantmentTarget.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});

        this.listType = ItemListType.REGISTRY;

        ShieldRegistry.registerEnchantmentEvent(this, event);
    }
}