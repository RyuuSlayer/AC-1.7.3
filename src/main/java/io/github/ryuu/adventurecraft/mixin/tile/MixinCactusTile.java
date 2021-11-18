package io.github.ryuu.adventurecraft.mixin.tile;

import net.minecraft.entity.Entity;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

import java.util.Random;

public class MixinCactusTile extends Tile {
    protected CactusTile(int id, int j) {
        super(id, j, Material.CACTUS);
        this.setTicksRandomly(true);
    }

    public void onScheduledTick(Level level, int x, int y, int z, Random rand) {
    }

    public Box getCollisionShape(Level level, int x, int y, int z) {
        float f = 0.0625f;
        return Box.getOrCreate((float)x + f, y, (float)z + f, (float)(x + 1) - f, (float)(y + 1) - f, (float)(z + 1) - f);
    }

    public Box getOutlineShape(Level level, int x, int y, int z) {
        float f = 0.0625f;
        return Box.getOrCreate((float)x + f, y, (float)z + f, (float)(x + 1) - f, y + 1, (float)(z + 1) - f);
    }

    public int getTextureForSide(int side) {
        if (side == 1) {
            return this.tex - 1;
        }
        if (side == 0) {
            return this.tex + 1;
        }
        return this.tex;
    }

    public boolean isFullCube() {
        return false;
    }

    public boolean isFullOpaque() {
        return false;
    }

    public int method_1621() {
        return 13;
    }

    public boolean canPlaceAt(Level level, int x, int y, int z) {
        if (!super.canPlaceAt(level, x, y, z)) {
            return false;
        }
        return this.isValidPosition(level, x, y, z);
    }

    public void method_1609(Level level, int x, int y, int z, int id) {
        if (!this.isValidPosition(level, x, y, z)) {
            this.drop(level, x, y, z, level.getTileMeta(x, y, z));
            level.setTile(x, y, z, 0);
        }
    }

    public boolean isValidPosition(Level world, int i, int j, int k) {
        if (world.getMaterial(i - 1, j, k).isSolid()) {
            return false;
        }
        if (world.getMaterial(i + 1, j, k).isSolid()) {
            return false;
        }
        if (world.getMaterial(i, j, k - 1).isSolid()) {
            return false;
        }
        if (world.getMaterial(i, j, k + 1).isSolid()) {
            return false;
        }
        int l = world.getTileId(i, j - 1, k);
        return l == Tile.CACTUS.id || l == Tile.SAND.id;
    }

    public void onEntityCollision(Level world, int i, int j, int k, Entity entity) {
        entity.damage(null, 1);
    }
}