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
				if(activeItem.getItem() instanceof FabricShield)
				{
					((FabricShield)activeItem.getItem()).onBlockDamage(entity, source, amount, entity.getActiveHand(), activeItem);
				}
				if(activeItem.hasEnchantments())
				{
					for(FabricShieldEnchantment enchantment : FabricShieldLib.enchantments)
					{
						if(enchantment.hasEnchantment(activeItem))
						{
							enchantment.onBlockDamage(entity, source, entity.getActiveHand(), activeItem, amount, EnchantmentHelper.getLevel(enchantment, activeItem));
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
		ItemStack mainItem = entity.getMainHandStack();
		ItemStack offhandItem = entity.getOffHandStack();
		
		//Holding Ticks ShieldItem
		if(mainItem.getItem() instanceof FabricShield)
		{
			((FabricShield)mainItem.getItem()).whileHoldingTick(entity, entity.isBlocking(), Hand.MAIN_HAND, mainItem);
		}
		else if(offhandItem.getItem() instanceof FabricShield)
		{
			((FabricShield)offhandItem.getItem()).whileHoldingTick(entity, entity.isBlocking(), Hand.OFF_HAND, offhandItem);
		}
		
		//Holding Ticks Enchantment
		if(mainItem.hasEnchantments())
		{
			for(FabricShieldEnchantment enchantment : FabricShieldLib.enchantments)
			{
				if(enchantment.hasEnchantment(mainItem))
				{
					enchantment.whileHoldingTick(entity, entity.isBlocking(), Hand.MAIN_HAND, mainItem, EnchantmentHelper.getLevel(enchantment, mainItem));
				}
			}
		}
		else if(offhandItem.hasEnchantments())
		{
			for(FabricShieldEnchantment enchantment : FabricShieldLib.enchantments)
			{
				if(enchantment.hasEnchantment(offhandItem))
				{
					enchantment.whileHoldingTick(entity, entity.isBlocking(), Hand.OFF_HAND, offhandItem, EnchantmentHelper.getLevel(enchantment, offhandItem));
				}
			}
		}
		
		//Blocking Ticks
		if(entity.isBlocking())
		{
			if(activeItem.getItem() instanceof FabricShield)
			{
				((FabricShield)activeItem.getItem()).whileBlockingTick(entity, entity.getActiveHand(), activeItem);
			}
			if(activeItem.hasEnchantments())
			{
				for(FabricShieldEnchantment enchantment : FabricShieldLib.enchantments)
				{
					if(enchantment.hasEnchantment(activeItem))
					{
						enchantment.whileBlockingTick(entity, entity.getActiveHand(), activeItem, EnchantmentHelper.getLevel(enchantment, activeItem));
					}
				}
			}
		}
	}
}