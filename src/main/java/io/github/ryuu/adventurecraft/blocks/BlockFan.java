package io.github.ryuu.adventurecraft.blocks;

import java.util.List;
import java.util.Random;

import io.github.ryuu.adventurecraft.entities.EntityAirFX;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingTile;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockFan extends Tile {
    private final boolean fanOn;

    public BlockFan(int i, int j, boolean f) {
        super(i, j, Material.STONE);
        this.fanOn = f;
    }

    @Override
    public int getTextureForSide(int i, int j) {
        if (i == j)
            return this.tex;
        return 74;
    }

    @Override
    public int getTickrate() {
        return 1;
    }

    @Override
    public void onPlaced(Level world, int i, int j, int k, int l) {
        world.setTileMeta(i, j, k, l);
        if (this.fanOn)
            world.method_216(i, j, k, this.id, getTickrate());
    }

    private boolean canGoThroughBlock(int blockID) {
        return (Tile.BY_ID[blockID] != null &&
                (Tile.BY_ID[blockID]).material != Material.AIR &&
                (Tile.BY_ID[blockID]).material != Material.WATER &&
                (Tile.BY_ID[blockID]).material != Material.LAVA);
    }

    @Override
    public void onScheduledTick(Level world, int i, int j, int k, Random random) {
        if (!this.fanOn)
            return;
        world.method_216(i, j, k, this.id, getTickrate());
        if (!DebugMode.active) {
            int direction = world.getTileMeta(i, j, k);
            int xOffset = 0;
            int yOffset = 0;
            int zOffset = 0;
            if (direction == 0) {
                for (yOffset = -1; yOffset >= -4; yOffset--) {
                    int blockID = world.getTileId(i, j + yOffset, k);
                    if (canGoThroughBlock(blockID)) {
                        yOffset++;
                        break;
                    }
                }
            } else if (direction == 1) {
                for (yOffset = 1; yOffset <= 4; yOffset++) {
                    int blockID = world.getTileId(i, j + yOffset, k);
                    if (canGoThroughBlock(blockID)) {
                        yOffset--;
                        break;
                    }
                }
            } else if (direction == 2) {
                for (zOffset = -1; zOffset >= -4; zOffset--) {
                    int blockID = world.getTileId(i, j, k + zOffset);
                    if (canGoThroughBlock(blockID)) {
                        zOffset++;
                        break;
                    }
                }
            } else if (direction == 3) {
                for (zOffset = 1; zOffset <= 4; zOffset++) {
                    int blockID = world.getTileId(i, j, k + zOffset);
                    if (canGoThroughBlock(blockID)) {
                        zOffset--;
                        break;
                    }
                }
            } else if (direction == 4) {
                for (xOffset = -1; xOffset >= -4; xOffset--) {
                    int blockID = world.getTileId(i + xOffset, j, k);
                    if (canGoThroughBlock(blockID)) {
                        xOffset++;
                        break;
                    }
                }
            } else if (direction == 5) {
                for (xOffset = 1; xOffset <= 4; xOffset++) {
                    int blockID = world.getTileId(i + xOffset, j, k);
                    if (canGoThroughBlock(blockID)) {
                        xOffset--;
                        break;
                    }
                }
            }
            Box aabb = getCollisionShape(world, i, j, k).add(xOffset, yOffset, zOffset);
            List entities = world.getEntities(Entity.class, aabb);
            for (Object obj : entities) {
                Entity e = (Entity) obj;
                if (!(e instanceof FallingTile)) {
                    double dist = e.method_1350(i + 0.5D, j + 0.5D, k + 0.5D) * Math.abs(xOffset + yOffset + zOffset) / 4.0D;
                    e.method_1322(0.07D * xOffset / dist, 0.07D * yOffset / dist, 0.07D * zOffset / dist);
                    if (e instanceof Player && ((Player) e).usingUmbrella())
                        e.method_1322(0.07D * xOffset / dist, 0.07D * yOffset / dist, 0.07D * zOffset / dist);
                }
            }
            entities = Minecraft.minecraftInstance.particleManager.getEffectsWithinAABB(aabb);
            for (Object obj : entities) {
                Entity e = (Entity) obj;
                if (!(e instanceof FallingTile)) {
                    double dist = e.method_1350(i + 0.5D, j + 0.5D, k + 0.5D) * Math.abs(xOffset + yOffset + zOffset) / 4.0D;
                    e.method_1322(0.03D * xOffset / dist, 0.03D * yOffset / dist, 0.03D * zOffset / dist);
                }
            }
            Minecraft.minecraftInstance.particleManager.addParticle(new EntityAirFX(world, i + random.nextDouble(), j + random.nextDouble(), k + random.nextDouble()));
        }
    }

    @Override
    public void randomDisplayTick(Level world, int i, int j, int k, Random random) {
        if (this.fanOn)
            world.method_216(i, j, k, this.id, getTickrate());
    }

    @Override
    public boolean activate(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active) {
            world.setTileMeta(i, j, k, (world.getTileMeta(i, j, k) + 1) % 6);
            world.method_246(i, j, k);
            return true;
        }
        return false;
    }

    @Override
    public void method_1609(Level world, int i, int j, int k, int l) {
        if (world.isClient)
            return;
        if (world.hasRedstonePower(i, j, k)) {
            if (this.fanOn) {
                int m = world.getTileMeta(i, j, k);
                world.method_201(i, j, k, Blocks.fanOff.id, m);
            }
        } else if (!this.fanOn) {
            int m = world.getTileMeta(i, j, k);
            world.method_201(i, j, k, Blocks.fan.id, m);
            world.method_216(i, j, k, Blocks.fan.id, getTickrate());
        }
        super.method_1609(world, i, j, k, l);
    }
}
