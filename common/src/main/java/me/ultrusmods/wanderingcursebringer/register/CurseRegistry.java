package me.ultrusmods.wanderingcursebringer.register;

import me.ultrusmods.wanderingcursebringer.Constants;
import me.ultrusmods.wanderingcursebringer.curse.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.function.BiConsumer;

public class CurseRegistry {
    public static final Curse GLUTTONY = new Curse(CurseTier.LESSER, 3);
    public static final Curse GLASS_HEART = new HealthBoostCurse(CurseTier.GREATER, 1, 10d, Constants.id("glass_heart_health"));
    public static final Curse INSOMNIA = new TemporaryAttributeCurse(CurseTier.NORMAL, 1, (player -> player.level().isNight()))
            .addAttribute(
                    Attributes.MOVEMENT_SPEED,
                    0.04,
                    AttributeModifier.Operation.ADD_VALUE,
                    Constants.id("insomnia_speed")
            )
            .addAttribute(
                    Attributes.ATTACK_DAMAGE,
                    0.5,
                    AttributeModifier.Operation.ADD_VALUE,
                    Constants.id("insomnia_damage")
            );
    public static final Curse DARKNESS = new DarknessCurse(CurseTier.GREATER, 3);

    public static void init(BiConsumer<Curse, ResourceLocation> consumer) {
        consumer.accept(GLUTTONY, Constants.id("gluttony"));
        consumer.accept(GLASS_HEART, Constants.id("glass_heart"));
        consumer.accept(INSOMNIA, Constants.id("insomnia"));
        consumer.accept(DARKNESS, Constants.id("darkness"));
    }
}
