package me.ultrusmods.wanderingcursebringer.data;

import com.mojang.serialization.Codec;
import me.ultrusmods.wanderingcursebringer.register.DamageTypeRegistry;
import me.ultrusmods.wanderingcursebringer.register.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.impl.biome.modification.BuiltInRegistryKeys;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class WanderingCursebringerDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(WanderingCursebringerModelProvider::new);
        pack.addProvider(WanderingCursebringerDamageTypeProvider::new);
        pack.addProvider(WanderingCursebringerDamageTypeTagProvider::new);
    }

    public static class WanderingCursebringerModelProvider extends FabricModelProvider {
        protected static final ModelTemplate EGG_TEMPLATE = new ModelTemplate(
                Optional.of(ResourceLocation.withDefaultNamespace("item/template_spawn_egg")),
                Optional.empty()
        );
        public WanderingCursebringerModelProvider(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {

        }

        @Override
        public void generateItemModels(ItemModelGenerators itemModelGenerator) {

            itemModelGenerator.generateFlatItem(ItemRegistry.GLUTTONY_SCROLL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(ItemRegistry.GLASS_HEART_SCROLL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(ItemRegistry.INSOMNIA_SCROLL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(ItemRegistry.DARKNESS_SCROLL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(ItemRegistry.WANDERING_CURSEBRINGER_SPAWN_EGG, EGG_TEMPLATE);


        }
    }

    public static class WanderingCursebringerDamageTypeProvider extends FabricCodecDataProvider<DamageType> {

        protected WanderingCursebringerDamageTypeProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(dataOutput, registriesFuture, Registries.DAMAGE_TYPE, DamageType.DIRECT_CODEC);
        }

        @Override
        protected void configure(BiConsumer<ResourceLocation, DamageType> provider, HolderLookup.Provider lookup) {
            provider.accept(
                    DamageTypeRegistry.DARKNESS.location(),
                    new DamageType("wanderingcursebringer.darkness", 0.5f)
            );
        }

        @Override
        public String getName() {
            return "damage_type";
        }
    }

    public static class WanderingCursebringerDamageTypeTagProvider extends FabricTagProvider<DamageType> {


        public WanderingCursebringerDamageTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, Registries.DAMAGE_TYPE, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR)
                    .addOptional(DamageTypeRegistry.DARKNESS);
            this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_RESISTANCE)
                    .addOptional(DamageTypeRegistry.DARKNESS);
            this.getOrCreateTagBuilder(DamageTypeTags.NO_KNOCKBACK)
                    .addOptional(DamageTypeRegistry.DARKNESS);
        }
    }
}
