package me.ultrusmods.wanderingcursebringer.entity;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import me.ultrusmods.wanderingcursebringer.register.ItemRegistry;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;

public class WanderingCursebringerTrades {
    public static final Int2ObjectMap<VillagerTrades.ItemListing[]> WANDERING_TRADER_TRADES = new Int2ObjectOpenHashMap<>(
            ImmutableMap.of(
                    1,
                    new VillagerTrades.ItemListing[]{
                            new VillagerTrades.EnchantBookForEmeralds(2, EnchantmentTags.CURSE),
                            new VillagerTrades.ItemsForEmeralds(ItemRegistry.GLUTTONY_SCROLL, 10, 1, 1, 5),
                            new VillagerTrades.ItemsForEmeralds(ItemRegistry.INSOMNIA_SCROLL, 10, 1, 1, 5),
                    },
                    2,
                    new VillagerTrades.ItemListing[]{
                            new VillagerTrades.ItemsForEmeralds(ItemRegistry.DARKNESS_SCROLL, 15, 1, 1, 5),
                            new VillagerTrades.ItemsForEmeralds(ItemRegistry.GLASS_HEART_SCROLL, 15, 1, 1, 5),
                            new VillagerTrades.ItemsForEmeralds(Items.OMINOUS_BOTTLE, 20, 1, 1, 5),
                    }
            )
    );

}
