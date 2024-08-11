package net.mitask.emcgenesis.item.armor;

import net.mitask.emcgenesis.util.items.BaseArmorItem;

public class RedMatterArmor extends BaseArmorItem {
    public RedMatterArmor(String id, Slot slot) {
        super(id, ArmorType.REDMATTER, slot);
    }

    @Override
    public String getTextureLocation() {
        return super.getTextureLocation() + "rm_armor/";
    }
}
