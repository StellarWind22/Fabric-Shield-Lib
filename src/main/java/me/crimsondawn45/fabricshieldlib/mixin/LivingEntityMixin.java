package me.crimsondawn45.fabricshieldlib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import me.crimsondawn45.fabricshieldlib.lib.ShieldRegistry;
import me.crimsondawn45.fabricshieldlib.lib.object.FabricShield;
import me.crimsondawn45.fabricshieldlib.lib.object.ShieldEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {	
	@Inject(at = @At(value = "HEAD"), method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", locals = LocalCapture.CAPTURE_FAILHARD)
	private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callbackInfo) {
		LivingEntity entity = (LivingEntity)(Object)this;
		ItemStack activeItem = entity.getActiveItem();
		
		if(!(entity.isInvulnerableTo(source) || entity.world.isClient || entity.isDead() || (source.isFire() && entity.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)))) {
			if (amount > 0.0F && ((LivingEntityAccessor)entity).fabricshieldlib$invokeBlockedByShield(source)) {
				
				/*
				 * Handle onBlockDamage Events
				 */
				if(ShieldRegistry.isFabricShield(activeItem.getItem())) {
					FabricShield shield = (FabricShield) activeItem.getItem();
					if(shield.hasEvent()) {
						if(shield.getEvent().usesOnBlockDamage()) {
							shield.getEvent().onBlockDamage(entity, source, amount, 0, entity.getActiveHand(), activeItem);;
						}
					}
				}
				for(ShieldEnchantment entry : ShieldRegistry.getAllShieldEnchantments()) {
					if(entry.hasEnchantment(activeItem) && entry.hasEvent()) {
						if(entry.getEvent().usesOnBlockDamage()) {
							entry.getEvent().onBlockDamage(entity, source, amount, EnchantmentHelper.getLevel(entry, activeItem), entity.getActiveHand(), activeItem);
						}
					}
				}

				//Handle Shield
				((LivingEntityAccessor)entity).fabricshieldlib$invokeDamageShield(amount);
				amount = 0.0F;

				if (!source.isProjectile()) {
					Entity sourceEntity = source.getSource();
					
					if (sourceEntity instanceof LivingEntity) {
					   ((LivingEntityAccessor)entity).fabricshieldlib$invokeTakeShieldHit((LivingEntity)sourceEntity);
					}
				}
			}
		}
	}
	
	@Inject(at = @At(value = "HEAD"), method = "tick()V", locals = LocalCapture.CAPTURE_FAILHARD)
	private void tick(CallbackInfo callbackInfo) {
		LivingEntity entity = (LivingEntity)(Object)this;

		ItemStack mainItem = entity.getMainHandStack();
		ItemStack offhandItem = entity.getOffHandStack();
		
		/*
		 * Handle WhileHolding Events
		 */
		if(ShieldRegistry.isFabricShield(mainItem.getItem())) {
			FabricShield shield = (FabricShield) mainItem.getItem();
			if(shield.hasEvent()) {
				if(shield.getEvent().usesWhileHolding()) {
					shield.getEvent().whileHolding(entity, 0, Hand.MAIN_HAND, mainItem);
				}
			}
		}
		for(ShieldEnchantment entry : ShieldRegistry.getAllShieldEnchantments()) {
			if(entry.hasEnchantment(mainItem) && entry.hasEvent()) {
				if(entry.getEvent().usesWhileHolding()) {
					entry.getEvent().whileHolding(entity, EnchantmentHelper.getLevel(entry, mainItem), entity.getActiveHand(), mainItem);
				}
			}
		}
		if(ShieldRegistry.isFabricShield(offhandItem.getItem())) {
			FabricShield shield = (FabricShield) offhandItem.getItem();
			if(shield.hasEvent()) {
				if(shield.getEvent().usesWhileHolding()) {
					shield.getEvent().whileHolding(entity, 0, Hand.MAIN_HAND, offhandItem);
				}
			}
		}
		for(ShieldEnchantment entry : ShieldRegistry.getAllShieldEnchantments()) {
			if(entry.hasEnchantment(offhandItem) && entry.hasEvent()) {
				if(entry.getEvent().usesWhileHolding()) {
					entry.getEvent().whileHolding(entity, EnchantmentHelper.getLevel(entry, offhandItem), entity.getActiveHand(), offhandItem);
				}
			}
		}
	}
}