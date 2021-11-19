package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.TileView;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockDarkness extends MixinTile {

    protected BlockDarkness(int i, int j) {
        super(i, j, Material.AIR);
        this.method_1590(2);
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public Box getCollisionShape(MixinLevel level, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }
}
