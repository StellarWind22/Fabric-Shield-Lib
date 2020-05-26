package me.crimsondawn45.fabricshieldlib.util;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.List;

import me.crimsondawn45.fabricshieldlib.object.AbstractShield;
import me.crimsondawn45.fabricshieldlib.util.event.ShieldEvent;
import me.crimsondawn45.fabricshieldlib.util.event.ShieldEventType;
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
	private static final List<AbstractMap.SimpleImmutableEntry<Item, ShieldEvent>> itemEvents = new ArrayList<>();
	private static final List<AbstractMap.SimpleImmutableEntry<Enchantment, ShieldEvent>> enchantmentEvents = new ArrayList<>();

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

		itemEvents.add(new AbstractMap.SimpleImmutableEntry<Item, ShieldEvent>(shield, event));
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

		enchantmentEvents.add(new AbstractMap.SimpleImmutableEntry<Enchantment, ShieldEvent>(enchantment, event));
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
		for(AbstractMap.SimpleImmutableEntry<Item, ShieldEvent> entry : itemEvents)
		{
			if(entry.getKey() == shield)
			{
				return true;
			}
		}
		return false;
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
		for(AbstractMap.SimpleImmutableEntry<Enchantment, ShieldEvent> entry : enchantmentEvents)
		{
			if(entry.getKey() == enchantment)
			{
				return true;
			}
		}
		return false;
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
		
		for(AbstractMap.SimpleImmutableEntry<Enchantment, ShieldEvent> entry : enchantmentEvents)
		{
			if(hasEnchantment(entry.getKey(), shield))
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
		for(AbstractMap.SimpleImmutableEntry<Item, ShieldEvent> entry : itemEvents)
		{
			if(entry.getKey() == shield && entry.getValue().usesOnBlockDamage())
			{
				return true;
			}
		}
		return false;
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
		for(SimpleImmutableEntry<Enchantment, ShieldEvent> entry : enchantmentEvents)
		{
			if(entry.getKey() == enchantment && entry.getValue().usesOnBlockDamage())
			{
				return true;
			}
		}
		return false;
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
		for(AbstractMap.SimpleImmutableEntry<Item, ShieldEvent> entry : itemEvents)
		{
			if(entry.getKey() == shield && entry.getValue().usesOnDisable())
			{
				return true;
			}
		}
		return false;
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
		for(SimpleImmutableEntry<Enchantment, ShieldEvent> entry : enchantmentEvents)
		{
			if(entry.getKey() == enchantment && entry.getValue().usesOnDisable())
			{
				return true;
			}
		}
		return false;
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
		for(AbstractMap.SimpleImmutableEntry<Item, ShieldEvent> entry : itemEvents)
		{
			if(entry.getKey() == shield && entry.getValue().usesWhileHolding())
			{
				return true;
			}
		}
		return false;
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
		for(SimpleImmutableEntry<Enchantment, ShieldEvent> entry : enchantmentEvents)
		{
			if(entry.getKey() == enchantment && entry.getValue().usesWhileHolding())
			{
				return true;
			}
		}
		return false;
	}

	public static ShieldEvent[] getEvents(Item shield)
	{
		List<ShieldEvent> temp = new ArrayList<ShieldEvent>();

		for(AbstractMap.SimpleImmutableEntry<Item, ShieldEvent> entry : itemEvents)
		{
			if(entry.getKey() == shield)
			{
				temp.add(entry.getValue());
			}
		}

		ShieldEvent[] result = new ShieldEvent[temp.size()];
		result = temp.toArray(result);

		return result;
	}

	public static ShieldEvent[] getEvents(Enchantment enchantment)
	{
		List<ShieldEvent> temp = new ArrayList<ShieldEvent>();

		for(AbstractMap.SimpleImmutableEntry<Enchantment, ShieldEvent> entry : enchantmentEvents)
		{
			if(entry.getKey() == enchantment)
			{
				temp.add(entry.getValue());
			}
		}

		ShieldEvent[] result = new ShieldEvent[temp.size()];
		result = temp.toArray(result);

		return result;
	}

	public static ShieldEvent[] getEvents(ItemStack shield)
	{
		List<ShieldEvent> temp = new ArrayList<ShieldEvent>();

		for(AbstractMap.SimpleImmutableEntry<Item, ShieldEvent> entry : itemEvents)
		{
			if(entry.getKey() == shield.getItem())
			{
				temp.add(entry.getValue());
			}
		}

		for(AbstractMap.SimpleImmutableEntry<Enchantment, ShieldEvent> entry : enchantmentEvents)
		{
			if(hasEnchantment(entry.getKey(), shield))
			{
				temp.add(entry.getValue());
			}
		}

		ShieldEvent[] result = new ShieldEvent[temp.size()];
		result = temp.toArray(result);

		return result;
	}

	public static Item[] getItems(ShieldEvent event)
	{
		List<Item> temp = new ArrayList<Item>();

		for(AbstractMap.SimpleImmutableEntry<Item, ShieldEvent> entry : itemEvents)
		{
			if(entry.getValue() == event)
			{
				temp.add(entry.getKey());
			}
		}

		Item[] result = new Item[temp.size()];
		result = temp.toArray(result);

		return result;
	}

	public static Enchantment[] getEnchantments(ShieldEvent event)
	{
		List<Enchantment> temp = new ArrayList<Enchantment>();

		for(AbstractMap.SimpleImmutableEntry<Enchantment, ShieldEvent> entry : enchantmentEvents)
		{
			if(entry.getValue() == event)
			{
				temp.add(entry.getKey());
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

	public static void fireOnDisable(PlayerEntity defender, Hand hand, ItemStack shield, ShieldEvent...events)
	{
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

	public static void fireWhileHolding(LivingEntity defender, Hand hand, ItemStack shield, ShieldEvent...events)
	{
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

	public static boolean hasEnchantment(Enchantment enchantment, ItemStack stack)
	{
		return EnchantmentHelper.getLevel(enchantment, stack) > 0;
	}
}