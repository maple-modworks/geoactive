package com.herrkatze.geoactive;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class GeoactiveScreenLoader {
    private GeoactiveScreenLoader() {}

    public static void loadGeyserSpawnerGUI(final Player playerIn) {
        Minecraft.getInstance().setScreen(new GeyserCreatorScreen());
    }
}
