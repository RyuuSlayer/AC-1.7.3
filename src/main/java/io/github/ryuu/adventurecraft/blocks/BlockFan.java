package io.github.ryuu.adventurecraft.blocks;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.util.List
 *  java.util.Random
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import java.util.List;
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingTile;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockFan extends Tile {

    private boolean fanOn;

    public BlockFan(int i, int j, boolean f) {
        super(i, j, Material.STONE);
        this.fanOn = f;
    }

    @Override
    public int getTextureForSide(int side, int meta) {
        if (side == meta) {
            return this.tex;
        }
        return 74;
    }

    @Override
    public int getTickrate() {
        return 1;
    }

    @Override
    public void onPlaced(Level level, int x, int y, int z, int facing) {
        level.setTileMeta(x, y, z, facing);
        if (this.fanOn) {
            level.method_216(x, y, z, this.id, this.getTickrate());
        }
    }

    private boolean canGoThroughBlock(int blockID) {
        return Tile.BY_ID[blockID] != null && Tile.BY_ID[blockID].material != Material.AIR && Tile.BY_ID[blockID].material != Material.WATER && Tile.BY_ID[blockID].material != Material.LAVA;
    }

    @Override
    public void onScheduledTick(Level level, int x, int y, int z, Random rand) {
        if (!this.fanOn) {
            return;
        }
        level.method_216(x, y, z, this.id, this.getTickrate());
        if (!DebugMode.active) {
            double dist;
            Entity e;
            int blockID;
            int direction = level.getTileMeta(x, y, z);
            int xOffset = 0;
            int yOffset = 0;
            int zOffset = 0;
            if (direction == 0) {
                for (yOffset = -1; yOffset >= -4; --yOffset) {
                    blockID = level.getTileId(x, y + yOffset, z);
                    if (!this.canGoThroughBlock(blockID))
                        continue;
                    ++yOffset;
                    break;
                }
            } else if (direction == 1) {
                for (yOffset = 1; yOffset <= 4; ++yOffset) {
                    blockID = level.getTileId(x, y + yOffset, z);
                    if (!this.canGoThroughBlock(blockID))
                        continue;
                    --yOffset;
                    break;
                }
            } else if (direction == 2) {
                for (zOffset = -1; zOffset >= -4; --zOffset) {
                    blockID = level.getTileId(x, y, z + zOffset);
                    if (!this.canGoThroughBlock(blockID))
                        continue;
                    ++zOffset;
                    break;
                }
            } else if (direction == 3) {
                for (zOffset = 1; zOffset <= 4; ++zOffset) {
                    blockID = level.getTileId(x, y, z + zOffset);
                    if (!this.canGoThroughBlock(blockID))
                        continue;
                    --zOffset;
                    break;
                }
            } else if (direction == 4) {
                for (xOffset = -1; xOffset >= -4; --xOffset) {
                    blockID = level.getTileId(x + xOffset, y, z);
                    if (!this.canGoThroughBlock(blockID))
                        continue;
                    ++xOffset;
                    break;
                }
            } else if (direction == 5) {
                for (xOffset = 1; xOffset <= 4; ++xOffset) {
                    blockID = level.getTileId(x + xOffset, y, z);
                    if (!this.canGoThroughBlock(blockID))
                        continue;
                    --xOffset;
                    break;
                }
            }
            Box aabb = this.getCollisionShape(level, x, y, z).add(xOffset, yOffset, zOffset);
            List entities = level.getEntities(Entity.class, aabb);
            for (Object obj : entities) {
                e = (Entity) obj;
                if (e instanceof FallingTile)
                    continue;
                dist = e.method_1350((double) x + 0.5, (double) y + 0.5, (double) z + 0.5) * (double) Math.abs((int) (xOffset + yOffset + zOffset)) / 4.0;
                e.method_1322(0.07 * (double) xOffset / dist, 0.07 * (double) yOffset / dist, 0.07 * (double) zOffset / dist);
                if (!(e instanceof Player) || !((Player) e).usingUmbrella())
                    continue;
                e.method_1322(0.07 * (double) xOffset / dist, 0.07 * (double) yOffset / dist, 0.07 * (double) zOffset / dist);
            }
            entities = Minecraft.minecraftInstance.particleManager.getEffectsWithinAABB(aabb);
            for (Object obj : entities) {
                e = (Entity) obj;
                if (e instanceof FallingTile)
                    continue;
                dist = e.method_1350((double) x + 0.5, (double) y + 0.5, (double) z + 0.5) * (double) Math.abs((int) (xOffset + yOffset + zOffset)) / 4.0;
                e.method_1322(0.03 * (double) xOffset / dist, 0.03 * (double) yOffset / dist, 0.03 * (double) zOffset / dist);
            }
            Minecraft.minecraftInstance.particleManager.addParticle(new EntityAirFX(level, (double) x + rand.nextDouble(), (double) y + rand.nextDouble(), (double) z + rand.nextDouble()));
        }
    }

    @Override
    public void randomDisplayTick(Level level, int x, int y, int z, Random rand) {
        if (this.fanOn) {
            level.method_216(x, y, z, this.id, this.getTickrate());
        }
    }

    @Override
    public boolean activate(Level level, int x, int y, int z, Player player) {
        if (DebugMode.active) {
            level.setTileMeta(x, y, z, (level.getTileMeta(x, y, z) + 1) % 6);
            level.method_246(x, y, z);
            return true;
        }
        return false;
    }

    @Override
    public void method_1609(Level level, int x, int y, int z, int id) {
        if (level.isClient) {
            return;
        }
        if (level.hasRedstonePower(x, y, z)) {
            if (this.fanOn) {
                int m = level.getTileMeta(x, y, z);
                level.method_201(x, y, z, Blocks.fanOff.id, m);
            }
        } else if (!this.fanOn) {
            int m = level.getTileMeta(x, y, z);
            level.method_201(x, y, z, Blocks.fan.id, m);
            level.method_216(x, y, z, Blocks.fan.id, this.getTickrate());
        }
        super.method_1609(level, x, y, z, id);
    }
}
