package com.github.crimsondawn45.fabricshieldlib.lib.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

/**
 * Event for doing things when the shield successfully blocks damage.
 */
public interface ShieldBlockCallback {
	/**
	 * Handle event listeners.
	 */
	Event<ShieldBlockCallback> EVENT = EventFactory.createArrayBacked(ShieldBlockCallback.class,
		(listeners) -> (defender, source, amount, hand, shield) -> {
			for (ShieldBlockCallback listener : listeners) {
				ActionResult result = listener.block(defender, source, amount, hand, shield);

				if (result != ActionResult.PASS) {
					return result;
				}
			}

			return ActionResult.PASS;
		}
	);

	/**
	 * Note: event can't be cancelled because the LivingEntity.damage method is very
	 * monolithic, and canceling it results in broken behavior.
	 *
	 * @param defender The {@link LivingEntity} being attacked.
	 * @param source   The {@link DamageSource}
	 * @param amount   amount of damage.
	 * @param hand     which hand the shield is in.
	 * @param shield   {@link ItemStack} instance of shield.
	 * @return whether or not to skip/cancel the event.
	 *         Simply return {@link ActionResult#PASS}
	 */
	ActionResult block(LivingEntity defender, DamageSource source, float amount, Hand hand, ItemStack shield);
}
