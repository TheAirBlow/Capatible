package net.theairblow.capatible.data;

import net.theairblow.capatible.Capatible;
import net.theairblow.capatible.api.ICapability;
import net.theairblow.capatible.util.ObjectSerializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CapabilityImpl implements ICapabilityHolder {
    private final HashMap<String, ICapability> data = new HashMap<>();

    @Override
    public <T extends ICapability> T getCapability(Class<?> type) {
        return (T)data.get(type.getCanonicalName());
    }

    @Override
    public void setCapabilities(List<ICapability> inp) {
        data.clear();
        for (ICapability cap : inp)
            data.put(cap.getClass().getCanonicalName(), cap);
    }

    @Override
    public void setCapabilitiesSerialized(HashMap<String, String> inp) {
        data.clear();
        for (Map.Entry<String, String> entry : inp.entrySet()) {
            try {
                data.put(entry.getKey(), ObjectSerializer.fromString(entry.getValue()));
            } catch (IOException | ClassNotFoundException e) {
                Capatible.LOGGER.error("Failed to deserialize {}", entry.getKey());
                e.printStackTrace();

                Capatible.LOGGER.error("THIS IS A SERIOUS PROBLEM! DO NOT IGNORE! " +
                        "IT MEANS THAT A MOD THAT IS USING CAPATIBLE IS BROKEN, " +
                        "SO REPORT THIS ISSUE TO DEVS OF THAT MOD IMMEDIATELY!");
            }
        }
        onUpdate();
    }

    @Override
    public Set<String> getCapabilityNames() {
        return data.keySet();
    }

    @Override
    public void onUpdate() {
        // Do nothing, this is the default implementation
    }
}
