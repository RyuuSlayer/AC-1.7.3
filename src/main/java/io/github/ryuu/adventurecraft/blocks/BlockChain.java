package io.github.ryuu.adventurecraft.blocks;

public class BlockChain extends BlockRope {
    protected BlockChain(int i, int j) {
        super(i, j);
    }

    public int a(int i, int j) {
        return this.bm + i % 2 + j / 3 * 2;
    }
}
