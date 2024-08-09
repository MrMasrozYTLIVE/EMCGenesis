package net.mitask.emcgenesis.event.client;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.mitask.emcgenesis.EMCGenesisClient;
import net.mitask.emcgenesis.api.EMCManager;
import net.mitask.emcgenesis.util.Player;
import net.modificationstation.stationapi.api.client.event.gui.screen.container.TooltipBuildEvent;

public class EMCTooltip {
    @EventListener
    public void addEMCTooltip(TooltipBuildEvent event) {
        Item item = event.itemStack.getItem();
        long emc = EMCManager.ITEM.getEMC(item, event.itemStack.getDamage());
        if (emc == 0) return;

        PlayerEntity playerEntity = event.inventory.player;
        Player player = playerEntity.world.isRemote ? EMCGenesisClient.serverPlayerState : EMCManager.PLAYER.getPlayer(playerEntity);

        String emcString = String.format("%,d", emc);
        String isLearnt = " (" + (player.isLearnt(item) ? "§a+" : "§c-") + "§r)";
        event.tooltip.add("§eEMC: §r" + emcString + isLearnt);
        if(event.itemStack.count > 1) event.tooltip.add("§eStack EMC: §r" + String.format("%,d", emc * event.itemStack.count));
    }
}
