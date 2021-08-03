package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.TileView;

public class BlockTransparent extends BlockSolid {
    protected BlockTransparent(int i, int j) {
        super(i, j);
    }

    public boolean c() {
        return false;
    }

    public boolean b(TileView iblockaccess, int i, int j, int k, int l) {
        int i1 = iblockaccess.a(i, j, k);
        if (i1 == this.bn)
            return false;
        return super.b(iblockaccess, i, j, k, l);
    }
}
