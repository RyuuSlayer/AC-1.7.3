package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockOverlay extends Tile implements IBlockColor {
    protected BlockOverlay(int i, int j) {
        super(i, j, Material.PLANT);
        setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.1F, 1.0F);
    }

    @Override
    public int getTextureForSide(int i, int j) {
        return this.tex + j;
    }

    @Override
    public Box getCollisionShape(Level world, int i, int j, int k) {
        updateBounds(world, i, j, k);
        return null;
    }

    @Override
    public Box getOutlineShape(Level world, int i, int j, int k) {
        updateBounds(world, i, j, k);
        return super.getOutlineShape(world, i, j, k);
    }

    public void updateBounds(TileView world, int i, int j, int k) {
        if (world.isFullOpaque(i, j - 1, k)) {
            setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.01F, 1.0F);
        } else if (world.isFullOpaque(i, j + 1, k)) {
            setBoundingBox(0.0F, 0.99F, 0.0F, 1.0F, 1.0F, 1.0F);
        } else if (world.isFullOpaque(i - 1, j, k)) {
            setBoundingBox(0.0F, 0.0F, 0.0F, 0.01F, 1.0F, 1.0F);
        } else if (world.isFullOpaque(i + 1, j, k)) {
            setBoundingBox(0.99F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        } else if (world.isFullOpaque(i, j, k - 1)) {
            setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.01F);
        } else if (world.isFullOpaque(i, j, k + 1)) {
            setBoundingBox(0.0F, 0.0F, 0.99F, 1.0F, 1.0F, 1.0F);
        } else {
            setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.01F, 1.0F);
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
        world.setTileMeta(i, j, k, (metadata + 1) % subTypes[this.id]);
    }
}
