package net.theairblow.capatible.data;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.theairblow.capatible.Capatible;
import net.theairblow.capatible.api.CapabilityRegistry;
import net.theairblow.capatible.api.ICapability;
import net.theairblow.capatible.util.ObjectSerializer;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.HashMap;

public class CapabilityStorage implements Capability.IStorage<ICapabilityHolder> {
    @Nullable @Override
    public NBTBase writeNBT(Capability<ICapabilityHolder> capability,
                            ICapabilityHolder instance, EnumFacing side) {
        NBTTagCompound compound = new NBTTagCompound();
        for (String name : instance.getCapabilityNames()) {
            Class<?> cl = CapabilityRegistry.get(name);
            try {
                compound.setString(name, ObjectSerializer.toString((ICapability)cl.newInstance()));
            } catch (IOException | InstantiationException | IllegalAccessException e) {
                Capatible.LOGGER.error("Failed to serialize {}", name);
                e.printStackTrace();

                Capatible.LOGGER.error("THIS IS A SERIOUS PROBLEM! DO NOT IGNORE! " +
                        "IT MEANS THAT A MOD THAT IS USING CAPATIBLE IS BROKEN, " +
                        "SO REPORT THIS ISSUE TO DEVS OF THAT MOD IMMEDIATELY!");
            }
        }

        return compound;
    }

    @Override
    public void readNBT(Capability<ICapabilityHolder> capability,
                        ICapabilityHolder instance, EnumFacing side, NBTBase nbt) {
        if (!(nbt instanceof NBTTagCompound)) return;
        NBTTagCompound compound = (NBTTagCompound) nbt;
        HashMap<String, String> values = new HashMap<>();
        for (String i : compound.getKeySet())
            values.put(i, compound.getString(i));
        instance.setCapabilitiesSerialized(values);
    }
}
