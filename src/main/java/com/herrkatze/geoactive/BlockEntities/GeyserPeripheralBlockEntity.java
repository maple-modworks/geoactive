package com.herrkatze.geoactive.BlockEntities;

import com.herrkatze.geoactive.cctweaked_features.GeyserPeripheral;
import com.herrkatze.geoactive.lists.BlockEntityList;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import static dan200.computercraft.shared.Capabilities.CAPABILITY_PERIPHERAL;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;

public class GeyserPeripheralBlockEntity extends BlockEntity {

    private GeyserBlockEntity geyser;
    protected GeyserPeripheral peripheral = new GeyserPeripheral(this);
    private LazyOptional<IPeripheral> peripheralCap;


    public GeyserPeripheralBlockEntity(@NotNull BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {

        super(blockEntityType, pos, state);

    }

    public GeyserPeripheralBlockEntity(BlockPos pos, BlockState state) {

        this(BlockEntityList.GEYSER_PERIPHERAL_BE.get(), pos, state);
    }
    public void load(CompoundTag tag) {
        super.load(tag);
        int[] pos = tag.getIntArray("geyserPos");
        BlockPos blockPos = new BlockPos(pos[0],pos[1],pos[2]);
        BlockEntity be = this.level.getBlockEntity(blockPos);
        if (be instanceof GeyserBlockEntity) {
            this.geyser = (GeyserBlockEntity) be;
        }

    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        BlockPos blockPos = this.geyser.getBlockPos();
        int x,y,z;

        int[] pos = new int[]{blockPos.getX(),blockPos.getY(),blockPos.getZ()};
        tag.putIntArray("geyserPos",pos);
    }

    public GeyserBlockEntity getGeyser() {
        if (!geyser.isRemoved()) {
            return geyser;
        }
        else return null;
    }

    private int ticks;
    public void tick(Level level, BlockPos pos, BlockState state){

    }

    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
        if (capability == CAPABILITY_PERIPHERAL) {
            if (peripheralCap == null) {
                peripheralCap = LazyOptional.of(() -> peripheral);
                return peripheralCap.cast();
            }
        }
        return super.getCapability(capability,facing);
    }
}