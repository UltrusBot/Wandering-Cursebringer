package me.ultrusmods.wanderingcursebringer;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.network.chat.Component;

public class WanderingCursebringerEarlyRiser implements Runnable {
    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();
        String bedSleepingReason = remapper.mapClassName("intermediary", "net.minecraft.class_1657$class_1658");
        String component = "L" + remapper.mapClassName("intermediary", "net.minecraft.class_2561") + ";";
        ClassTinkerers.enumBuilder(
                bedSleepingReason,
                component
        ).addEnum(
                "INSOMNIA_CURSE",
                () -> new Object[]{Component.translatable("sleep_reason.wanderingcursebringer.insomnia_curse")})
                .build();

    }
}
