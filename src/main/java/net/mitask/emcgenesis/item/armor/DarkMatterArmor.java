package net.mitask.emcgenesis.item.armor;

import net.mitask.emcgenesis.util.items.BaseArmorItem;

public class DarkMatterArmor extends BaseArmorItem {
    public DarkMatterArmor(String id, Slot slot) {
        super(id, ArmorType.DARKMATTER, slot);
    }

    @Override
    public String getTextureLocation() {
        return super.getTextureLocation() + "dm_armor/";
    }
}
