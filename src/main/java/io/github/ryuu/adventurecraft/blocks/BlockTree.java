package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityTree;
import io.github.ryuu.adventurecraft.gui.GuiTree;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockTree extends TileWithEntity implements IBlockColor {
    protected BlockTree(int i, int j) {
        super(i, j, Material.PLANT);
        float f = 0.2F;
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityTree();
    }

    @Override
    public int getTextureForSide(int i, int j) {
        return this.tex + j;
    }

    @Override
    public Box getCollisionShape(Level world, int i, int j, int k) {
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
    public boolean activate(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active) {
            TileEntityTree obj = (TileEntityTree) world.getTileEntity(i, j, k);
            GuiTree.showUI(world, i, j, k, obj);
        }
        return true;
    }

    @Override
    public void incrementColor(Level world, int i, int j, int k) {
        int metadata = world.getTileMeta(i, j, k);
        world.setTileMeta(i, j, k, (metadata + 1) % subTypes[this.id]);
    }
}
