package me.ultrusmods.wanderingcursebringer;

import com.chocohead.mm.api.ClassTinkerers;
import me.ultrusmods.wanderingcursebringer.command.CurseCommand;
import me.ultrusmods.wanderingcursebringer.curse.CurseManager;
import me.ultrusmods.wanderingcursebringer.entity.WanderingCursebringer;
import me.ultrusmods.wanderingcursebringer.network.s2c.SyncCursesPacketS2C;
import me.ultrusmods.wanderingcursebringer.network.s2c.SyncDarknessLevelS2C;
import me.ultrusmods.wanderingcursebringer.platform.Services;
import me.ultrusmods.wanderingcursebringer.register.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.player.Player;

public class WanderingCursebringerFabric implements ModInitializer {

    public static final Player.BedSleepingProblem INSOMNIA_CURSE = ClassTinkerers.getEnum(
            Player.BedSleepingProblem.class,
            "INSOMNIA_CURSE"
    );

    @Override
    public void onInitialize() {
        WanderingCursebringerMod.init();
        WanderingCursebringerRegistries.CURSE_REGISTRY = FabricRegistryBuilder.createDefaulted(
                WanderingCursebringerRegistries.CURSE_REGISTRY_KEY,
                Constants.id("gluttony")
        ).buildAndRegister();
        ItemRegistry.init();
        EntityTypeRegistry.init();
        CreativeTabRegistry.init();
        SoundEventRegistry.init();
        FabricDefaultAttributeRegistry.register(
            EntityTypeRegistry.WANDERING_CURSEBRINGER,
            WanderingCursebringer.createMobAttributes()
        );
        CurseRegistry.init((curse, id) -> Registry.register(WanderingCursebringerRegistries.CURSE_REGISTRY, id, curse));

        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> CurseCommand.register(dispatcher, registryAccess)));
        WanderingCursebringerFabricDataAttachment.init();

        // Do this to reapply curses after a player respawns
        ServerPlayerEvents.COPY_FROM.register(((oldPlayer, newPlayer, alive) -> Services.PLATFORM.getPlayerCurses(newPlayer).forEach((curse, level) -> curse.onCurseApplied(newPlayer, level))));

        

        EntitySleepEvents.ALLOW_SLEEPING.register((player, sleepingPos) -> {
           if (CurseManager.hasCurse(player, CurseRegistry.INSOMNIA)) {
               return INSOMNIA_CURSE;
           }
            return null;
        });




        PayloadTypeRegistry.playS2C().register(SyncCursesPacketS2C.TYPE, SyncCursesPacketS2C.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(SyncDarknessLevelS2C.TYPE, SyncDarknessLevelS2C.STREAM_CODEC);
    }
}
