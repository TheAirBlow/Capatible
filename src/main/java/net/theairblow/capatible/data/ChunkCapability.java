package net.theairblow.capatible.data;

import net.minecraft.world.chunk.Chunk;

public class ChunkCapability extends CapabilityImpl {
    private Chunk chunk;

    public ChunkCapability(Chunk chunk) {
        this.chunk = chunk;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        chunk.markDirty();
    }
}
