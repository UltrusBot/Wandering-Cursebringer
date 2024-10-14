package me.ultrusmods.wanderingcursebringer.client;

import com.mojang.blaze3d.vertex.PoseStack;
import me.ultrusmods.wanderingcursebringer.Constants;
import me.ultrusmods.wanderingcursebringer.entity.WanderingCursebringer;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.resources.ResourceLocation;

public class WanderingCursebringerRenderer extends MobRenderer<WanderingCursebringer, VillagerModel<WanderingCursebringer>> {

    private static final ResourceLocation TEXTURE = Constants.id("textures/entity/wandering_cursebringer.png");


    public WanderingCursebringerRenderer(EntityRendererProvider.Context context) {
        super(context, new VillagerModel<>(context.bakeLayer(WanderingCursebringerModelLayers.WANDERING_CURSEBRINGER)), 0.5F);
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
        this.addLayer(new CrossedArmsItemLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(WanderingCursebringer wanderingCursebringer) {
        return TEXTURE;
    }

    @Override
    protected void scale(WanderingCursebringer livingEntity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(0.9375F, 0.9375F, 0.9375F);
    }
}
