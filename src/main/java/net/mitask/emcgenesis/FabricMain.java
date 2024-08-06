package net.mitask.emcgenesis;

import net.fabricmc.api.ClientModInitializer;

public class FabricMain implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EMCGenesisClient.init();
    }
}
