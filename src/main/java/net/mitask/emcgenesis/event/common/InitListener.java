package net.mitask.emcgenesis.event.common;

import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.mine_diver.unsafeevents.listener.ListenerPriority;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;

public class InitListener {
    @EventListener(priority = ListenerPriority.HIGHEST)
    public void preInit(InitEvent event) {
        FabricLoader.getInstance().getEntrypointContainers("emcgenesis:event_bus", Object.class).forEach(EntrypointManager::setup);
    }
}
