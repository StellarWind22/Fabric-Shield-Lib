package me.crimsondawn45.fabricshieldlib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor
{	
	@Accessor(value = "activeItemStack")
	void setActiveItemStack(ItemStack stack);
}