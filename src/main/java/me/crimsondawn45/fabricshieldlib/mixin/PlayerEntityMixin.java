package me.crimsondawn45.fabricshieldlib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import me.crimsondawn45.fabricshieldlib.FabricShieldLib;
import me.crimsondawn45.fabricshieldlib.object.FabricShield;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin
{
	@Inject(at = @At(value = "RETURN"), method = "damageShield()V", locals=LocalCapture.CAPTURE_FAILHARD)
	private void damageShield(float amount, CallbackInfo callBackInfo)
	{
		PlayerEntity player = (PlayerEntity) (Object) this;
		ItemStack activeItem = player.getActiveItem();
		
		if(amount >= 3.0F && activeItem.getItem() instanceof FabricShield)
		{
			int i = 1 + MathHelper.floor(amount);
			Hand activeHand = player.getActiveHand();
			
			activeItem.damage(i, player, ((playerEntity) -> {playerEntity.sendToolBreakStatus(activeHand);}));
			
			if(activeItem.isEmpty())
			{
				if(activeHand == Hand.MAIN_HAND)
				{
					player.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
				}
				else
				{
					player.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
				}
				
				((LivingEntityAccessor)player).setActiveItemStack(ItemStack.EMPTY);
				player.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + player.world.random.nextFloat() * 0.4F);
			}
		}
	}
	
	@Inject(at = @At(value = "HEAD"), method = "disableShield()V", locals = LocalCapture.CAPTURE_FAILHARD)
	private void disableShield(boolean sprinting, CallbackInfo callBackInfo)
	{	
		PlayerEntity player = (PlayerEntity) (Object) this;
		Item shield = player.getActiveItem().getItem();
		
		if(shield instanceof FabricShield)
		{
			float f = 0.25F + (float)EnchantmentHelper.getEfficiency(player) * 0.05F;
		
			if (sprinting)
			{
				f += 0.75F;
			}
			
			if (player.getRandom().nextFloat() < f)
			{
				/*
				 * Disables all shields for the cooldown time of the one that was disabled
				 */
				player.getItemCooldownManager().set(shield, ((FabricShield) shield).getCooldownTicks());
				
				for(FabricShield entry : FabricShieldLib.shields)
				{	
					if(Registry.ITEM.getId(entry) == Registry.ITEM.getDefaultId())
					{
						continue;
					}
					else
					{
						player.getItemCooldownManager().set(entry, ((FabricShield) shield).getCooldownTicks());
					}
				}
				
				player.clearActiveItem();
				player.world.sendEntityStatus(player, (byte)30);
			}
		}
	}
}
