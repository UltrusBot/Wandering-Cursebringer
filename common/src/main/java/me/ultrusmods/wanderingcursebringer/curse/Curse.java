package me.ultrusmods.wanderingcursebringer.curse;

import me.ultrusmods.wanderingcursebringer.register.WanderingCursebringerRegistries;
import net.minecraft.Util;
import net.minecraft.world.entity.player.Player;


public class Curse {



    private String descriptionId;
    private final CurseTier tier;
    private final int maxLevel;

    public Curse(CurseTier tier, int maxLevel) {
        this.tier = tier;
        this.maxLevel = maxLevel;

    }

    protected String getOrCreateDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("curse", WanderingCursebringerRegistries.CURSE_REGISTRY.getKey(this));
        }

        return this.descriptionId;
    }


    public CurseTier getTier() {
        return this.tier;
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    /**
     * Called when the curse is applied to a player, or when the level of the curse is increased/decreased.
     * Also called when the player entity is respawned or moved to a new dimension.
     * @param player The player the curse is applied to
     * @param level The level of the curse
     */
    public void onCurseApplied(Player player, int level) {
    }

    /**
     * Called when the curse is removed from a player.
     * @param player The player the curse is removed from
     * @param level The level of the curse
     */
    public void onCurseRemoved(Player player, int level) {
    }

    public String getLoreDescriptionId() {
        return this.getOrCreateDescriptionId() + ".desc";
    }

    public void tick(Player player, int level) {
    }

    public void applyCurse(Player player) {
        CurseManager.addPlayerCurse(player, this);
    }

    /**
     * Called when the player respawns
     * @param player The player that respawned with the curse
     */
    public void onPlayerRespawn(Player player) {
    }

    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }

}
