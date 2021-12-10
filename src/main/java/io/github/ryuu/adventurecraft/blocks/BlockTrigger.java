package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityMinMax;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTrigger;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.gui.GuiTrigger;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.TriggerArea;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

import java.util.Random;

public class BlockTrigger extends TileWithEntity implements AcRenderConditionTile, AcResetTile {

    protected BlockTrigger(int i, int j) {
        super(i, j, Material.AIR);
        this.hardness(5.0f);
        this.sounds(Tile.METAL_SOUNDS);
        ((ExTile) this).setTextureNum(2);
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityTrigger();
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
    public int method_1626(TileView iblockaccess, int i, int j, int k, int l) {
        return super.method_1626(iblockaccess, i, j, k, l);
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }

    private void setNotVisited(Level world, int i, int j, int k) {
        TileEntityTrigger obj = (TileEntityTrigger) world.getTileEntity(i, j, k);
        if (obj != null && obj.visited) {
            obj.visited = false;
            for (int x = i - 1; x <= i + 1; ++x) {
                for (int y = j - 1; y <= j + 1; ++y) {
                    for (int z = k - 1; z <= k + 1; ++z) {
                        if (world.getTileId(x, y, z) != this.id) continue;
                        this.setNotVisited(world, x, y, z);
                    }
                }
            }
        }
    }

    public boolean isAlreadyActivated(Level world, int i, int j, int k) {
        boolean isActivated = this._isAlreadyActivated(world, i, j, k);
        this.setNotVisited(world, i, j, k);
        return isActivated;
    }

    private boolean _isAlreadyActivated(Level world, int i, int j, int k) {
        boolean isActivated = false;
        TileEntityTrigger obj = (TileEntityTrigger) world.getTileEntity(i, j, k);
        if (obj != null && !obj.visited) {
            obj.visited = true;
            if (obj.activated > 0) {
                return true;
            }
            for (int x = i - 1; x <= i + 1; ++x) {
                for (int y = j - 1; y <= j + 1; ++y) {
                    for (int z = k - 1; z <= k + 1; ++z) {
                        if (world.getTileId(x, y, z) != this.id || !this._isAlreadyActivated(world, x, y, z)) continue;
                        isActivated = true;
                        break;
                    }
                    if (isActivated) break;
                }
                if (isActivated) break;
            }
        }
        return isActivated;
    }

    public void removeArea(Level world, int i, int j, int k) {
        this._removeArea(world, i, j, k);
        this.setNotVisited(world, i, j, k);
    }

    private void _removeArea(Level level, int i, int j, int k) {
        TileEntityTrigger obj = (TileEntityTrigger) level.getTileEntity(i, j, k);
        if (!obj.visited) {
            obj.visited = true;
            ((ExLevel) level).getTriggerManager().removeArea(i, j, k);
            for (int x = i - 1; x <= i + 1; ++x) {
                for (int y = j - 1; y <= j + 1; ++y) {
                    for (int z = k - 1; z <= k + 1; ++z) {
                        if (level.getTileId(x, y, z) != this.id) continue;
                        this._removeArea(level, x, y, z);
                    }
                }
            }
        }
    }

    @Override
    public void onEntityCollision(Level level, int i, int j, int k, Entity entity) {
        if (DebugMode.active) {
            return;
        }
        TileEntityTrigger obj = (TileEntityTrigger) level.getTileEntity(i, j, k);
        if (entity instanceof Player) {
            if (!this.isAlreadyActivated(level, i, j, k)) {
                if (!obj.resetOnTrigger) {
                    ((ExLevel) level).getTriggerManager().addArea(i, j, k, new TriggerArea(obj.minX, obj.minY, obj.minZ, obj.maxX, obj.maxY, obj.maxZ));
                } else {
                    ((ExLevel) level).getTriggerManager().resetArea(obj.minX, obj.minY, obj.minZ, obj.maxX, obj.maxY, obj.maxZ);
                }
            }
            obj.activated = 2;
        }
    }

    public void deactivateTrigger(Level world, int i, int j, int k) {
        TileEntityTrigger obj = (TileEntityTrigger) world.getTileEntity(i, j, k);
        if (!this.isAlreadyActivated(world, i, j, k) && !obj.resetOnTrigger) {
            this.removeArea(world, i, j, k);
        }
    }

    public void setTriggerToSelection(Level world, int i, int j, int k) {
        TileEntityMinMax obj = (TileEntityMinMax) world.getTileEntity(i, j, k);
        if (obj.minX == ItemCursor.minX && obj.minY == ItemCursor.minY && obj.minZ == ItemCursor.minZ && obj.maxX == ItemCursor.maxX && obj.maxY == ItemCursor.maxY && obj.maxZ == ItemCursor.maxZ) {
            return;
        }
        obj.minX = ItemCursor.minX;
        obj.minY = ItemCursor.minY;
        obj.minZ = ItemCursor.minZ;
        obj.maxX = ItemCursor.maxX;
        obj.maxY = ItemCursor.maxY;
        obj.maxZ = ItemCursor.maxZ;
        for (int x = i - 1; x <= i + 1; ++x) {
            for (int y = j - 1; y <= j + 1; ++y) {
                for (int z = k - 1; z <= k + 1; ++z) {
                    if (world.getTileId(x, y, z) != this.id) continue;
                    this.setTriggerToSelection(world, x, y, z);
                }
            }
        }
    }

    public void setTriggerReset(Level world, int i, int j, int k, boolean resetOnTrigger) {
        TileEntityTrigger obj = (TileEntityTrigger) world.getTileEntity(i, j, k);
        if (obj.resetOnTrigger == resetOnTrigger) {
            return;
        }
        obj.resetOnTrigger = resetOnTrigger;
        for (int x = i - 1; x <= i + 1; ++x) {
            for (int y = j - 1; y <= j + 1; ++y) {
                for (int z = k - 1; z <= k + 1; ++z) {
                    if (world.getTileId(x, y, z) != this.id) continue;
                    this.setTriggerReset(world, x, y, z, resetOnTrigger);
                }
            }
        }
    }

    @Override
    public boolean activate(Level level, int x, int y, int z, Player player) {
        if (DebugMode.active && player.getHeldItem() != null && player.getHeldItem().itemId == Items.cursor.id) {
            TileEntityTrigger obj = (TileEntityTrigger) level.getTileEntity(x, y, z);
            GuiTrigger.showUI(level, x, y, z, obj);
        }
        return true;
    }

    @Override
    public void reset(Level world, int i, int j, int k, boolean death) {
        TileEntityTrigger obj = (TileEntityTrigger) world.getTileEntity(i, j, k);
        obj.activated = 0;
    }
}
