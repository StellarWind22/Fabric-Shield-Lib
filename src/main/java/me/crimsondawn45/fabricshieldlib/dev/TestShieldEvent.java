package me.crimsondawn45.fabricshieldlib.dev;

import me.crimsondawn45.fabricshieldlib.FabricShieldLib;
import me.crimsondawn45.fabricshieldlib.lib.event.ShieldEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class TestShieldEvent extends ShieldEvent {

	public TestShieldEvent(boolean usesOnBlockDamage, boolean usesOnDisable, boolean usesWhileHolding) {
		super(usesOnBlockDamage, usesOnDisable, usesWhileHolding);
	}
	
	@Override
	public void onBlockDamage(LivingEntity defender, DamageSource source, float amount, int level, Hand hand, ItemStack shield) {
		if(defender instanceof PlayerEntity) {
			FabricShieldLib.debugMsg("onBlockDamage ran!!");
		}
	}
	
	@Override
	public void onDisable(PlayerEntity defender, int level, Hand hand, ItemStack shield) {
		FabricShieldLib.debugMsg("onDisable ran!");
	}
	
	@Override
	public void whileHolding(LivingEntity defender, int level, Hand hand, ItemStack shield) {
		if(defender instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) defender;
			
			//Require sneaking to reduce spam
			if(player.isSneaking()) {
				FabricShieldLib.debugMsg("whileHolding ran and you are sneaking!");
			}
		}
	}
}
