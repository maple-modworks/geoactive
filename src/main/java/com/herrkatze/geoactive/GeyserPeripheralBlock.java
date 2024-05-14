package com.herrkatze.geoactive;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GeyserPeripheralBlock extends BaseGeyserPeripheral{
    public static final DirectionProperty facing = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty visible = BooleanProperty.create("visible");
    public static final BooleanProperty small = BooleanProperty.create("small");
    static VoxelShape FULL_BLOCK = Block.box(0,0,0,16,16,16);
    static VoxelShape SLAB_BLOCK = Block.box(0,0,0,16,10,16);

    public GeyserPeripheralBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(facing, Direction.SOUTH).setValue(visible,false).setValue(small,false));

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(new Property[]{facing,visible,small});
    }
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if(pState.getValue(small)) {
            return SLAB_BLOCK;
        }
        else return FULL_BLOCK;
    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        if(pState.getValue(visible)) {
            return RenderShape.MODEL;
        }
        return RenderShape.INVISIBLE;
    }
}