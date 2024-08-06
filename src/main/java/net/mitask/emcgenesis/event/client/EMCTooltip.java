package net.mitask.emcgenesis.event.client;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.mitask.emcgenesis.api.EMCManager;
import net.modificationstation.stationapi.api.client.event.gui.screen.container.TooltipBuildEvent;

public class EMCTooltip {
    @EventListener
    public void addEMCTooltip(TooltipBuildEvent event) {
        Item item = event.itemStack.getItem();
        long emc = EMCManager.ITEM.getEMC(item);
        if (emc == 0) return;

        event.tooltip.add("§eEMC: §r" + String.format("%,d", emc));
        if(event.itemStack.count > 1) event.tooltip.add("§eStack EMC: §r" + String.format("%,d", emc * event.itemStack.count));
    }
}
