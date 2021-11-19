package io.github.ryuu.adventurecraft.mixin.tile;

import net.minecraft.tile.TranslucentTile;
import net.minecraft.tile.material.Material;

import java.util.Random;

public class MixinGlassTile extends TranslucentTile {
    public GlassTile(int i, int j, Material material, boolean flag) {
        super(i, j, material, flag);
    }

    public int getDropCount(Random rand) {
        return 0;
    }

    public int method_1619() {
        return 1;
    }
}