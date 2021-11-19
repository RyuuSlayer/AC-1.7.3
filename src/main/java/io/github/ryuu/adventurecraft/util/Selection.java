package io.github.ryuu.adventurecraft.util;

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
        ItemCursor.minX = Math.min(this.oneX, this.twoX);
        ItemCursor.minY = Math.min(this.oneY, this.twoY);
        ItemCursor.minZ = Math.min(this.oneZ, this.twoZ);
        ItemCursor.maxX = Math.max(this.oneX, this.twoX);
        ItemCursor.maxY = Math.max(this.oneY, this.twoY);
        ItemCursor.maxZ = Math.max(this.oneZ, this.twoZ);
    }
}
