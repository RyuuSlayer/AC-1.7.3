package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityTriggerMemory;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.TriggerArea;
import net.minecraft.level.Level;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.maths.Box;

import java.util.Random;

public class BlockTriggerMemory extends TileWithEntity {
    protected BlockTriggerMemory(int i, int j) {
        super(i, j, ln.a);
    }

    protected TileEntity a_() {
        return new TileEntityTriggerMemory();
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

    public boolean v_() {
        return DebugMode.active;
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        TileEntityTriggerMemory obj = (TileEntityTriggerMemory)world.b(i, j, k);
        if (!obj.isActivated && !obj.activateOnDetrigger) {
            obj.isActivated = true;
            triggerActivate(world, i, j, k);
        }
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
        TileEntityTriggerMemory obj = (TileEntityTriggerMemory)world.b(i, j, k);
        if (!obj.isActivated && obj.activateOnDetrigger) {
            obj.isActivated = true;
            triggerActivate(world, i, j, k);
        }
    }

    public void triggerActivate(Level world, int i, int j, int k) {
        TileEntityTriggerMemory obj = (TileEntityTriggerMemory)world.b(i, j, k);
        world.triggerManager.addArea(i, j, k, new TriggerArea(obj.minX, obj.minY, obj.minZ, obj.maxX, obj.maxY, obj.maxZ));
    }

    public void triggerDeactivate(Level world, int i, int j, int k) {
        world.triggerManager.removeArea(i, j, k);
    }

    public void b(Level world, int i, int j, int k) {
        TileEntityTriggerMemory obj = (TileEntityTriggerMemory)world.b(i, j, k);
        if (obj.isSet())
            if (world.e(i, j, k) > 0) {
                onTriggerDeactivated(world, i, j, k);
            } else {
                onTriggerActivated(world, i, j, k);
            }
        super.b(world, i, j, k);
    }

    public void setTriggerToSelection(Level world, int i, int j, int k) {
        TileEntityTriggerMemory obj = (TileEntityTriggerMemory)world.b(i, j, k);
        if (obj.minX == ItemCursor.minX && obj.minY == ItemCursor.minY && obj.minZ == ItemCursor.minZ && obj.maxX == ItemCursor.maxX && obj.maxY == ItemCursor.maxY && obj.maxZ == ItemCursor.maxZ)
            return;
        obj.set(ItemCursor.minX, ItemCursor.minY, ItemCursor.minZ, ItemCursor.maxX, ItemCursor.maxY, ItemCursor.maxZ);
    }

    public boolean a(Level world, int i, int j, int k, gs entityplayer) {
        if (DebugMode.active && entityplayer.G() != null && (entityplayer.G()).c == Items.cursor.bf) {
            TileEntityTriggerMemory obj = (TileEntityTriggerMemory)world.b(i, j, k);
            GuiTriggerMemory.showUI(world, i, j, k, obj);
            return true;
        }
        return false;
    }

    public void a(Level world, int i, int j, int k, Random random) {
        TileEntityTriggerMemory obj = (TileEntityTriggerMemory)world.b(i, j, k);
        if (obj.isActivated)
            triggerActivate(world, i, j, k);
    }

    public void reset(Level world, int i, int j, int k, boolean death) {
        TileEntityTriggerMemory obj = (TileEntityTriggerMemory)world.b(i, j, k);
        if ((!death || obj.resetOnDeath) && obj.isActivated) {
            obj.isActivated = false;
            triggerDeactivate(world, i, j, k);
        }
    }
}
