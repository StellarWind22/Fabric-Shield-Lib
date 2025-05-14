package com.github.crimsondawn45.fabricshieldlib.mixin;

import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldBlockCallback;
import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin to implement {@link ShieldBlockCallback}
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Inject(
			at = @At(
				value = "INVOKE",
				shift = Shift.AFTER,
				target = "Lnet/minecraft/component/type/BlocksAttacksComponent;onShieldHit(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/Hand;F)V"
			),
			method = "Lnet/minecraft/entity/LivingEntity;getDamageBlockedAmount(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/damage/DamageSource;F)F"
		)
	private void invokeShieldBlockCallback(ServerWorld world, DamageSource source, float amount,
			CallbackInfoReturnable<Float> callbackInfo, @Local(ordinal = 0) ItemStack blockingItem) {
		LivingEntity defender = (LivingEntity) (Object) this;
		ShieldBlockCallback.EVENT.invoker().block(defender, source, amount, defender.getActiveHand(), blockingItem);
	}
}
