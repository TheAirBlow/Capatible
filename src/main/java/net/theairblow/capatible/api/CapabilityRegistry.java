package net.theairblow.capatible.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** Registry of all capabilities. */
public class CapabilityRegistry {
    private static final HashMap<GenericType, List<ICapability>> capabilities = new HashMap<GenericType, List<ICapability>>() {{
        put(GenericType.ItemStack, new ArrayList<>());
        put(GenericType.TileEntity, new ArrayList<>());
        put(GenericType.Entity, new ArrayList<>());
        put(GenericType.World, new ArrayList<>());
        put(GenericType.Chunk, new ArrayList<>());
    }};

    private static final HashMap<String, Class<? extends ICapability>> canonicalNamesMap = new HashMap<>();
    private static final List<GenericType> types = new ArrayList<>();

    /** Get all of the capabilities registered for specific generic type. */
    public static List<ICapability> getAll(GenericType type) {
        return capabilities.get(type);
    }

    /** Get a capability from its canonical name. */
    public static Class<? extends ICapability> get(String name) {
        return canonicalNamesMap.get(name);
    }

    /**
     * Register a capability.
     * @throws IllegalArgumentException
     * Capability is already registered
     */
    public static void register(ICapability capability) throws IllegalArgumentException {
        final String name = capability.getType().getCanonicalName();
        if (canonicalNamesMap.containsKey(name))
            throw new IllegalArgumentException("This ICapability is already registered!");
        capabilities.get(capability.getGenericType()).add(capability);
        canonicalNamesMap.put(name, capability.getClass());
        types.add(capability.getGenericType());
    }

    /** Returns true in case if a capability of this type was registered. */
    public static boolean any(GenericType type) {
        return types.contains(type);
    }
}
