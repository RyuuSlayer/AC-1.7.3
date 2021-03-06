package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import net.minecraft.level.Level;
import net.minecraft.tile.TileSounds;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockPlant extends BlockColor {

    protected BlockPlant(int i, int j, TileSounds sounds, float luminance, int subTypes, int textureNum) {
        super(i, j, Material.PLANT);
        float f = 0.2f;
        this.setBoundingBox(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, f * 3.0f, 0.5f + f);
        this.hardness(5.0f);
        this.sounds(sounds);
        this.luminance(luminance);
        ((ExTile) this).setSubTypes(subTypes);
        ((ExTile) this).setTextureNum(textureNum);
    }

    @Override
    public int getTextureForSide(int side, int meta) {
        return this.tex + meta;
    }

    @Override
    public Box getCollisionShape(Level level, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public int method_1621() {
        return 1;
    }

    @Override
    public void incrementColor(Level world, int i, int j, int k) {
        int metadata = world.getTileMeta(i, j, k);
        world.setTileMeta(i, j, k, (metadata + 1) % ExTile.subTypes[this.id]);
    }
}
