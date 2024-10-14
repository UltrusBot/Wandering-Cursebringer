package me.ultrusmods.wanderingcursebringer;

import me.ultrusmods.wanderingcursebringer.entity.WanderingCursebringer;
import me.ultrusmods.wanderingcursebringer.register.EntityTypeRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class WanderingCursebringerFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        WanderingCursebringerMod.init();
        EntityTypeRegistry.init();
        FabricDefaultAttributeRegistry.register(
            EntityTypeRegistry.WANDERING_CURSEBRINGER,
            WanderingCursebringer.createMobAttributes()
        );
    }
}
