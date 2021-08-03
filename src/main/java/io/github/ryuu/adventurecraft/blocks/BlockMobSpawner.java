package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityMobSpawner;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.maths.Box;

import java.util.Random;

public class BlockMobSpawner extends TileWithEntity {
    protected BlockMobSpawner(int i, int j) {
        super(i, j, ln.a);
    }

    protected TileEntity a_() {
        return new TileEntityMobSpawner();
    }

    public int a(int i, Random random) {
        return 0;
    }

    public int a(Random random) {
        return 0;
    }

    public boolean c() {
        return false;
    }

    public Box e(Level world, int i, int j, int k) {
        return null;
    }

    public boolean a(Level world, int i, int j, int k, gs entityplayer) {
        if (DebugMode.active) {
            TileEntityMobSpawner obj = (TileEntityMobSpawner)world.b(i, j, k);
            GuiMobSpawner.showUI(obj);
            return true;
        }
        return false;
    }

    public boolean shouldRender(xp blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        TileEntityMobSpawner obj = (TileEntityMobSpawner)world.b(i, j, k);
        if (obj.spawnOnTrigger && !Tile.resetActive)
            obj.spawnMobs();
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
        TileEntityMobSpawner obj = (TileEntityMobSpawner)world.b(i, j, k);
        if (obj.spawnOnDetrigger && !Tile.resetActive)
            obj.spawnMobs();
    }

    public boolean v_() {
        return DebugMode.active;
    }

    public void reset(Level world, int i, int j, int k, boolean death) {
        TileEntityMobSpawner obj = (TileEntityMobSpawner)world.b(i, j, k);
        if (!death)
            obj.hasDroppedItem = false;
        obj.resetMobs();
        obj.delay = 0;
    }
}
