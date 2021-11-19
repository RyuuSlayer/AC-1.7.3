package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockSpike extends MixinTile {

    protected BlockSpike(int i) {
        super(i, 246, Material.METAL);
    }

    @Override
    public Box getCollisionShape(MixinLevel level, int x, int y, int z) {
        float f = 0.25f;
        return Box.getOrCreate((float) x + f, y, (float) z + f, (float) (x + 1) - f, (float) (y + 1) - f, (float) (z + 1) - f);
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public int method_1621() {
        return 32;
    }

    @Override
    public void onEntityCollision(MixinLevel world, int i, int j, int k, MixinEntity entity) {
        entity.damage(null, 10);
    }
}
