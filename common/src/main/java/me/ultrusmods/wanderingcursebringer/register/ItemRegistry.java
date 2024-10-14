package me.ultrusmods.wanderingcursebringer.register;

import me.ultrusmods.wanderingcursebringer.Constants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public class ItemRegistry {

    public static void init() {

    }


    private static Item register(String id, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, Constants.id(id), item);
    }
}
