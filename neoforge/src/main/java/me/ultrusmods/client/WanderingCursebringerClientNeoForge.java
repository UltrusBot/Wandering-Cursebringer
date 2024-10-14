package me.ultrusmods.client;

import me.ultrusmods.wanderingcursebringer.Constants;
import me.ultrusmods.wanderingcursebringer.client.WanderingCursebringerEntityRenderers;
import me.ultrusmods.wanderingcursebringer.client.WanderingCursebringerModelLayers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class WanderingCursebringerClientNeoForge {

    @SubscribeEvent
    public static void registerEntityLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        WanderingCursebringerModelLayers.register(event::registerLayerDefinition);

    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        WanderingCursebringerEntityRenderers.register(event::registerEntityRenderer);
    }
}
