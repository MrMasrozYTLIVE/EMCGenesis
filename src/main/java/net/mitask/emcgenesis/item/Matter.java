package net.mitask.emcgenesis.item;

import net.mitask.emcgenesis.util.items.BaseItem;

public class Matter extends BaseItem {
    public Matter(String id) {
        super(id);
    }

    @Override
    public String getTexturePrefix() {
        return super.getTexturePrefix() + "matter/";
    }
}
