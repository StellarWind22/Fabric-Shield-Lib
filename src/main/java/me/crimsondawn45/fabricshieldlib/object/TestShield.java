package me.crimsondawn45.fabricshieldlib.object;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;

public class TestShield extends FabricShield
{
	public TestShield(Settings settings, int cooldownTicks, int durability, Tag<Item> repairItemTag)
	{
		super(settings, cooldownTicks, durability, repairItemTag);
	}
	
	@Override
	public void onBlockDamage(LivingEntity defender, DamageSource source, float amount, Hand hand, ItemStack shield)
	{
		if(defender instanceof PlayerEntity)
		{
			((PlayerEntity)defender).sendMessage(new LiteralText("onBlockDamage ran!"));
		}
	}
	
	@Override
	public void whileBlocking(LivingEntity defender, Hand hand, ItemStack shield)
	{
		if(defender instanceof PlayerEntity)
		{
			((PlayerEntity)defender).sendMessage(new LiteralText("whileBlocking ran!"));
		}
	}
	
	@Override
	public void whileHolding(LivingEntity defender, boolean isBlocking, Hand hand, ItemStack shield)
	{
		if(defender instanceof PlayerEntity)
		{
			((PlayerEntity)defender).sendMessage(new LiteralText("whileHolding ran!"));
		}
	}
}
