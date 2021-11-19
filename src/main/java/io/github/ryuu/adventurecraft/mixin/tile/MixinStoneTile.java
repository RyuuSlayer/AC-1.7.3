package io.github.ryuu.adventurecraft.mixin.tile;

import io.github.ryuu.adventurecraft.blocks.BlockColor;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;

import java.util.Random;

public class MixinStoneTile extends BlockColor {
    public StoneTile(int id, int texture) {
        super(id, texture, Material.STONE);
    }

    public int getDropId(int meta, Random rand) {
        return Tile.COBBLESTONE.id;
    }
}