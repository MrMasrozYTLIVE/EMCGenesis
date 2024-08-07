package net.mitask.emcgenesis;

import net.minecraft.item.Item;
import net.mitask.emcgenesis.util.ItemUtil;
import net.modificationstation.stationapi.api.tag.TagKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public class EMCGenesis {
    public static final Logger LOGGER = LoggerFactory.getLogger("EMCGenesis");

    public static Map<String, Long> emcMap = new LinkedHashMap<>();

    public static void addItem(Item item, long amount) {
        addItem(ItemUtil.toStringId(item), amount);
    }
    public static void addItem(String id, long amount) {
        emcMap.put(id, amount);
    }

    public static void addItem(Item item, int meta, long amount) {
        addItem(ItemUtil.toStringId(item), meta, amount);
    }
    public static void addItem(String id, int meta, long amount) {
        emcMap.put(id + "_" + meta, amount);
    }

    public static void addTag(TagKey<Item> tagKey, long amount) {
        ItemUtil.getItemsOfTag(tagKey).forEach(item -> addItem(item, amount));
    }

    public static long get(Item item) {
        if(item == null) {
            LOGGER.error("[long get(Item)] Item is null!");
            return 0L;
        }

        return emcMap.getOrDefault(ItemUtil.toStringId(item), 0L);
    }
    public static long getWithMetadata(Item item, int meta) {
        if(item == null) {
            LOGGER.error("[long get(Item, Int)] Item is null!");
            return 0L;
        }
        if(meta <= 0) return get(item);

        return emcMap.getOrDefault(ItemUtil.toStringId(item) + "_" + meta, 0L);
    }
}
