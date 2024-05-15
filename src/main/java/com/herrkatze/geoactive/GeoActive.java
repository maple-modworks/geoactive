package com.herrkatze.geoactive;

import com.herrkatze.geoactive.lists.BlockEntityList;
import com.herrkatze.geoactive.lists.BlockList;
import com.herrkatze.geoactive.lists.ItemList;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import org.slf4j.Logger;

import java.util.Optional;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(GeoActive.MODID)
public class GeoActive
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "geoactive";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();


    public GeoActive()
    {
        gamerules.initializeGamerules();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered\
        BlockList.register(modEventBus);
        BlockEntityList.Register(modEventBus);
        ItemList.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        // Register ourselves for server and other game events we are interested in

        MinecraftForge.EVENT_BUS.register(this);
        int id = 0;
        GeoActivePacketHandler.CHANNEL.registerMessage(id++,GeyserCreatorPacket.class,GeyserCreatorPacket::toBytes,GeyserCreatorPacket::fromBytes,GeyserCreatorPacket::handlePacket, Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }
    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }


}

