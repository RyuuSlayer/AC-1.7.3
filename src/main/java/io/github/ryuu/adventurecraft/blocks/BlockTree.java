package io.github.ryuu.adventurecraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.gui.GuiTree;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTree;

public class BlockTree extends TileWithEntity implements IBlockColor {

    protected BlockTree(int i, int j) {
        super(i, j, Material.PLANT);
        float f = 0.2f;
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityTree();
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
    public boolean activate(Level level, int x, int y, int z, Player player) {
        if (DebugMode.active) {
            TileEntityTree obj = (TileEntityTree) level.getTileEntity(x, y, z);
            GuiTree.showUI(level, x, y, z, obj);
        }
        return true;
    }

    @Override
    public void incrementColor(Level world, int i, int j, int k) {
        int metadata = world.getTileMeta(i, j, k);
        world.setTileMeta(i, j, k, (metadata + 1) % subTypes[this.id]);
    }
}
