package net.mitask.emcgenesis.util.items;

import com.matthewperiut.accessoryapi.api.Accessory;
import net.minecraft.item.ItemStack;

public class RingItem extends BaseItem implements Accessory {
    public RingItem(String id) {
        super(id);
    }

    @Override
    public String[] getAccessoryTypes(ItemStack item) {
        return new String[] { "ring" };
    }
}
