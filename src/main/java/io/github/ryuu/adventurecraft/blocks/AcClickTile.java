package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.Level;

public interface AcClickTile {

    int alwaysUseClick(Level world, int i, int j, int k); // default: return -1
}
