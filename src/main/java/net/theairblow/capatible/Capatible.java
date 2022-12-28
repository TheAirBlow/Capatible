package net.theairblow.capatible;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;
import net.theairblow.capatible.data.CapabilityFactory;
import net.theairblow.capatible.data.CapabilityImpl;
import net.theairblow.capatible.data.CapabilityStorage;
import net.theairblow.capatible.data.ICapabilityHolder;
import net.theairblow.capatible.handlers.CapabilityHandler;
import org.apache.logging.log4j.Logger;

@Mod(modid = Capatible.MOD_ID, name = Capatible.MOD_NAME, version = Capatible.VERSION)
public class Capatible {
    public static final String MOD_ID = "capatible";
    public static final String MOD_NAME = "Capatible!";
    public static final String VERSION = "1.0.0";
    public static Logger LOGGER;

    @CapabilityInject(ICapabilityHolder.class)
    public static Capability<ICapabilityHolder> CAPABILITY_HOLDER = null;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CapabilityManager.INSTANCE.register(ICapabilityHolder.class,
                new CapabilityStorage(), new CapabilityFactory());
        MinecraftForge.EVENT_BUS.register(CapabilityHandler.class);
        LOGGER = event.getModLog();
    }
}
