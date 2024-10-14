package me.ultrusmods.wanderingcursebringer.register;

import me.ultrusmods.wanderingcursebringer.Constants;
import me.ultrusmods.wanderingcursebringer.entity.WanderingCursebringer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class EntityTypeRegistry {
    public static final EntityType<WanderingCursebringer> WANDERING_CURSEBRINGER = EntityType.Builder.of(WanderingCursebringer::new, MobCategory.CREATURE)
            .sized(0.6F, 1.95F)
            .eyeHeight(1.62F)
            .clientTrackingRange(10)
            .build("wandering_cursebringer");

    public static void init() {
        register("wandering_cursebringer", WANDERING_CURSEBRINGER);
    }


    private static EntityType<?> register(String id, EntityType<?> entityType) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, Constants.id(id), entityType);
    }
}
