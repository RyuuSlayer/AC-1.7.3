package io.github.ryuu.adventurecraft.blocks;

public class BlockTable extends BlockSolid {
    protected BlockTable(int i, int j) {
        super(i, j);
        setBoundingBox(0.0F, 0.875F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public int getTextureForSide(int i, int j) {
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
        return 33;
    }
}
