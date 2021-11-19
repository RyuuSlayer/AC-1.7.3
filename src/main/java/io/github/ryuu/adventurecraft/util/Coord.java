package io.github.ryuu.adventurecraft.util;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

class Coord {

    public int x;

    public int y;

    public int z;

    public Coord() {
        this.set(0, 0, 0);
    }

    public Coord(int i, int j, int k) {
        this.set(i, j, k);
    }

    public void set(int i, int j, int k) {
        this.x = i;
        this.y = j;
        this.z = k;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Coord) {
            Coord otherCoord = (Coord) obj;
            return this.x == otherCoord.x && this.y == otherCoord.y && this.z == otherCoord.z;
        }
        return false;
    }

    public void min(int i, int j, int k) {
        this.x = Math.min((int) this.x, (int) i);
        this.y = Math.min((int) this.y, (int) j);
        this.z = Math.min((int) this.z, (int) k);
    }

    public void max(int i, int j, int k) {
        this.x = Math.max((int) this.x, (int) i);
        this.y = Math.max((int) this.y, (int) j);
        this.z = Math.max((int) this.z, (int) k);
    }
}
