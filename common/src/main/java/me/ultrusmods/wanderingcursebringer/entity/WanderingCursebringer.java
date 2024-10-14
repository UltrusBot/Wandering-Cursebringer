package me.ultrusmods.wanderingcursebringer.entity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;

public class WanderingCursebringer extends WanderingTrader {
    public WanderingCursebringer(EntityType<? extends WanderingTrader> entityType, Level level) {
        super(entityType, level);
    }


    @Override
    protected void updateTrades() {
        VillagerTrades.ItemListing[] commonTrades = WanderingCursebringerTrades.WANDERING_TRADER_TRADES.get(1);
        VillagerTrades.ItemListing[] rareTrades = WanderingCursebringerTrades.WANDERING_TRADER_TRADES.get(2);
        if (commonTrades != null && rareTrades != null) {
            MerchantOffers merchantoffers = this.getOffers();
            this.addOffersFromItemListings(merchantoffers, commonTrades, 3);
            int i = this.random.nextInt(rareTrades.length);
            VillagerTrades.ItemListing rareListing = rareTrades[i];
            MerchantOffer merchantoffer = rareListing.getOffer(this, this.random);
            if (merchantoffer != null) {
                merchantoffers.add(merchantoffer);
            }
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new UseItemGoal<>(this, PotionContents.createItemStack(Items.POTION, Potions.INVISIBILITY), SoundEvents.WANDERING_TRADER_DISAPPEARED, (cursebringer) -> this.level().isNight() && !cursebringer.isInvisible()));
        this.goalSelector.addGoal(0, new UseItemGoal<>(this, new ItemStack(Items.MILK_BUCKET), SoundEvents.WANDERING_TRADER_REAPPEARED, (cursebringer) -> this.level().isDay() && cursebringer.isInvisible()));
        this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Zombie.class, 8.0F, 0.5, 0.5));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Zoglin.class, 10.0F, 0.5, 0.5));
        this.goalSelector.addGoal(1, new PanicGoal(this, 0.5));
        this.goalSelector.addGoal(1, new LookAtTradingPlayerGoal(this));
        this.goalSelector.addGoal(2, new WanderToPositionGoal(this, 2.0, 0.35));
        this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 0.35));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.35));
        this.goalSelector.addGoal(9, new InteractGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }
}
