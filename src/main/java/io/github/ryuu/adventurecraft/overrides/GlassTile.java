package io.github.ryuu.adventurecraft.overrides;

import net.minecraft.tile.TranslucentTile;
import net.minecraft.tile.material.Material;

import java.util.Random;

public class GlassTile extends TranslucentTile {
    public GlassTile(int i, int j, Material material, boolean flag) {
        super(i, j, material, flag);
    }

    public int a(Random random) {
        return 0;
    }

    public int b_() {
        return 1;
    }
}
