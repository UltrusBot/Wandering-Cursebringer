package me.ultrusmods.wanderingcursebringer.network.s2c;

import me.ultrusmods.wanderingcursebringer.Constants;
import me.ultrusmods.wanderingcursebringer.curse.Curse;
import me.ultrusmods.wanderingcursebringer.register.WanderingCursebringerRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.HashMap;
import java.util.Map;

public record SyncCursesPacketS2C(Map<Curse, Integer> curses) implements CustomPacketPayload {
    public static final Type<SyncCursesPacketS2C> TYPE = new Type<>(Constants.id("sync_curses"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncCursesPacketS2C> STREAM_CODEC = StreamCodec.of(SyncCursesPacketS2C::write, SyncCursesPacketS2C::new);


    public SyncCursesPacketS2C(FriendlyByteBuf buf) {
        this(getCurses(buf));
    }

    public static Map<Curse, Integer> getCurses(FriendlyByteBuf buf) {
        int size = buf.readVarInt();
        Map<Curse, Integer> curses = new HashMap<>();
        for (int i = 0; i < size; i++) {
            Curse curse = WanderingCursebringerRegistries.CURSE_REGISTRY.get(buf.readResourceLocation());
            int level = buf.readVarInt();
            curses.put(curse, level);
        }
        return curses;
    }

    public static void write(FriendlyByteBuf buf, SyncCursesPacketS2C packet) {
        buf.writeVarInt(packet.curses.size());
        packet.curses.forEach((curse, level) -> {
            buf.writeResourceLocation(WanderingCursebringerRegistries.CURSE_REGISTRY.getKey(curse));
            buf.writeVarInt(level);
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
