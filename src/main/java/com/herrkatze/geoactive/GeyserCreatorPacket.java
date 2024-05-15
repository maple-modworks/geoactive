package com.herrkatze.geoactive;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GeyserCreatorPacket {



    public GeyserCreatorPacket(String fluid,int amountValue,int delayValue,int idleValue,int activeValue){
        this.fluid = fluid;
        this.amountValue = amountValue;
        this.delayValue = delayValue;
        this.idleValue = idleValue;
        this.activeValue = activeValue;
    }
    protected String fluid;
    protected int amountValue,delayValue,idleValue,activeValue;
    public static void toBytes(final GeyserCreatorPacket msg, final FriendlyByteBuf buf){
        buf.writeUtf(msg.fluid);
        buf.writeInt(msg.amountValue);
        buf.writeInt(msg.delayValue);
        buf.writeInt(msg.idleValue);
        buf.writeInt(msg.activeValue);
    }
    public static GeyserCreatorPacket fromBytes(final FriendlyByteBuf buf){
        final String fluid = buf.readUtf();
        final int amountValue = buf.readInt();
        final int delayValue = buf.readInt();
        final int idleValue = buf.readInt();
        final int activeValue = buf.readInt();
        return new GeyserCreatorPacket(fluid,amountValue,delayValue,idleValue,activeValue);
    }
    public static void handlePacket(final GeyserCreatorPacket message, final Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide() == LogicalSide.SERVER) {
            context.enqueueWork(() -> {
                ServerPlayer player = context.getSender();
                ItemStack stack = player.getInventory().getSelected();
                if (stack.getItem() instanceof GeyserCreatorItem) {
                    CompoundTag tag = stack.getOrCreateTag();
                    tag.putString("generatedFluid", message.fluid);
                    tag.putInt("delay",message.delayValue);
                    tag.putInt("amount",message.amountValue);
                    tag.putInt("idleLength",message.idleValue);
                    tag.putInt("activeLength",message.activeValue);
                    tag.putBoolean("hasPlacedGeyser",false);
                }

            });
        }
        context.setPacketHandled(true);
    }
}
