package net.mitask.emcgenesis.event.client;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.mitask.emcgenesis.api.EMCManager;
import net.modificationstation.stationapi.api.client.event.gui.screen.container.TooltipBuildEvent;

public class EMCTooltip {
    @EventListener
    public void addEMCTooltip(TooltipBuildEvent event) {
        Item item = event.itemStack.getItem();
        long emc = EMCManager.ITEM.getEMC(item, event.itemStack.getDamage());
        if (emc == 0) return;

        String emcString = String.format("%,d", emc);
        String isLearnt = EMCManager.PLAYER.getPlayer(event.inventory.player).isLearnt(item) ? "§a\\/" : "§cX";
        event.tooltip.add("§eEMC: §r" + emcString + " (" + isLearnt + "§r)");
        if(event.itemStack.count > 1) event.tooltip.add("§eStack EMC: §r" + String.format("%,d", emc * event.itemStack.count));
    }
}
