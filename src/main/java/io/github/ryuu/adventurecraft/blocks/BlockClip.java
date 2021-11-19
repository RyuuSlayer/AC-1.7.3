package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.TileView;
import net.minecraft.tile.material.Material;

public class BlockClip extends MixinTile {

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
