package me.ultrusmods.wanderingcursebringer.data;

import me.ultrusmods.wanderingcursebringer.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class WanderingCursebringerSavedData extends SavedData {

    public int wanderingCursebringerSpawnDelay = 0;
    public int wanderingCursebringerSpawnChance = 0;


    public static final SavedData.Factory<WanderingCursebringerSavedData> FACTORY = new SavedData.Factory<>(
            WanderingCursebringerSavedData::new,
            WanderingCursebringerSavedData::fromNbt,
            null
    );

    @Override
    public CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider provider) {
        compoundTag.putInt("wanderingCursebringerSpawnDelay", wanderingCursebringerSpawnDelay);
        compoundTag.putInt("wanderingCursebringerSpawnChance", wanderingCursebringerSpawnChance);
        return compoundTag;
    }


    public static WanderingCursebringerSavedData fromNbt(CompoundTag compoundTag, HolderLookup.Provider provider) {
        WanderingCursebringerSavedData wanderingCursebringerSavedData = new WanderingCursebringerSavedData();
        wanderingCursebringerSavedData.wanderingCursebringerSpawnDelay = compoundTag.getInt("wanderingCursebringerSpawnDelay");
        wanderingCursebringerSavedData.wanderingCursebringerSpawnChance = compoundTag.getInt("wanderingCursebringerSpawnChance");
        return wanderingCursebringerSavedData;
    }


    public static WanderingCursebringerSavedData get(MinecraftServer server) {
        DimensionDataStorage data = server.getLevel(ServerLevel.OVERWORLD).getDataStorage();
        return data.computeIfAbsent(FACTORY, Constants.MOD_ID + "_wandering_cursebringer");

    }


}
