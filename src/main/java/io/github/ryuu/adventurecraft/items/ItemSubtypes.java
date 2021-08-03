package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.overrides.ck;
import net.minecraft.tile.Tile;

public class ItemSubtypes extends ck {
    public ItemSubtypes(int i) {
        super(i);
        e(0);
        a(true);
    }

    public int a(int i) {
        return Tile.m[this.bf].a(0, i);
    }

    public int b(int i) {
        return i;
    }
}
