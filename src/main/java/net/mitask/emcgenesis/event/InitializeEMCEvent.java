package net.mitask.emcgenesis.event;

import com.mojang.datafixers.util.Either;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.ShapedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipeManager;
import net.minecraft.recipe.ShapelessRecipe;
import net.mitask.emcgenesis.EMCGenesis;
import net.mitask.emcgenesis.api.EMCManager;
import net.mitask.emcgenesis.api.def.VanillaEMCDef;
import net.mitask.emcgenesis.mixin.ShapedRecipeAccessor;
import net.mitask.emcgenesis.mixin.ShapelessRecipeAccessor;
import net.mitask.emcgenesis.mixin.StationShapedRecipeAccessor;
import net.mitask.emcgenesis.mixin.StationShapelessRecipeAccessor;
import net.mitask.emcgenesis.util.ItemUtil;
import net.modificationstation.stationapi.api.event.registry.RegistriesFrozenEvent;
import net.modificationstation.stationapi.api.recipe.StationRecipe;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.impl.recipe.StationShapedRecipe;
import net.modificationstation.stationapi.impl.recipe.StationShapelessRecipe;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class InitializeEMCEvent {
    @EventListener
    public void initializeEMC(RegistriesFrozenEvent event) {
        EMCGenesis.LOGGER.info("Loading EMC Def");
        EMCManager.addDef(new VanillaEMCDef());

        List<StationRecipe> recipes = CraftingRecipeManager.getInstance().getRecipes();
        EMCGenesis.LOGGER.info("Loading EMC from " + recipes.size() + " recipes");
        for(StationRecipe rec : recipes) {
            if(rec instanceof ShapedRecipe recipe) handleShapedRecipe(recipe);
            if(rec instanceof ShapelessRecipe recipe) handleShapelessRecipe(recipe);
            if(rec instanceof StationShapedRecipe recipe) handleShapedRecipe(recipe);
            if(rec instanceof StationShapelessRecipe recipe) handleShapelessRecipe(recipe);
        }
    }

    private void handleRecipe(long calculatedEMC, ItemStack output) {
        long setEMC = EMCManager.ITEM.getEMC(output);

        if(setEMC != calculatedEMC) {
            System.out.println("ShapedRecipe - " + ItemUtil.toStringId(output.getItem()));
            System.out.println("This item has calculated EMC of " + calculatedEMC + " (" + calculatedEMC * output.count + "emc / " + output.count + "count)");
            System.out.println("This item set EMC is " + setEMC);
            System.out.println("------------------------------");
        }
    }

    private void handleShapedRecipe(ShapedRecipe recipe) {
        List<ItemStack> input = Arrays.stream(((ShapedRecipeAccessor) recipe).getInput()).toList();
        List<Either<TagKey<Item>, ItemStack>> grid = input.stream().map(Either::<TagKey<Item>, ItemStack>right).toList();
        ItemStack output = recipe.getOutput();
        handleRecipe(calculateEMC(output, grid), output);
    }

    private void handleShapelessRecipe(ShapelessRecipe recipe) {
        List<ItemStack> input = ((ShapelessRecipeAccessor) recipe).getInput();
        List<Either<TagKey<Item>, ItemStack>> grid = input.stream().map(Either::<TagKey<Item>, ItemStack>right).toList();
        ItemStack output = recipe.getOutput();
        handleRecipe(calculateEMC(output, grid), output);
    }

    private void handleShapedRecipe(StationShapedRecipe recipe) {
        List<Either<TagKey<Item>, ItemStack>> grid = Arrays.stream(((StationShapedRecipeAccessor) recipe).getGrid()).toList();
        ItemStack output = recipe.getOutput();
        handleRecipe(calculateEMC(output, grid), output);
    }

    private void handleShapelessRecipe(StationShapelessRecipe recipe) {
        List<Either<TagKey<Item>, ItemStack>> grid = Arrays.stream(((StationShapelessRecipeAccessor) recipe).getIngredients()).toList();
        ItemStack output = recipe.getOutput();
        handleRecipe(calculateEMC(output, grid), output);
    }

    private long calculateEMC(ItemStack output, List<Either<TagKey<Item>, ItemStack>> grid) {
        AtomicLong emc = new AtomicLong();
        for(var gridItem : grid) {
            if(gridItem == null) continue;
            gridItem.ifLeft(itemTagKey -> {
                if(itemTagKey == null) {
                    System.out.println("tagKey == null for item " + output.getItem().getTranslatedName());
                    return;
                }
                ItemUtil.getItemsOfTag(itemTagKey).forEach(item -> {
                    long itemEmc = EMCManager.ITEM.getEMC(item);
                    if (itemEmc == 0) {
                        System.out.println("This recipe has item without EMC! Returning!");
                        return;
                    }
                    emc.addAndGet(itemEmc);
                });
            });
            gridItem.ifRight(item -> {
                if(item == null) {
                    System.out.println("item == null for item " + output.getItem().getTranslatedName());
                    return;
                }
                long itemEmc = EMCManager.ITEM.getEMC(item);
                if(itemEmc == 0) {
                    System.out.println("This recipe has item without EMC! Returning!");
                    return;
                }
                emc.addAndGet(itemEmc);
            });
        }
        return emc.get() / output.count;
    }
}
