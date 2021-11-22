package io.github.ryuu.adventurecraft.blocks;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.gui.GuiMobSpawner;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityMobSpawner;

public class BlockMobSpawner extends TileWithEntity {

    protected BlockMobSpawner(int i, int j) {
        super(i, j, Material.AIR);
    }

    @Override
    protected TileEntity createTileEntity() {
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
    public Box getCollisionShape(Level level, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean activate(Level level, int x, int y, int z, Player player) {
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
    public void onTriggerActivated(Level world, int i, int j, int k) {
        TileEntityMobSpawner obj = (TileEntityMobSpawner) world.getTileEntity(i, j, k);
        if (obj.spawnOnTrigger && !Tile.resetActive) {
            obj.spawnMobs();
        }
    }

    @Override
    public void onTriggerDeactivated(Level world, int i, int j, int k) {
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
    public void reset(Level world, int i, int j, int k, boolean death) {
        TileEntityMobSpawner obj = (TileEntityMobSpawner) world.getTileEntity(i, j, k);
        if (!death) {
            obj.hasDroppedItem = false;
        }
        obj.resetMobs();
        obj.delay = 0;
    }
}
