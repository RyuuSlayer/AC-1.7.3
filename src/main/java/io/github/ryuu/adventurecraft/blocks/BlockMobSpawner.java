package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

import java.util.Random;

public class BlockMobSpawner extends TileWithEntity {

    protected BlockMobSpawner(int i, int j) {
        super(i, j, Material.AIR);
    }

    @Override
    protected MixinTileEntity createTileEntity() {
        return new TileEntityMobSpawner();
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
    public Box getCollisionShape(MixinLevel level, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean activate(MixinLevel level, int x, int y, int z, MixinPlayer player) {
        if (DebugMode.active) {
            TileEntityMobSpawner obj = (TileEntityMobSpawner) level.getTileEntity(x, y, z);
            GuiMobSpawner.showUI(obj);
            return true;
        }
        return false;
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
        TileEntityMobSpawner obj = (TileEntityMobSpawner) world.getTileEntity(i, j, k);
        if (obj.spawnOnTrigger && !Tile.resetActive) {
            obj.spawnMobs();
        }
    }

    @Override
    public void onTriggerDeactivated(MixinLevel world, int i, int j, int k) {
        TileEntityMobSpawner obj = (TileEntityMobSpawner) world.getTileEntity(i, j, k);
        if (obj.spawnOnDetrigger && !Tile.resetActive) {
            obj.spawnMobs();
        }
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }

    @Override
    public void reset(MixinLevel world, int i, int j, int k, boolean death) {
        TileEntityMobSpawner obj = (TileEntityMobSpawner) world.getTileEntity(i, j, k);
        if (!death) {
            obj.hasDroppedItem = false;
        }
        obj.resetMobs();
        obj.delay = 0;
    }
}
