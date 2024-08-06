package net.mitask.emcgenesis;

import net.minecraft.item.Item;
import net.mitask.emcgenesis.util.ItemUtil;
import net.modificationstation.stationapi.api.tag.TagKey;

import java.util.LinkedHashMap;
import java.util.Map;

public class EMCGenesis {
    public static Map<String, Long> emcMap = new LinkedHashMap<>();

    public static void addItem(Item item, long amount) {
        addItem(ItemUtil.toStringId(item), amount);
    }
    
    public static void addItem(String id, long amount) {
        emcMap.put(id, amount);
    }

    public static void addTag(TagKey<Item> tagKey, long amount) {
        ItemUtil.getItemsOfTag(tagKey).forEach(item -> addItem(item, amount));
    }

    public static long get(Item item) {
        return emcMap.getOrDefault(ItemUtil.toStringId(item), 0L);
    }
}
