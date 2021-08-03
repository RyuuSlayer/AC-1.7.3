package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.Box;

public class BlockLockedDoor extends Tile {
    int doorKeyToUse;

    protected BlockLockedDoor(int i, int j, int keyToUse) {
        super(i, ln.f);
        this.bm = j;
        this.doorKeyToUse = keyToUse;
    }

    public boolean c() {
        return false;
    }

    public vf a(Level world, int i, int j, int k, bt vec3d, bt vec3d1) {
        int metadata = world.e(i, j, k);
        if (!DebugMode.active && metadata == 1)
            return null;
        return super.a(world, i, j, k, vec3d, vec3d1);
    }

    public Box e(Level world, int i, int j, int k) {
        int metadata = world.e(i, j, k);
        if (DebugMode.active || metadata == 1)
            return null;
        return super.e(world, i, j, k);
    }

    public boolean shouldRender(xp blockAccess, int i, int j, int k) {
        int metadata = blockAccess.e(i, j, k);
        return (DebugMode.active || metadata == 0);
    }

    public int a(xp world, int i, int j, int k, int side) {
        if (side == 0 || side == 1)
            return this.bm;
        int height = 1;
        while (world.a(i, j + height, k) == this.bn)
            height++;
        int offset = 1;
        while (world.a(i, j - offset, k) == this.bn) {
            height++;
            offset++;
        }
        int textureToReturn = this.bm;
        if (height > 2) {
            if (height / 2 == offset - 1) {
                int width = 1;
                offset = 1;
                while (world.a(i + width, j, k) == this.bn)
                    width++;
                while (world.a(i - offset, j, k) == this.bn) {
                    width++;
                    offset++;
                }
                if (width == 1) {
                    while (world.a(i, j, k + width) == this.bn)
                        width++;
                    while (world.a(i, j, k - offset) == this.bn) {
                        width++;
                        offset++;
                    }
                }
                if (width / 2 == offset - 1)
                    textureToReturn++;
            }
        } else {
            textureToReturn += 16;
            if (world.a(i, j - 1, k) != this.bn)
                textureToReturn += 16;
            if (side == 2) {
                if (world.a(i + 1, j, k) == this.bn)
                    textureToReturn++;
            } else if (side == 3) {
                if (world.a(i - 1, j, k) == this.bn)
                    textureToReturn++;
            } else if (side == 4) {
                if (world.a(i, j, k - 1) == this.bn)
                    textureToReturn++;
            } else if (side == 5) {
                if (world.a(i, j, k + 1) == this.bn)
                    textureToReturn++;
            }
        }
        return textureToReturn;
    }

    public void b(Level world, int i, int j, int k, gs entityplayer) {
        if (entityplayer.c.c(this.doorKeyToUse)) {
            world.a(i + 0.5D, j + 0.5D, k + 0.5D, "random.door_open", 1.0F, world.r.nextFloat() * 0.1F + 0.9F);
            int offsetY = 0;
            while (world.a(i, j + offsetY, k) == this.bn) {
                int offsetX = 0;
                while (world.a(i + offsetX, j + offsetY, k) == this.bn) {
                    world.d(i + offsetX, j + offsetY, k, 1);
                    world.j(i + offsetX, j + offsetY, k);
                    offsetX++;
                }
                offsetX = 1;
                while (world.a(i - offsetX, j + offsetY, k) == this.bn) {
                    world.d(i - offsetX, j + offsetY, k, 1);
                    world.j(i - offsetX, j + offsetY, k);
                    offsetX++;
                }
                offsetX = 1;
                while (world.a(i, j + offsetY, k + offsetX) == this.bn) {
                    world.d(i, j + offsetY, k + offsetX, 1);
                    world.j(i, j + offsetY, k + offsetX);
                    offsetX++;
                }
                offsetX = 1;
                while (world.a(i, j + offsetY, k - offsetX) == this.bn) {
                    world.d(i, j + offsetY, k - offsetX, 1);
                    world.j(i, j + offsetY, k - offsetX);
                    offsetX++;
                }
                offsetY++;
            }
            offsetY = -1;
            while (world.a(i, j + offsetY, k) == this.bn) {
                int offsetX = 0;
                while (world.a(i + offsetX, j + offsetY, k) == this.bn) {
                    world.d(i + offsetX, j + offsetY, k, 1);
                    world.j(i + offsetX, j + offsetY, k);
                    offsetX++;
                }
                offsetX = 1;
                while (world.a(i - offsetX, j + offsetY, k) == this.bn) {
                    world.d(i - offsetX, j + offsetY, k, 1);
                    world.j(i - offsetX, j + offsetY, k);
                    offsetX++;
                }
                offsetX = 1;
                while (world.a(i, j + offsetY, k + offsetX) == this.bn) {
                    world.d(i, j + offsetY, k + offsetX, 1);
                    world.j(i, j + offsetY, k + offsetX);
                    offsetX++;
                }
                offsetX = 1;
                while (world.a(i, j + offsetY, k - offsetX) == this.bn) {
                    world.d(i, j + offsetY, k - offsetX, 1);
                    world.j(i, j + offsetY, k - offsetX);
                    offsetX++;
                }
                offsetY--;
            }
        }
    }

    public void reset(Level world, int i, int j, int k, boolean death) {
        if (!death)
            world.d(i, j, k, 0);
    }

    public int alwaysUseClick(Level world, int i, int j, int k) {
        return 0;
    }
}
