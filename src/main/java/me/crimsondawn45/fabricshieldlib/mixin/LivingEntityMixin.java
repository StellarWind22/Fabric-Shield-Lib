package me.crimsondawn45.fabricshieldlib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import me.crimsondawn45.fabricshieldlib.FabricShieldLib;
import me.crimsondawn45.fabricshieldlib.object.FabricShield;
import me.crimsondawn45.fabricshieldlib.object.FabricShieldEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;

@Mixin(LivingEntity.class)
public class LivingEntityMixin
{	
	@Inject(at = @At(value = "HEAD"), method = "damage()V", locals = LocalCapture.CAPTURE_FAILHARD)
	private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callbackInfo)
	{
		LivingEntity entity = (LivingEntity)(Object)this;
		
		if(!(entity.isInvulnerableTo(source) || entity.world.isClient || entity.getHealth() <= 0.0F || (source.isFire() && entity.hasStatusEffect(StatusEffects.FIRE_RESISTANCE))))
		{
			ItemStack activeItem = entity.getActiveItem();
			
			if (amount > 0.0F && ((LivingEntityAccessor)entity).invokeBlockedByShield(source))
			{
				if(activeItem.getItem() instanceof FabricShield)
				{
					((FabricShield)activeItem.getItem()).onBlockDamage(entity, source, amount);
				}
				if(activeItem.hasEnchantments())
				{
					for(FabricShieldEnchantment enchantment : FabricShieldLib.enchantments)
					{
						if(EnchantmentHelper.getLevel(enchantment, activeItem) > 0)
						{
							enchantment.onBlockDamage(entity, source, amount);
						}
					}
				}
			}
		}
	}
	
	@Inject(at = @At(value = "TAIL"), method = "tick()V", locals = LocalCapture.CAPTURE_FAILHARD)
	private void tick(CallbackInfo callbackInfo)
	{
		LivingEntity entity = (LivingEntity)(Object)this;
		ItemStack activeItem = entity.getActiveItem();
		
		if(entity.isBlocking())
		{
			if(activeItem.getItem() instanceof FabricShield)
			{
				((FabricShield)activeItem.getItem()).onBlockingTick(entity);
			}
			if(activeItem.hasEnchantments())
			{
				for(FabricShieldEnchantment enchantment : FabricShieldLib.enchantments)
				{
					if(EnchantmentHelper.getLevel(enchantment, activeItem) > 0)
					{
						enchantment.onBlockingTick(entity);
					}
				}
			}
		}
	}
}