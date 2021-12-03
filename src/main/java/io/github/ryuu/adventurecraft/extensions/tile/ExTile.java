package io.github.ryuu.adventurecraft.extensions.tile;

import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;

public interface ExTile {

    int[] subTypes = new int[256];

    int alwaysUseClick(Level world, int i, int j, int k);

    int getTextureNum();

    Tile setTextureNum(int t);

    boolean shouldRender(TileView blockAccess, int i, int j, int k);

    Tile setSubTypes(int i);

    boolean canBeTriggered();

    void addTriggerActivation(Level world, int i, int j, int k);

    void removeTriggerActivation(Level world, int i, int j, int k);

    void onTriggerActivated(Level world, int i, int j, int k);

    void onTriggerDeactivated(Level world, int i, int j, int k);

    void reset(Level world, int i, int j, int k, boolean death);

    int getBlockLightValue(TileView iblockaccess, int i, int j, int k);
}
