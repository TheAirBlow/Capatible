package net.theairblow.capatible.util;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;

import java.util.ArrayList;
import java.util.List;

public class ConfigUtil {
    private final static List<String> modIds = new ArrayList<>();

    public static void autoSync(String modId) {
        modIds.add(modId);
    }

    public static void sync(String modId) {
        ConfigManager.sync(modId, Config.Type.INSTANCE);
    }

    public static boolean shouldSync(String modId) {
        return modIds.contains(modId);
    }
}
