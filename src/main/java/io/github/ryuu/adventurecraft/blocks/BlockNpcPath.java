package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.TileView;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockNpcPath extends TileWithEntity {

    public BlockNpcPath(int i, int j) {
        super(i, j, Material.STONE);
    }

    @Override
    protected MixinTileEntity createTileEntity() {
        return new TileEntityNpcPath();
    }

    @Override
    public Box getCollisionShape(MixinLevel level, int x, int y, int z) {
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
    public void onTriggerActivated(MixinLevel world, int i, int j, int k) {
        TileEntityNpcPath obj = (TileEntityNpcPath) world.getTileEntity(i, j, k);
        if (obj != null) {
            obj.pathEntity();
        }
    }

    @Override
    public boolean activate(MixinLevel level, int x, int y, int z, MixinPlayer player) {
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
