package com.github.crimsondawn45.fabricshieldlib.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * Accessor for allowing other mixins to invoke important private methods.
 */
@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {

    /**
     * @param source source of the damage.
     * @return if it was blocked.
     */
    @Invoker(value = "blockedByShield")
    boolean fabricshieldlib$invokeBlockedByShield(DamageSource source);

    /**
     * @param amount amount of damage the shield blocked.
     */
    @Invoker(value = "damageShield")
    void fabricshieldlib$invokeDamageShield(float amount);

    /**
     * @param attacker entity attacking the entity blocking.
     */
    @Invoker(value = "takeShieldHit")
    void fabricshieldlib$invokeTakeShieldHit(LivingEntity attacker);
}
