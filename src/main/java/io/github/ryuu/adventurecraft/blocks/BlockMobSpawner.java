package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityMobSpawner;
import io.github.ryuu.adventurecraft.gui.GuiMobSpawner;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

import java.util.Random;

public class BlockMobSpawner extends TileWithEntity {
    protected BlockMobSpawner(int i, int j) {
        super(i, j, Material.AIR);
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityMobSpawner();
    }

    @Override
    public int getDropId(int i, Random random) {
        return 0;
    }

    @Override
    public int getDropCount(Random random) {
        return 0;
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public Box getCollisionShape(Level world, int i, int j, int k) {
        return null;
    }

    @Override
    public boolean activate(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active) {
            TileEntityMobSpawner obj = (TileEntityMobSpawner) world.getTileEntity(i, j, k);
            GuiMobSpawner.showUI(obj);
            return true;
        }
        return false;
    }

    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        TileEntityMobSpawner obj = (TileEntityMobSpawner) world.getTileEntity(i, j, k);
        if (obj.spawnOnTrigger && !Tile.resetActive)
            obj.spawnMobs();
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
        TileEntityMobSpawner obj = (TileEntityMobSpawner) world.getTileEntity(i, j, k);
        if (obj.spawnOnDetrigger && !Tile.resetActive)
            obj.spawnMobs();
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }

    public void reset(Level world, int i, int j, int k, boolean death) {
        TileEntityMobSpawner obj = (TileEntityMobSpawner) world.getTileEntity(i, j, k);
        if (!death)
            obj.hasDroppedItem = false;
        obj.resetMobs();
        obj.delay = 0;
    }
}
