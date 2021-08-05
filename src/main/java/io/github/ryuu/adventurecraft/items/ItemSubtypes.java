package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.overrides.ck;
import net.minecraft.item.PlaceableTileItem;
import net.minecraft.tile.Tile;

public class ItemSubtypes extends PlaceableTileItem {
    public ItemSubtypes(int i) {
        super(i);
        setDurability(0);
        method_457(true);
    }

    @Override
    public int getTexturePosition(int i) {
        return Tile.BY_ID[this.id].getTextureForSide(0, i);
    }

    @Override
    public int method_470(int i) {
        return i;
    }
}
