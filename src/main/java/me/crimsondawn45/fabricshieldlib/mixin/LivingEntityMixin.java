package me.crimsondawn45.fabricshieldlib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import me.crimsondawn45.fabricshieldlib.util.ShieldRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

@Mixin(LivingEntity.class)
public class LivingEntityMixin
{	
	@Inject(at = @At(value = "HEAD"), method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", locals = LocalCapture.CAPTURE_FAILHARD)
	private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callbackInfo)
	{
		LivingEntity entity = (LivingEntity)(Object)this;
		
		if(!(entity.isInvulnerableTo(source) || entity.world.isClient || entity.getHealth() <= 0.0F || (source.isFire() && entity.hasStatusEffect(StatusEffects.FIRE_RESISTANCE))))
		{
			ItemStack activeItem = entity.getActiveItem();
			
			if (amount > 0.0F && ((LivingEntityAccessor)entity).invokeBlockedByShield(source))
			{
				if(ShieldRegistry.hasEvent(activeItem))
				{
					ShieldRegistry.fireOnBlockDamage(entity, source, amount, entity.getActiveHand(), activeItem, ShieldRegistry.getEvents(activeItem));
				}
			}
		}
	}
	
	@Inject(at = @At(value = "HEAD"), method = "tick()V", locals = LocalCapture.CAPTURE_FAILHARD)
	private void tick(CallbackInfo callbackInfo)
	{
		LivingEntity entity = (LivingEntity)(Object)this;

		ItemStack mainItem = entity.getMainHandStack();
		ItemStack offhandItem = entity.getOffHandStack();
		
		//Holding Ticks ShieldItem
		if(ShieldRegistry.hasEvent(mainItem))
		{
			ShieldRegistry.fireWhileHolding(entity, Hand.MAIN_HAND, mainItem, ShieldRegistry.getEvents(mainItem));
		}
		else if(ShieldRegistry.hasEvent(offhandItem))
		{
			ShieldRegistry.fireWhileHolding(entity, Hand.OFF_HAND, offhandItem, ShieldRegistry.getEvents(offhandItem));
		}
	}
}