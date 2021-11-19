package io.github.ryuu.adventurecraft.mixin.overrides;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.recipe.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Recipe.class)
public interface MixinRecipe {

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    boolean canCraft(CraftingInventory var1);

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    MixinItemInstance craft(CraftingInventory var1);

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    int getIngredientCount();

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    MixinItemInstance getOutput();
}
