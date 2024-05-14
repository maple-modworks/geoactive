package com.herrkatze.geoactive.BlockEntities;

import com.herrkatze.geoactive.GeyserFluidBlocker;
import com.herrkatze.geoactive.GeyserGeneratorBlock;
import com.herrkatze.geoactive.GeyserPeripheralBlock;
import com.herrkatze.geoactive.gamerules;
import com.herrkatze.geoactive.lists.BlockEntityList;
import com.herrkatze.geoactive.lists.BlockList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.commands.SetBlockCommand;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;


public class GeyserGeneratorBlockEntity extends BlockEntity {

    private String generatedFluid;
    private int delay ;
    private int amount ;
    private boolean isIdle;
    private int activeLength;
    private int idleLength;
    private boolean ignoreIdle;

    public GeyserGeneratorBlockEntity(@NotNull BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {

        super(blockEntityType, pos, state);

    }

    public GeyserGeneratorBlockEntity(BlockPos pos, BlockState state) {

        this(BlockEntityList.GEYSER_GENERATOR_BE.get(), pos, state);
    }
    public void load(CompoundTag tag) {
        super.load(tag);
        this.generatedFluid = tag.getString("generatedFluid");
        this.delay = tag.getInt("delay");
        this.amount = tag.getInt("amount");
        this.idleLength = tag.getInt("idleLength");
        this.activeLength = tag.getInt("activeLength");
        this.isIdle = tag.getBoolean("isIdle");
        this.ignoreIdle = tag.getBoolean("ignoreIdle");
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (this.generatedFluid == null) {this.generatedFluid = "minecraft:lava";}
        tag.putString("generatedFluid", this.generatedFluid);
        tag.putInt("delay",delay);
        tag.putInt("amount",amount);
        tag.putInt("idleLength",idleLength);
        tag.putInt("activeLength",activeLength);
        tag.putBoolean("isIdle",isIdle);
        tag.putBoolean("ignoreIdle",ignoreIdle);
    }

    private void spawnGeyserPeripheral(Level level, BlockPos pos, boolean visible, Direction direction, boolean small, BlockPos geyserPos) {
        if (small) {visible = false;}
        BlockState state = BlockList.GEYSER_PERIPHERAL_BLOCK.get().defaultBlockState().setValue(GeyserPeripheralBlock.visible,visible).setValue(GeyserPeripheralBlock.facing,direction).setValue(GeyserPeripheralBlock.small,small);
        level.setBlock(pos, state, 3);
        GeyserPeripheralBlockEntity gpbe = (GeyserPeripheralBlockEntity) level.getBlockEntity(pos);
        CompoundTag tag = new CompoundTag();
        tag.putIntArray("geyserPos",new int[]{geyserPos.getX(), geyserPos.getY(), geyserPos.getZ()});
        gpbe.load(tag);
    }
    private void spawnFluidBlocker(Level level, BlockPos pos, Direction direction, BlockPos geyserPos) {
        level.setBlock(pos,BlockList.GEYSER_FLUID_BLOCKER.get().defaultBlockState().setValue(GeyserFluidBlocker.facing,direction),3);
        GeyserPeripheralBlockEntity gpbe = (GeyserPeripheralBlockEntity) level.getBlockEntity(pos);
        CompoundTag tag = new CompoundTag();
        tag.putIntArray("geyserPos",new int[]{geyserPos.getX(), geyserPos.getY(), geyserPos.getZ()});
        gpbe.load(tag);
    }


    private void deleteme(){
        assert this.level != null;
        this.setRemoved();
        this.level.setBlock(this.getBlockPos(), Blocks.AIR.defaultBlockState(),3);
    }

    public void tick(Level level, BlockPos pos, BlockState state){
        // This gamerule is used to disable the function of the Geyser Generator so that it can be made into a structure to spawn a new Geyser
        if (!level.getGameRules().getBoolean(gamerules.RULE_DISABLE_GEYSER_GENERATOR_BLOCK)) {
            BlockPos geyserPos = pos.below();
            level.setBlock(geyserPos, BlockList.GEYSER_BLOCK.get().defaultBlockState(), 3);
            GeyserBlockEntity gbe = (GeyserBlockEntity) level.getBlockEntity(geyserPos);
            CompoundTag tag = new CompoundTag();
            this.saveAdditional(tag);
            gbe.load(tag);
            BlockPos[] small_positions = new BlockPos[]{geyserPos.north(2).west(),geyserPos.north(2),geyserPos.north(2).east(),
                    geyserPos.north().west(2),geyserPos.north().east(2),
                    geyserPos.west(2),geyserPos.east(2),
                    geyserPos.south().west(2),geyserPos.south().east(2),
                    geyserPos.south(2).west(),geyserPos.south(2),geyserPos.south(2).east()
            };
            for (BlockPos position:small_positions) {
                spawnGeyserPeripheral(level,position,false,Direction.SOUTH,true,geyserPos);
            }
            spawnGeyserPeripheral(level,geyserPos.north().west(),true,Direction.NORTH,false,geyserPos);
            spawnGeyserPeripheral(level,geyserPos.north().east(),true,Direction.EAST,false,geyserPos);
            spawnGeyserPeripheral(level,geyserPos.south().west(),true,Direction.WEST,false,geyserPos);
            spawnGeyserPeripheral(level,geyserPos.south().east(),true,Direction.SOUTH,false,geyserPos);

            spawnGeyserPeripheral(level,geyserPos.north(),false,Direction.NORTH,false,geyserPos);
            spawnGeyserPeripheral(level,geyserPos.east(),false,Direction.EAST,false,geyserPos);
            spawnGeyserPeripheral(level,geyserPos.west(),false,Direction.WEST,false,geyserPos);
            spawnGeyserPeripheral(level,geyserPos.south(),false,Direction.SOUTH,false,geyserPos);

            spawnFluidBlocker(level,pos.north(),Direction.NORTH,geyserPos);
            spawnFluidBlocker(level,pos.west(),Direction.WEST,geyserPos);
            spawnFluidBlocker(level,pos.east(),Direction.EAST,geyserPos);
            spawnFluidBlocker(level,pos.south(),Direction.SOUTH,geyserPos);

            deleteme();
        }
    }


}
