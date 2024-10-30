package me.ultrusmods.wanderingcursebringer.curse;

import net.minecraft.ChatFormatting;
import net.minecraft.util.StringRepresentable;

public enum CurseTier implements StringRepresentable {
    LESSER("lesser", ChatFormatting.AQUA),
    NORMAL("normal", ChatFormatting.YELLOW),
    GREATER("greater", ChatFormatting.RED);

    private final String translationKey;
    private final String name;
    private final ChatFormatting color;

    CurseTier(String name, ChatFormatting color) {
        this.name = name;
        this.color = color;
        this.translationKey = "curse_tier.wanderingcursebringer." + this.name().toLowerCase();
    }


    public String getTranslationKey() {
        return this.translationKey;
    }
    public String getTierName() {
        return this.name;
    }
    public ChatFormatting getColor() {
        return this.color;
    }

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }
}
