package io.github.ryuu.adventurecraft.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.PlaceableTileItem;
import net.minecraft.tile.Tile;

public class ItemSubtypes extends MixinPlaceableTileItem {

    public ItemSubtypes(int id) {
        super(id);
        this.setDurability(0);
        this.method_457(true);
    }

    @Override
    public int getTexturePosition(int damage) {
        return Tile.BY_ID[this.id].getTextureForSide(0, damage);
    }

    @Override
    public int method_470(int i) {
        return i;
    }
}
