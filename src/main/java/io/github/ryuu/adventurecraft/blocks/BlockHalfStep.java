package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.TileSounds;
import net.minecraft.util.maths.Box;

public class BlockHalfStep extends BlockSolid {

    protected BlockHalfStep(int i, int j, TileSounds sounds, int textureNum) {
        super(i, j, sounds);
        this.hardness(5.0f);
        ((ExTile) this).setSubTypes(16);
        ((ExTile) this).setTextureNum(textureNum);
    }

    @Override
    public int getTextureForSide(int side, int meta) {
        if (meta % 2 == 0) {
            this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        } else {
            this.setBoundingBox(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        meta = 2 * (meta / 2);
        if (side <= 1) {
            return this.tex + meta + 1;
        }
        return this.tex + meta;
    }

    @Override
    public boolean method_1618(TileView iblockaccess, int i, int j, int k, int l) {
        this.updateBlockBounds(iblockaccess, i, j, k);
        return super.method_1618(iblockaccess, i, j, k, l);
    }

    @Override
    public Box getCollisionShape(Level level, int x, int y, int z) {
        this.updateBlockBounds(level, x, y, z);
        return super.getCollisionShape(level, x, y, z);
    }

    private void updateBlockBounds(TileView iblockaccess, int i, int j, int k) {
        int metadata = iblockaccess.getTileMeta(i, j, k);
        if (metadata % 2 == 0) {
            this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        } else {
            this.setBoundingBox(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }
}
