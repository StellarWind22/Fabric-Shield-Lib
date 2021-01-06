package me.crimsondawn45.fabricshieldlib.dev;

import me.crimsondawn45.fabricshieldlib.util.event.ShieldEvent;

public class TestShieldEvent extends ShieldEvent {

	public TestShieldEvent(boolean usesOnBlockDamage, boolean usesOnDisable, boolean usesWhileHolding)
	{
		super(usesOnBlockDamage, usesOnDisable, usesWhileHolding);
	}
}
