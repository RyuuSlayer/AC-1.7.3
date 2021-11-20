package io.github.ryuu.adventurecraft.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

class Selection {

    int oneX;

    int oneY;

    int oneZ;

    int twoX;

    int twoY;

    int twoZ;

    Selection() {
    }

    void record() {
        this.oneX = ItemCursor.oneX;
        this.oneY = ItemCursor.oneY;
        this.oneZ = ItemCursor.oneZ;
        this.twoX = ItemCursor.twoX;
        this.twoY = ItemCursor.twoY;
        this.twoZ = ItemCursor.twoZ;
    }

    void load() {
        ItemCursor.oneX = this.oneX;
        ItemCursor.oneY = this.oneY;
        ItemCursor.oneZ = this.oneZ;
        ItemCursor.twoX = this.twoX;
        ItemCursor.twoY = this.twoY;
        ItemCursor.twoZ = this.twoZ;
        ItemCursor.minX = Math.min((int) this.oneX, (int) this.twoX);
        ItemCursor.minY = Math.min((int) this.oneY, (int) this.twoY);
        ItemCursor.minZ = Math.min((int) this.oneZ, (int) this.twoZ);
        ItemCursor.maxX = Math.max((int) this.oneX, (int) this.twoX);
        ItemCursor.maxY = Math.max((int) this.oneY, (int) this.twoY);
        ItemCursor.maxZ = Math.max((int) this.oneZ, (int) this.twoZ);
    }
}
