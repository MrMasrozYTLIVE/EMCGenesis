package net.mitask.emcgenesis.item;

import net.mitask.emcgenesis.util.items.BaseItem;

public class Fuel extends BaseItem {
    public Fuel(String id) {
        super(id);
    }

    @Override
    public String getTexturePrefix() {
        return super.getTexturePrefix() + "fuels/";
    }
}
