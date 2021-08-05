package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

import java.util.Random;

public class BlockSpawn extends Tile {
    protected BlockSpawn(int i, int j) {
        super(i, j, Material.AIR);
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

    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }

    @Override
    public void onEntityCollision(Level world, int i, int j, int k, Entity entity) {
        if (entity instanceof Player) {
            world.properties.setSpawnPosition(i, j, k);
            world.setSpawnYaw(entity.yaw);
        }
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        world.properties.setSpawnPosition(i, j, k);
    }
}
