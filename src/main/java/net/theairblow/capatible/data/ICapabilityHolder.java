package net.theairblow.capatible.data;

import net.theairblow.capatible.api.ICapability;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface ICapabilityHolder {
    /** Returns a capability if it's present, null otherwise. */
    public <T extends ICapability> T getCapability(Class<?> type);
    public void setCapabilities(List<ICapability> data);
    public void setCapabilitiesSerialized(HashMap<String, String> data);
    public Set<String> getCapabilityNames();
    public void onUpdate();
}
