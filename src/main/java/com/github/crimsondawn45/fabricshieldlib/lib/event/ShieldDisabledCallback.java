package com.github.crimsondawn45.fabricshieldlib.lib.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

/**
 * Event for doing things when the shield is disabled.
 */
public interface ShieldDisabledCallback {

    /**
     * Handle event listeners.
     */
    Event<ShieldDisabledCallback> EVENT = EventFactory.createArrayBacked(ShieldDisabledCallback.class,
            (listeners) -> (defender, hand, shield) -> {
                for (ShieldDisabledCallback listener : listeners) {
                    ActionResult result = listener.disable(defender, hand, shield);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    /**
     * Note: event can't be cancelled if mixing into LivingEntity.damage method because it is very monolithic, and cancelling it results in broken behavior.
     *
     * @param defender entity being attacked.
     * @param hand     which hand the shield is in.
     * @param shield   shield itemstack instance.
     * @return whether or not to skip/cancel the event.
     */
    ActionResult disable(PlayerEntity defender, Hand hand, ItemStack shield);
}
