package net.theairblow.capatible.data;

import net.minecraft.tileentity.TileEntity;

public class TileEntityCapability extends CapabilityImpl {
    private TileEntity entity;

    public TileEntityCapability(TileEntity entity) {
        this.entity = entity;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        entity.markDirty();
    }
}
