package com.github.crimsondawn45.fabricshieldlib.mixin;


import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShield;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    //thanks Starexify (Nova on disc) for this mixin!
    @WrapOperation(
            method = "renderFirstPersonItem",
            constant = @Constant(classValue = ShieldItem.class)
    )
    private boolean wrapInstanceCheck(Object instance, Operation<Boolean> original) {
        return original.call(instance) || instance instanceof FabricShield;
    }
}
