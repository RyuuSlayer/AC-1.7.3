package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityTriggerMemory;
import io.github.ryuu.adventurecraft.gui.GuiTriggerMemory;
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
import net.minecraft.util.maths.Box;

import java.util.Random;

public class BlockTriggerMemory extends TileWithEntity {
    protected BlockTriggerMemory(int i, int j) {
        super(i, j, Material.AIR);
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityTriggerMemory();
    }

    @Override
    public int getDropId(int i, Random random) {
        return 0;
    }

    @Override
    public int getDropCount(Random random) {
        return 0;
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public Box getCollisionShape(Level world, int i, int j, int k) {
        return null;
    }

    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        TileEntityTriggerMemory obj = (TileEntityTriggerMemory) world.getTileEntity(i, j, k);
        if (!obj.isActivated && !obj.activateOnDetrigger) {
            obj.isActivated = true;
            triggerActivate(world, i, j, k);
        }
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
        TileEntityTriggerMemory obj = (TileEntityTriggerMemory) world.getTileEntity(i, j, k);
        if (!obj.isActivated && obj.activateOnDetrigger) {
            obj.isActivated = true;
            triggerActivate(world, i, j, k);
        }
    }

    public void triggerActivate(Level world, int i, int j, int k) {
        TileEntityTriggerMemory obj = (TileEntityTriggerMemory) world.getTileEntity(i, j, k);
        world.triggerManager.addArea(i, j, k, new TriggerArea(obj.minX, obj.minY, obj.minZ, obj.maxX, obj.maxY, obj.maxZ));
    }

    public void triggerDeactivate(Level world, int i, int j, int k) {
        world.triggerManager.removeArea(i, j, k);
    }

    @Override
    public void onTileRemoved(Level world, int i, int j, int k) {
        TileEntityTriggerMemory obj = (TileEntityTriggerMemory) world.getTileEntity(i, j, k);
        if (obj.isSet())
            if (world.getTileMeta(i, j, k) > 0) {
                onTriggerDeactivated(world, i, j, k);
            } else {
                onTriggerActivated(world, i, j, k);
            }
        super.onTileRemoved(world, i, j, k);
    }

    public void setTriggerToSelection(Level world, int i, int j, int k) {
        TileEntityTriggerMemory obj = (TileEntityTriggerMemory) world.getTileEntity(i, j, k);
        if (obj.minX == ItemCursor.minX && obj.minY == ItemCursor.minY && obj.minZ == ItemCursor.minZ && obj.maxX == ItemCursor.maxX && obj.maxY == ItemCursor.maxY && obj.maxZ == ItemCursor.maxZ)
            return;
        obj.set(ItemCursor.minX, ItemCursor.minY, ItemCursor.minZ, ItemCursor.maxX, ItemCursor.maxY, ItemCursor.maxZ);
    }

    @Override
    public boolean activate(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active && entityplayer.getHeldItem() != null && (entityplayer.getHeldItem()).itemId == Items.cursor.id) {
            TileEntityTriggerMemory obj = (TileEntityTriggerMemory) world.getTileEntity(i, j, k);
            GuiTriggerMemory.showUI(world, i, j, k, obj);
            return true;
        }
        return false;
    }

    @Override
    public void onScheduledTick(Level world, int i, int j, int k, Random random) {
        TileEntityTriggerMemory obj = (TileEntityTriggerMemory) world.getTileEntity(i, j, k);
        if (obj.isActivated)
            triggerActivate(world, i, j, k);
    }

    public void reset(Level world, int i, int j, int k, boolean death) {
        TileEntityTriggerMemory obj = (TileEntityTriggerMemory) world.getTileEntity(i, j, k);
        if ((!death || obj.resetOnDeath) && obj.isActivated) {
            obj.isActivated = false;
            triggerDeactivate(world, i, j, k);
        }
    }
}
