package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;

class BlockColor extends Tile implements IBlockColor {
    static final int numColors = 7;
    protected int defaultColor;

    protected BlockColor(int i, int j, Material material) {
        super(i, j, material);
        this.defaultColor = 13421772;
    }

    @Override
    public int getTint(TileView iblockaccess, int i, int j, int k) {
        int color = getColorMetaData(iblockaccess, i, j, k);
        if (color == 1) {
            color = 16775065;
        } else if (color == 2) {
            color = 16767663;
        } else if (color == 3) {
            color = 10736540;
        } else if (color == 4) {
            color = 9755639;
        } else if (color == 5) {
            color = 8880573;
        } else if (color == 6) {
            color = 15539236;
        } else {
            color = this.defaultColor;
        }
        return color;
    }

    protected int getColorMetaData(TileView iblockaccess, int i, int j, int k) {
        return iblockaccess.getTileMeta(i, j, k);
    }

    protected void setColorMetaData(Level world, int i, int j, int k, int color) {
        world.setTileMeta(i, j, k, color);
    }

    @Override
    public void incrementColor(Level world, int i, int j, int k) {
        int color = (getColorMetaData(world, i, j, k) + 1) % numColors;
        setColorMetaData(world, i, j, k, color);
    }
}
