package io.github.ryuu.adventurecraft.extensions.tile;

import io.github.ryuu.adventurecraft.blocks.*;
import net.minecraft.tile.Tile;

public interface ExTile extends AcTriggerTile, AcTriggerHostTile, AcResetTile, AcRenderConditionTile, AcLightTile, AcClickTile {

    int[] subTypes = new int[256];

    int getTextureNum();

    Tile setTextureNum(int t);

    Tile setSubTypes(int i);
}
