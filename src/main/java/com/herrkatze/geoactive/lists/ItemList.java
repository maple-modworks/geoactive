package com.herrkatze.geoactive.lists;

import com.herrkatze.geoactive.GeoActive;
import com.herrkatze.geoactive.GeyserCreatorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemList {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GeoActive.MODID);

    public static final RegistryObject<Item> GEYSER_CREATOR = ITEMS.register("geyser_creator",() -> new GeyserCreatorItem(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
