package com.herrkatze.geoactive.cctweaked_features;

import com.herrkatze.geoactive.BlockEntities.GeyserBlockEntity;
import com.herrkatze.geoactive.BlockEntities.GeyserPeripheralBlockEntity;
import com.herrkatze.geoactive.GeoActive;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class GeyserPeripheral implements IPeripheral {
    private GeyserPeripheralBlockEntity gpbe;
    public GeyserPeripheral(GeyserPeripheralBlockEntity gpbe) {
        this.gpbe = gpbe;
    }

    @Override
    public String getType() {
        return "geyser";
    }

    @Override
    public Set<String> getAdditionalTypes() {
        return IPeripheral.super.getAdditionalTypes();
    }

    @Override
    public void attach(IComputerAccess computer) {
        IPeripheral.super.attach(computer);
    }

    @Override
    public void detach(IComputerAccess computer) {
        GeoActive.LOGGER.debug("Detached. oops.");
        IPeripheral.super.detach(computer);
    }

    @Nullable
    @Override
    public Object getTarget() {
        return IPeripheral.super.getTarget();
    }

    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        return this == iPeripheral;
    }

    @LuaFunction(mainThread = true)
    public final int getTankAmount() {
        GeyserBlockEntity geyser = this.gpbe.getGeyser();
        if (geyser != null) {
            return geyser.getTankAmount();
        }
        return -1;
    }
    @LuaFunction(mainThread = true)
    public final int getDelay() {
        GeyserBlockEntity geyser = this.gpbe.getGeyser();
        if (geyser != null) {
            return geyser.getDelay();
        }
        return -1;
    }
    @LuaFunction(mainThread = true)
    public final String getGeneratedFluid() {
        GeyserBlockEntity geyser = this.gpbe.getGeyser();
        if (geyser != null) {
            return geyser.getGeneratedFluid();
        }
        return null;
    }
    @LuaFunction(mainThread = true)
    public final int getGeneratedAmount() {
        GeyserBlockEntity geyser = this.gpbe.getGeyser();
        if (geyser != null) {
            return geyser.getGeneratedAmount();
        }
        return -1;
    }
    @LuaFunction(mainThread = true)
    public final int getIdleLength() {
        GeyserBlockEntity geyser = this.gpbe.getGeyser();
        if (geyser != null) {
            return geyser.getIdleLength();
        }
        return -1;
    }
    @LuaFunction(mainThread = true)
    public final int getActiveLength() {
        GeyserBlockEntity geyser = this.gpbe.getGeyser();
        if (geyser != null) {
            return geyser.getActiveLength();
        }
        return -1;
    }
    @LuaFunction(mainThread = true)
    public final boolean isIdle() {
        GeyserBlockEntity geyser = this.gpbe.getGeyser();
        if (geyser != null) {
            return geyser.isIdle();
        }
        return false;
    }
}
