package me.ultrusmods.wanderingcursebringer.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.ultrusmods.wanderingcursebringer.curse.CurseManager;
import me.ultrusmods.wanderingcursebringer.register.CurseRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FoodData.class)
public class FoodDataMixin {

    @ModifyArg(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;addExhaustion(F)V")

    )
    public float modifyExhaustionAmountGluttony(float exhaustion, @Local(argsOnly = true) Player player) {
        if (CurseManager.hasCurse(player, CurseRegistry.GLUTTONY)) {
            exhaustion *= 1.25f + 0.25f * CurseManager.getCurseLevel(player, CurseRegistry.GLUTTONY);
        }
        return exhaustion;
    }


    @ModifyExpressionValue(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z")
    )
    public boolean disableNaturalRegenGlassHeart(boolean original, Player player) {
        return !CurseManager.hasCurse(player, CurseRegistry.GLASS_HEART) && original;
    }
}
