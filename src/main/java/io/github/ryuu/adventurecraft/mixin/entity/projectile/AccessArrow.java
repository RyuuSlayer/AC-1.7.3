package io.github.ryuu.adventurecraft.mixin.entity.projectile;

import net.minecraft.entity.projectile.Arrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Arrow.class)
public interface AccessArrow {

    @Accessor
    int getXTile();

    @Accessor
    int getYTile();

    @Accessor
    int getZTile();

    @Accessor
    int getInTile();
}
