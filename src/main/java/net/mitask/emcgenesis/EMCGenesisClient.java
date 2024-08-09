package net.mitask.emcgenesis;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.mitask.emcgenesis.util.Player;

@Environment(EnvType.CLIENT)
public class EMCGenesisClient {
    public static Player serverPlayerState;

    public static Minecraft getMinecraft() {
        return (Minecraft) FabricLoader.getInstance().getGameInstance();
    }
    public static World getWorld() {
        return getMinecraft().world;
    }
}
