package net.mitask.emcgenesis.util;

import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {
    private static final ItemRegistry ITEMS = ItemRegistry.INSTANCE;

    public static Item fromId(Identifier id) {
        return ITEMS.get(id);
    }

    public static Identifier toId(Item item) {
        return ITEMS.getId(item);
    }
    public static String toStringId(Item item) {
        return toId(item).toString();
    }

    public static List<Item> getItemsOfTag(TagKey<Item> tagKey) {
        List<Item> list = new ArrayList<>();
        ITEMS.getEntryList(tagKey).ifPresent(registryEntries -> registryEntries.forEach(itemRegistryEntry -> {
            var key = itemRegistryEntry.getKey();
            if(key.isEmpty()) return;
            list.add(ITEMS.get(key.get()));
        }));

        return list;
    }
}
