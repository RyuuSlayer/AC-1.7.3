package io.github.ryuu.adventurecraft.mixin.tile;

import io.github.ryuu.adventurecraft.blocks.BlockColor;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(StoneTile.class)
public class MixinStoneTile extends BlockColor {

    public MixinStoneTile(int id, int texture) {
        super(id, texture, Material.STONE);
    }
}
