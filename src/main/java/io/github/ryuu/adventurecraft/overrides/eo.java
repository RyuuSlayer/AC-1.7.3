package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.blocks.BlockColor;

import java.util.Random;

public class eo extends BlockColor {
    public eo(int i, int j) {
        super(i, j, ln.e);
    }

    public int a(int i, Random random) {
        return Tile.x.bn;
    }
}
