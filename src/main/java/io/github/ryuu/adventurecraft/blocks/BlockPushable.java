package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.extensions.entity.ExFallingTile;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import net.minecraft.entity.FallingTile;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;

import java.util.Random;

public class BlockPushable extends BlockColor {

    public BlockPushable(int id, int tex, Material material) {
        super(id, tex, material);
        this.hardness(2.0f);
        this.blastResistance(10.0f);
        this.sounds(Tile.PISTON_SOUNDS);
        ((ExTile) this).setTextureNum(3);
    }

    public static boolean canFallBelow(Level world, int i, int j, int k) {
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

    @Override
    public void method_1611(Level level, int x, int y, int z) {
        level.method_216(x, y, z, this.id, this.getTickrate());
    }

    @Override
    public void method_1609(Level level, int x, int y, int z, int id) {
        level.method_216(x, y, z, this.id, this.getTickrate());
    }

    @Override
    public void onScheduledTick(Level level, int x, int y, int z, Random rand) {
        this.tryToFall(level, x, y, z);
    }

    private void tryToFall(Level world, int i, int j, int k) {
        if (BlockPushable.canFallBelow(world, i, j - 1, k) && j >= 0) {
            FallingTile entityfallingsand = new FallingTile(world, (float) i + 0.5f, (float) j + 0.5f, (float) k + 0.5f, this.id);
            ((ExFallingTile) entityfallingsand).setMetadata(world.getTileMeta(i, j, k));
            world.spawnEntity(entityfallingsand);
        }
    }

    @Override
    public int getTickrate() {
        return 3;
    }
}
