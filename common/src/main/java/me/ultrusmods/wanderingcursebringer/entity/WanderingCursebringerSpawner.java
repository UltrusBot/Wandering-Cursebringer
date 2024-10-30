package me.ultrusmods.wanderingcursebringer.entity;

import me.ultrusmods.wanderingcursebringer.data.WanderingCursebringerSavedData;
import me.ultrusmods.wanderingcursebringer.register.EntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.animal.horse.TraderLlama;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.Iterator;
import java.util.Optional;

// Code yoinked from WanderingTraderSpawner since I wanted the spawning conditions to be the same, but needed to switch out ServerLevelData stuff to my own SavedData
public class WanderingCursebringerSpawner implements CustomSpawner {

    private static final int DEFAULT_TICK_DELAY = 10;
    public static final int DEFAULT_SPAWN_DELAY = 24000;
    private static final int MIN_SPAWN_CHANCE = 25;
    private static final int MAX_SPAWN_CHANCE = 75;
    private static final int SPAWN_CHANCE_INCREASE = 25;
    private static final int SPAWN_ONE_IN_X_CHANCE = 10;
    private static final int NUMBER_OF_SPAWN_ATTEMPTS = 10;
    private final RandomSource random = RandomSource.create();
    private final MinecraftServer level;
    private int tickDelay;
    private int spawnDelay;
    private int spawnChance;
    // Have to do this because saved data is not initialized when this is created
    private boolean initalized = false;

    public WanderingCursebringerSpawner(MinecraftServer serverLevelData) {
        this.level = serverLevelData;
        this.tickDelay = DEFAULT_TICK_DELAY;
        this.spawnDelay = DEFAULT_SPAWN_DELAY;
        this.spawnChance = MIN_SPAWN_CHANCE;

    }


    @Override
    public int tick(ServerLevel serverLevel, boolean b, boolean b1) {
        if (!initalized) {
            WanderingCursebringerSavedData wanderingCursebringerSavedData = WanderingCursebringerSavedData.get(serverLevel.getServer());
            this.spawnDelay = wanderingCursebringerSavedData.wanderingCursebringerSpawnDelay;
            this.spawnChance = wanderingCursebringerSavedData.wanderingCursebringerSpawnChance;
            if (this.spawnDelay == 0 && this.spawnChance == 0) {
                this.spawnDelay = DEFAULT_SPAWN_DELAY;
                wanderingCursebringerSavedData.wanderingCursebringerSpawnDelay = this.spawnDelay;

                this.spawnChance = MIN_SPAWN_CHANCE;
                wanderingCursebringerSavedData.wanderingCursebringerSpawnChance = this.spawnChance;
                wanderingCursebringerSavedData.setDirty();
            }
            initalized = true;
        }
        if (!level.getGameRules().getBoolean(GameRules.RULE_DO_TRADER_SPAWNING)) {
            return 0;
        } else if (--this.tickDelay > 0) {
            return 0;
        } else {
            this.tickDelay = DEFAULT_TICK_DELAY;
            this.spawnDelay -= DEFAULT_TICK_DELAY;
            WanderingCursebringerSavedData wanderingCursebringerSavedData = WanderingCursebringerSavedData.get(serverLevel.getServer());
            wanderingCursebringerSavedData.wanderingCursebringerSpawnDelay = this.spawnDelay;
            wanderingCursebringerSavedData.setDirty();
            if (this.spawnDelay > 0) {
                return 0;
            } else {
                this.spawnDelay = DEFAULT_SPAWN_DELAY;
                if (!level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
                    return 0;
                } else {
                    int i = this.spawnChance;
                    this.spawnChance = Mth.clamp(this.spawnChance + SPAWN_CHANCE_INCREASE, MIN_SPAWN_CHANCE, MAX_SPAWN_CHANCE);
                    wanderingCursebringerSavedData.wanderingCursebringerSpawnChance = this.spawnChance;
                    if (this.random.nextInt(100) > i) {
                        return 0;
                    } else if (this.spawn(serverLevel)) {
                        this.spawnChance = MIN_SPAWN_CHANCE;
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }
        }
    }

    private boolean spawn(ServerLevel serverLevel) {
        Player player = serverLevel.getRandomPlayer();
        if (player == null) {
            return true;
        } else if (this.random.nextInt(SPAWN_ONE_IN_X_CHANCE) != 0) {
            return false;
        } else {
            BlockPos blockpos = player.blockPosition();
            PoiManager poimanager = serverLevel.getPoiManager();
            Optional<BlockPos> optional = poimanager.find((p_219713_) -> p_219713_.is(PoiTypes.MEETING), (p_219711_) -> true, blockpos, 48, PoiManager.Occupancy.ANY);
            BlockPos blockpos1 = optional.orElse(blockpos);
            BlockPos blockpos2 = this.findSpawnPositionNear(serverLevel, blockpos1, 48);
            if (blockpos2 != null && this.hasEnoughSpace(serverLevel, blockpos2)) {
                if (serverLevel.getBiome(blockpos2).is(BiomeTags.WITHOUT_WANDERING_TRADER_SPAWNS)) {
                    return false;
                }

                WanderingCursebringer wanderingtrader = EntityTypeRegistry.WANDERING_CURSEBRINGER.spawn(serverLevel, blockpos2, MobSpawnType.EVENT);
                if (wanderingtrader != null) {
                    for(int j = 0; j < 2; ++j) {
                        this.tryToSpawnLlamaFor(serverLevel, wanderingtrader, 4);
                    }

                    wanderingtrader.setDespawnDelay(48000);
                    wanderingtrader.setWanderTarget(blockpos1);
                    wanderingtrader.restrictTo(blockpos1, 16);
                    return true;
                }
            }

            return false;
        }
    }



    private void tryToSpawnLlamaFor(ServerLevel serverLevel, WanderingTrader trader, int maxDistance) {
        BlockPos blockpos = this.findSpawnPositionNear(serverLevel, trader.blockPosition(), maxDistance);
        if (blockpos != null) {
            TraderLlama traderllama = EntityType.TRADER_LLAMA.spawn(serverLevel, blockpos, MobSpawnType.EVENT);
            if (traderllama != null) {
                traderllama.setLeashedTo(trader, true);
            }
        }

    }

    private BlockPos findSpawnPositionNear(LevelReader level, BlockPos pos, int maxDistance) {
        BlockPos blockpos = null;
        SpawnPlacementType spawnplacementtype = SpawnPlacements.getPlacementType(EntityType.WANDERING_TRADER);

        for(int i = 0; i < NUMBER_OF_SPAWN_ATTEMPTS; ++i) {
            int j = pos.getX() + this.random.nextInt(maxDistance * 2) - maxDistance;
            int k = pos.getZ() + this.random.nextInt(maxDistance * 2) - maxDistance;
            int l = level.getHeight(Heightmap.Types.WORLD_SURFACE, j, k);
            BlockPos blockpos1 = new BlockPos(j, l, k);
            if (spawnplacementtype.isSpawnPositionOk(level, blockpos1, EntityType.WANDERING_TRADER)) {
                blockpos = blockpos1;
                break;
            }
        }

        return blockpos;
    }

    private boolean hasEnoughSpace(BlockGetter level, BlockPos pos) {
        Iterator<BlockPos> var3 = BlockPos.betweenClosed(pos, pos.offset(1, 2, 1)).iterator();

        BlockPos blockpos;
        do {
            if (!var3.hasNext()) {
                return true;
            }

            blockpos = var3.next();
        } while (level.getBlockState(blockpos).getCollisionShape(level, blockpos).isEmpty());

        return false;
    }
}
