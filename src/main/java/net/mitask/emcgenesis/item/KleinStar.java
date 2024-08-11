package net.mitask.emcgenesis.item;

import net.mitask.emcgenesis.util.items.BaseItem;

public class KleinStar extends BaseItem {
    public KleinStar(String id) {
        super(id);
        setMaxCount(1);
    }

    @Override
    public String getTexturePrefix() {
        return super.getTexturePrefix() + "stars/";
    }
}
