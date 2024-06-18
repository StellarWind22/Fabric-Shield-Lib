package com.github.crimsondawn45.fabricshieldlib.mixin;

import com.github.crimsondawn45.fabricshieldlib.lib.config.FabricShieldLibConfig;
import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldDisabledCallback;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShield;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Mixin that allows custom shields to be damaged, and to be disabled with axes.
 */
@SuppressWarnings("UnreachableCode")
@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Shadow
    @Final
    PlayerInventory inventory;

    @Inject(at = @At(value = "HEAD"), method = "damageShield(F)V", locals = LocalCapture.CAPTURE_FAILHARD)
    private void damageShield(float amount, CallbackInfo callBackInfo) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack activeItem = player.getActiveItem();

        if (activeItem.getItem() instanceof FabricShield) {
            if (amount >= 3.0F) {
                int i = 1 + MathHelper.floor(amount);
                boolean offHand = player.getActiveHand().equals(Hand.OFF_HAND);
                boolean mainHand = player.getActiveHand().equals(Hand.MAIN_HAND);

                if(offHand){
                    activeItem.damage(i, (LivingEntity) player, EquipmentSlot.OFFHAND);
                } else if (mainHand){
                    activeItem.damage(i, (LivingEntity) player, EquipmentSlot.MAINHAND);
                }

                if (activeItem.isEmpty()) {
                    if (mainHand) {
                        player.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    } else {
                        player.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    }

                    activeItem = ItemStack.EMPTY;
                    player.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + player.getWorld().random.nextFloat() * 0.4F);
                }
            }

        }
    }

    /**
     * @param callbackInfo callback information
     */
    @Inject(at = @At(value = "HEAD"), method = "disableShield()V", locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void disableShieldHead(CallbackInfo callbackInfo) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack activeItemStack = player.getActiveItem();
        Item activeItem = activeItemStack.getItem();

        ShieldDisabledCallback.EVENT.invoker().disable(player, player.getActiveHand(), activeItemStack);

        if (activeItem instanceof FabricShield shield) {
            if (!FabricShieldLibConfig.universal_disable) {
                player.getItemCooldownManager().set((Item) shield, shield.getCoolDownTicks());
                player.clearActiveItem();
                player.getWorld().sendEntityStatus(player, (byte) 30);
                callbackInfo.cancel();
            } else {
                getEntryList(player);
            }
        } else if (activeItem instanceof ShieldItem) {
            if (FabricShieldLibConfig.universal_disable) {
                getEntryList(player);
            }
        }
    }

    @Unique
    private void getEntryList(PlayerEntity player) {
        Optional<RegistryEntryList.Named<Item>> opt = Registries.ITEM.getEntryList(ConventionalItemTags.SHIELDS_TOOLS);
        List<Item> list = new ArrayList<>();
        if (opt.isPresent()) {
            list = opt.get().stream().map(RegistryEntry::value).toList();
        }

        for (int amountOfShields = list.size(); amountOfShields > 0; amountOfShields--) {

            if (list.get(amountOfShields - 1) instanceof ShieldItem) {
                player.getItemCooldownManager().set(Items.SHIELD, 100);
            } else if (list.get(amountOfShields - 1) instanceof FabricShield) {
                player.getItemCooldownManager().set(list.get(amountOfShields - 1), ((FabricShield) list.get(amountOfShields - 1)).getCoolDownTicks());
            }
            player.clearActiveItem();
            player.getWorld().sendEntityStatus(player, (byte) 30);
        }
    }
}
