package io.github.ryuu.adventurecraft.mixin.item;

import net.minecraft.item.ItemType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemType.class)
public interface AccessorItemType {

    @Invoker
    ItemType invokeSetDurability(int durability);
}
