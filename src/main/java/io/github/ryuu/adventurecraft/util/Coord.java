package io.github.ryuu.adventurecraft.util;

public class Coord {
    public int x;

    public int y;

    public int z;

    public Coord() {
        set(0, 0, 0);
    }

    public Coord(int i, int j, int k) {
        set(i, j, k);
    }

    public void set(int i, int j, int k) {
        this.x = i;
        this.y = j;
        this.z = k;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Coord) {
            Coord otherCoord = (Coord)obj;
            return (this.x == otherCoord.x && this.y == otherCoord.y && this.z == otherCoord.z);
        }
        return false;
    }

    public void min(int i, int j, int k) {
        this.x = Math.min(this.x, i);
        this.y = Math.min(this.y, j);
        this.z = Math.min(this.z, k);
    }

    public void max(int i, int j, int k) {
        this.x = Math.max(this.x, i);
        this.y = Math.max(this.y, j);
        this.z = Math.max(this.z, k);
    }
}
