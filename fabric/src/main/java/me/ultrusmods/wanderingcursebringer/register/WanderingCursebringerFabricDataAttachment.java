package me.ultrusmods.wanderingcursebringer.register;

import com.mojang.serialization.Codec;
import me.ultrusmods.wanderingcursebringer.Constants;
import me.ultrusmods.wanderingcursebringer.curse.Curse;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

import java.util.HashMap;
import java.util.function.Function;

public class WanderingCursebringerFabricDataAttachment {

    private static final Codec<HashMap<Curse, Integer>> PLAYER_CURSES_MAP_CODEC = Codec.unboundedMap(
            WanderingCursebringerRegistries.CURSE_REGISTRY.byNameCodec(),
            Codec.INT
    ).xmap(HashMap::new, Function.identity());


    @SuppressWarnings("UnstableApiUsage")
    public static final AttachmentType<HashMap<Curse, Integer>> PLAYER_CURSES = AttachmentRegistry.<HashMap<Curse, Integer>>builder()
            .persistent(PLAYER_CURSES_MAP_CODEC)
            .initializer(HashMap::new)
            .copyOnDeath()
            .buildAndRegister(Constants.id("player_curses"));

    @SuppressWarnings("UnstableApiUsage")
    public static final AttachmentType<Integer> DARKNESS_TIMER = AttachmentRegistry.<Integer>builder()
            .persistent(Codec.INT)
            .initializer(() -> 0)
            .buildAndRegister(Constants.id("darkness_timer"));

    public static void init() {

    }
}
