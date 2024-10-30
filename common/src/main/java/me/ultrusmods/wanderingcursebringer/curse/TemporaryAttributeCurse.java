package me.ultrusmods.wanderingcursebringer.curse;

import net.minecraft.world.entity.player.Player;

import java.util.function.Function;

public class TemporaryAttributeCurse extends AttributeCurse {
    private final Function<Player, Boolean> shouldApply;

    public TemporaryAttributeCurse(CurseTier tier, int maxLevel, Function<Player, Boolean> shouldApply) {
        super(tier, maxLevel);
        this.shouldApply = shouldApply;
    }

    @Override
    public void onCurseApplied(Player player, int level) {

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

    @Override
    public void tick(Player player, int level) {
        if (shouldApply.apply(player)) {
            for (CurseAttributeData attributeData : attributes) {
                if (player.getAttribute(attributeData.attribute()) == null) {
                    continue;
                }
                player.getAttribute(attributeData.attribute()).addOrUpdateTransientModifier(attributeData.createModifier(level));
            }
        } else {
            for (CurseAttributeData attributeData : attributes) {
                if (player.getAttribute(attributeData.attribute()) == null) {
                    continue;
                }
                player.getAttribute(attributeData.attribute()).removeModifier(attributeData.attributeId());
            }
        }
    }
}
