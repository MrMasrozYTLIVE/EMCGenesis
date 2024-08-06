package net.mitask.emcgenesis.event;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.CraftingRecipeManager;
import net.mitask.emcgenesis.EMCGenesis;
import net.mitask.emcgenesis.api.EMCManager;
import net.mitask.emcgenesis.api.def.VanillaEMCDef;
import net.modificationstation.stationapi.api.event.registry.RegistriesFrozenEvent;
import net.modificationstation.stationapi.api.recipe.StationRecipe;
import net.modificationstation.stationapi.impl.recipe.StationShapedRecipe;

import java.util.List;

public class InitializeEMCEvent {
    @EventListener
    public void initializeEMC(RegistriesFrozenEvent event) {
        EMCGenesis.LOGGER.info("Loading EMC");
        EMCManager.addDef(new VanillaEMCDef());

        List<StationRecipe> recipes = CraftingRecipeManager.getInstance().getRecipes();
    }
}
