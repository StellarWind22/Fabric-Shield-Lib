package me.crimsondawn45.fabricshieldlib.object;

import net.minecraft.item.Item;
import net.minecraft.tag.Tag;

public class FabricBannerShield extends FabricShield
{
	public FabricBannerShield(Settings settings, int cooldownTicks, int durability, Item repairItem)
	{
		super(settings, cooldownTicks, durability, repairItem);
	}
	
	public FabricBannerShield(Settings settings, int cooldownTicks, int durability, Tag.Identified<Item> repairItemTag)
	{
		super(settings, cooldownTicks, durability, repairItemTag);
	}
	
	public FabricBannerShield(Settings settings, int cooldownTicks, int durability, Item...repairItems)
	{
		super(settings, cooldownTicks, durability, repairItems);
	}
}
