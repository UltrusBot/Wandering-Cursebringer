package me.ultrusmods.wanderingcursebringer.register;

import me.ultrusmods.wanderingcursebringer.Constants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public class SoundEventRegistry {

    public static final SoundEvent CURSE_APPLIED = SoundEvent.createVariableRangeEvent(Constants.id("curse_applied"));

    public static void init() {
        register("curse_applied", CURSE_APPLIED);
    }



    private static SoundEvent register(String id, SoundEvent soundEvent) {
        return Registry.register(BuiltInRegistries.SOUND_EVENT, Constants.id(id), soundEvent);
    }
}
