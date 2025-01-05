package com.github.crimsondawn45.fabricshieldlib.mixin;

import com.github.crimsondawn45.fabricshieldlib.lib.config.FabricShieldLibConfig;
import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldDisabledCallback;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShield;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldUtils;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import org.spongepowered.asm.mixin.Mixin;
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
	/**
	 * <blockquote>
	 *
	 * <pre>
	 *     protected void damageShield(float amount) {
	 * -       if (this.activeItemStack.isOf(Items.SHIELD))
	 * +       if (damageFabricShield(this.activeItemStack.isOf(Items.SHIELD), damageAmount))
	 * </pre>
	 *
	 * </blockquote>
	 */
	@ModifyExpressionValue(
			allow = 1,
			require = 1,
			method = "damageShield(F)V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z")
		)
	private boolean damageFabricShield(boolean isVanillaShield) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		ItemStack activeItemStack = player.getActiveItem();
		Item activeItem = activeItemStack.getItem();
		return isVanillaShield || FabricShieldUtils.isShieldItem(activeItem);
	}

    /**
     * @param callbackInfo callback information
     */
    @Inject(at = @At(value = "HEAD"), method = "disableShield(Lnet/minecraft/item/ItemStack;)V", locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void disableShieldHead(ItemStack itemStack, CallbackInfo callbackInfo) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        ShieldDisabledCallback.EVENT.invoker().disable(player, player.getActiveHand(), itemStack);

        if (itemStack.getItem() instanceof FabricShield shield) {
            if (!FabricShieldLibConfig.universal_disable) {
                player.getItemCooldownManager().set(itemStack, shield.getCoolDownTicks());
                player.clearActiveItem();
                player.getWorld().sendEntityStatus(player, (byte) 30);
                callbackInfo.cancel();
            } else {
                getEntryList(player);
            }
        } else if (itemStack.isOf(Items.SHIELD)) {
            if (FabricShieldLibConfig.universal_disable) {
                getEntryList(player);
            }
        }
    }

    @Unique
    private void getEntryList(PlayerEntity player) {
        Optional<RegistryEntryList.Named<Item>> opt = Registries.ITEM.getOptional(ConventionalItemTags.SHIELD_TOOLS);
        List<Item> list = new ArrayList<>();
        if (opt.isPresent()) {
            list = opt.get().stream().map(RegistryEntry::value).toList();
        }

        for (int amountOfShields = list.size(); amountOfShields > 0; amountOfShields--) {

            if (list.get(amountOfShields - 1) instanceof ShieldItem) {
                player.getItemCooldownManager().set(Items.SHIELD.getDefaultStack(), 100);
            } else if (list.get(amountOfShields - 1) instanceof FabricShield) {
                player.getItemCooldownManager().set(list.get(amountOfShields - 1).getDefaultStack(), ((FabricShield) list.get(amountOfShields - 1)).getCoolDownTicks());
            }
            player.clearActiveItem();
            player.getWorld().sendEntityStatus(player, (byte) 30);
        }
    }
}
