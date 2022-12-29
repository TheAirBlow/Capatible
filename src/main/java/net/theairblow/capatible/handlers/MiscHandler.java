package net.theairblow.capatible.handlers;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.theairblow.capatible.annotations.EventHandler;
import net.theairblow.capatible.util.ConfigUtil;

@EventHandler
public class MiscHandler {
    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (ConfigUtil.shouldSync(event.getModID()))
            ConfigUtil.sync(event.getModID());
    }
}
