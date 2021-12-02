package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;

public class BlockColor extends Tile implements IBlockColor {

    static final int numColors = 7;

    protected int defaultColor = 0xCCCCCC;

    public BlockColor(int id, int tex, Material material) {
        super(id, tex, material);
    }

    @Override
    public int getTint(TileView iblockaccess, int i, int j, int k) {
        int color = this.getColorMetaData(iblockaccess, i, j, k);
        color = color == 1 ? 0xFFF799 : (color == 2 ? 0xFFDAAF : (color == 3 ? 10736540 : (color == 4 ? 9755639 : (color == 5 ? 8880573 : (color == 6 ? 15539236 : this.defaultColor)))));
        return color;
    }

    @Override
    public int getColorMetaData(TileView iblockaccess, int i, int j, int k) {
        return iblockaccess.getTileMeta(i, j, k);
    }

    @Override
    public void setColorMetaData(Level world, int i, int j, int k, int color) {
        world.setTileMeta(i, j, k, color);
    }

    @Override
    public void incrementColor(Level world, int i, int j, int k) {
        int color = (this.getColorMetaData(world, i, j, k) + 1) % numColors;
        this.setColorMetaData(world, i, j, k, color);
    }
}
