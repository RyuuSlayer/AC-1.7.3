package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityMinMax;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTriggerPushable;
import io.github.ryuu.adventurecraft.gui.GuiTriggerPushable;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.TriggerArea;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;

public class BlockTriggerPushable extends BlockContainerColor {
    protected BlockTriggerPushable(int i, int j) {
        super(i, j, Material.STONE);
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityTriggerPushable();
    }

    private boolean checkBlock(Level world, int i, int j, int k, int m) {
        return (world.getTileId(i, j, k) == Blocks.pushableBlock.id && world.getTileMeta(i, j, k) == m);
    }

    @Override
    public void method_1609(Level world, int i, int j, int k, int l) {
        TileEntityTriggerPushable obj = (TileEntityTriggerPushable) world.getTileEntity(i, j, k);
        int metadata = world.getTileMeta(i, j, k);
        boolean hasNeighbor = checkBlock(world, i + 1, j, k, metadata);
        hasNeighbor |= checkBlock(world, i - 1, j, k, metadata);
        hasNeighbor |= checkBlock(world, i, j + 1, k, metadata);
        hasNeighbor |= checkBlock(world, i, j - 1, k, metadata);
        hasNeighbor |= checkBlock(world, i, j, k + 1, metadata);
        hasNeighbor |= checkBlock(world, i, j, k - 1, metadata);
        if (obj.activated) {
            if (!hasNeighbor) {
                obj.activated = false;
                world.triggerManager.removeArea(i, j, k);
            }
        } else if (hasNeighbor) {
            obj.activated = true;
            if (!obj.resetOnTrigger) {
                world.triggerManager.addArea(i, j, k, new TriggerArea(obj.minX, obj.minY, obj.minZ, obj.maxX, obj.maxY, obj.maxZ));
            } else {
                Tile.resetArea(world, obj.minX, obj.minY, obj.minZ, obj.maxX, obj.maxY, obj.maxZ);
            }
        }
    }

    public void setTriggerToSelection(Level world, int i, int j, int k) {
        TileEntityMinMax obj = (TileEntityMinMax) world.getTileEntity(i, j, k);
        obj.minX = ItemCursor.minX;
        obj.minY = ItemCursor.minY;
        obj.minZ = ItemCursor.minZ;
        obj.maxX = ItemCursor.maxX;
        obj.maxY = ItemCursor.maxY;
        obj.maxZ = ItemCursor.maxZ;
    }

    @Override
    public boolean activate(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active && entityplayer.getHeldItem() != null && (entityplayer.getHeldItem()).itemId == Items.cursor.id) {
            TileEntityTriggerPushable obj = (TileEntityTriggerPushable) world.getTileEntity(i, j, k);
            GuiTriggerPushable.showUI(obj);
            return true;
        }
        return false;
    }

    public void incrementColor(Level world, int i, int j, int k) {
        super.incrementColor(world, i, j, k);
        method_1609(world, i, j, k, 0);
    }
}
