package io.github.ryuu.adventurecraft.extensions.tile;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import net.minecraft.tile.Tile;

public interface ExLadderTile {

    static boolean isLadderID(int bID) {
        return bID == Tile.LADDER.id ||
                bID == Blocks.ladders1.id ||
                bID == Blocks.ladders2.id ||
                bID == Blocks.ladders3.id ||
                bID == Blocks.ladders4.id;
    }
}
