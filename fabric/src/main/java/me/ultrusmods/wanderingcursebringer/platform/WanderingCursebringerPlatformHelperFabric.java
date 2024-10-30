package me.ultrusmods.wanderingcursebringer.platform;

import me.ultrusmods.wanderingcursebringer.curse.Curse;
import me.ultrusmods.wanderingcursebringer.network.s2c.SyncCursesPacketS2C;
import me.ultrusmods.wanderingcursebringer.network.s2c.SyncDarknessLevelS2C;
import me.ultrusmods.wanderingcursebringer.platform.services.IPlatformHelper;
import me.ultrusmods.wanderingcursebringer.register.WanderingCursebringerFabricDataAttachment;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;

import java.util.Map;

public class WanderingCursebringerPlatformHelperFabric implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public Map<Curse, Integer> getPlayerCurses(Player player) {
        return player.getAttachedOrCreate(WanderingCursebringerFabricDataAttachment.PLAYER_CURSES);
    }

    @Override
    public void sendCurseSyncPacket(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            ServerPlayNetworking.send(
                    serverPlayer,
                    new SyncCursesPacketS2C(getPlayerCurses(player))
            );
        }
    }

    @Override
    public int getDarknessTimer(Player player) {
        return player.getAttachedOrCreate(WanderingCursebringerFabricDataAttachment.DARKNESS_TIMER);
    }

    @Override
    public void setDarknessTimer(Player player, int timer) {
        player.setAttached(WanderingCursebringerFabricDataAttachment.DARKNESS_TIMER, timer);
        if (player instanceof ServerPlayer serverPlayer) {
            ServerPlayNetworking.send(
                    serverPlayer,
                    new SyncDarknessLevelS2C(timer)
            );
        }
    }

    @Override
    public CreativeModeTab.Builder getCreativeTab() {
        return FabricItemGroup.builder();
    }

}
