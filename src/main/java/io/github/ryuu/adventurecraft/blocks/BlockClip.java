package io.github.ryuu.adventurecraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;

public class BlockClip extends Tile {

    protected BlockClip(int id, int tex, Material material) {
        super(id, tex, material);
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }
}
