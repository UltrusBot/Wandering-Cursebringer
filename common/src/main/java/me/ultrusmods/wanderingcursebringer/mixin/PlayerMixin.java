package me.ultrusmods.wanderingcursebringer.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.ultrusmods.wanderingcursebringer.curse.CurseManager;
import me.ultrusmods.wanderingcursebringer.platform.Services;
import me.ultrusmods.wanderingcursebringer.register.CurseRegistry;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {

    @ModifyArg(
            method = "causeFoodExhaustion",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;addExhaustion(F)V")

    )
    public float modifyExhaustionAmountGluttony(float exhaustion) {
        var player = (Player) (Object) this;
        if (CurseManager.hasCurse(player, CurseRegistry.GLUTTONY)) {
            exhaustion *= 2f + 0.5f * CurseManager.getCurseLevel(player, CurseRegistry.GLUTTONY);
        }
        return exhaustion;
    }


    @ModifyExpressionValue(
            method = "aiStep",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z")
    )
    public boolean disableNaturalRegenGlassHeart(boolean original) {
        var player = (Player) (Object) this;
        return !CurseManager.hasCurse(player, CurseRegistry.GLASS_HEART) && original;
    }


    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    public void tickPlayerCurses(CallbackInfo ci) {
        var player = (Player) (Object) this;
        Services.PLATFORM.getPlayerCurses(player).forEach((curse, level) -> curse.tick(player, level));
    }
}
