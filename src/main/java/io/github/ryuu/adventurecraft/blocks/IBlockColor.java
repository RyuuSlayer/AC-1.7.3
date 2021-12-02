package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.Level;
import net.minecraft.level.TileView;

public interface IBlockColor {

    void incrementColor(Level var1, int var2, int var3, int var4);

    int getColorMetaData(TileView iblockaccess, int i, int j, int k);

    void setColorMetaData(Level world, int i, int j, int k, int color);
}
