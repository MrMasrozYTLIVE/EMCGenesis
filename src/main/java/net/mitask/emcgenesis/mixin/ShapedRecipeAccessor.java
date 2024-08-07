package net.mitask.emcgenesis.mixin;

import net.minecraft.ShapedRecipe;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ShapedRecipe.class)
public interface ShapedRecipeAccessor {
    @Accessor("input")
    ItemStack[] getInput();
}
