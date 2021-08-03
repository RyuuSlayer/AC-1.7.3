package io.github.ryuu.adventurecraft.blocks;

import java.util.List;
import java.util.Random;

import io.github.ryuu.adventurecraft.entities.EntityAirFX;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.Box;

public class BlockFan extends Tile {
    private boolean fanOn;

    public BlockFan(int i, int j, boolean f) {
        super(i, j, ln.e);
        this.fanOn = f;
    }

    public int a(int i, int j) {
        if (i == j)
            return this.bm;
        return 74;
    }

    public int e() {
        return 1;
    }

    public void e(Level world, int i, int j, int k, int l) {
        world.d(i, j, k, l);
        if (this.fanOn)
            world.c(i, j, k, this.bn, e());
    }

    private boolean canGoThroughBlock(int blockID) {
        return (Tile.m[blockID] != null && (Tile.m[blockID]).bA != ln.a && (Tile.m[blockID]).bA != ln.g && (Tile.m[blockID]).bA != ln.h);
    }

    public void a(Level world, int i, int j, int k, Random random) {
        if (!this.fanOn)
            return;
        world.c(i, j, k, this.bn, e());
        if (!DebugMode.active) {
            int direction = world.e(i, j, k);
            int xOffset = 0;
            int yOffset = 0;
            int zOffset = 0;
            if (direction == 0) {
                for (yOffset = -1; yOffset >= -4; yOffset--) {
                    int blockID = world.a(i, j + yOffset, k);
                    if (canGoThroughBlock(blockID)) {
                        yOffset++;
                        break;
                    }
                }
            } else if (direction == 1) {
                for (yOffset = 1; yOffset <= 4; yOffset++) {
                    int blockID = world.a(i, j + yOffset, k);
                    if (canGoThroughBlock(blockID)) {
                        yOffset--;
                        break;
                    }
                }
            } else if (direction == 2) {
                for (zOffset = -1; zOffset >= -4; zOffset--) {
                    int blockID = world.a(i, j, k + zOffset);
                    if (canGoThroughBlock(blockID)) {
                        zOffset++;
                        break;
                    }
                }
            } else if (direction == 3) {
                for (zOffset = 1; zOffset <= 4; zOffset++) {
                    int blockID = world.a(i, j, k + zOffset);
                    if (canGoThroughBlock(blockID)) {
                        zOffset--;
                        break;
                    }
                }
            } else if (direction == 4) {
                for (xOffset = -1; xOffset >= -4; xOffset--) {
                    int blockID = world.a(i + xOffset, j, k);
                    if (canGoThroughBlock(blockID)) {
                        xOffset++;
                        break;
                    }
                }
            } else if (direction == 5) {
                for (xOffset = 1; xOffset <= 4; xOffset++) {
                    int blockID = world.a(i + xOffset, j, k);
                    if (canGoThroughBlock(blockID)) {
                        xOffset--;
                        break;
                    }
                }
            }
            Box aabb = e(world, i, j, k).a(xOffset, yOffset, zOffset);
            List entities = world.a(sn.class, aabb);
            for (Object obj : entities) {
                sn e = (sn)obj;
                if (!(e instanceof ju)) {
                    double dist = e.h(i + 0.5D, j + 0.5D, k + 0.5D) * Math.abs(xOffset + yOffset + zOffset) / 4.0D;
                    e.d(0.07D * xOffset / dist, 0.07D * yOffset / dist, 0.07D * zOffset / dist);
                    if (e instanceof gs && ((gs)e).usingUmbrella())
                        e.d(0.07D * xOffset / dist, 0.07D * yOffset / dist, 0.07D * zOffset / dist);
                }
            }
            entities = Minecraft.minecraftInstance.j.getEffectsWithinAABB(aabb);
            for (Object obj : entities) {
                sn e = (sn)obj;
                if (!(e instanceof ju)) {
                    double dist = e.h(i + 0.5D, j + 0.5D, k + 0.5D) * Math.abs(xOffset + yOffset + zOffset) / 4.0D;
                    e.d(0.03D * xOffset / dist, 0.03D * yOffset / dist, 0.03D * zOffset / dist);
                }
            }
            Minecraft.minecraftInstance.j.a((xw)new EntityAirFX(world, i + random.nextDouble(), j + random.nextDouble(), k + random.nextDouble()));
        }
    }

    public void b(Level world, int i, int j, int k, Random random) {
        if (this.fanOn)
            world.c(i, j, k, this.bn, e());
    }

    public boolean a(Level world, int i, int j, int k, gs entityplayer) {
        if (DebugMode.active) {
            world.d(i, j, k, (world.e(i, j, k) + 1) % 6);
            world.k(i, j, k);
            return true;
        }
        return false;
    }

    public void b(Level world, int i, int j, int k, int l) {
        if (world.B)
            return;
        if (world.s(i, j, k)) {
            if (this.fanOn) {
                int m = world.e(i, j, k);
                world.b(i, j, k, Blocks.fanOff.bn, m);
            }
        } else if (!this.fanOn) {
            int m = world.e(i, j, k);
            world.b(i, j, k, Blocks.fan.bn, m);
            world.c(i, j, k, Blocks.fan.bn, e());
        }
        super.b(world, i, j, k, l);
    }
}
