package io.github.ryuu.adventurecraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
    protected MixinTileEntity createTileEntity() {
        return new TileEntityStorage();
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public Box getCollisionShape(MixinLevel level, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    @Override
    public boolean canBeTriggered() {
        return true;
    }

    @Override
    public void onTriggerActivated(MixinLevel world, int i, int j, int k) {
        TileEntityStorage obj = (TileEntityStorage) world.getTileEntity(i, j, k);
        obj.loadCurrentArea();
    }

    @Override
    public void onTriggerDeactivated(MixinLevel world, int i, int j, int k) {
    }

    @Override
    public boolean activate(MixinLevel level, int x, int y, int z, MixinPlayer player) {
        if (DebugMode.active) {
            TileEntityStorage obj = (TileEntityStorage) level.getTileEntity(x, y, z);
            GuiStorage.showUI(obj);
        }
        return true;
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }
}
