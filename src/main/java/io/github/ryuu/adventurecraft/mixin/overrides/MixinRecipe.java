package io.github.ryuu.adventurecraft.mixin.overrides;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Recipe.class)
public interface MixinRecipe {
}
