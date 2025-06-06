package com.github.crimsondawn45.fabricshieldlib.mixin;

import com.github.crimsondawn45.fabricshieldlib.lib.config.FabricShieldLibConfig;
import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldDisabledCallback;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Mixin to implement {@link ShieldDisabledCallback} and {@link FabricShieldLibConfig#universal_disable}
 */
@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
	/**
	 * @param callbackInfo callback information
	 */
	@Inject(
		at = @At(
			value = "INVOKE",
			shift = Shift.AFTER,
			target = "Lnet/minecraft/entity/LivingEntity;takeShieldHit(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/LivingEntity;)V"
		),
		method = "takeShieldHit(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/LivingEntity;)V",
		locals = LocalCapture.CAPTURE_FAILHARD,
		cancellable = true
	)
	private void takeShieldHitHookAfterCallSuper(
		ServerWorld world, LivingEntity attacker, CallbackInfo callbackInfo) {

		PlayerEntity player = (PlayerEntity) (Object) this;

		if (FabricShieldLibConfig.universal_disable) {
			// Disable all shields
			float blockForSeconds = attacker.getWeaponDisableBlockingForSeconds();
			if (blockForSeconds <= 0.0F) {
				return;
			}

			for (ItemStack itemStack: player.getInventory()) {
				BlocksAttacksComponent blocksAttacksComponent = itemStack != null ?
					itemStack.get(DataComponentTypes.BLOCKS_ATTACKS) : null;
				if (blocksAttacksComponent != null) {
					blocksAttacksComponent.applyShieldCooldown(world, player, blockForSeconds, itemStack);
					ShieldDisabledCallback.EVENT.invoker().disable(player, player.getActiveHand(), itemStack);
				}
			}

			callbackInfo.cancel();
		}
	}

	@Inject(
		at = @At(
			value = "INVOKE",
			shift = Shift.AFTER,
			target = "Lnet/minecraft/component/type/BlocksAttacksComponent;applyShieldCooldown(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/LivingEntity;FLnet/minecraft/item/ItemStack;)V"
		),
		method = "takeShieldHit(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/LivingEntity;)V",
		locals = LocalCapture.NO_CAPTURE,
		cancellable = false
	)
	private void takeShieldHitHookAfterApplyShieldCooldown(
		ServerWorld world, LivingEntity attacker, CallbackInfo callbackInfo, @Local(ordinal = 0) ItemStack itemStack) {

		PlayerEntity player = (PlayerEntity) (Object) this;

		ShieldDisabledCallback.EVENT.invoker().disable(player, player.getActiveHand(), itemStack);
	}
}
