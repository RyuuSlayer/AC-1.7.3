package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;

public class BlockTransparent extends BlockSolid {

    protected BlockTransparent(int i, int j, int subTypes) {
        super(i, j, Tile.GLASS_SOUNDS);
        this.hardness(5.0f);
        ((ExTile) this).setSubTypes(subTypes);
        ((ExTile) this).setTextureNum(2);
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public boolean method_1618(TileView iblockaccess, int i, int j, int k, int l) {
        int i1 = iblockaccess.getTileId(i, j, k);
        if (i1 == this.id) {
            return false;
        }
        return super.method_1618(iblockaccess, i, j, k, l);
    }
}
