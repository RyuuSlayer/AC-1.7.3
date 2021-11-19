package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.entity.LivingEntity;
import net.minecraft.level.Level;
import net.minecraft.util.maths.MathsHelper;

public class BlockChair extends BlockSolid {
    protected BlockChair(int i, int j) {
        super(i, j);
        setBoundingBox(0.125F, 0.5F, 0.125F, 0.875F, 0.625F, 0.875F);
    }

    @Override
    public int getTextureForSide(int i, int j) {
        j /= 4;
        if (i <= 1)
            return this.tex + j;
        return this.tex + 16 + j;
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
        int l = MathsHelper.floor((entityliving.yaw * 4.0F / 360.0F) + 0.5D) & 0x3;
        world.setTileMeta(i, j, k, meta + (l + 1) % 4);
    }
}
