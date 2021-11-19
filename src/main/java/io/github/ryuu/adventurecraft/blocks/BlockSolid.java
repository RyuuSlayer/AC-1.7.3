package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.tile.material.Material;

public class BlockSolid extends MixinTile implements IBlockColor {

    public BlockSolid(int i, int j) {
        super(i, j, Material.STONE);
    }

    @Override
    public int getTextureForSide(int side, int meta) {
        return this.tex + meta;
    }

    @Override
    public void incrementColor(MixinLevel world, int i, int j, int k) {
        int metadata = world.getTileMeta(i, j, k);
        world.setTileMeta(i, j, k, (metadata + 1) % subTypes[this.id]);
    }
}
