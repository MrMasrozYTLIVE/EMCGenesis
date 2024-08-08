package net.mitask.emcgenesis.api;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.mitask.emcgenesis.EMCGenesis;
import net.mitask.emcgenesis.api.def.EMCDef;
import net.mitask.emcgenesis.util.IdentifierUtil;
import net.mitask.emcgenesis.util.ItemUtil;
import net.mitask.emcgenesis.util.Player;
import net.mitask.emcgenesis.util.StateManager;
import net.modificationstation.stationapi.api.tag.TagKey;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@SuppressWarnings("unused")
public class EMCManager {
    public static class ITEM {
        public static void setEMC(Block block, long amount) {
            EMCGenesis.addItem(block.asItem(), amount);
        }
        public static void setEMC(Item item, long amount) {
            EMCGenesis.addItem(item, amount);
        }
        public static void setEMC(ItemStack item, long amount) {
            EMCGenesis.addItem(item, amount);
        }
        public static void setEMC(String id, long amount) {
            EMCGenesis.addItem(id, amount);
        }
        public static void setEMC(TagKey<Item> tagKey, long amount) {
            EMCGenesis.addTag(tagKey, amount);
        }


        public static long getEMC(Item item) {
            return EMCGenesis.get(item);
        }
        public static long getEMC(Item item, int meta) {
            return EMCGenesis.getWithMetadata(item, meta);
        }
        public static long getEMC(ItemStack item) {
            return getEMC(item.getItem(), item.getDamage());
        }

        public static long getEMC(String id) {
            return EMCGenesis.get(ItemUtil.fromId(IdentifierUtil.id(id)));
        }
        public static long getEMC(String id, int meta) {
            return EMCGenesis.getWithMetadata(ItemUtil.fromId(IdentifierUtil.id(id)), meta);
        }

        public static void setEMCWithMeta(Item item, int meta, long amount) {
            EMCGenesis.addItem(item, meta, amount);
        }
        public static void setEMCWithMeta(Block block, int meta, long amount) {
            EMCGenesis.addItem(block.asItem(), meta, amount);
        }
    }

    public static class PLAYER {
        public static void addEMC(PlayerEntity playerEntity, long amount) {
            Player player = getPlayer(playerEntity);
            player.setEMC(player.getEMC() + amount);
        }
        public static void setEMC(PlayerEntity playerEntity, long amount) {
            getPlayer(playerEntity).setEMC(amount);
        }
        public static void subtractEMC(PlayerEntity playerEntity, long amount) {
            Player player = getPlayer(playerEntity);
            player.setEMC(Math.max(player.getEMC() - amount, 0));
        }

        public static Long getPlayerEMC(PlayerEntity playerEntity) {
            return getPlayer(playerEntity).getEMC();
        }
        public static Player getPlayer(PlayerEntity playerEntity) {
            return StateManager.getOrCreateState(
                    Player.class,
                    "emcgenesis_player_" + UUID.nameUUIDFromBytes(playerEntity.name.getBytes(StandardCharsets.UTF_8))
            );
        }
    }

    public static void addDef(EMCDef def) {
        def.addAll();
    }
    public static void addDef(EMCDef... defs) {
        for(EMCDef def : defs) addDef(def);
    }
}
