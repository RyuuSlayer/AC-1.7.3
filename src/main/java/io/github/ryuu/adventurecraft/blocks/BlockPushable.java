package io.github.ryuu.adventurecraft.blocks;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class BlockPushable extends BlockColor {

    public BlockPushable(int id, int tex, Material material) {
        super(id, tex, material);
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
            entityfallingsand.metadata = world.getTileMeta(i, j, k);
            world.spawnEntity(entityfallingsand);
        }
    }

    @Override
    public int getTickrate() {
        return 3;
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
}
