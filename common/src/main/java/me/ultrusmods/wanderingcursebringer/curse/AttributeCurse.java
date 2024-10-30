package me.ultrusmods.wanderingcursebringer.curse;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class AttributeCurse extends Curse {
    public final List<CurseAttributeData> attributes = new ArrayList<>();
    private final Function<Player, Boolean> shouldApply;

    public AttributeCurse(CurseTier tier, int maxLevel) {
        super(tier, maxLevel);
        this.shouldApply = (player) -> true;
    }

    public AttributeCurse(CurseTier tier, int maxLevel, Function<Player, Boolean> shouldApply) {
        super(tier, maxLevel);
        this.shouldApply = shouldApply;
    }


    public AttributeCurse addAttribute(Holder<Attribute> attribute, double amountPerLevel, AttributeModifier.Operation operation, ResourceLocation attributeId) {
        attributes.add(new CurseAttributeData(attribute, amountPerLevel, operation, attributeId));
        return this;
    }

    @Override
    public void onCurseApplied(Player player, int level) {
        if (!shouldApply.apply(player)) {
            return;
        }
        for (CurseAttributeData attributeData : attributes) {
            if (player.getAttribute(attributeData.attribute()) == null) {
                continue;
            }
            player.getAttribute(attributeData.attribute()).addPermanentModifier(attributeData.createModifier(level));
        }
    }

    @Override
    public void onCurseRemoved(Player player, int level) {
        for (CurseAttributeData attributeData : attributes) {
            if (player.getAttribute(attributeData.attribute()) == null) {
                continue;
            }
            player.getAttribute(attributeData.attribute()).removeModifier(attributeData.attributeId());
        }
    }
}
