package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.TileView;

public interface AcRenderConditionTile {

    boolean shouldRender(TileView blockAccess, int i, int j, int k); // default: return true
}
