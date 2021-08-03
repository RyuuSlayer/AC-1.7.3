package io.github.ryuu.adventurecraft.blocks;

public class BlockTable extends BlockSolid {
    protected BlockTable(int i, int j) {
        super(i, j);
        a(0.0F, 0.875F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public int a(int i, int j) {
        if (i <= 1)
            return this.bm + j;
        return this.bm + 16 + j;
    }

    public boolean c() {
        return false;
    }

    public int b() {
        return 33;
    }
}
