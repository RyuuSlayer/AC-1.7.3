package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import net.minecraft.entity.LivingEntity;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.MathsHelper;

public class BlockChair extends BlockSolid {

    protected BlockChair(int i, int j) {
        super(i, j);
        this.setBoundingBox(0.125f, 0.5f, 0.125f, 0.875f, 0.625f, 0.875f);
        this.hardness(5.0f);
        this.sounds(Tile.WOOD_SOUNDS);
        ((ExTile) this).setSubTypes(16);
        ((ExTile) this).setTextureNum(3);
    }

    @Override
    public int getTextureForSide(int side, int meta) {
        meta /= 4;
        if (side <= 1) {
            return this.tex + meta;
        }
        return this.tex + 16 + meta;
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public int method_1621() {
        return 34;
    }

    @Override
    public void afterPlaced(Level world, int i, int j, int k, LivingEntity entityliving) {
        int meta = world.getTileMeta(i, j, k);
        int l = MathsHelper.floor((double) (entityliving.yaw * 4.0f / 360.0f) + 0.5) & 3;
        world.setTileMeta(i, j, k, meta + (l + 1) % 4);
    }
}
