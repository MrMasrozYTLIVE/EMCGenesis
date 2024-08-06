package net.mitask.emcgenesis;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class EMCGenesisClient {
    public static Minecraft getMinecraft() {
        return (Minecraft) FabricLoader.getInstance().getGameInstance();
    }

    public static World getWorld() {
        return getMinecraft().world;
    }
}
