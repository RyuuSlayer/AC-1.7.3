package io.github.ryuu.adventurecraft.mixin.entity.player;

import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PlayerInventory.class)
public interface AccessPlayerInventory {

    @Invoker
    int invokeGetSlotWithItem(int i);
}
