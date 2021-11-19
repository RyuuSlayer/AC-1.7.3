package io.github.ryuu.adventurecraft.blocks;

public class BlockChain extends BlockRope {
    protected BlockChain(int i, int j) {
        super(i, j);
    }

    @Override
    public int getTextureForSide(int i, int j) {
        return this.tex + i % 2 + j / 3 * 2;
    }
}
