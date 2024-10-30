package me.ultrusmods.wanderingcursebringer;


import me.ultrusmods.wanderingcursebringer.entity.WanderingCursebringer;
import me.ultrusmods.wanderingcursebringer.entity.WanderingCursebringerSpawner;
import me.ultrusmods.wanderingcursebringer.network.handler.PacketHandlers;
import me.ultrusmods.wanderingcursebringer.network.s2c.SyncCursesPacketS2C;
import me.ultrusmods.wanderingcursebringer.network.s2c.SyncDarknessLevelS2C;
import me.ultrusmods.wanderingcursebringer.register.*;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.level.ModifyCustomSpawnersEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

@Mod(Constants.MOD_ID)
public class WanderingCursebringerNeoForge {

    public WanderingCursebringerNeoForge(IEventBus eventBus) {
        WanderingCursebringerMod.init();
        eventBus.addListener(this::onRegisterEvent);
        eventBus.addListener(this::onEntityAttributeCreationEvent);
        eventBus.addListener(this::registerCreateEvent);
        eventBus.addListener(WanderingCursebringerNeoForge::registerPacketPayloads);
    }

    private void onRegisterEvent(RegisterEvent event) {
        if (event.getRegistryKey() == Registries.ITEM) {
            ItemRegistry.init();
        }
        if (event.getRegistryKey() == Registries.ENTITY_TYPE) {
            EntityTypeRegistry.init();
        }
        if (event.getRegistryKey() == WanderingCursebringerRegistries.CURSE_REGISTRY_KEY) {
            CurseRegistry.init((curse, id) -> event.register(WanderingCursebringerRegistries.CURSE_REGISTRY_KEY, id, () -> curse));
        }
        if (event.getRegistryKey() == NeoForgeRegistries.Keys.ATTACHMENT_TYPES) {
            WanderinvCursebringerNeoforgeDataAttachment.init();
        }
        if (event.getRegistryKey() == Registries.CREATIVE_MODE_TAB) {
            CreativeTabRegistry.init();
        }
        if (event.getRegistryKey() == Registries.SOUND_EVENT) {
            SoundEventRegistry.init();
        }
    }

    public static void registerPacketPayloads(RegisterPayloadHandlersEvent event) {
        event.registrar(Constants.MOD_ID)
                .versioned("1.0.0")
                .playToClient(SyncCursesPacketS2C.TYPE, SyncCursesPacketS2C.STREAM_CODEC, (payload, context) -> context.enqueueWork(() -> PacketHandlers.handle(payload)))
                .playToClient(SyncDarknessLevelS2C.TYPE, SyncDarknessLevelS2C.STREAM_CODEC, (payload, context) -> context.enqueueWork(() -> PacketHandlers.handle(payload)));

    }

    private void registerCreateEvent(NewRegistryEvent event) {
        WanderingCursebringerRegistries.CURSE_REGISTRY = event.create(new RegistryBuilder<>(WanderingCursebringerRegistries.CURSE_REGISTRY_KEY));
    }

    private void onEntityAttributeCreationEvent(EntityAttributeCreationEvent event) {
         event.put(EntityTypeRegistry.WANDERING_CURSEBRINGER, WanderingCursebringer.createMobAttributes().build());
    }
}