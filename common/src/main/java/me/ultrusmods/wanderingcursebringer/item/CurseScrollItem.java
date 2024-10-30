package me.ultrusmods.wanderingcursebringer.item;

import me.ultrusmods.wanderingcursebringer.curse.Curse;
import me.ultrusmods.wanderingcursebringer.curse.CurseManager;
import me.ultrusmods.wanderingcursebringer.register.SoundEventRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurseScrollItem extends Item {

    public static final Map<Curse, CurseScrollItem> CURSE_SCROLLS = new HashMap<>();
    private final Curse curse;

    public CurseScrollItem(Properties properties, Curse curse) {
        super(properties);
        this.curse = curse;
        CURSE_SCROLLS.put(curse, this);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable(curse.getTier().getTranslationKey()).withStyle(curse.getTier().getColor()));
        tooltipComponents.add(Component.translatable(curse.getLoreDescriptionId()).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (CurseManager.hasCurse(player, curse)) {
            if (CurseManager.getCurseLevel(player, curse) < curse.getMaxLevel()) {
                player.startUsingItem(hand);
                return InteractionResultHolder.consume(player.getItemInHand(hand));
            } else {
                return InteractionResultHolder.fail(player.getItemInHand(hand));
            }
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (livingEntity instanceof Player player) {
            if (level.isClientSide()) {
                level.addParticle(ParticleTypes.LARGE_SMOKE,
                        player.getX() + (level.random.nextDouble() - 0.5D) * 0.5D,
                        player.getY(),
                        player.getZ() + (level.random.nextDouble() - 0.5D) * 0.5D,
                        0.0D,
                        0.05D,
                        0.0D);
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 60;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        livingEntity.getMainHandItem().shrink(1);
        if (livingEntity instanceof Player player && !level.isClientSide()) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEventRegistry.CURSE_APPLIED, SoundSource.PLAYERS);

            for (int i = 0; i < 30; i++) {
                level.addParticle(ParticleTypes.LARGE_SMOKE,
                        player.getX() + (level.random.nextDouble() - 0.5D) * 0.5D,
                        player.getY(),
                        player.getZ() + (level.random.nextDouble() - 0.5D) * 0.5D,
                        (level.random.nextDouble() - 0.5D) * 3.0D,
                        (level.random.nextDouble() - 0.5D) * 3.0D,
                        (level.random.nextDouble() - 0.5D) * 3.0D);
            }
            if (CurseManager.hasCurse(player, curse)) {

                if (CurseManager.getCurseLevel(player, curse) < curse.getMaxLevel()) {
                    CurseManager.setCurse(player, curse, CurseManager.getCurseLevel(player, curse) + 1);


                }
            } else {
                CurseManager.addPlayerCurse(player, curse);
            }
        }

        return super.finishUsingItem(stack, level, livingEntity);
    }
}
