package me.ultrusmods.wanderingcursebringer.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class WanderingCursebringerClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WanderingCursebringerModelLayers.register(((modelLayerLocation, layerDefinitionSupplier) -> EntityModelLayerRegistry.registerModelLayer(modelLayerLocation, layerDefinitionSupplier::get)));
        WanderingCursebringerEntityRenderers.register(EntityRendererRegistry::register);

    }
}
