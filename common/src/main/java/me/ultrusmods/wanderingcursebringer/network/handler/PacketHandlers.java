package me.ultrusmods.wanderingcursebringer.network.handler;

import me.ultrusmods.wanderingcursebringer.network.s2c.SyncCursesPacketS2C;
import me.ultrusmods.wanderingcursebringer.network.s2c.SyncDarknessLevelS2C;
import me.ultrusmods.wanderingcursebringer.platform.Services;
import net.minecraft.client.Minecraft;

public class PacketHandlers {
    public static void handle(SyncCursesPacketS2C packet) {
        Minecraft.getInstance().execute(() -> {
            var curses = Services.PLATFORM.getPlayerCurses(Minecraft.getInstance().player);
            curses.clear();
            curses.putAll(packet.curses());
        });
    }

    public static void handle(SyncDarknessLevelS2C packet) {
        Minecraft.getInstance().execute(() -> {
            Services.PLATFORM.setDarknessTimer(Minecraft.getInstance().player, packet.darknessLevel());
        });
    }
}
