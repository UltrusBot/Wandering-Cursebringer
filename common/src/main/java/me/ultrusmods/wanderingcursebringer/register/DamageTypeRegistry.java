package me.ultrusmods.wanderingcursebringer.register;

import me.ultrusmods.wanderingcursebringer.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class DamageTypeRegistry {
    public static final ResourceKey<DamageType> DARKNESS = ResourceKey.create(Registries.DAMAGE_TYPE, Constants.id("darkness"));
}
