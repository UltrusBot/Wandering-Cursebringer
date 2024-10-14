package me.ultrusmods.wanderingcursebringer.client;

import me.ultrusmods.wanderingcursebringer.Constants;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class WanderingCursebringerModelLayers {
    public static final ModelLayerLocation WANDERING_CURSEBRINGER = new ModelLayerLocation(Constants.id("wandering_cursebringer"), "main");



    public static void register(BiConsumer<ModelLayerLocation, Supplier<LayerDefinition>> consumer) {
        consumer.accept(WANDERING_CURSEBRINGER, () -> LayerDefinition.create(VillagerModel.createBodyModel(), 64, 64));
    }




}
