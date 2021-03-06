package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityMinMax;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTriggerPushable;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
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
        this.hardness(2.0f);
        this.blastResistance(10.0f);
        this.sounds(Tile.PISTON_SOUNDS);
        ((ExTile) this).setTextureNum(3);
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityTriggerPushable();
    }

    private boolean checkBlock(Level world, int i, int j, int k, int m) {
        return world.getTileId(i, j, k) == Blocks.pushableBlock.id && world.getTileMeta(i, j, k) == m;
    }

    @Override
    public void method_1609(Level level, int x, int y, int z, int id) {
        TileEntityTriggerPushable obj = (TileEntityTriggerPushable) level.getTileEntity(x, y, z);
        int metadata = level.getTileMeta(x, y, z);
        boolean hasNeighbor = this.checkBlock(level, x + 1, y, z, metadata);
        hasNeighbor |= this.checkBlock(level, x - 1, y, z, metadata);
        hasNeighbor |= this.checkBlock(level, x, y + 1, z, metadata);
        hasNeighbor |= this.checkBlock(level, x, y - 1, z, metadata);
        hasNeighbor |= this.checkBlock(level, x, y, z + 1, metadata);
        hasNeighbor |= this.checkBlock(level, x, y, z - 1, metadata);
        if (obj.activated) {
            if (!hasNeighbor) {
                obj.activated = false;
                ((ExLevel) level).getTriggerManager().removeArea(x, y, z);
            }
        } else if (hasNeighbor) {
            obj.activated = true;
            if (!obj.resetOnTrigger) {
                ((ExLevel) level).getTriggerManager().addArea(x, y, z, new TriggerArea(obj.minX, obj.minY, obj.minZ, obj.maxX, obj.maxY, obj.maxZ));
            } else {
                ((ExLevel) level).getTriggerManager().resetArea(obj.minX, obj.minY, obj.minZ, obj.maxX, obj.maxY, obj.maxZ);
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
    public boolean activate(Level level, int x, int y, int z, Player player) {
        if (DebugMode.active && player.getHeldItem() != null && player.getHeldItem().itemId == Items.cursor.id) {
            TileEntityTriggerPushable obj = (TileEntityTriggerPushable) level.getTileEntity(x, y, z);
            GuiTriggerPushable.showUI(obj);
            return true;
        }
        return false;
    }

    @Override
    public void incrementColor(Level world, int i, int j, int k) {
        super.incrementColor(world, i, j, k);
        this.method_1609(world, i, j, k, 0);
    }
}
