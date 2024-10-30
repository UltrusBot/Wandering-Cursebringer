package me.ultrusmods.wanderingcursebringer.curse;

import me.ultrusmods.wanderingcursebringer.platform.Services;
import net.minecraft.world.entity.player.Player;

import java.util.Map;

public class CurseManager {

    public static void removeCurse(Player player, Curse curse) {
        Map<Curse, Integer> curses = Services.PLATFORM.getPlayerCurses(player);
        int level = curses.get(curse);
        curses.remove(curse);
        curse.onCurseRemoved(player, level);
        Services.PLATFORM.sendCurseSyncPacket(player);
    }

    public static void setCurse(Player player, Curse curse, int level) {
        Map<Curse, Integer> curses = Services.PLATFORM.getPlayerCurses(player);
        curses.remove(curse);
        curses.put(curse, level);
        curse.onCurseApplied(player, level);
        Services.PLATFORM.sendCurseSyncPacket(player);
    }

    public static void addPlayerCurse(Player player, Curse curse) {
        CurseManager.setCurse(player, curse, 1);
    }

    public static int getCurseLevel(Player player, Curse curse) {
        Map<Curse, Integer> curses = Services.PLATFORM.getPlayerCurses(player);
        return curses.getOrDefault(curse, 0);
    }

    public static void clearCurses(Player player) {
        Services.PLATFORM.getPlayerCurses(player).forEach((curse, level) -> curse.onCurseRemoved(player, level));
        Services.PLATFORM.getPlayerCurses(player).clear();
        Services.PLATFORM.sendCurseSyncPacket(player);
    }

    public static boolean hasCurse(Player player, Curse curse) {
        return getCurseLevel(player, curse) > 0;
    }
}
