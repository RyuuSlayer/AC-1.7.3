package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityStorage;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.level.Level;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.maths.Box;

public class BlockStorage extends TileWithEntity {
    protected BlockStorage(int i, int j) {
        super(i, j, ln.a);
    }

    protected TileEntity a_() {
        return new TileEntityStorage();
    }

    public boolean c() {
        return false;
    }

    public Box e(Level world, int i, int j, int k) {
        return null;
    }

    public boolean shouldRender(xp blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        TileEntityStorage obj = (TileEntityStorage)world.b(i, j, k);
        obj.loadCurrentArea();
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {}

    public boolean a(Level world, int i, int j, int k, gs entityplayer) {
        if (DebugMode.active) {
            TileEntityStorage obj = (TileEntityStorage)world.b(i, j, k);
            GuiStorage.showUI(obj);
        }
        return true;
    }

    public boolean v_() {
        return DebugMode.active;
    }
}
