package io.github.ryuu.adventurecraft.blocks;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockSpawn extends Tile {

    protected BlockSpawn(int i, int j) {
        super(i, j, Material.AIR);
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

    @Override
    public boolean canBeTriggered() {
        return true;
    }

    @Override
    public void onTriggerActivated(Level world, int i, int j, int k) {
        world.properties.setSpawnPosition(i, j, k);
    }
}
