package me.crimsondawn45.fabricshieldlib.object;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class TestEnchantment extends FabricShieldEnchantment
{
    public TestEnchantment(Rarity weight)
    {
        super(weight);
    }

    @Override
    public void whileBlocking(LivingEntity defender, int level, Hand hand, ItemStack shield)
    {
        defender.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 2, level, true, false, false));
    }
}