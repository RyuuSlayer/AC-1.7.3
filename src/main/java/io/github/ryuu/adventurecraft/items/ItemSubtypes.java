package io.github.ryuu.adventurecraft.items;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.PlaceableTileItem;
import net.minecraft.tile.Tile;

public class ItemSubtypes extends PlaceableTileItem {

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
