package io.github.ryuu.adventurecraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class BlockPillar extends Tile implements IBlockColor {

    public BlockPillar(int i, int j) {
        super(i, j, Material.STONE);
    }

    @Override
    public int getTextureForSide(int side, int meta) {
        if (side == 1) {
            return this.tex - 16 + meta;
        }
        if (side == 0) {
            return this.tex + 16 + meta;
        }
        return this.tex + meta;
    }

    @Override
    public void incrementColor(Level world, int i, int j, int k) {
        int metadata = world.getTileMeta(i, j, k);
        world.setTileMeta(i, j, k, (metadata + 1) % subTypes[this.id]);
    }
}
