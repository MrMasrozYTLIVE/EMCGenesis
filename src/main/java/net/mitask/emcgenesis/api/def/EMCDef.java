package net.mitask.emcgenesis.api.def;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.mitask.emcgenesis.api.EMCManager;

public interface EMCDef {
    void addAll();

    default void add(Item item, long emc) {
        EMCManager.ITEM.setEMC(item, emc);
    }
    default void add(Block block, long emc) {
        EMCManager.ITEM.setEMC(block, emc);
    }
}
