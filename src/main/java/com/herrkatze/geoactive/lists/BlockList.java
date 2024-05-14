package com.herrkatze.geoactive.lists;

import com.herrkatze.geoactive.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.event.InputEvent;

public class BlockList {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, GeoActive.MODID);

    public static final RegistryObject<Block> GEYSER_BLOCK = BLOCKS.register("geyser_block",() -> new GeyserBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));

    public static final RegistryObject<Block> GEYSER_PERIPHERAL_BLOCK = BLOCKS.register("geyser_peripheral",() -> new GeyserPeripheralBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));

    public static final RegistryObject<Block> GEYSER_GENERATOR_BLOCK = BLOCKS.register("geyser_generator", () -> new GeyserGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));

    public static final RegistryObject<Block> GEYSER_FLUID_BLOCKER = BLOCKS.register("geyser_fluid_blocker",() -> new GeyserFluidBlocker(BlockBehaviour.Properties.copy(Blocks.BEDROCK)));

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
