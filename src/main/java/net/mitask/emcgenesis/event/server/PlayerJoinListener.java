package net.mitask.emcgenesis.event.server;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.mitask.emcgenesis.api.EMCManager;
import net.modificationstation.stationapi.api.server.event.network.PlayerLoginEvent;

public class PlayerJoinListener {
    @EventListener
    public void emcgenesis_onPlayerJoin(PlayerLoginEvent event) {
        EMCManager.PLAYER.getPlayer(event.player).sendPlayerState();
    }
}
