package net.mitask.emcgenesis.item;

import net.mitask.emcgenesis.util.items.BaseItem;

public class CovalenceDust extends BaseItem {
    public CovalenceDust(String id) {
        super(id);
    }

    @Override
    public String getTexturePrefix() {
        return super.getTexturePrefix() + "covalence_dust/";
    }
}
