package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityTriggerInverter;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.TriggerArea;
import net.minecraft.level.Level;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.maths.Box;

import java.util.Random;

public class BlockTriggerInverter extends TileWithEntity {
    protected BlockTriggerInverter(int i, int j) {
        super(i, j, ln.a);
    }

    protected TileEntity a_() {
        return new TileEntityTriggerInverter();
    }

    public int a(int i, Random random) {
        return 0;
    }

    public int a(Random random) {
        return 0;
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

    public int a(xp iblockaccess, int i, int j, int k, int l) {
        return super.a(iblockaccess, i, j, k, l);
    }

    public boolean v_() {
        return DebugMode.active;
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        world.triggerManager.removeArea(i, j, k);
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
        TileEntityTriggerInverter obj = TileEntityTriggerInverter)world.b(i, j, k);
        world.triggerManager.addArea(i, j, k, new TriggerArea(obj.minX, obj.minY, obj.minZ, obj.maxX, obj.maxY, obj.maxZ));
    }

    public void setTriggerToSelection(Level world, int i, int j, int k) {
        TileEntityTriggerInverter obj = (TileEntityTriggerInverter)world.b(i, j, k);
        if (obj.minX == ItemCursor.minX && obj.minY == ItemCursor.minY && obj.minZ == ItemCursor.minZ && obj.maxX == ItemCursor.maxX && obj.maxY == ItemCursor.maxY && obj.maxZ == ItemCursor.maxZ)
            return;
        obj.set(ItemCursor.minX, ItemCursor.minY, ItemCursor.minZ, ItemCursor.maxX, ItemCursor.maxY, ItemCursor.maxZ);
    }

    public boolean a(fd world, int i, int j, int k, gs entityplayer) {
        if (DebugMode.active && entityplayer.G() != null && (entityplayer.G()).c == Items.cursor.bf) {
            TileEntityTriggerInverter obj = (TileEntityTriggerInverter)world.b(i, j, k);
            GuiTriggerInverter.showUI(world, i, j, k, obj);
            return true;
        }
        return false;
    }

    public void reset(Level world, int i, int j, int k, boolean death) {
        if (!world.triggerManager.isActivated(i, j, k))
            onTriggerDeactivated(world, i, j, k);
    }
}
