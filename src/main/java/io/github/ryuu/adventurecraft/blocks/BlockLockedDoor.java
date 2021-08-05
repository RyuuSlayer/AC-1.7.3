package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.Vec3f;

public class BlockLockedDoor extends Tile {
    int doorKeyToUse;

    protected BlockLockedDoor(int i, int j, int keyToUse) {
        super(i, Material.METAL);
        this.tex = j;
        this.doorKeyToUse = keyToUse;
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public HitResult raycast(Level world, int i, int j, int k, Vec3f vec3d, Vec3f vec3d1) {
        int metadata = world.getTileMeta(i, j, k);
        if (!DebugMode.active && metadata == 1)
            return null;
        return super.raycast(world, i, j, k, vec3d, vec3d1);
    }

    @Override
    public Box getCollisionShape(Level world, int i, int j, int k) {
        int metadata = world.getTileMeta(i, j, k);
        if (DebugMode.active || metadata == 1)
            return null;
        return super.getCollisionShape(world, i, j, k);
    }

    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        int metadata = blockAccess.getTileMeta(i, j, k);
        return (DebugMode.active || metadata == 0);
    }

    @Override
    public int method_1626(TileView world, int i, int j, int k, int side) {
        if (side == 0 || side == 1)
            return this.tex;
        int height = 1;
        while (world.getTileId(i, j + height, k) == this.id)
            height++;
        int offset = 1;
        while (world.getTileId(i, j - offset, k) == this.id) {
            height++;
            offset++;
        }
        int textureToReturn = this.tex;
        if (height > 2) {
            if (height / 2 == offset - 1) {
                int width = 1;
                offset = 1;
                while (world.getTileId(i + width, j, k) == this.id)
                    width++;
                while (world.getTileId(i - offset, j, k) == this.id) {
                    width++;
                    offset++;
                }
                if (width == 1) {
                    while (world.getTileId(i, j, k + width) == this.id)
                        width++;
                    while (world.getTileId(i, j, k - offset) == this.id) {
                        width++;
                        offset++;
                    }
                }
                if (width / 2 == offset - 1)
                    textureToReturn++;
            }
        } else {
            textureToReturn += 16;
            if (world.getTileId(i, j - 1, k) != this.id)
                textureToReturn += 16;
            if (side == 2) {
                if (world.getTileId(i + 1, j, k) == this.id)
                    textureToReturn++;
            } else if (side == 3) {
                if (world.getTileId(i - 1, j, k) == this.id)
                    textureToReturn++;
            } else if (side == 4) {
                if (world.getTileId(i, j, k - 1) == this.id)
                    textureToReturn++;
            } else if (side == 5) {
                if (world.getTileId(i, j, k + 1) == this.id)
                    textureToReturn++;
            }
        }
        return textureToReturn;
    }

    @Override
    public void onPunched(Level world, int i, int j, int k, Player entityplayer) {
        if (entityplayer.inventory.decreaseAmountOfItem(this.doorKeyToUse)) {
            world.playSound(i + 0.5D, j + 0.5D, k + 0.5D, "random.door_open", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
            int offsetY = 0;
            while (world.getTileId(i, j + offsetY, k) == this.id) {
                int offsetX = 0;
                while (world.getTileId(i + offsetX, j + offsetY, k) == this.id) {
                    world.setTileMeta(i + offsetX, j + offsetY, k, 1);
                    world.method_243(i + offsetX, j + offsetY, k);
                    offsetX++;
                }
                offsetX = 1;
                while (world.getTileId(i - offsetX, j + offsetY, k) == this.id) {
                    world.setTileMeta(i - offsetX, j + offsetY, k, 1);
                    world.method_243(i - offsetX, j + offsetY, k);
                    offsetX++;
                }
                offsetX = 1;
                while (world.getTileId(i, j + offsetY, k + offsetX) == this.id) {
                    world.setTileMeta(i, j + offsetY, k + offsetX, 1);
                    world.method_243(i, j + offsetY, k + offsetX);
                    offsetX++;
                }
                offsetX = 1;
                while (world.getTileId(i, j + offsetY, k - offsetX) == this.id) {
                    world.setTileMeta(i, j + offsetY, k - offsetX, 1);
                    world.method_243(i, j + offsetY, k - offsetX);
                    offsetX++;
                }
                offsetY++;
            }
            offsetY = -1;
            while (world.getTileId(i, j + offsetY, k) == this.id) {
                int offsetX = 0;
                while (world.getTileId(i + offsetX, j + offsetY, k) == this.id) {
                    world.setTileMeta(i + offsetX, j + offsetY, k, 1);
                    world.method_243(i + offsetX, j + offsetY, k);
                    offsetX++;
                }
                offsetX = 1;
                while (world.getTileId(i - offsetX, j + offsetY, k) == this.id) {
                    world.setTileMeta(i - offsetX, j + offsetY, k, 1);
                    world.method_243(i - offsetX, j + offsetY, k);
                    offsetX++;
                }
                offsetX = 1;
                while (world.getTileId(i, j + offsetY, k + offsetX) == this.id) {
                    world.setTileMeta(i, j + offsetY, k + offsetX, 1);
                    world.method_243(i, j + offsetY, k + offsetX);
                    offsetX++;
                }
                offsetX = 1;
                while (world.getTileId(i, j + offsetY, k - offsetX) == this.id) {
                    world.setTileMeta(i, j + offsetY, k - offsetX, 1);
                    world.method_243(i, j + offsetY, k - offsetX);
                    offsetX++;
                }
                offsetY--;
            }
        }
    }

    public void reset(Level world, int i, int j, int k, boolean death) {
        if (!death)
            world.setTileMeta(i, j, k, 0);
    }

    public int alwaysUseClick(Level world, int i, int j, int k) {
        return 0;
    }
}
