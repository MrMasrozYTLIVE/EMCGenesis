package net.mitask.emcgenesis.mixin;

import com.mojang.datafixers.util.Either;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.impl.recipe.StationShapedRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StationShapedRecipe.class)
public interface StationShapedRecipeAccessor {
    @Accessor("grid")
    Either<TagKey<Item>, ItemStack>[] getGrid();
}
