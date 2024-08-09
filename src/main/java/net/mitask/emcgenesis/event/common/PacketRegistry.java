package net.mitask.emcgenesis.event.common;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.mitask.emcgenesis.packet.PlayerStatePacket;
import net.modificationstation.stationapi.api.event.network.packet.PacketRegisterEvent;

public class PacketRegistry {
    @EventListener
    public void emcgenesis_registerPacket(PacketRegisterEvent event) {
        PlayerStatePacket.register();
    }
}
