package io.github.ryuu.adventurecraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

    @Override
    protected MixinTileEntity createTileEntity() {
        return new TileEntityRedstoneTrigger();
    }

    @Override
    public void method_1609(MixinLevel level, int x, int y, int z, int id) {
        this.updateBlock(level, x, y, z, id);
    }

    @Override
    public int method_1626(TileView iblockaccess, int i, int j, int k, int l) {
        TileEntityRedstoneTrigger obj = (TileEntityRedstoneTrigger) iblockaccess.getTileEntity(i, j, k);
        if (obj.isActivated) {
            return this.tex;
        }
        return this.tex + 1;
    }

    private void updateBlock(MixinLevel world, int i, int j, int k, int l) {
        boolean flag = world.hasRedstonePower(i, j, k);
        TileEntityRedstoneTrigger obj = (TileEntityRedstoneTrigger) world.getTileEntity(i, j, k);
        if (obj != null && obj.isActivated != flag) {
            obj.isActivated = flag;
            world.method_243(i, j, k);
            if (flag) {
                if (!obj.resetOnTrigger) {
                    world.triggerManager.addArea(i, j, k, new TriggerArea(obj.minX, obj.minY, obj.minZ, obj.maxX, obj.maxY, obj.maxZ));
                } else {
                    BlockRedstoneTrigger.resetArea(world, obj.minX, obj.minY, obj.minZ, obj.maxX, obj.maxY, obj.maxZ);
                }
            } else {
                world.triggerManager.removeArea(i, j, k);
            }
        }
    }

    @Override
    public boolean activate(MixinLevel level, int x, int y, int z, MixinPlayer player) {
        if (DebugMode.active && player.getHeldItem() != null && player.getHeldItem().itemId == Items.cursor.id) {
            TileEntityRedstoneTrigger obj = (TileEntityRedstoneTrigger) level.getTileEntity(x, y, z);
            GuiRedstoneTrigger.showUI(level, x, y, z, obj);
        }
        return true;
    }

    public void setTriggerToSelection(MixinLevel world, int i, int j, int k) {
        TileEntityRedstoneTrigger obj = (TileEntityRedstoneTrigger) world.getTileEntity(i, j, k);
        if (obj.minX == ItemCursor.minX && obj.minY == ItemCursor.minY && obj.minZ == ItemCursor.minZ && obj.maxX == ItemCursor.maxX && obj.maxY == ItemCursor.maxY && obj.maxZ == ItemCursor.maxZ) {
            return;
        }
        obj.minX = ItemCursor.minX;
        obj.minY = ItemCursor.minY;
        obj.minZ = ItemCursor.minZ;
        obj.maxX = ItemCursor.maxX;
        obj.maxY = ItemCursor.maxY;
        obj.maxZ = ItemCursor.maxZ;
    }
}
