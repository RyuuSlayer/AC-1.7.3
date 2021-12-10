package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.Level;

public interface AcResetTile {

    void reset(Level world, int i, int j, int k, boolean death);
}
