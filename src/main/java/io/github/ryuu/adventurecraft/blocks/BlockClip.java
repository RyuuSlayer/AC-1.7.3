package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;

public class BlockClip extends Tile implements AcRenderConditionTile {

    protected BlockClip(int id, int tex, Material material) {
        super(id, tex, material);
        this.hardness(5.0f);
        this.sounds(Tile.METAL_SOUNDS);
        ((ExTile) this).setTextureNum(2);
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
