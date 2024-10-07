package me.ultrusmods.wanderingcursebringer;

import net.fabricmc.api.ModInitializer;

public class WanderingCursebringerFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        Constants.LOG.info("Hello Fabric world!");
        WanderingCursebringerMod.init();
    }
}
