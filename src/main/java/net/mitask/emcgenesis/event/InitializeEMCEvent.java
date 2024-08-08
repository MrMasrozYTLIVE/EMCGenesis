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
import net.mitask.emcgenesis.util.ItemUtil;
import net.modificationstation.stationapi.api.event.registry.RegistriesFrozenEvent;
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

        List recipes = CraftingRecipeManager.getInstance().getRecipes();
        EMCGenesis.LOGGER.info("Loading EMC from {} recipes", recipes.size());
        for(Object rec : recipes) {
            if(rec instanceof ShapedRecipe recipe) handleShapedRecipe(recipe);
            if(rec instanceof ShapelessRecipe recipe) handleShapelessRecipe(recipe);
            if(rec instanceof StationShapedRecipe recipe) handleShapedRecipe(recipe);
            if(rec instanceof StationShapelessRecipe recipe) handleShapelessRecipe(recipe);
        }
    }

    private void handleRecipe(long calculatedEMC, ItemStack output) {
//        I will actually let it override EMC to 0, so I can know if something is wrong
//        if(calculatedEMC == 0) return;

        long setEMC = EMCManager.ITEM.getEMC(output);

        if(setEMC != calculatedEMC) {
            EMCGenesis.LOGGER.warn("Overwriting EMC for item {} based of its recipe!", ItemUtil.toStringId(output.getItem()));
            EMCGenesis.LOGGER.warn("This item has calculated EMC of {} ({}emc / {}count)", calculatedEMC, calculatedEMC * output.count, output.count);
            EMCGenesis.LOGGER.warn("This item set EMC is {}", setEMC);
            EMCGenesis.LOGGER.warn("------------------------------");

            EMCManager.ITEM.setEMC(output, setEMC);
        }
    }

    private boolean shouldIgnoreRecipe(ItemStack output) {
        return ItemUtil.toStringId(output).equals("minecraft:wool") ||
                ItemUtil.toStringId(output).equals("minecraft:dye") ||
                ItemUtil.toStringId(output).equals("minecraft:torch") ||
                ItemUtil.toStringId(output).equals("minecraft:oak_pressure_plate") ||
                ItemUtil.toStringId(output).equals("minecraft:stone_pressure_plate") ||
                ItemUtil.toStringId(output).startsWith("minecraft:chainmail_");
    }

    private void handleShapedRecipe(ShapedRecipe recipe) {
        List<ItemStack> input = Arrays.stream(recipe.input).toList();
        ItemStack output = recipe.getOutput();
        if(shouldIgnoreRecipe(output)) return;

        handleRecipe(calculateEMCVanilla(output, input), output);
    }

    private void handleShapelessRecipe(ShapelessRecipe recipe) {
        List<ItemStack> input = recipe.input;
        ItemStack output = recipe.getOutput();
        if(shouldIgnoreRecipe(output)) return;

        handleRecipe(calculateEMCVanilla(output, input), output);
    }

    private void handleShapedRecipe(StationShapedRecipe recipe) {
        List<Either<TagKey<Item>, ItemStack>> grid = Arrays.stream(recipe.getGrid()).toList();
        ItemStack output = recipe.getOutput();
        if(shouldIgnoreRecipe(output)) return;

        handleRecipe(calculateEMC(output, grid), output);
    }

    // TODO: Replace recipe.getInputs() in alpha.3
    private void handleShapelessRecipe(StationShapelessRecipe recipe) {
        List<Either<TagKey<Item>, ItemStack>> grid = Arrays.stream(recipe.getInputs()).toList();
        ItemStack output = recipe.getOutput();
        if(shouldIgnoreRecipe(output)) return;

        handleRecipe(calculateEMC(output, grid), output);
    }

    private long calculateEMCVanilla(ItemStack output, List<ItemStack> input) {
        long emc = 0;
        for(ItemStack itemStack : input) {
            if(itemStack == null) continue;

            long itemEmc = EMCManager.ITEM.getEMC(itemStack);
            if(itemEmc == 0) {
                EMCGenesis.LOGGER.error("Recipe for {} has item without EMC! Returning!", output.getItem().getTranslatedName());
                break;
            }

            emc += itemEmc;
        }
        return emc / output.count;
    }

    private long calculateEMC(ItemStack output, List<Either<TagKey<Item>, ItemStack>> grid) {
        AtomicLong emc = new AtomicLong();
        for(var gridItem : grid) {
            if(gridItem == null) continue;

            gridItem.ifLeft(itemTagKey -> {
                if(itemTagKey == null) return;

                ItemUtil.getItemsOfTag(itemTagKey).forEach(item -> {
                    long itemEmc = EMCManager.ITEM.getEMC(item);
                    if (itemEmc == 0) {
                        EMCGenesis.LOGGER.error("Recipe for {} has item without EMC! Returning!", output.getItem().getTranslatedName());
                        return;
                    }
                    emc.addAndGet(itemEmc);
                });
            });

            gridItem.ifRight(item -> {
                if(item == null) return;

                long itemEmc = EMCManager.ITEM.getEMC(item);
                if(itemEmc == 0) {
                    EMCGenesis.LOGGER.error("Recipe for {} has item without EMC! Returning!", output.getItem().getTranslatedName());
                    return;
                }
                emc.addAndGet(itemEmc);
            });
        }
        return emc.get() / output.count;
    }
}
