package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityTree;
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

    protected TileEntity a_() {
        return new TileEntityTree();
    }

    public int a(int i, int j) {
        return this.bm + j;
    }

    public Box e(Level world, int i, int j, int k) {
        return null;
    }

    public boolean v_() {
        return DebugMode.active;
    }

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
    }

    public int b() {
        return 36;
    }

    public boolean a(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active) {
            TileEntityTree obj = (TileEntityTree)world.b(i, j, k);
            GuiTree.showUI(world, i, j, k, obj);
        }
        return true;
    }

    public void incrementColor(Level world, int i, int j, int k) {
        int metadata = world.e(i, j, k);
        world.d(i, j, k, (metadata + 1) % subTypes[this.bn]);
    }
}
