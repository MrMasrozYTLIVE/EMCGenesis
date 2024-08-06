package net.mitask.emcgenesis;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.mitask.emcgenesis.api.EMCManager;

import java.util.logging.Logger;

public class EMCGenesisClient {
    private static final Logger LOGGER = Logger.getLogger("EMCGenesis/Client");

    public static void init() {
        LOGGER.info("Loading EMC");
        EMCManager.ITEM.setEMC(Item.WOODEN_AXE, 100);
    }

    public static Minecraft getMinecraft() {
        return (Minecraft) FabricLoader.getInstance().getGameInstance();
    }

    public static World getWorld() {
        return getMinecraft().world;
    }
}
