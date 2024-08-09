package net.mitask.emcgenesis.api.event;

import net.mine_diver.unsafeevents.Event;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.mitask.emcgenesis.api.EMCManager;

public class EMCDefEvent extends Event {
    public void add(Item item, long emc) {
        EMCManager.ITEM.setEMC(item, emc);
    }
    public void add(Block block, long emc) {
        EMCManager.ITEM.setEMC(block, emc);
    }

    public void addWithMetadata(Item item, int meta, long emc) {
        if(meta > 0) EMCManager.ITEM.setEMCWithMeta(item, meta, emc);
        else EMCManager.ITEM.setEMC(item, emc);
    }
    public void addWithMetadata(Block block, int meta, long emc) {
        if(meta > 0) EMCManager.ITEM.setEMCWithMeta(block, meta, emc);
        else EMCManager.ITEM.setEMC(block, emc);
    }
}
