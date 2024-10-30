package me.ultrusmods.wanderingcursebringer.platform;

import me.ultrusmods.wanderingcursebringer.curse.Curse;
import me.ultrusmods.wanderingcursebringer.network.s2c.SyncCursesPacketS2C;
import me.ultrusmods.wanderingcursebringer.network.s2c.SyncDarknessLevelS2C;
import me.ultrusmods.wanderingcursebringer.platform.services.IPlatformHelper;
import me.ultrusmods.wanderingcursebringer.register.WanderinvCursebringerNeoforgeDataAttachment;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Map;

public class WanderingCursebringerPlatformHelperNeoForge implements IPlatformHelper {

    @Override
    public String getPlatformName() {
            return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public Map<Curse, Integer> getPlayerCurses(Player player) {
        return player.getData(WanderinvCursebringerNeoforgeDataAttachment.PLAYER_CURSES);
    }

    @Override
    public void sendCurseSyncPacket(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(
                    serverPlayer,
                    new SyncCursesPacketS2C(getPlayerCurses(player))
            );
        }
    }

    @Override
    public int getDarknessTimer(Player player) {
        return player.getData(WanderinvCursebringerNeoforgeDataAttachment.DARKNESS_TIMER);
    }

    @Override
    public void setDarknessTimer(Player player, int timer) {
        player.setData(WanderinvCursebringerNeoforgeDataAttachment.DARKNESS_TIMER, timer);
        if (player instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(
                    serverPlayer,
                    new SyncDarknessLevelS2C(timer)
            );
        }
    }

    @Override
    public CreativeModeTab.Builder getCreativeTab() {
        return CreativeModeTab.builder();
    }
}