package net.mitask.emcgenesis.api.def;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.mitask.emcgenesis.util.ItemUtil;

public class VanillaEMCDef implements EMCDef {
    @Override
    public void addAll() {
        add(Block.COBBLESTONE, 1);
        add(Block.DEAD_BUSH, 1);
        add(Block.DIRT, 1);
        add(Block.GLASS, 1);
        add(Block.GRASS_BLOCK, 1);
        add(Block.ICE, 1);
        add(Block.NETHERRACK, 1);
        add(Block.SNOW, 1);
        add(Block.STONE, 1);
        ItemUtil.getItemsOfTag("minecraft:leaves").forEach((item) -> add(item, 1));
        add(Block.STONE_PRESSURE_PLATE, 2);
        add(Block.GRAVEL, 4);
        add(Block.SAND, 1);
        add(Block.SANDSTONE, 4);
        add(Item.FLINT, 4);
        add(Item.STICK, 4);
        add(Block.LEVER, 5);
        add(Block.CACTUS, 8);
        ItemUtil.getItemsOfTag("minecraft:planks").forEach((item) -> add(item, 8));
        add(Item.DYE, 16);

        add(Block.TORCH, 9);

        add(Block.COBWEB, 12);
        add(Item.FISHING_ROD, 12);
        add(Item.STRING, 12);

        add(Block.LADDER, 14);
        add(Item.ARROW, 14);

        add(Item.SEEDS, 16);
        add(Item.WHEAT, 24);

        ItemUtil.getItemsOfTag("minecraft:saplings").forEach((item) -> add(item, 32));
        ItemUtil.getItemsOfTag("minecraft:logs").forEach((item) -> add(item, 32));
        add(Item.PAPER, 32);
        add(Item.SUGAR, 15);
        add(Item.BONE, 144);
        add(Item.EGG, 32);

        ItemUtil.getItemsOfTag("minecraft:wool").forEach((item) -> add(item, 48));
        add(Item.FEATHER, 48);

        add(Block.OBSIDIAN, 64);
        add(Item.REDSTONE, 64);
        add(Item.RAW_PORKCHOP, 64);
        add(Item.COOKED_PORKCHOP, 64);
        add(Item.LEATHER, 64);
        add(Item.CLAY, 64);
        add(Item.BREAD, 72);
        add(Item.PAINTING, 80);

        add(Item.GUNPOWDER, 192);

        add(Item.GLOWSTONE_DUST, 384);

        add(Block.COAL_ORE, 128);
        add(Block.IRON_ORE, 256);
        add(Block.LAPIS_ORE, 864);
        add(Block.GOLD_ORE, 2048);
        add(Block.DIAMOND_ORE, 8192);

        add(Block.REDSTONE_ORE, 256);

        add(Item.WATER_BUCKET, 768);
        add(Item.LAVA_BUCKET, 832);
        add(Item.SNOWBALL, 1);
        add(Item.MILK_BUCKET, 784);
        add(Item.BRICK, 16);
        add(Item.CLAY, 16);

        add(Item.MAP, 1344);
        add(Item.SADDLE, 192);
        add(Item.FISHING_ROD, 36);
        add(Item.SUGAR_CANE, 32);
        add(Item.APPLE, 128);
    }
}
