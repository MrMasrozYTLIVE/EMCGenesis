package net.mitask.emcgenesis.item;

import net.mitask.emcgenesis.util.items.BaseItem;

public class PhilosophersStone extends BaseItem {
    public PhilosophersStone(String id) {
        super(id);
        setMaxCount(1);
        setCraftingReturnItem(this);
    }
}
