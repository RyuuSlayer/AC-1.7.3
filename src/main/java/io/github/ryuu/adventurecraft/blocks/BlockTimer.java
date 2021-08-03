package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityMinMax;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTimer;
import io.github.ryuu.adventurecraft.gui.GuiTimer;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockTimer extends TileWithEntity {
    protected BlockTimer(int i, int j) {
        super(i, j, Material.AIR);
    }

    protected TileEntity a_() {
        return new TileEntityTimer();
    }

    public boolean c() {
        return false;
    }

    public Box e(Level world, int i, int j, int k) {
        return null;
    }

    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void setTriggerToSelection(Level world, int i, int j, int k) {
        TileEntityMinMax obj = (TileEntityMinMax) world.b(i, j, k);
        if (obj.minX == ItemCursor.minX && obj.minY == ItemCursor.minY && obj.minZ == ItemCursor.minZ && obj.maxX == ItemCursor.maxX && obj.maxY == ItemCursor.maxY && obj.maxZ == ItemCursor.maxZ)
            return;
        obj.minX = ItemCursor.minX;
        obj.minY = ItemCursor.minY;
        obj.minZ = ItemCursor.minZ;
        obj.maxX = ItemCursor.maxX;
        obj.maxY = ItemCursor.maxY;
        obj.maxZ = ItemCursor.maxZ;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        TileEntityTimer obj = (TileEntityTimer) world.b(i, j, k);
        if (obj.canActivate && !obj.active)
            obj.startActive();
    }

    public boolean a(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active) {
            TileEntityTimer obj = (TileEntityTimer) world.b(i, j, k);
            GuiTimer.showUI(world, i, j, k, obj);
        }
        return true;
    }

    public boolean v_() {
        return DebugMode.active;
    }

    public void reset(Level world, int i, int j, int k, boolean death) {
        TileEntityTimer obj = (TileEntityTimer) world.b(i, j, k);
        obj.active = false;
        obj.canActivate = true;
        obj.ticks = 0;
        world.triggerManager.removeArea(i, j, k);
    }
}
