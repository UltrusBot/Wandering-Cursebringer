package me.ultrusmods.wanderingcursebringer.mixin.client;

import me.ultrusmods.wanderingcursebringer.Constants;
import me.ultrusmods.wanderingcursebringer.platform.Services;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Unique
    private static final ResourceLocation DARKNESS_OVERLAY_1 = Constants.id("textures/misc/darkness_overlay_1.png");
    @Unique
    private static final ResourceLocation DARKNESS_OVERLAY_2 = Constants.id("textures/misc/darkness_overlay_2.png");
    @Unique
    private static final ResourceLocation DARKNESS_OVERLAY_3 = Constants.id("textures/misc/darkness_overlay_3.png");


    @Shadow @Final private Minecraft minecraft;

    @Shadow protected abstract void renderTextureOverlay(GuiGraphics guiGraphics, ResourceLocation shaderLocation, float alpha);

    @Inject(
            method = "renderCameraOverlays",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/DeltaTracker;getGameTimeDeltaPartialTick(Z)F")
    )
    private void renderCameraOverlaysInject(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (Services.PLATFORM.getDarknessTimer(this.minecraft.player) > 0) {
            int darknessTimer = Services.PLATFORM.getDarknessTimer(this.minecraft.player);
            if (darknessTimer >= 800) {
                this.renderTextureOverlay(guiGraphics, DARKNESS_OVERLAY_2, 1.0F);
                float darknessAlpha = Math.min(1.0F, (darknessTimer - 800) / 200.0F);
                this.renderTextureOverlay(guiGraphics, DARKNESS_OVERLAY_3, darknessAlpha);
            } else if (darknessTimer >= 400) {
                this.renderTextureOverlay(guiGraphics, DARKNESS_OVERLAY_1, 1.0F);
                float darknessAlpha = Math.min(1.0F, (darknessTimer - 400) / 200.0F);
                this.renderTextureOverlay(guiGraphics, DARKNESS_OVERLAY_2, darknessAlpha);
            } else {
                float darknessAlpha = Math.min(1.0F, darknessTimer / 200.0F);
                this.renderTextureOverlay(guiGraphics, DARKNESS_OVERLAY_1, darknessAlpha);
            }

        }
    }
}
