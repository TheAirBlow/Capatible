package net.theairblow.capatible.data;

import net.minecraft.nbt.NBTBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.theairblow.capatible.Capatible;
import net.theairblow.capatible.api.ICapability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class CapabilityProvider implements ICapabilitySerializable<NBTBase> {
    private final ICapabilityHolder capability;

    public CapabilityProvider(List<ICapability> capabilities) {
        capability = new CapabilityImpl();
        capability.setCapabilities(capabilities);
    }

    public CapabilityProvider(Chunk chunk, List<ICapability> capabilities) {
        capability = new ChunkCapability(chunk);
        capability.setCapabilities(capabilities);
    }

    public CapabilityProvider(TileEntity entity, List<ICapability> capabilities) {
        capability = new TileEntityCapability(entity);
        capability.setCapabilities(capabilities);
    }

    @Override
    public NBTBase serializeNBT() {
        return Capatible.CAPABILITY_HOLDER.getStorage().writeNBT(
                Capatible.CAPABILITY_HOLDER, capability, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        Capatible.CAPABILITY_HOLDER.getStorage().readNBT(
                Capatible.CAPABILITY_HOLDER, capability, null, nbt);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == Capatible.CAPABILITY_HOLDER;
    }

    @Nullable @Override
    public <T> T getCapability(@Nonnull Capability<T> fuckThis, @Nullable EnumFacing facing) {
        return capability == Capatible.CAPABILITY_HOLDER ? Capatible.CAPABILITY_HOLDER.cast(capability) : null;
    }
}
