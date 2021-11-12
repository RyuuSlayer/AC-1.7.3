package io.github.ryuu.adventurecraft.mixin;

import io.github.ryuu.adventurecraft.blocks.BlockColor;

import java.util.Random;

public class MixinStoneTile extends BlockColor {
    public MixinStoneTile(int i, int j) {
        super(i, j, ln.e);
    }

    public int a(int i, Random random) {
        return MixinTile.x.bn;
    }
}
