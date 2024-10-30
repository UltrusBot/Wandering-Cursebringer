package me.ultrusmods.wanderingcursebringer.curse;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.ultrusmods.wanderingcursebringer.register.WanderingCursebringerRegistries;

public record CurseInstance(Curse curse, int level) {
    public static Codec<CurseInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            WanderingCursebringerRegistries.CURSE_REGISTRY.byNameCodec().fieldOf("curse").forGetter(CurseInstance::curse),
            Codec.INT.fieldOf("level").forGetter(CurseInstance::level)
    ).apply(instance, CurseInstance::new));
}
