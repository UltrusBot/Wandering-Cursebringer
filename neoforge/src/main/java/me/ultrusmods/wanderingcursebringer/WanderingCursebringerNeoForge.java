package me.ultrusmods.wanderingcursebringer;


import me.ultrusmods.wanderingcursebringer.entity.WanderingCursebringer;
import me.ultrusmods.wanderingcursebringer.register.EntityTypeRegistry;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
public class WanderingCursebringerNeoForge {

    public WanderingCursebringerNeoForge(IEventBus eventBus) {
        WanderingCursebringerMod.init();
        eventBus.addListener(this::onRegisterEvent);
        eventBus.addListener(this::onEntityAttributeCreationEvent);
    }

    private void onRegisterEvent(RegisterEvent event) {
        if (event.getRegistryKey() == Registries.ENTITY_TYPE) {
            EntityTypeRegistry.init();
        }
    }

    private void onEntityAttributeCreationEvent(EntityAttributeCreationEvent event) {
         event.put(EntityTypeRegistry.WANDERING_CURSEBRINGER, WanderingCursebringer.createMobAttributes().build());
    }
}