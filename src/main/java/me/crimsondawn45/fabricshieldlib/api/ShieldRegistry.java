package me.crimsondawn45.fabricshieldlib.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.crimsondawn45.fabricshieldlib.api.event.ShieldEvent;
import me.crimsondawn45.fabricshieldlib.api.event.ShieldEventType;
import me.crimsondawn45.fabricshieldlib.api.object.AbstractShield;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class ShieldRegistry
{
	//Shield item stuff
	private static final List<Item> allShields = new ArrayList<Item>();
	private static final List<AbstractShield> fabricShields = new ArrayList<AbstractShield>();

	//Shield event stuff
	private static final HashMap<Item, Set<ShieldEvent>> itemEvents = new HashMap<Item, Set<ShieldEvent>>();
	private static final HashMap<Enchantment, Set<ShieldEvent>> enchantmentEvents = new HashMap<Enchantment, Set<ShieldEvent>>();

	/**
	 * registerShield
	 * 
	 * Registers an instance of FabricShieldItem or Item into the shield registry.
	 * 
	 * @param shield - Instance of shield to register.
	 */
	public static void register(Item shield)
	{
		allShields.add(shield);
		
		if(shield instanceof AbstractShield)
		{
			fabricShields.add((AbstractShield) shield);
		}
	}
	
	/**
	 * containsShield
	 * 
	 * Check if the shield registry contains a particular Item instance.
	 * 
	 * @param shield - Item instance to check for.
	 */
	public static boolean containsShield(Item shield)
	{
		return allShields.contains(shield);
	}
	
	/**
	 * containsFabricShield
	 * 
	 * Check if the shield registry contains a particular FabricShield instance.
	 * 
	 * @param shield - Item instance to check for.
	 */
	public static boolean containsFabricShield(Item shield)
	{
		return fabricShields.contains(shield);
	}

	/**
	 * getAllShields
	 * 
	 * @return Every registered Item instance at the time this is invoked.
	 */
	public static Item[] getAllShields()
	{
		Item[] result = new Item[allShields.size()];
		result = allShields.toArray(result);

		return result;
	}
	
	/**
	 * getAllFabricShields
	 * 
	 * @return Every registered FabricShield instance at the time this is invoked.
	 */
	public static AbstractShield[] getAllFabricShields()
	{
		AbstractShield[] result = new AbstractShield[fabricShields.size()];
		result = fabricShields.toArray(result);

		return result;
	}

	/**
	 * registerItemEvent
	 * 
	 * Registers a ShieldEvent to a particular Item instance.
	 * 
	 * @param shield - Instance to tie it to.
	 * @param event - Event to be registered
	 */
	public static void registerItemEvent(Item shield, ShieldEvent event)
	{
		if(event.getType() == ShieldEventType.UNSET)
		{
			event.setType(ShieldEventType.ITEM);
		}
		else if(event.getType() == ShieldEventType.ENCHANTMENT)
		{
			event.setType(ShieldEventType.BOTH);
		}

		if (itemEvents.containsKey(shield)) {
			itemEvents.get(shield).add(event);
		} else {
			itemEvents.put(shield, new HashSet<ShieldEvent>());
			itemEvents.get(shield).add(event);
		}
	}

	/**
	 * registerItemEvent
	 * 
	 * Registers a ShieldEvent to a particular Enchantment instance.
	 * 
	 * @param enchantment - Instance to tie it to.
	 * @param event - Event to be registered
	 */
	public static void registerEnchantmentEvent(Enchantment enchantment, ShieldEvent event)
	{
		if(event.getType() == ShieldEventType.UNSET)
		{
			event.setType(ShieldEventType.ENCHANTMENT);
		}
		else if(event.getType() == ShieldEventType.ITEM)
		{
			event.setType(ShieldEventType.BOTH);
		}

		if(enchantmentEvents.containsKey(enchantment)) {
			enchantmentEvents.get(enchantment).add(event);
		} else {
			enchantmentEvents.put(enchantment, new HashSet<ShieldEvent>());
			enchantmentEvents.get(enchantment).add(event);
		}
	}

	/**
	 * hasEvent
	 * 
	 * Wether or not a particular Item instance has any events registered.
	 * 
	 * @param shield - Item to check for.
	 */
	public static boolean hasEvent(Item shield)
	{
		return itemEvents.get(shield) != null;
	}

	/**
	 * hasEvent
	 * 
	 * Wether or not a particular Enchantment instance has any events registered.
	 * 
	 * @param enchantment - Enchantment to check for.
	 */
	public static boolean hasEvent(Enchantment enchantment)
	{
		return enchantmentEvents.containsKey(enchantment);
	}

	/**
	 * hasEvent
	 * 
	 * Whether or not a particular ItemStack has any events.
	 * 
	 * @param shield - ItemStack to check for.
	 */
	public static boolean hasEvent(ItemStack shield)
	{
		if(hasEvent(shield.getItem()))
		{
			return true;
		}
		
		for(Enchantment entry : enchantmentEvents.keySet())
		{
			if(hasEnchantment(entry, shield))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * hasOnBlockDamage
	 * 
	 * whether or not the item has an event that makes use of OnBlockDamage
	 * 
	 * @param shield - Shield to check for event.
	 */
	public static boolean hasOnBlockDamage(Item shield)
	{
		if(itemEvents.containsKey(shield)) {
			for(ShieldEvent entry : itemEvents.get(shield)) {
				if(entry.usesOnBlockDamage()) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	/**
	 * hasOnBlockDamage
	 * 
	 * whether or not the enchantment has an event that makes use of OnBlockDamage
	 * 
	 * @param shield - Enchantment to check for event.
	 */
	public static boolean hasOnBlockDamage(Enchantment enchantment)
	{
		if(enchantmentEvents.containsKey(enchantment)) {
			for(ShieldEvent entry : enchantmentEvents.get(enchantment)) {
				if(entry.usesOnBlockDamage()) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	/**
	 * hasOnDisable
	 * 
	 * whether or not the item has an event that makes use of OnDisable
	 * 
	 * @param shield - Shield to check for event.
	 */
	public static boolean hasOnDisable(Item shield)
	{
		if(itemEvents.containsKey(shield)) {
			for(ShieldEvent entry : itemEvents.get(shield)) {
				if(entry.usesOnDisable()) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	/**
	 * hasOnDisable
	 * 
	 * whether or not the enchantment has an event that makes use of OnDisable
	 * 
	 * @param shield - Enchantment to check for event.
	 */
	public static boolean hasOnDisable(Enchantment enchantment)
	{
		if(enchantmentEvents.containsKey(enchantment)) {
			for(ShieldEvent entry : enchantmentEvents.get(enchantment)) {
				if(entry.usesOnDisable()) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	/**
	 * hasWhileHolding
	 * 
	 * whether or not the item has an event that makes use of WhileHolding
	 * 
	 * @param shield - Shield to check for event.
	 */
	public static boolean hasWhileHolding(Item shield)
	{
		if(itemEvents.containsKey(shield)) {
			for(ShieldEvent entry : itemEvents.get(shield)) {
				if(entry.usesWhileHolding()) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	/**
	 * hasWhileHolding
	 * 
	 * whether or not the enchantment has an event that makes use of WhileHolding
	 * 
	 * @param shield - Enchantment to check for event.
	 */
	public static boolean hasWhileHolding(Enchantment enchantment)
	{
		if(enchantmentEvents.containsKey(enchantment)) {
			for(ShieldEvent entry : enchantmentEvents.get(enchantment)) {
				if(entry.usesWhileHolding()) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	public static ShieldEvent[] getEvents(Enchantment enchantment)
	{
		ShieldEvent[] result = new ShieldEvent[enchantmentEvents.get(enchantment).size()];
		result = enchantmentEvents.get(enchantment).toArray(result);
		return result;
	}

	public static Enchantment[] getEnchantments(ShieldEvent event)
	{
		ArrayList<Enchantment> temp = new ArrayList<Enchantment>();
		
		for(Enchantment entry : enchantmentEvents.keySet()) {
			if(enchantmentEvents.get(entry).contains(event)) {
				temp.add(entry);
			}
		}
		
		Enchantment[] result = new Enchantment[temp.size()];
		result = temp.toArray(result);
		return result;
	}

	public static void fireOnBlockDamage(ShieldEvent event, LivingEntity defender, DamageSource source, float amount, int level, Hand hand, ItemStack shield)
	{
		if(event.usesOnBlockDamage())
		{
			event.onBlockDamage(defender, source, amount, level, hand, shield);
		}
	}

	public static void fireOnDisable(ShieldEvent event, PlayerEntity defender, int level, Hand hand, ItemStack shield)
	{
		if(event.usesOnDisable())
		{
			event.onDisable(defender, level, hand, shield);
		}
	}

	public static void fireWhileHolding(ShieldEvent event, LivingEntity defender, int level, Hand hand, ItemStack shield)
	{
		if(event.usesWhileHolding())
		{
			event.whileHolding(defender, level, hand, shield);
		}
	}

	public static void fireOnBlockDamage(LivingEntity defender, DamageSource source, float amount, Hand hand, ItemStack shield, ShieldEvent...events)
	{
		if(events != null) {
			for(ShieldEvent event : events)
			{
				switch(event.getType())
				{
					case ITEM:

						fireOnBlockDamage(event, defender, source, amount, 0, hand, shield);
						continue;

					case ENCHANTMENT:

						for(Enchantment enchantment : getEnchantments(event))
						{
							fireOnBlockDamage(event, defender, source, amount, EnchantmentHelper.getLevel(enchantment, shield), hand, shield);
						}
						continue;

					case BOTH:
						
						for(Enchantment enchantment : getEnchantments(event))
						{
							fireOnBlockDamage(event, defender, source, amount, EnchantmentHelper.getLevel(enchantment, shield), hand, shield);
						}
						continue;

					default:
						continue;
				}
			}
		}
	}

	public static void fireOnDisable(PlayerEntity defender, Hand hand, ItemStack shield, ShieldEvent...events)
	{
		if(events != null) {
			for(ShieldEvent event : events)
			{
				switch(event.getType())
				{
					case ITEM:

						fireOnDisable(event, defender, 0, hand, shield);
						continue;

					case ENCHANTMENT:

						for(Enchantment enchantment : getEnchantments(event))
						{
							fireOnDisable(event, defender, EnchantmentHelper.getLevel(enchantment, shield), hand, shield);
						}
						continue;

					case BOTH:
						
						for(Enchantment enchantment : getEnchantments(event))
						{
							fireOnDisable(event, defender, EnchantmentHelper.getLevel(enchantment, shield), hand, shield);
						}
						continue;

					default:
						continue;
				}
			}
		}
	}

	public static void fireWhileHolding(LivingEntity defender, Hand hand, ItemStack shield, ShieldEvent...events)
	{
		if(events != null) {
			for(ShieldEvent event : events)
			{
				switch(event.getType())
				{
					case ITEM:

						fireWhileHolding(event, defender, 0, hand, shield);
						continue;

					case ENCHANTMENT:

						for(Enchantment enchantment : getEnchantments(event))
						{
							fireWhileHolding(event, defender, EnchantmentHelper.getLevel(enchantment, shield), hand, shield);
						}
						continue;

					case BOTH:
						
						for(Enchantment enchantment : getEnchantments(event))
						{
							fireWhileHolding(event, defender, EnchantmentHelper.getLevel(enchantment, shield), hand, shield);
						}
						continue;

					default:
						continue;
				}
			}
		}
	}

	public static boolean hasEnchantment(Enchantment enchantment, ItemStack stack)
	{
		return EnchantmentHelper.getLevel(enchantment, stack) > 0;
	}

	public static ShieldEvent[] getEvents(ItemStack shield) {
		if(itemEvents.containsKey(shield.getItem())) {
			ShieldEvent[] result = new ShieldEvent[itemEvents.get(shield.getItem()).size()];
			result = itemEvents.get(shield.getItem()).toArray(result);
			return result;
		} else {
			return null;
		}
	}
	
	public static boolean isFabricShield(Item shield) {
		return shield instanceof AbstractShield;
	}
}