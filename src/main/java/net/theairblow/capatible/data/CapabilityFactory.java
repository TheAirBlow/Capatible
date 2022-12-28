package net.theairblow.capatible.data;

import java.util.concurrent.Callable;

public class CapabilityFactory implements Callable<ICapabilityHolder> {
    @Override
    public ICapabilityHolder call() {
        return new CapabilityImpl();
    }
}