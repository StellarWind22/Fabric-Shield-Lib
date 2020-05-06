package me.crimsondawn45.fabricshieldlib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

@Mixin(ModelPredicateProviderRegistry.class)
public interface ModelPredicateProviderRegistryAccessor
{
	@Invoker
	static void invokeRegister(Item item, Identifier id, ModelPredicateProvider provider) {};
}
