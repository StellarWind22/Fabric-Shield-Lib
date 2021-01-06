package me.crimsondawn45.fabricshieldlib.api.object;

import net.minecraft.item.Item;
import net.minecraft.tag.Tag.Identified;

public class BasicShield extends AbstractShield
{
    public BasicShield(Settings settings, int cooldownTicks, int durability, Item repairItem)
    {
        super(settings, cooldownTicks, durability, repairItem);
    }

    public BasicShield(Settings settings, int cooldownTicks, int durability, Identified<Item> repairItemTag)
    {
        super(settings, cooldownTicks, durability, repairItemTag);
    }

    public BasicShield(Settings settings, int cooldownTicks, int durability, Item...repairItems)
    {
        super(settings, cooldownTicks, durability, repairItems);
    }
}