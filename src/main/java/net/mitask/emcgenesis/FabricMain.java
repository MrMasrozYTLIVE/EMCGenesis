package net.mitask.emcgenesis;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.mitask.emcgenesis.api.EMCManager;

public class FabricMain implements ModInitializer {
    @Override
    public void onInitialize() {
        EMCGenesis.LOGGER.info("Loading EMC");
        EMCManager.ITEM.setEMC(Item.WOODEN_AXE, 100);
        EMCManager.ITEM.setEMC(Item.DIAMOND, 100);
    }
}
