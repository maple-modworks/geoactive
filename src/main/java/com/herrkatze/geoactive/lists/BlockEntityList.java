package com.herrkatze.geoactive.lists;

import com.herrkatze.geoactive.BlockEntities.GeyserBlockEntity;
import com.herrkatze.geoactive.BlockEntities.GeyserPeripheralBlockEntity;
import com.herrkatze.geoactive.GeoActive;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityList {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, GeoActive.MODID);

    public static final RegistryObject<BlockEntityType<GeyserBlockEntity>> GEYSER_BE =
        BLOCK_ENTITIES.register("geyser_be",() ->
                BlockEntityType.Builder.of(GeyserBlockEntity::new,
                        BlockList.GEYSER_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<GeyserPeripheralBlockEntity>> GEYSER_PERIPHERAL_BE =
            BLOCK_ENTITIES.register("geyser_peripheral_be",() ->
                    BlockEntityType.Builder.of(GeyserPeripheralBlockEntity::new,
                            BlockList.GEYSER_PERIPHERAL_BLOCK.get()).build(null));

    public static void Register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
