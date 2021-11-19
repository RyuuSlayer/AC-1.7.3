package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityNpcPath;
import io.github.ryuu.adventurecraft.gui.GuiNpcPath;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockNpcPath extends TileWithEntity {
    public BlockNpcPath(int i, int j) {
        super(i, j, Material.STONE);
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityNpcPath();
    }

    @Override
    public Box getCollisionShape(Level world, int i, int j, int k) {
        return null;
    }

    @Override
    public boolean isFullOpaque() {
        return false;
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
        TileEntityNpcPath obj = (TileEntityNpcPath) world.getTileEntity(i, j, k);
        if (obj != null)
            obj.pathEntity();
    }

    @Override
    public boolean activate(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active && entityplayer.getHeldItem() != null && (entityplayer.getHeldItem()).itemId == Items.cursor.id) {
            TileEntityNpcPath obj = (TileEntityNpcPath) world.getTileEntity(i, j, k);
            if (obj != null)
                GuiNpcPath.showUI(obj);
            return true;
        }
        return false;
    }
}
