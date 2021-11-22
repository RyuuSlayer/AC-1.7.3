package io.github.ryuu.adventurecraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.gui.GuiNpcPath;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityNpcPath;

public class BlockNpcPath extends TileWithEntity {

    public BlockNpcPath(int i, int j) {
        super(i, j, Material.STONE);
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityNpcPath();
    }

    @Override
    public Box getCollisionShape(Level level, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean isFullOpaque() {
        return false;
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
        TileEntityNpcPath obj = (TileEntityNpcPath) world.getTileEntity(i, j, k);
        if (obj != null) {
            obj.pathEntity();
        }
    }

    @Override
    public boolean activate(Level level, int x, int y, int z, Player player) {
        if (DebugMode.active && player.getHeldItem() != null && player.getHeldItem().itemId == Items.cursor.id) {
            TileEntityNpcPath obj = (TileEntityNpcPath) level.getTileEntity(x, y, z);
            if (obj != null) {
                GuiNpcPath.showUI(obj);
            }
            return true;
        }
        return false;
    }
}
