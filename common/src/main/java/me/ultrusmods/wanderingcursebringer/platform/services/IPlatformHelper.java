package me.ultrusmods.wanderingcursebringer.platform.services;

import me.ultrusmods.wanderingcursebringer.curse.Curse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;

import java.util.Map;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }

    Map<Curse, Integer> getPlayerCurses(Player player);
    void sendCurseSyncPacket(Player player);

    int getDarknessTimer(Player player);
    void setDarknessTimer(Player player, int timer);

    CreativeModeTab.Builder getCreativeTab();
}