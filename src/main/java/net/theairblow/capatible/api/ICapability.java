package net.theairblow.capatible.api;

import java.io.Serializable;

/**
 * Allows to make capabilities in a very easy way.
 * Use this ONLY in the case when you just want to store data.
 */
public interface ICapability extends Serializable {
    /** Used to determine the event generic for adding this capability. */
    public GenericType getGenericType();

    /**
     * Used to determine the object that the capability should be injected into.
     * Does not apply for ItemStack, World and Chunk generic types.
     */
    public Class<?> getType();
}
