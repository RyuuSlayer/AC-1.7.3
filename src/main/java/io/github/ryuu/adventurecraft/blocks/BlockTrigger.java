package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityMinMax;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTrigger;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.TriggerArea;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.maths.Box;

import java.util.Random;

public class BlockTrigger extends TileWithEntity {
    protected BlockTrigger(int i, int j) {
        super(i, j, ln.a);
    }

    protected TileEntity a_() {
        return new TileEntityTrigger();
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

    private void setNotVisited(Level world, int i, int j, int k) {
        TileEntityTrigger obj = (TileEntityTrigger)world.b(i, j, k);
        if (obj != null && obj.visited) {
            obj.visited = false;
            for (int x = i - 1; x <= i + 1; x++) {
                for (int y = j - 1; y <= j + 1; y++) {
                    for (int z = k - 1; z <= k + 1; z++) {
                        if (world.a(x, y, z) == this.bn)
                            setNotVisited(world, x, y, z);
                    }
                }
            }
        }
    }

    public boolean isAlreadyActivated(Level world, int i, int j, int k) {
        boolean isActivated = _isAlreadyActivated(world, i, j, k);
        setNotVisited(world, i, j, k);
        return isActivated;
    }

    private boolean _isAlreadyActivated(Level world, int i, int j, int k) {
        boolean isActivated = false;
        TileEntityTrigger obj = (TileEntityTrigger)world.b(i, j, k);
        if (obj != null && !obj.visited) {
            obj.visited = true;
            if (obj.activated > 0)
                return true;
            for (int x = i - 1; x <= i + 1; x++) {
                for (int y = j - 1; y <= j + 1; y++) {
                    for (int z = k - 1; z <= k + 1; z++) {
                        if (world.a(x, y, z) == this.bn)
                            if (_isAlreadyActivated(world, x, y, z)) {
                                isActivated = true;
                                break;
                            }
                    }
                    if (isActivated)
                        break;
                }
                if (isActivated)
                    break;
            }
        }
        return isActivated;
    }

    public void removeArea(Level world, int i, int j, int k) {
        _removeArea(world, i, j, k);
        setNotVisited(world, i, j, k);
    }

    private void _removeArea(Level world, int i, int j, int k) {
        TileEntityTrigger obj = (TileEntityTrigger)world.b(i, j, k);
        if (!obj.visited) {
            obj.visited = true;
            world.triggerManager.removeArea(i, j, k);
            for (int x = i - 1; x <= i + 1; x++) {
                for (int y = j - 1; y <= j + 1; y++) {
                    for (int z = k - 1; z <= k + 1; z++) {
                        if (world.a(x, y, z) == this.bn)
                            _removeArea(world, x, y, z);
                    }
                }
            }
        }
    }

    public void a(Level world, int i, int j, int k, sn entity) {
        if (DebugMode.active)
            return;
        TileEntityTrigger obj = (TileEntityTrigger)world.b(i, j, k);
        if (entity instanceof gs) {
            if (!isAlreadyActivated(world, i, j, k))
                if (!obj.resetOnTrigger) {
                    world.triggerManager.addArea(i, j, k, new TriggerArea(obj.minX, obj.minY, obj.minZ, obj.maxX, obj.maxY, obj.maxZ));
                } else {
                    Tile.resetArea(world, obj.minX, obj.minY, obj.minZ, obj.maxX, obj.maxY, obj.maxZ);
                }
            obj.activated = 2;
        }
    }

    public void deactivateTrigger(Level world, int i, int j, int k) {
        TileEntityTrigger obj = (TileEntityTrigger)world.b(i, j, k);
        if (!isAlreadyActivated(world, i, j, k))
            if (!obj.resetOnTrigger)
                removeArea(world, i, j, k);
    }

    public void setTriggerToSelection(Level world, int i, int j, int k) {
        TileEntityMinMax obj = (TileEntityMinMax)world.b(i, j, k);
        if (obj.minX == ItemCursor.minX && obj.minY == ItemCursor.minY && obj.minZ == ItemCursor.minZ && obj.maxX == ItemCursor.maxX && obj.maxY == ItemCursor.maxY && obj.maxZ == ItemCursor.maxZ)
            return;
        obj.minX = ItemCursor.minX;
        obj.minY = ItemCursor.minY;
        obj.minZ = ItemCursor.minZ;
        obj.maxX = ItemCursor.maxX;
        obj.maxY = ItemCursor.maxY;
        obj.maxZ = ItemCursor.maxZ;
        for (int x = i - 1; x <= i + 1; x++) {
            for (int y = j - 1; y <= j + 1; y++) {
                for (int z = k - 1; z <= k + 1; z++) {
                    if (world.a(x, y, z) == this.bn)
                        setTriggerToSelection(world, x, y, z);
                }
            }
        }
    }

    public void setTriggerReset(Level world, int i, int j, int k, boolean resetOnTrigger) {
        TileEntityTrigger obj = (TileEntityTrigger)world.b(i, j, k);
        if (obj.resetOnTrigger == resetOnTrigger)
            return;
        obj.resetOnTrigger = resetOnTrigger;
        for (int x = i - 1; x <= i + 1; x++) {
            for (int y = j - 1; y <= j + 1; y++) {
                for (int z = k - 1; z <= k + 1; z++) {
                    if (world.a(x, y, z) == this.bn)
                        setTriggerReset(world, x, y, z, resetOnTrigger);
                }
            }
        }
    }

    public boolean a(Level world, int i, int j, int k, gs entityplayer) {
        if (DebugMode.active && entityplayer.G() != null && (entityplayer.G()).c == Items.cursor.bf) {
            TileEntityTrigger obj = (TileEntityTrigger)world.b(i, j, k);
            GuiTrigger.showUI(world, i, j, k, obj);
        }
        return true;
    }

    public void reset(Level world, int i, int j, int k, boolean death) {
        TileEntityTrigger obj = (TileEntityTrigger)world.b(i, j, k);
        obj.activated = 0;
    }
}
