package me.crimsondawn45.fabricshieldlib.object;

import me.crimsondawn45.fabricshieldlib.util.event.ShieldEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class TestEvent extends ShieldEvent
{
    public TestEvent()
    {
        super(true, true, true);
    }

    @Override
    public void onBlockDamage(LivingEntity defender, DamageSource source, float amount, int level, Hand hand, ItemStack shield)
    {
        defender.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 80, 0, false, true, true));
    }

    @Override
    public void onDisable(PlayerEntity defender, int level, Hand hand, ItemStack shield)
    {
        defender.setFireTicks(40);    
    }

    @Override
    public void whileHolding(LivingEntity defender, int level, Hand hand, ItemStack shield)
    {
        defender.setGlowing(defender.isBlocking());
    }
}