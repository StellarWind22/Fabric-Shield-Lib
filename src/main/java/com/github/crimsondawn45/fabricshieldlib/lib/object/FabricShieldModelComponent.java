package com.github.crimsondawn45.fabricshieldlib.lib.object;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public record FabricShieldModelComponent(Identifier baseModel, Identifier baseModelNoPat, String layer) {

    public static final Codec<FabricShieldModelComponent> CODEC = RecordCodecBuilder.<FabricShieldModelComponent>create(builder -> {
        return builder.group(
                Identifier.CODEC.fieldOf("baseModel").forGetter(FabricShieldModelComponent::baseModel),
                Identifier.CODEC.fieldOf("baseModelNoPat").forGetter(FabricShieldModelComponent::baseModelNoPat),
                Codec.STRING.fieldOf("EML").forGetter(FabricShieldModelComponent::layer)
                ).apply(builder, FabricShieldModelComponent::new);
    });

}
