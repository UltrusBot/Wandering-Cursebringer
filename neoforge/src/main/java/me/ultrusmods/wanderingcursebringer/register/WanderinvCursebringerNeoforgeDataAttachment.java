package me.ultrusmods.wanderingcursebringer.register;

import com.mojang.serialization.Codec;
import me.ultrusmods.wanderingcursebringer.Constants;
import me.ultrusmods.wanderingcursebringer.curse.Curse;
import net.minecraft.core.Registry;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.HashMap;
import java.util.function.Function;

public class WanderinvCursebringerNeoforgeDataAttachment {

    private static final Codec<HashMap<Curse, Integer>> PLAYER_CURSES_MAP_CODEC = Codec.unboundedMap(
            WanderingCursebringerRegistries.CURSE_REGISTRY.byNameCodec(),
            Codec.INT
    ).xmap(HashMap::new, Function.identity());

    public static final AttachmentType<HashMap<Curse, Integer>> PLAYER_CURSES = AttachmentType.builder(() -> new HashMap<Curse, Integer>())
            .serialize(PLAYER_CURSES_MAP_CODEC)
            .copyOnDeath()
            .build();

    public static final AttachmentType<Integer> DARKNESS_TIMER = AttachmentType.builder(() -> 0)
            .serialize(Codec.INT)
            .build();


    public static void init() {
        register("player_curses", PLAYER_CURSES);
        register("darkness_timer", DARKNESS_TIMER);
    }

    private static AttachmentType<?> register(String id, AttachmentType<?> attachmentType) {
        return Registry.register(NeoForgeRegistries.ATTACHMENT_TYPES, Constants.id(id), attachmentType);
    }
}
