package me.crimsondawn45.fabricshieldlib.object;

import java.util.List;

import me.crimsondawn45.fabricshieldlib.util.FabricShieldLibRenderHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BannerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;

public class FabricBannerShield extends FabricShield
{
	private String modId;
	private String itemName;
	
	public FabricBannerShield(String modId, String itemName, Settings settings, int cooldownTicks, int durability, Item repairItem)
	{	
		super(settings, cooldownTicks, durability, repairItem);
		
		this.modId = modId;
		this.itemName = itemName;
		
		if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
		{
			FabricShieldLibRenderHelper.registerRender(this);
		}
	}
	
	public FabricBannerShield(String modId, String itemName, Settings settings, int cooldownTicks, int durability, Tag.Identified<Item> repairItemTag)
	{
		super(settings, cooldownTicks, durability, repairItemTag);
		
		this.modId = modId;
		this.itemName = itemName;
		
		if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
		{
			FabricShieldLibRenderHelper.registerRender(this);
		}
	}
	
	public FabricBannerShield(String modId, String itemName, Settings settings, int cooldownTicks, int durability, Item...repairItems)
	{
		super(settings, cooldownTicks, durability, repairItems);
		
		this.modId = modId;
		this.itemName = itemName;
		
		if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
		{
			FabricShieldLibRenderHelper.registerRender(this);
		}
	}
	
	@Override
	public String getTranslationKey(ItemStack stack)
	{
      return stack.getSubTag("BlockEntityTag") != null ? this.getTranslationKey() + '.' + getColor(stack).getName() : super.getTranslationKey(stack);
    }

	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context)
	{
		BannerItem.appendBannerTooltip(stack, tooltip);
	}
	
	public static DyeColor getColor(ItemStack stack)
	{
      return DyeColor.byId(stack.getOrCreateSubTag("BlockEntityTag").getInt("Base"));
    }
	
	public String getModId()
	{
		return this.modId;
	}
	
	public String getItemName()
	{
		return this.itemName;
	}
}
