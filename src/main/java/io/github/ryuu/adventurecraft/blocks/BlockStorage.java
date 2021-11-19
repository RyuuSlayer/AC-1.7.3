package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityStorage;
import io.github.ryuu.adventurecraft.gui.GuiStorage;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockStorage extends TileWithEntity {
    protected BlockStorage(int i, int j) {
        super(i, j, Material.AIR);
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityStorage();
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

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        TileEntityStorage obj = (TileEntityStorage) world.b(i, j, k);
        obj.loadCurrentArea();
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
    }

    @Override
    public boolean activate(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active) {
            TileEntityStorage obj = (TileEntityStorage) world.getTileEntity(i, j, k);
            GuiStorage.showUI(obj);
        }
        return true;
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }
}
