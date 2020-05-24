package me.crimsondawn45.fabricshieldlib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import me.crimsondawn45.fabricshieldlib.object.FabricShield;
import me.crimsondawn45.fabricshieldlib.object.FabricShieldEnchantment;
import me.crimsondawn45.fabricshieldlib.util.FabricShieldLibRegistry;
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
					for(FabricShieldEnchantment enchantment : FabricShieldLibRegistry.getAllShieldEnchantments())
					{
						if(enchantment.hasEnchantment(activeItem))
						{
							enchantment.onBlockDamage(entity, source, amount, EnchantmentHelper.getLevel(enchantment, activeItem), entity.getActiveHand(), activeItem);
						}
					}
				}
			}
		}
	}
	
	@Inject(at = @At(value = "HEAD"), method = "tick()V", locals = LocalCapture.CAPTURE_FAILHARD)
	private void tick(CallbackInfo callbackInfo)
	{
		LivingEntity entity = (LivingEntity)(Object)this;
		ItemStack activeItem = entity.getActiveItem();
		ItemStack mainItem = entity.getMainHandStack();
		ItemStack offhandItem = entity.getOffHandStack();
		
		//Holding Ticks ShieldItem
		if(mainItem.getItem() instanceof FabricShield)
		{
			((FabricShield)mainItem.getItem()).whileHolding(entity, entity.method_6039(), Hand.MAIN_HAND, mainItem);
		}
		else if(offhandItem.getItem() instanceof FabricShield)
		{
			((FabricShield)offhandItem.getItem()).whileHolding(entity, entity.method_6039(), Hand.OFF_HAND, offhandItem);
		}
		
		//Holding Ticks Enchantment
		if(mainItem.hasEnchantments())
		{
			for(FabricShieldEnchantment enchantment : FabricShieldLibRegistry.getAllShieldEnchantments())
			{
				if(enchantment.hasEnchantment(mainItem))
				{
					enchantment.whileHolding(entity, entity.method_6039(), EnchantmentHelper.getLevel(enchantment, mainItem), Hand.MAIN_HAND, mainItem);
				}
			}
		}
		else if(offhandItem.hasEnchantments())
		{
			for(FabricShieldEnchantment enchantment : FabricShieldLibRegistry.getAllShieldEnchantments())
			{
				if(enchantment.hasEnchantment(offhandItem))
				{
					enchantment.whileHolding(entity, entity.method_6039(), EnchantmentHelper.getLevel(enchantment, offhandItem), Hand.OFF_HAND, offhandItem);
				}
			}
		}
		
		//Blocking Ticks
		if(entity.method_6039())
		{
			if(activeItem.getItem() instanceof FabricShield)
			{
				((FabricShield)activeItem.getItem()).whileBlocking(entity, entity.getActiveHand(), activeItem);
			}
			if(activeItem.hasEnchantments())
			{
				for(FabricShieldEnchantment enchantment : FabricShieldLibRegistry.getAllShieldEnchantments())
				{
					if(enchantment.hasEnchantment(activeItem))
					{
						enchantment.whileBlocking(entity, EnchantmentHelper.getLevel(enchantment, activeItem), entity.getActiveHand(), activeItem);
					}
				}
			}
		}
	}
}