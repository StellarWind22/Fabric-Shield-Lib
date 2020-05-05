package me.crimsondawn45.fabricshieldlib.test;

import java.util.List;

import me.crimsondawn45.fabricshieldlib.object.FabricShieldEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class ReflectionEnchantment extends FabricShieldEnchantment
{

	public ReflectionEnchantment(Rarity weight, EnchantmentTarget type, List<Item> acceptedItems)
	{
		super(weight, type, acceptedItems);
	}
	
	@Override
	public void onBlockDamage(LivingEntity defender, DamageSource source, Hand hand, ItemStack shield, float amount, int enchantmentLevel)
	{
		Entity attacker = source.getAttacker();
		float fLevel = enchantmentLevel;
		
		if(defender instanceof PlayerEntity)
		{
			attacker.damage(DamageSource.player((PlayerEntity) defender), amount * (fLevel * 0.25F));
		}
		else
		{
			attacker.damage(DamageSource.mob(defender), amount * (fLevel * 0.25F));
		}
	}
	
	@Override
	public int getMaximumLevel()
	{
		return 3;
	}
	
	@Override
	public int getMinimumPower(int power)
	{
		return 1;
	}
}
