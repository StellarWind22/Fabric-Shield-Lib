package com.github.crimsondawn45.fabricshieldlib.lib.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.util.ActionResult;

public interface ShieldSetModelCallback {

    /**
     * Handle event listeners.
     */
    Event<ShieldSetModelCallback> EVENT = EventFactory.createArrayBacked(ShieldSetModelCallback.class,
            (listeners) -> (loader) -> {
                for (ShieldSetModelCallback listener : listeners) {
                    ActionResult result = listener.setModel(loader);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult setModel(EntityModelLoader loader);
}
