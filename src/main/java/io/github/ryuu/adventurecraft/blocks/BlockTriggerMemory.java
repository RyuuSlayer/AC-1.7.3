package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityTriggerMemory;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.gui.GuiTriggerMemory;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.TriggerArea;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

import java.util.Random;

public class BlockTriggerMemory extends TileWithEntity implements AcTriggerTile, AcRenderConditionTile, AcResetTile {

    protected BlockTriggerMemory(int i, int j) {
        super(i, j, Material.AIR);
        this.hardness(5.0f);
        this.sounds(Tile.METAL_SOUNDS);
        ((ExTile) this).setTextureNum(2);
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityTriggerMemory();
    }

    @Override
    public int getDropId(int meta, Random rand) {
        return 0;
    }

    @Override
    public int getDropCount(Random rand) {
        return 0;
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public Box getCollisionShape(Level level, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }

    @Override
    public boolean canBeTriggered() {
        return true;
    }

    @Override
    public void onTriggerActivated(Level world, int i, int j, int k) {
        TileEntityTriggerMemory obj = (TileEntityTriggerMemory) world.getTileEntity(i, j, k);
        if (!obj.isActivated && !obj.activateOnDetrigger) {
            obj.isActivated = true;
            this.triggerActivate(world, i, j, k);
        }
    }

    @Override
    public void onTriggerDeactivated(Level world, int i, int j, int k) {
        TileEntityTriggerMemory obj = (TileEntityTriggerMemory) world.getTileEntity(i, j, k);
        if (!obj.isActivated && obj.activateOnDetrigger) {
            obj.isActivated = true;
            this.triggerActivate(world, i, j, k);
        }
    }

    public void triggerActivate(Level level, int i, int j, int k) {
        TileEntityTriggerMemory obj = (TileEntityTriggerMemory) level.getTileEntity(i, j, k);
        ((ExLevel) level).getTriggerManager().addArea(i, j, k, new TriggerArea(obj.minX, obj.minY, obj.minZ, obj.maxX, obj.maxY, obj.maxZ));
    }

    public void triggerDeactivate(Level level, int i, int j, int k) {
        ((ExLevel) level).getTriggerManager().removeArea(i, j, k);
    }

    @Override
    public void onTileRemoved(Level level, int x, int y, int z) {
        TileEntityTriggerMemory obj = (TileEntityTriggerMemory) level.getTileEntity(x, y, z);
        if (obj.isSet()) {
            if (level.getTileMeta(x, y, z) > 0) {
                this.onTriggerDeactivated(level, x, y, z);
            } else {
                this.onTriggerActivated(level, x, y, z);
            }
        }
        super.onTileRemoved(level, x, y, z);
    }

    public void setTriggerToSelection(Level world, int i, int j, int k) {
        TileEntityTriggerMemory obj = (TileEntityTriggerMemory) world.getTileEntity(i, j, k);
        if (obj.minX == ItemCursor.minX && obj.minY == ItemCursor.minY && obj.minZ == ItemCursor.minZ && obj.maxX == ItemCursor.maxX && obj.maxY == ItemCursor.maxY && obj.maxZ == ItemCursor.maxZ) {
            return;
        }
        obj.set(ItemCursor.minX, ItemCursor.minY, ItemCursor.minZ, ItemCursor.maxX, ItemCursor.maxY, ItemCursor.maxZ);
    }

    @Override
    public boolean activate(Level level, int x, int y, int z, Player player) {
        if (DebugMode.active && player.getHeldItem() != null && player.getHeldItem().itemId == Items.cursor.id) {
            TileEntityTriggerMemory obj = (TileEntityTriggerMemory) level.getTileEntity(x, y, z);
            GuiTriggerMemory.showUI(level, x, y, z, obj);
            return true;
        }
        return false;
    }

    @Override
    public void onScheduledTick(Level level, int x, int y, int z, Random rand) {
        TileEntityTriggerMemory obj = (TileEntityTriggerMemory) level.getTileEntity(x, y, z);
        if (obj.isActivated) {
            this.triggerActivate(level, x, y, z);
        }
    }

    @Override
    public void reset(Level world, int i, int j, int k, boolean death) {
        TileEntityTriggerMemory obj = (TileEntityTriggerMemory) world.getTileEntity(i, j, k);
        if ((!death || obj.resetOnDeath) && obj.isActivated) {
            obj.isActivated = false;
            this.triggerDeactivate(world, i, j, k);
        }
    }
}
