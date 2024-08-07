package net.mitask.emcgenesis.mixin;

import com.mojang.datafixers.util.Either;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.impl.recipe.StationShapelessRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StationShapelessRecipe.class)
public interface StationShapelessRecipeAccessor {
    @Accessor("ingredients")
    Either<TagKey<Item>, ItemStack>[] getIngredients();
}
