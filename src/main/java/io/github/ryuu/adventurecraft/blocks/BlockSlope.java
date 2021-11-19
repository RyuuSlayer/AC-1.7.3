package io.github.ryuu.adventurecraft.blocks;

public class BlockSlope extends BlockStairMulti {

    protected BlockSlope(int i, MixinTile block, int textureID) {
        super(i, block, textureID);
    }

    @Override
    public int method_1621() {
        return 38;
    }
}
