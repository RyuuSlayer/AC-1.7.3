package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockTree extends TileWithEntity implements IBlockColor {

    protected BlockTree(int i, int j) {
        super(i, j, Material.PLANT);
        float f = 0.2f;
    }

    @Override
    protected MixinTileEntity createTileEntity() {
        return new TileEntityTree();
    }

    @Override
    public int getTextureForSide(int side, int meta) {
        return this.tex + meta;
    }

    @Override
    public Box getCollisionShape(MixinLevel level, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
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
        return 36;
    }

    @Override
    public boolean activate(MixinLevel level, int x, int y, int z, MixinPlayer player) {
        if (DebugMode.active) {
            TileEntityTree obj = (TileEntityTree) level.getTileEntity(x, y, z);
            GuiTree.showUI(level, x, y, z, obj);
        }
        return true;
    }

    @Override
    public void incrementColor(MixinLevel world, int i, int j, int k) {
        int metadata = world.getTileMeta(i, j, k);
        world.setTileMeta(i, j, k, (metadata + 1) % subTypes[this.id]);
    }
}
