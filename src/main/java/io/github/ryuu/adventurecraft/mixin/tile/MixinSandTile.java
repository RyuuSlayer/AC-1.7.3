package io.github.ryuu.adventurecraft.mixin.tile;

import io.github.ryuu.adventurecraft.blocks.IBlockColor;
import net.minecraft.entity.FallingTile;
import net.minecraft.level.Level;
import net.minecraft.tile.SandTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;

import java.util.Random;

public class MixinSandTile extends Tile implements IBlockColor {
    public static boolean fallInstantly = false;

    public SandTile(int id, int j) {
        super(id, j, Material.SAND);
    }

    public void method_1611(Level level, int x, int y, int z) {
        level.method_216(x, y, z, this.id, this.getTickrate());
    }

    public void method_1609(Level level, int x, int y, int z, int id) {
        level.method_216(x, y, z, this.id, this.getTickrate());
    }

    public void onScheduledTick(Level level, int x, int y, int z, Random rand) {
        this.method_436(level, x, y, z);
    }

    private void method_436(Level world, int i, int j, int k) {
        int l = i;
        int i1 = j;
        int j1 = k;
        if (SandTile.method_435(world, l, i1 - 1, j1) && i1 >= 0) {
            int byte0 = 32;
            if (fallInstantly || !world.isRegionLoaded(i - byte0, j - byte0, k - byte0, i + byte0, j + byte0, k + byte0)) {
                world.setTile(i, j, k, 0);
                while (SandTile.method_435(world, i, j - 1, k) && j > 0) {
                    --j;
                }
                if (j > 0) {
                    world.setTile(i, j, k, this.id);
                }
            } else {
                int metadata = world.getTileMeta(i, j, k);
                FallingTile entityfallingsand = new FallingTile(world, (float)i + 0.5f, (float)j + 0.5f, (float)k + 0.5f, this.id);
                entityfallingsand.metadata = metadata;
                world.spawnEntity(entityfallingsand);
            }
        }
    }

    public int getTickrate() {
        return 3;
    }

    public static boolean method_435(Level world, int i, int j, int k) {
        int l = world.getTileId(i, j, k);
        if (l == 0) {
            return true;
        }
        if (l == Tile.FIRE.id) {
            return true;
        }
        Material material = Tile.BY_ID[l].material;
        if (material == Material.WATER) {
            return true;
        }
        return material == Material.LAVA;
    }

    public int getTextureForSide(int side, int meta) {
        if (meta == 0) {
            return this.tex;
        }
        return 228 + meta - 1;
    }

    public void incrementColor(Level world, int i, int j, int k) {
        if (subTypes[this.id] > 0) {
            int metadata = world.getTileMeta(i, j, k);
            world.setTileMeta(i, j, k, (metadata + 1) % subTypes[this.id]);
        }
    }
}