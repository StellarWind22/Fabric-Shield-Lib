package com.github.crimsondawn45.fabricshieldlib.mixin;

import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLib;
import com.github.crimsondawn45.fabricshieldlib.lib.config.FabricShieldLibConfig;
import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldDisabledCallback;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShield;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

/**
 * Mixin that allows custom shields to be damaged, and to be disabled with axes.
 */
@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(at = @At(value = "HEAD"), method = "damageShield(F)V", locals = LocalCapture.CAPTURE_FAILHARD, cancellable = false)
    private void damageShield(float amount, CallbackInfo callBackInfo) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack activeItem = player.getActiveItem();

        if (activeItem.getItem() instanceof FabricShield) {
            if (amount >= 3.0F) {
                int i = 1 + MathHelper.floor(amount);
                Hand hand = player.getActiveHand();

                activeItem.damage(i, (LivingEntity) player, ((playerEntity) -> {
                    player.sendToolBreakStatus(hand);
                }));

                if (activeItem.isEmpty()) {
                    if (hand == Hand.MAIN_HAND) {
                        player.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    } else {
                        player.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    }

                    activeItem = ItemStack.EMPTY;
                    player.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + player.world.random.nextFloat() * 0.4F);
                }
            }

        }
    }

    /**
     * @param sprinting if player is sprinting
     * @param callbackInfo callback information
     */
    @Inject(at = @At(value = "HEAD"), method = "disableShield(Z)V", locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void disableShieldHead(boolean sprinting, CallbackInfo callbackInfo) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack activeItemStack = player.getActiveItem();
        Item activeItem = activeItemStack.getItem();

        ShieldDisabledCallback.EVENT.invoker().disable(player, player.getActiveHand(), activeItemStack);

        if (activeItem instanceof FabricShield) {
            FabricShield shield = (FabricShield) activeItem;

            float f = 0.25F + (float) EnchantmentHelper.getEfficiency(player) * 0.05F;
            if (sprinting) {
                f += 0.75F;
            }

            if (player.getRandom().nextFloat() < f) {
                if (!FabricShieldLibConfig.universal_disable){
                    player.getItemCooldownManager().set((Item) shield, shield.getCooldownTicks());
                    player.clearActiveItem();
                    player.world.sendEntityStatus(player, (byte) 30);
                    callbackInfo.cancel();
                } else if (FabricShieldLibConfig.universal_disable){
                    List<RegistryEntryList.Named<Item>> listofShields = Registries.ITEM.getEntryList(ConventionalItemTags.SHIELDS).stream().toList();
                    for(int amountOfShields = listofShields.size(); amountOfShields > 0; amountOfShields--) {
                        List theShields = listofShields.stream().toList();
                        player.getItemCooldownManager().set((Item)theShields.get(amountOfShields-1), shield.getCooldownTicks());
                        player.clearActiveItem();
                        player.world.sendEntityStatus(player, (byte) 30);
                    }
                }
            }
        }
    }
}
