package io.github.ryuu.adventurecraft.extensions.tile;

import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;

public interface ExTile {

    int adventurecraft$alwaysUseClick(Level world, int i, int j, int k);

    int getTextureNum();

    Tile adventurecraft$setTextureNum(int t);

    boolean adventurecraft$shouldRender(TileView blockAccess, int i, int j, int k);

    Tile adventurecraft$setSubTypes(int i);

    boolean canBeTriggered();

    void addTriggerActivation(Level world, int i, int j, int k);

    void removeTriggerActivation(Level world, int i, int j, int k);

    void onTriggerActivated(Level world, int i, int j, int k);

    void onTriggerDeactivated(Level world, int i, int j, int k);

    void reset(Level world, int i, int j, int k, boolean death);
}
