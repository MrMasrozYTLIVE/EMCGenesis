package net.mitask.emcgenesis;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

@Environment(EnvType.SERVER)
public class EMCGenesisServer {
    public static MinecraftServer getMinecraft() {
        return (MinecraftServer) FabricLoader.getInstance().getGameInstance();
    }

    public static World[] getWorlds() {
        return getMinecraft().field_2841;
    }
    public static World getOverWorld() {
        return getWorlds()[0];
    }
}
