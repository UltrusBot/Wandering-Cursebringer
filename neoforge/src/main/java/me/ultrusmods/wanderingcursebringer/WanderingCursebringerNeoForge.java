package me.ultrusmods.wanderingcursebringer;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class WanderingCursebringerNeoForge {

    public WanderingCursebringerNeoForge(IEventBus eventBus) {
        WanderingCursebringerMod.init();
    }
}