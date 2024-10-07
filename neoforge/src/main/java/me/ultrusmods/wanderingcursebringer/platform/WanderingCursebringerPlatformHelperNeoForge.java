package me.ultrusmods.wanderingcursebringer.platform;

import me.ultrusmods.wanderingcursebringer.platform.services.IPlatformHelper;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

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
}