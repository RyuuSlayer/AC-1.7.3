package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityRedstoneTrigger;
import io.github.ryuu.adventurecraft.gui.GuiRedstoneTrigger;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.TriggerArea;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;

public class BlockRedstoneTrigger extends TileWithEntity {
    protected BlockRedstoneTrigger(int i, int j) {
        super(i, j, Material.STONE);
    }

    protected TileEntity a_() {
        return new TileEntityRedstoneTrigger();
    }

    public void b(Level world, int i, int j, int k, int l) {
        updateBlock(world, i, j, k, l);
    }

    public int a(TileView iblockaccess, int i, int j, int k, int l) {
        TileEntityRedstoneTrigger obj = (TileEntityRedstoneTrigger) iblockaccess.b(i, j, k);
        if (obj.isActivated)
            return this.bm;
        return this.bm + 1;
    }

    private void updateBlock(Level world, int i, int j, int k, int l) {
        boolean flag = world.s(i, j, k);
        TileEntityRedstoneTrigger obj = (TileEntityRedstoneTrigger) world.b(i, j, k);
        if (obj != null && obj.isActivated != flag) {
            obj.isActivated = flag;
            world.j(i, j, k);
            if (flag) {
                if (!obj.resetOnTrigger) {
                    world.triggerManager.addArea(i, j, k, new TriggerArea(obj.minX, obj.minY, obj.minZ, obj.maxX, obj.maxY, obj.maxZ));
                } else {
                    resetArea(world, obj.minX, obj.minY, obj.minZ, obj.maxX, obj.maxY, obj.maxZ);
                }
            } else {
                world.triggerManager.removeArea(i, j, k);
            }
        }
    }

    public boolean a(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active && entityplayer.G() != null && (entityplayer.G()).c == Items.cursor.bf) {
            TileEntityRedstoneTrigger obj = (TileEntityRedstoneTrigger) world.b(i, j, k);
            GuiRedstoneTrigger.showUI(world, i, j, k, obj);
        }
        return true;
    }

    public void setTriggerToSelection(Level world, int i, int j, int k) {
        TileEntityRedstoneTrigger obj = (TileEntityRedstoneTrigger) world.b(i, j, k);
        if (obj.minX == ItemCursor.minX && obj.minY == ItemCursor.minY && obj.minZ == ItemCursor.minZ && obj.maxX == ItemCursor.maxX && obj.maxY == ItemCursor.maxY && obj.maxZ == ItemCursor.maxZ)
            return;
        obj.minX = ItemCursor.minX;
        obj.minY = ItemCursor.minY;
        obj.minZ = ItemCursor.minZ;
        obj.maxX = ItemCursor.maxX;
        obj.maxY = ItemCursor.maxY;
        obj.maxZ = ItemCursor.maxZ;
    }
}