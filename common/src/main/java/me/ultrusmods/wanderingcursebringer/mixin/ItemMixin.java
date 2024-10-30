package me.ultrusmods.wanderingcursebringer.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.ultrusmods.wanderingcursebringer.curse.CurseManager;
import me.ultrusmods.wanderingcursebringer.register.CurseRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Item.class)
public class ItemMixin {

    @ModifyExpressionValue(
            method = "getUseDuration",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodProperties;eatDurationTicks()I")
    )
    private int changeFoodEatDuration(int original, ItemStack itemStack, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            if (CurseManager.hasCurse(player, CurseRegistry.GLUTTONY)) {
                return (int) (original / (1.5f + 0.25f * CurseManager.getCurseLevel(player, CurseRegistry.GLUTTONY)));
            }
        }
        return original;
    }

}
