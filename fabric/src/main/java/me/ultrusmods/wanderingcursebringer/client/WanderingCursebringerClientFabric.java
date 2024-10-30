package me.ultrusmods.wanderingcursebringer.client;

import me.ultrusmods.wanderingcursebringer.network.handler.PacketHandlers;
import me.ultrusmods.wanderingcursebringer.network.s2c.SyncCursesPacketS2C;
import me.ultrusmods.wanderingcursebringer.network.s2c.SyncDarknessLevelS2C;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class WanderingCursebringerClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WanderingCursebringerModelLayers.register(((modelLayerLocation, layerDefinitionSupplier) -> EntityModelLayerRegistry.registerModelLayer(modelLayerLocation, layerDefinitionSupplier::get)));
        WanderingCursebringerEntityRenderers.register(EntityRendererRegistry::register);

        ClientPlayNetworking.registerGlobalReceiver(SyncCursesPacketS2C.TYPE, (packet, context) -> PacketHandlers.handle(packet));
        ClientPlayNetworking.registerGlobalReceiver(SyncDarknessLevelS2C.TYPE, (packet, context) -> PacketHandlers.handle(packet));
    }
}
