package me.ultrusmods.wanderingcursebringer.register;

import me.ultrusmods.wanderingcursebringer.Constants;
import me.ultrusmods.wanderingcursebringer.platform.Services;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class CreativeTabRegistry {
    public static final CreativeModeTab CURSEBRINGER_ITEMS = Services.PLATFORM.getCreativeTab()
            .title(Component.translatable("itemGroup.wanderingcursebringer.items"))
            .icon(ItemRegistry.GLASS_HEART_SCROLL::getDefaultInstance)
            .displayItems(((itemDisplayParameters, output) -> {
                output.accept(ItemRegistry.WANDERING_CURSEBRINGER_SPAWN_EGG);
                output.accept(ItemRegistry.GLASS_HEART_SCROLL);
                output.accept(ItemRegistry.GLUTTONY_SCROLL);
                output.accept(ItemRegistry.INSOMNIA_SCROLL);
                output.accept(ItemRegistry.DARKNESS_SCROLL);
            }))
            .build();

    public static void init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, Constants.id("items"), CURSEBRINGER_ITEMS);
    }
}
