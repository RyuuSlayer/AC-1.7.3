package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;

public class BlockSolid extends Tile implements IBlockColor {
    public BlockSolid(int i, int j) {
        super(i, j, Material.STONE);
    }

    @Override
    public int getTextureForSide(int i, int j) {
        return this.tex + j;
    }

    @Override
    public void incrementColor(Level world, int i, int j, int k) {
        int metadata = world.getTileMeta(i, j, k);
        world.setTileMeta(i, j, k, (metadata + 1) % subTypes[this.id]);
    }
}
