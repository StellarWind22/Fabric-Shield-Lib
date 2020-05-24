package me.crimsondawn45.fabricshieldlib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor
{	
	@Accessor
	void setActiveItemStack(ItemStack stack);
	
	@Invoker("method_6061")
	boolean invokeBlockedByShield(DamageSource source);
	
	@Invoker
	void invokeDamageShield(float amount);
}