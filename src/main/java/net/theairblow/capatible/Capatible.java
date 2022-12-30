package net.theairblow.capatible;

import eu.infomas.annotation.AnnotationDetector;
import eu.infomas.annotation.Cursor;
import net.minecraft.command.ICommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.theairblow.capatible.annotations.Command;
import net.theairblow.capatible.annotations.EventHandler;
import net.theairblow.capatible.api.CapabilityRegistry;
import net.theairblow.capatible.api.ICapability;
import net.theairblow.capatible.data.CapabilityFactory;
import net.theairblow.capatible.data.CapabilityStorage;
import net.theairblow.capatible.data.ICapabilityHolder;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mod(modid = Capatible.MOD_ID, name = Capatible.MOD_NAME, version = Capatible.VERSION)
public class Capatible {
    private static final List<Class<?>> commands = new ArrayList<>();
    public static final String MOD_ID = "capatible";
    public static final String MOD_NAME = "Capatible!";
    public static final String VERSION = "1.0.2";
    public static Logger LOGGER;

    @CapabilityInject(ICapabilityHolder.class)
    public static Capability<ICapabilityHolder> CAPABILITY_HOLDER = null;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CapabilityManager.INSTANCE.register(ICapabilityHolder.class,
                new CapabilityStorage(), new CapabilityFactory());

        LOGGER = event.getModLog(); handleMod(MOD_ID);
    }

    @Mod.EventHandler
    public void registerCommands(FMLServerStartingEvent event) {
        for (Class<?> cl : commands) {
            LOGGER.info("Registering command {}", cl.getCanonicalName());
            try {
                event.registerServerCommand((ICommand) cl.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                Capatible.LOGGER.error("Failed to register command {}", cl.getCanonicalName());
                e.printStackTrace();

                Capatible.LOGGER.error("THIS IS A SERIOUS PROBLEM! DO NOT IGNORE! " +
                        "IT MEANS THAT A MOD THAT IS USING CAPATIBLE IS BROKEN, " +
                        "SO REPORT THIS ISSUE TO DEVS OF THAT MOD IMMEDIATELY!");
            }
        }
    }

    /**
     * Handles annotated classes in your mod.
     * Currently it handles annotations only.
     */
    public static void handleMod(String modId) {
        Optional<ModContainer> mod = Loader.instance().getModList().stream()
                .filter(x -> x.getModId().equals(modId)).findFirst();
        if (!mod.isPresent()) throw new IllegalArgumentException("Invalid mod ID!");
        try {
            List<Class<?>> classes = AnnotationDetector.scanClassPath(
                    mod.get().getMod().getClass().getPackage().getName())
                    .forAnnotations(EventHandler.class).collect(Cursor::getType);
            for (Class<?> cl : classes) {
                LOGGER.info("Registering event handler for {}", cl.getCanonicalName());
                MinecraftForge.EVENT_BUS.register(cl);
            }

            classes = AnnotationDetector.scanClassPath(mod.get().getMod().getClass().getPackage().getName())
                    .forAnnotations(net.theairblow.capatible.annotations.Capability.class).collect(Cursor::getType);
            for (Class<?> cl : classes) {
                LOGGER.info("Registering capability {}", cl.getCanonicalName());
                try {
                    CapabilityRegistry.register((ICapability) cl.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    Capatible.LOGGER.error("Failed to register capability {}", cl.getCanonicalName());
                    e.printStackTrace();

                    Capatible.LOGGER.error("THIS IS A SERIOUS PROBLEM! DO NOT IGNORE! " +
                            "IT MEANS THAT A MOD THAT IS USING CAPATIBLE IS BROKEN, " +
                            "SO REPORT THIS ISSUE TO DEVS OF THAT MOD IMMEDIATELY!");
                }
            }

            classes = AnnotationDetector.scanClassPath(
                    mod.get().getMod().getClass().getPackage().getName())
                    .forAnnotations(Command.class).collect(Cursor::getType);
            commands.addAll(classes);
        } catch (Exception e) {
            Capatible.LOGGER.error("Failed to process annotations for " + modId);
            e.printStackTrace();
        }
    }
}
