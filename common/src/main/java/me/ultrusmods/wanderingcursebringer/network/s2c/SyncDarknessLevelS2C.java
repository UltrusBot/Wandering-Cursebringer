package me.ultrusmods.wanderingcursebringer.network.s2c;

import me.ultrusmods.wanderingcursebringer.Constants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record SyncDarknessLevelS2C(int darknessLevel) implements CustomPacketPayload {
    public static final Type<SyncDarknessLevelS2C> TYPE = new Type<>(Constants.id("sync_darkness"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncDarknessLevelS2C> STREAM_CODEC = StreamCodec.of(SyncDarknessLevelS2C::write, SyncDarknessLevelS2C::new);


    public SyncDarknessLevelS2C(FriendlyByteBuf buf) {
        this(buf.readInt());
    }


    public static void write(FriendlyByteBuf buf, SyncDarknessLevelS2C packet) {
        buf.writeInt(packet.darknessLevel);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
