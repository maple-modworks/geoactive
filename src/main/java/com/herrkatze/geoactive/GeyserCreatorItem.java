package com.herrkatze.geoactive;

import com.herrkatze.geoactive.BlockEntities.GeyserBlockEntity;
import com.herrkatze.geoactive.BlockEntities.GeyserGeneratorBlockEntity;
import com.herrkatze.geoactive.lists.BlockList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import org.apache.logging.log4j.core.jmx.Server;

import static net.minecraftforge.fml.loading.FMLEnvironment.dist;

public class GeyserCreatorItem  extends Item {
    public GeyserCreatorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(pUsedHand == InteractionHand.MAIN_HAND && dist == Dist.CLIENT) {
            GeoactiveScreenLoader.loadGeyserSpawnerGUI(pPlayer);
        }
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        ItemStack stack = pContext.getItemInHand();
        if (stack.getTag() == null || !stack.getTag().getBoolean("hasPlacedGeyser")) {
            Level level = pContext.getLevel();
            if (level instanceof ServerLevel) {
                level.setBlock(pContext.getClickedPos().above(2), BlockList.GEYSER_GENERATOR_BLOCK.get().defaultBlockState(), 3);
                CompoundTag Tag = stack.getTag();
                if (Tag != null) {
                    GeyserGeneratorBlockEntity ggbe = (GeyserGeneratorBlockEntity) level.getBlockEntity(pContext.getClickedPos().above(2));
                    ggbe.load(Tag);
                    Tag.putBoolean("hasPlacedGeyser",true);
                }
                else {
                    CompoundTag newTag = new CompoundTag();
                    newTag.putBoolean("hasPlacedGeyser",true);
                    stack.setTag(newTag);
                }

            }

        }
        return InteractionResult.SUCCESS;
    }


}
