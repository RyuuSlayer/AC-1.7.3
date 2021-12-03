package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;

public class BlockBombable extends BlockColor {

    public BlockBombable(int id, int tex, Material material, float hardness) {
        super(id, tex, material);
        this.hardness(hardness);
        this.blastResistance(10.0f);
        this.sounds(Tile.PISTON_SOUNDS);
    }
}
