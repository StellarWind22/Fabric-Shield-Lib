package me.crimsondawn45.fabricshieldlib.util;

import me.crimsondawn45.fabricshieldlib.object.FabricBannerShield;
import me.crimsondawn45.fabricshieldlib.render.FabricBannerShieldRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.loader.api.FabricLoader;

public class FabricShieldLibRenderHelper
{
	public static void registerRender(FabricBannerShield shield)
	{
		if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
		{
			BuiltinItemRendererRegistry.INSTANCE.register(shield, new FabricBannerShieldRenderer(shield.getModId(), shield.getItemName()));
		}
	}
}
