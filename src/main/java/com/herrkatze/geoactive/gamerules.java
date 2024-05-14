package com.herrkatze.geoactive;

import net.minecraft.world.level.GameRules;

public class gamerules {
    public static GameRules.Key<GameRules.BooleanValue> RULE_DISABLE_GEYSER_GENERATOR_BLOCK;

    public static void initializeGamerules(){
        RULE_DISABLE_GEYSER_GENERATOR_BLOCK= GameRules.register("disableGeyserGeneratorBlock", GameRules.Category.MISC, GameRules.BooleanValue.create(false));
    }
}
