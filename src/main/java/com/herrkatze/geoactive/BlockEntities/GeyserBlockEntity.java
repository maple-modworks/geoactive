package com.herrkatze.geoactive.BlockEntities;

import com.herrkatze.geoactive.lists.BlockEntityList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GeyserBlockEntity extends BlockEntity {
    protected FluidTank tank = new FluidTank(1000);
        private final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> {
        return this.tank;
    });

    private Fluid storedFluid;
    private int delay ;
    private int amount ;

    public GeyserBlockEntity(@NotNull BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state);
    }
    public GeyserBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityList.GEYSER_BE.get(), pos, state);
    }
    public void load(CompoundTag tag) {
        super.load(tag);
        this.tank.readFromNBT(tag);
        ResourceLocation fluidName = new ResourceLocation(tag.getString("storedFluid"));
        this.storedFluid = (Fluid) ForgeRegistries.FLUIDS.getValue(fluidName);
        this.delay = tag.getInt("delay");
        this.amount = tag.getInt("amount");
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        this.tank.writeToNBT(tag);
        tag.putString("storedFluid", ForgeRegistries.FLUIDS.getKey(this.storedFluid).toString());
        tag.putInt("delay",delay);
        tag.putInt("amount",amount);
    }

    public int getTankAmount() {
        return this.tank.getFluidAmount();
    }
    public int getDelay() {
        return this.delay;
    }
    public String getGeneratedFluid() {
        return ForgeRegistries.FLUIDS.getKey(this.storedFluid).toString();
    }
    public int getGeneratedAmount() {
        return amount;
    }

    private int ticks;
    public void tick(Level level, BlockPos pos, BlockState state){
        if (this.delay == 0) {this.delay = 10;}
        if (this.amount == 0) {this.amount = 5;}
        if (this.ticks++ % this.delay == 0) {
            this.ticks = 1;
            FluidStack fluid;
            if (this.storedFluid == null) {
                this.storedFluid = Fluids.LAVA;

            }
            if (this.tank.getFluid().getFluid() != this.storedFluid) {
                this.tank.drain(1000, IFluidHandler.FluidAction.EXECUTE);
            }
            fluid = new FluidStack(this.storedFluid,this.amount);


            this.tank.fill(fluid, IFluidHandler.FluidAction.EXECUTE);
            if (this.tank.getFluidAmount() == 1000) {
                BlockState state1 = level.getBlockState(pos.above());

                if (state1.getBlock() == Blocks.AIR) {
                    level.setBlock(pos.above(),fluid.getFluid().defaultFluidState().createLegacyBlock(),3);
                    this.tank.drain(1000, IFluidHandler.FluidAction.EXECUTE);

                }

            }
        }
    }
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
        return capability == ForgeCapabilities.FLUID_HANDLER ? this.holder.cast() : super.getCapability(capability, facing);
    }
}