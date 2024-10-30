package me.ultrusmods.wanderingcursebringer.register;

import me.ultrusmods.wanderingcursebringer.Constants;
import me.ultrusmods.wanderingcursebringer.curse.Curse;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class WanderingCursebringerRegistries {
    public static final ResourceKey<Registry<Curse>> CURSE_REGISTRY_KEY = ResourceKey.createRegistryKey(Constants.id("curse"));
    public static Registry<Curse> CURSE_REGISTRY;
}
