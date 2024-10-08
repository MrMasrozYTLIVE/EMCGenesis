package net.mitask.emcgenesis.event.common;

import com.mojang.datafixers.util.Either;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.mine_diver.unsafeevents.listener.ListenerPriority;
import net.minecraft.ShapedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.CraftingRecipeManager;
import net.minecraft.recipe.ShapelessRecipe;
import net.mitask.emcgenesis.EMCGenesis;
import net.mitask.emcgenesis.api.EMCManager;
import net.mitask.emcgenesis.api.event.EMCDefEvent;
import net.mitask.emcgenesis.item.PhilosophersStone;
import net.mitask.emcgenesis.util.ItemUtil;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.event.registry.RegistriesFrozenEvent;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.impl.recipe.StationShapedRecipe;
import net.modificationstation.stationapi.impl.recipe.StationShapelessRecipe;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InitializeEMCEvent {
    @EventListener(priority = ListenerPriority.HIGHEST)
    public void initializeEMC(RegistriesFrozenEvent event) {
        EMCGenesis.LOGGER.info("Loading EMC Definitions");
        StationAPI.EVENT_BUS.post(new EMCDefEvent());

        List<Object> recipes = CraftingRecipeManager.getInstance().getRecipes();
        EMCGenesis.LOGGER.info("Loading EMC from {} recipes", recipes.size());
        iterateOverRecipes(recipes);

        recalculateEMC();
    }

    private void iterateOverRecipes(List<Object> recipes) {
        for(Object rec : recipes) {
            var input = getInputs(rec);
            CraftingRecipe recipe = (CraftingRecipe) rec;

            ItemStack output = recipe.getOutput();
            if(shouldIgnoreRecipe(output)) continue;

            long EMC = calculateEMC(output, input);
            if(EMC == 0 && !retryRecipes.contains(recipe)) retryRecipes.add(recipe);
            else if(EMC != 0) retryRecipes.remove(recipe);

            handleRecipe(EMC, output);
        }
    }

    boolean wasEMCCalculated;
    private void recalculateEMC() {
        if(retryRecipes.isEmpty()) return;
        wasEMCCalculated = false;

        EMCGenesis.LOGGER.debug("Starting new thread for calculating EMC!");
        Thread thread = new Thread(() -> {
            EMCGenesis.LOGGER.info("Retrying to calculate EMC for {} recipes", retryRecipes.size());

            iterateOverRecipes(retryRecipes.stream().toList());

            if(!retryRecipes.isEmpty() && wasEMCCalculated) recalculateEMC();
            else if(!retryRecipes.isEmpty()) {
                EMCGenesis.LOGGER.error("Did not calculate new EMC but still having {} recipes unmapped!", retryRecipes.size());
                EMCGenesis.LOGGER.error("Unmapped items: {}", retryRecipes.stream().map(this::getOutputId).collect(Collectors.joining(", ")));
            }
        });
        thread.setName("EMCCalculation");
        thread.start();
    }

    ConcurrentLinkedQueue<Object> retryRecipes = new ConcurrentLinkedQueue<>();
    private void handleRecipe(long calculatedEMC, ItemStack output) {
        if(calculatedEMC == 0) return;
//            EMCGenesis.LOGGER.error("Item {} calculated EMC from recipe equals 0!", ItemUtil.toStringId(output.getItem()));

        long setEMC = EMCManager.ITEM.getEMC(output);

        if(setEMC == 0) {
            EMCGenesis.LOGGER.warn("Overwriting EMC for item {} based of its recipe!", ItemUtil.toStringId(output.getItem()));
            EMCGenesis.LOGGER.debug("This item has calculated EMC of {} ({}emc / {}count)", calculatedEMC, calculatedEMC * output.count, output.count);
            EMCGenesis.LOGGER.debug("This item set EMC is {}", setEMC);
            EMCGenesis.LOGGER.debug("------------------------------");

            EMCManager.ITEM.setEMC(output, calculatedEMC);
        }
    }

    private boolean shouldIgnoreRecipe(ItemStack output) {
        return ItemUtil.toStringId(output).equals("minecraft:torch") ||
                ItemUtil.toStringId(output).equals("minecraft:oak_pressure_plate") ||
                ItemUtil.toStringId(output).equals("minecraft:stone_pressure_plate") ||
                ItemUtil.toStringId(output).startsWith("minecraft:chainmail_");
    }

    private List<Either<TagKey<Item>, ItemStack>> getInputs(Object rec) {
        if(rec instanceof ShapedRecipe recipe) return Arrays.stream(recipe.input).map(Either::<TagKey<Item>, ItemStack>right).toList();
        if(rec instanceof ShapelessRecipe recipe) return ((List<ItemStack>) recipe.input).stream().map(Either::<TagKey<Item>, ItemStack>right).toList();
        if(rec instanceof StationShapedRecipe recipe) return Arrays.stream(recipe.getGrid()).toList();
        // TODO: Replace recipe.getInputs() in alpha.3
        if(rec instanceof StationShapelessRecipe recipe) return Arrays.stream(recipe.getInputs()).toList();
        return null;
    }

    private String getOutputId(Object rec) {
        if(rec instanceof ShapedRecipe recipe) return ItemUtil.toStringId(recipe.getOutput());
        if(rec instanceof ShapelessRecipe recipe) return ItemUtil.toStringId(recipe.getOutput());
        if(rec instanceof StationShapedRecipe recipe) return ItemUtil.toStringId(recipe.getOutput());
        if(rec instanceof StationShapelessRecipe recipe) return ItemUtil.toStringId(recipe.getOutput());
        return "Unknown Recipe!";
    }


    private long calculateEMC(ItemStack output, List<Either<TagKey<Item>, ItemStack>> grid) {
        AtomicLong emc = new AtomicLong();

        AtomicBoolean shouldContinue = new AtomicBoolean(true);
        for(var gridItem : grid) {
            if(gridItem == null) continue;
            if(!shouldContinue.get()) break;

            gridItem.ifLeft(itemTagKey -> {
                if(itemTagKey == null) return;
                if(!shouldContinue.get()) return;

                AtomicLong lowestEMC = new AtomicLong(Long.MAX_VALUE);
                ItemUtil.getItemsOfTag(itemTagKey).forEach(item -> {
                    long itemEMC = EMCManager.ITEM.getEMC(item);
                    if(itemEMC != 0 && lowestEMC.get() > itemEMC) lowestEMC.set(itemEMC);
                });

                if(lowestEMC.get() == Long.MAX_VALUE) {
                    EMCGenesis.LOGGER.debug("Recipe for {} has item tag without EMC!", output.getItem().getTranslatedName());
                    emc.set(0);
                    shouldContinue.set(false);
                    return;
                }
                emc.addAndGet(lowestEMC.get());
            });

            gridItem.ifRight(item -> {
                if(item == null) return;
                if(item.getItem() instanceof PhilosophersStone) return;
                if(!shouldContinue.get()) return;

                long itemEmc = EMCManager.ITEM.getEMC(item);
                if(itemEmc == 0) {
                    EMCGenesis.LOGGER.debug("Recipe for {} has item without EMC!", output.getItem().getTranslatedName());
                    emc.set(0);
                    shouldContinue.set(false);
                    return;
                }
                emc.addAndGet(itemEmc);
            });
        }

        if(emc.get() > 0) wasEMCCalculated = true;

        return emc.get() / output.count;
    }
}
