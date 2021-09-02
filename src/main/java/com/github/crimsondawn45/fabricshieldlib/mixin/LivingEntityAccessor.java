package com.github.crimsondawn45.fabricshieldlib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

/**
 * Accessor for allowing other mixins to invoke important private methods.
 */
@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {

	/**
	 * @param source source of the damage.
	 * @return if it was blocked.
	 */
    @Invoker
	boolean fabricshieldlib$invokeBlockedByShield(DamageSource source);

	/**
	 * @param amount amount of damage the shield blocked.
	 */
	@Invoker
	void fabricshieldlib$invokeDamageShield(float amount);

	/**
	 * @param attacker entity attacking the entity blocking.
	 */
	@Invoker
	void fabricshieldlib$invokeTakeShieldHit(LivingEntity attacker);
}
