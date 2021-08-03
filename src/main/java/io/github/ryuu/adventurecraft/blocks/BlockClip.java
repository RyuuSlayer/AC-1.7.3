package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;

public class BlockClip extends Tile {
    protected BlockClip(int i, int j, Material material) {
        super(i, j, material);
    }

    public boolean c() {
        return false;
    }

    public boolean shouldRender(xp blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }
}
