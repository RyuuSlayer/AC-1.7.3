package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockOverlay extends BlockColor {

    protected BlockOverlay(int i, int j) {
        super(i, j, Material.PLANT);
        this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 0.1f, 1.0f);
        this.hardness(5.0f);
        this.sounds(Tile.GRASS_SOUNDS);
        ((ExTile) this).setSubTypes(7);
        ((ExTile) this).setTextureNum(3);
    }

    @Override
    public int getTextureForSide(int side, int meta) {
        return this.tex + meta;
    }

    @Override
    public Box getCollisionShape(Level level, int x, int y, int z) {
        this.updateBounds(level, x, y, z);
        return null;
    }

    @Override
    public Box getOutlineShape(Level level, int x, int y, int z) {
        this.updateBounds(level, x, y, z);
        return super.getOutlineShape(level, x, y, z);
    }

    public void updateBounds(TileView world, int i, int j, int k) {
        if (world.isFullOpaque(i, j - 1, k)) {
            this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 0.01f, 1.0f);
        } else if (world.isFullOpaque(i, j + 1, k)) {
            this.setBoundingBox(0.0f, 0.99f, 0.0f, 1.0f, 1.0f, 1.0f);
        } else if (world.isFullOpaque(i - 1, j, k)) {
            this.setBoundingBox(0.0f, 0.0f, 0.0f, 0.01f, 1.0f, 1.0f);
        } else if (world.isFullOpaque(i + 1, j, k)) {
            this.setBoundingBox(0.99f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        } else if (world.isFullOpaque(i, j, k - 1)) {
            this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.01f);
        } else if (world.isFullOpaque(i, j, k + 1)) {
            this.setBoundingBox(0.0f, 0.0f, 0.99f, 1.0f, 1.0f, 1.0f);
        } else {
            this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 0.01f, 1.0f);
        }
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public int method_1621() {
        return 37;
    }

    @Override
    public void incrementColor(Level world, int i, int j, int k) {
        int metadata = world.getTileMeta(i, j, k);
        world.setTileMeta(i, j, k, (metadata + 1) % ExTile.subTypes[this.id]);
    }
}
