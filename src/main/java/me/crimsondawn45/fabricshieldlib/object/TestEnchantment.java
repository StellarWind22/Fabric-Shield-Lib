package me.crimsondawn45.fabricshieldlib.object;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;

public class TestEnchantment extends FabricShieldEnchantment
{
	public TestEnchantment(Weight weight)
	{
		super(weight);
	}

	@Override
	public void onBlockDamage(LivingEntity defender, DamageSource source, Hand hand, ItemStack shield, float amount, int level)
	{
		if(defender instanceof PlayerEntity)
		{
			((PlayerEntity)defender).sendMessage(new LiteralText("onBlockDamage ran!"));
		}
	}
	
	@Override
	public void whileBlocking(LivingEntity defender, Hand hand, ItemStack shield, int level)
	{
		if(defender instanceof PlayerEntity)
		{
			((PlayerEntity)defender).sendMessage(new LiteralText("whileBlocking ran!"));
		}
	}
	
	@Override
	public void whileHolding(LivingEntity defender, boolean isBlocking, Hand hand, ItemStack shield, int level)
	{
		if(defender instanceof PlayerEntity)
		{
			((PlayerEntity)defender).sendMessage(new LiteralText("whileHolding ran!"));
		}
	}
}
