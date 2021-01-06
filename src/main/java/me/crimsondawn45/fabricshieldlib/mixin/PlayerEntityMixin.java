package me.crimsondawn45.fabricshieldlib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import me.crimsondawn45.fabricshieldlib.lib.ShieldRegistry;
import me.crimsondawn45.fabricshieldlib.lib.object.AbstractShield;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin
{
	@Inject(at = @At(value = "TAIL"), method = "damageShield(F)V", locals=LocalCapture.CAPTURE_FAILHARD)
	private void damageShield(float amount, CallbackInfo callBackInfo) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		ItemStack activeItem = player.getActiveItem();
		
		if(amount >= 3.0F && ShieldRegistry.isFabricShield(activeItem.getItem())) {
			int i = 1 + MathHelper.floor(amount);
            Hand hand = player.getActiveHand();
            
            activeItem.damage(i, (LivingEntity)player, ((playerEntity) -> {player.sendToolBreakStatus(hand);}));
            
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

	@Inject(at = @At(value = "HEAD"), method = "disableShield(Z)V", locals = LocalCapture.CAPTURE_FAILHARD)
	private void disableShieldHead(boolean sprinting, CallbackInfo callbackInfo) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		ItemStack shield = player.getActiveItem();

		if(ShieldRegistry.hasEvent(shield)) {
			ShieldRegistry.fireOnDisable(player, player.getActiveHand(), shield, ShieldRegistry.getEvents(shield));
		}
	}
	
	@Inject(at = @At(value = "TAIL"), method = "disableShield(Z)V", locals = LocalCapture.CAPTURE_FAILHARD)
	private void disableShieldTail(boolean sprinting, CallbackInfo callBackInfo) {	
		PlayerEntity player = (PlayerEntity) (Object) this;
		Item shield = player.getActiveItem().getItem();
		
		if(ShieldRegistry.isFabricShield(shield)) {
			float f = 0.25F + (float)EnchantmentHelper.getEfficiency(player) * 0.05F;
			if (sprinting) {
        		f += 0.75F;
      		}

			if (player.getRandom().nextFloat() < f) {
         		player.getItemCooldownManager().set(shield, ((AbstractShield)shield).getCooldownTicks());
         		player.clearActiveItem();
         		player.world.sendEntityStatus(player, (byte)30);
      		}
		}
	}
}
