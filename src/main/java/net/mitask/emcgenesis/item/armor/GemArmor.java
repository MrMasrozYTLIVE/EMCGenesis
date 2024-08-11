package net.mitask.emcgenesis.item.armor;

import net.mitask.emcgenesis.util.items.BaseArmorItem;

public class GemArmor extends BaseArmorItem {
    public GemArmor(String id, Slot slot) {
        super(id, ArmorType.GEM, slot);
    }

    @Override
    public String getTextureLocation() {
        return super.getTextureLocation() + "gem_armor/";
    }
}
