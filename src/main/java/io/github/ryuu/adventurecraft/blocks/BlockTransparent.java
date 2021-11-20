package io.github.ryuu.adventurecraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.level.TileView;

public class BlockTransparent extends BlockSolid {

    protected BlockTransparent(int i, int j) {
        super(i, j);
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
