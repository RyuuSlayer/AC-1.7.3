package io.github.ryuu.adventurecraft.mixin.tile;

import net.minecraft.tile.GrassTile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GrassTile.class)
public interface AccessGrassTile {

    @Invoker("<init>")
    static GrassTile newGrassTile(int i) {
        throw new AssertionError();
    }
}
