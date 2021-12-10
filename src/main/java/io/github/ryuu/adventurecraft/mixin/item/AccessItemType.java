package io.github.ryuu.adventurecraft.mixin.item;

import net.minecraft.item.ItemType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemType.class)
public interface AccessItemType {

    @Accessor
    void setField_402(int value);
}
