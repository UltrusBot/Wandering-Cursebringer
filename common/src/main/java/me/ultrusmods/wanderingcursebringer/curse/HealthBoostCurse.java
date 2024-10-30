package me.ultrusmods.wanderingcursebringer.curse;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

/**
 *
 */
public class HealthBoostCurse extends AttributeCurse {
    private final double amountPerLevel;
    public HealthBoostCurse(CurseTier tier, int maxLevel, double amountPerLevel, ResourceLocation id) {
        super(tier, maxLevel);
        addAttribute(
                Attributes.MAX_HEALTH,
                amountPerLevel,
                AttributeModifier.Operation.ADD_VALUE,
                id
        );
        this.amountPerLevel = amountPerLevel;
    }

    @Override
    public void onCurseApplied(Player player, int level) {
        super.onCurseApplied(player, level);
        player.heal((float) (amountPerLevel * level));
    }
}
