package net.mitask.emcgenesis.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {
    private static final ItemRegistry ITEMS = ItemRegistry.INSTANCE;
    private static final BlockRegistry BLOCKS = BlockRegistry.INSTANCE;

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
        List<Item> items = new ArrayList<>();

        ITEMS.getEntryList(tagKey).ifPresent(registryEntries -> registryEntries.forEach(itemRegistryEntry -> {
            var key = itemRegistryEntry.getKey();
            if(key.isEmpty()) return;
            items.add(ITEMS.get(key.get()));
        }));

        return items;
    }
    public static List<Item> getItemsOfTag(String tag) {
        return getItemsOfTag(TagKey.of(ItemRegistry.KEY, IdentifierUtil.id(tag)));
    }

    public static List<Block> getBlocksOfTag(TagKey<Block> tagKey) {
        List<Block> blocks = new ArrayList<>();

        BLOCKS.getEntryList(tagKey).ifPresent(registryEntries -> registryEntries.forEach(itemRegistryEntry -> {
            var key = itemRegistryEntry.getKey();
            if(key.isEmpty()) return;
            blocks.add(BLOCKS.get(key.get()));
        }));

        return blocks;
    }
    public static List<Block> getBlocksOfTag(String tag) {
        return getBlocksOfTag(TagKey.of(BlockRegistry.KEY, IdentifierUtil.id(tag)));
    }
}
