package me.ultrusmods.wanderingcursebringer.client;

import me.ultrusmods.wanderingcursebringer.register.EntityTypeRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;

import java.util.function.BiConsumer;

public class WanderingCursebringerEntityRenderers {
    public static void  register(BiConsumer<EntityType<?>, EntityRendererProvider> consumer) {
        consumer.accept(EntityTypeRegistry.WANDERING_CURSEBRINGER, WanderingCursebringerRenderer::new);
    }
}
