package io.github.ryuu.adventurecraft.mixin.tile;

import java.util.Random;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(StoneTile.class)
public class MixinStoneTile extends BlockColor {

    public MixinStoneTile(int id, int texture) {
        super(id, texture, Material.STONE);
    }
}
