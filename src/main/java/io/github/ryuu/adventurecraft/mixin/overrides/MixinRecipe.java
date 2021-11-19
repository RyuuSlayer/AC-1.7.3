/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package io.github.ryuu.adventurecraft.mixin.overrides;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemInstance;
import io.github.ryuu.adventurecraft.mixin.item.MixinItemInstance;
import net.minecraft.recipe.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Recipe.class)
public interface MixinRecipe {

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean canCraft(CraftingInventory var1);

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemInstance craft(CraftingInventory var1);

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getIngredientCount();

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemInstance getOutput();
}
