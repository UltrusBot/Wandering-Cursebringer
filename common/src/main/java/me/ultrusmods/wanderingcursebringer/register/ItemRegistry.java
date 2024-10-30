package me.ultrusmods.wanderingcursebringer.register;

import me.ultrusmods.wanderingcursebringer.Constants;
import me.ultrusmods.wanderingcursebringer.curse.Curse;
import me.ultrusmods.wanderingcursebringer.item.CurseScrollItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;

public class ItemRegistry {

    public static final Item WANDERING_CURSEBRINGER_SPAWN_EGG = createSpawnEggItem(
            EntityTypeRegistry.WANDERING_CURSEBRINGER,
            0x00052E,
            0x8A210C
    );
    public static final Item GLUTTONY_SCROLL = createScrollItem(CurseRegistry.GLUTTONY);
    public static final Item GLASS_HEART_SCROLL = createScrollItem(CurseRegistry.GLASS_HEART);
    public static final Item INSOMNIA_SCROLL = createScrollItem(CurseRegistry.INSOMNIA);
    public static final Item DARKNESS_SCROLL = createScrollItem(CurseRegistry.DARKNESS);

    public static void init() {
        register("gluttony_scroll", GLUTTONY_SCROLL);
        register("glass_heart_scroll", GLASS_HEART_SCROLL);
        register("insomnia_scroll", INSOMNIA_SCROLL);
        register("darkness_scroll", DARKNESS_SCROLL);
        register("wandering_cursebringer_spawn_egg", WANDERING_CURSEBRINGER_SPAWN_EGG);

    }

    public static Item createScrollItem(Curse curse) {
        return new CurseScrollItem(
                new Item.Properties().stacksTo(1).rarity(Rarity.RARE),
                curse
        );
    }

    public static Item createSpawnEggItem(EntityType<? extends Mob> entityType, int backgroundColor, int highlightColor) {
        return new SpawnEggItem(entityType, backgroundColor, highlightColor, new Item.Properties());
    }


    private static Item register(String id, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, Constants.id(id), item);
    }
}
