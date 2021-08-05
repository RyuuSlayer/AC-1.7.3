package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;

public class BlockClip extends Tile {
    protected BlockClip(int i, int j, Material material) {
        super(i, j, material);
    }

    public boolean isFullOpaque() {
        return false;
    }

    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }
}
