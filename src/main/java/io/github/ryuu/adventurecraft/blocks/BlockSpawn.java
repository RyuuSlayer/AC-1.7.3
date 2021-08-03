package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.util.DebugMode;
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

    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    public boolean v_() {
        return DebugMode.active;
    }

    public void a(Level world, int i, int j, int k, sn entity) {
        if (entity instanceof Player) {
            world.x.a(i, j, k);
            world.setSpawnYaw(entity.aS);
        }
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        world.x.a(i, j, k);
    }
}
