package com.herrkatze.geoactive.BlockEntities;

import com.herrkatze.geoactive.GeoActive;
import com.herrkatze.geoactive.cctweaked_features.GeyserPeripheral;
import com.herrkatze.geoactive.lists.BlockEntityList;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import static dan200.computercraft.shared.Capabilities.CAPABILITY_PERIPHERAL;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class GeyserPeripheralBlockEntity extends BlockEntity {

    private BlockPos geyserPos;
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
        if (pos.length >= 3) {
            this.geyserPos = new BlockPos(pos[0],pos[1],pos[2]);

        }

    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
            int[] pos = new int[]{this.geyserPos.getX(), this.geyserPos.getY(), this.geyserPos.getZ()};
            tag.putIntArray("geyserPos", pos);

    }

    public GeyserBlockEntity getGeyser() {
        assert this.level != null;
        BlockEntity be = this.level.getBlockEntity(this.geyserPos);
        if (be instanceof GeyserBlockEntity && !be.isRemoved()){
         return (GeyserBlockEntity) be;
        }
        if(Thread.currentThread() == this.level.getServer().getRunningThread()) {
            deleteme();
        }
        return null;
    }

    private void deleteme(){
        assert this.level != null;
        this.setRemoved();
        this.level.setBlock(this.getBlockPos(), Blocks.AIR.defaultBlockState(),3);
    }

    private int ticks;
    public void tick(Level level, BlockPos pos, BlockState state){
        if (ticks++ % 30 == 0){
            this.getGeyser();
        }
    }

    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
        if (capability == CAPABILITY_PERIPHERAL) {
            if (peripheralCap == null) {
                peripheralCap = LazyOptional.of(() -> peripheral);
            }
            return peripheralCap.cast();

        }
        return super.getCapability(capability,facing);
    }
}