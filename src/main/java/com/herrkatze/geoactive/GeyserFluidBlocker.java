package com.herrkatze.geoactive;

import com.herrkatze.geoactive.BlockEntities.GeyserGeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GeyserFluidBlocker extends BaseGeyserPeripheral implements LiquidBlockContainer {
    public static final DirectionProperty facing = BlockStateProperties.HORIZONTAL_FACING;
    static VoxelShape NORTH_SHAPE = Block.box(0,0,12,16,6,16);
    static VoxelShape SOUTH_SHAPE = Block.box(0,0,0,16,6,4);
    static VoxelShape EAST_SHAPE = Block.box(0,0,0,4,6,16);
    static VoxelShape WEST_SHAPE = Block.box(12,0,0,16,6,16);

    public GeyserFluidBlocker(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(facing, Direction.SOUTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(new Property[]{facing});
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch (pState.getValue(facing)){
            case NORTH -> {return NORTH_SHAPE;}
            case SOUTH -> {return SOUTH_SHAPE;}
            case EAST -> {return EAST_SHAPE;}
            case WEST -> {return WEST_SHAPE;}
            default -> {return NORTH_SHAPE;} //This shouldn't ever happen but I have to return something
        }
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        return false;
    }
}
