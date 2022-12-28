package net.theairblow.capatible.handlers;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.theairblow.capatible.Capatible;
import net.theairblow.capatible.api.CapabilityRegistry;
import net.theairblow.capatible.api.GenericType;
import net.theairblow.capatible.data.CapabilityProvider;

public class CapabilityHandler {
    @SubscribeEvent
    public void onAttachItemStackCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        attach(event, GenericType.ItemStack);
    }

    @SubscribeEvent
    public void onAttachTileEntityCapabilities(AttachCapabilitiesEvent<TileEntity> event) {
        attach(event, GenericType.TileEntity);
    }

    @SubscribeEvent
    public void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        attach(event, GenericType.Entity);
    }

    @SubscribeEvent
    public void onAttachWorldCapabilities(AttachCapabilitiesEvent<World> event) {
        attach(event, GenericType.World);
    }

    @SubscribeEvent
    public void onAttachChunkCapabilities(AttachCapabilitiesEvent<Chunk> event) {
        attach(event, GenericType.Chunk);
    }

    /** Aww man this looks awful */
    private void attach(AttachCapabilitiesEvent<?> event, GenericType type) {
        if (!CapabilityRegistry.any(type)) return;
        final Object obj = event.getObject();
        if (obj instanceof TileEntity)
            event.addCapability(new ResourceLocation(Capatible.MOD_ID, type.name()),
                    new CapabilityProvider((TileEntity) obj, CapabilityRegistry.getAll(type)));
        else if (obj instanceof Chunk)
            event.addCapability(new ResourceLocation(Capatible.MOD_ID, type.name()),
                    new CapabilityProvider((Chunk) obj, CapabilityRegistry.getAll(type)));
        else event.addCapability(new ResourceLocation(Capatible.MOD_ID, type.name()),
                    new CapabilityProvider(CapabilityRegistry.getAll(type)));
    }
}
