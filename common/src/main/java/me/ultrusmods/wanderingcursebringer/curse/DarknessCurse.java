package me.ultrusmods.wanderingcursebringer.curse;

import me.ultrusmods.wanderingcursebringer.Constants;
import me.ultrusmods.wanderingcursebringer.platform.Services;
import me.ultrusmods.wanderingcursebringer.register.DamageTypeRegistry;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;

public class DarknessCurse extends Curse  {
    public DarknessCurse(CurseTier tier, int maxLevel) {
        super(tier, maxLevel);
    }


    @Override
    public void onCurseRemoved(Player player, int level) {
        super.onCurseRemoved(player, level);
        Services.PLATFORM.setDarknessTimer(player, 0);
        player.getAttribute(
                Attributes.ATTACK_DAMAGE
        ).removeModifier(
                Constants.id("darkness_damage")
        );
    }

    @Override
    public void tick(Player player, int level) {
        super.tick(player, level);

        int lightLevel = 0;
        if (player.level().isNight()) {
            // Gets light level from block, don't want to include moonlight at night
            lightLevel = player.level().getBrightness(LightLayer.BLOCK, player.blockPosition());
        } else {
            // Gets raw brightness, so sunlight is included
            lightLevel = player.level().getRawBrightness(player.blockPosition(), 0);
        }

        int darknessTimer = Services.PLATFORM.getDarknessTimer(player);
        if (lightLevel == 0) {
            darknessTimer++;
            if (darknessTimer > 1000) darknessTimer = 1000;
            Services.PLATFORM.setDarknessTimer(player, darknessTimer);
            float maxTime = 110F - (level * 10f);
            player.getAttribute(
                    Attributes.ATTACK_DAMAGE
            ).addOrUpdateTransientModifier(
                    new AttributeModifier(
                            Constants.id("darkness_damage"),
                            (darknessTimer / maxTime), //+1 damage every 5 seconds of being in darkness upto +10 damage at level 1
                            AttributeModifier.Operation.ADD_VALUE
                    )
            );
        } else {
            darknessTimer = Math.max(0, darknessTimer - (2 + level * 3));

            Services.PLATFORM.setDarknessTimer(player, darknessTimer);
        }
        if (darknessTimer >= 600 && player.tickCount % 20 == 0) {
            player.hurt(player.damageSources().source(DamageTypeRegistry.DARKNESS), 0.5F + level * 0.5F);
        }
    }
}
