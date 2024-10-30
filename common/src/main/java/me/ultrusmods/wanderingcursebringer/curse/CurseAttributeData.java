package me.ultrusmods.wanderingcursebringer.curse;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public record CurseAttributeData(Holder<Attribute> attribute, double amountPerLevel, AttributeModifier.Operation operation, ResourceLocation attributeId) {
    public AttributeModifier createModifier(int level) {
        return new AttributeModifier(
                attributeId,
                amountPerLevel * level,
                operation
        );
    }
}
