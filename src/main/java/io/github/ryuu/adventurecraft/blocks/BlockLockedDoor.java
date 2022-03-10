package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileSounds;
import net.minecraft.tile.material.Material;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.Vec3d;

public class BlockLockedDoor extends Tile implements AcRenderConditionTile, AcResetTile, AcClickTile {

    int doorKeyToUse;

    protected BlockLockedDoor(int i, int j, int keyToUse, TileSounds sounds) {
        super(i, Material.METAL);
        this.tex = j;
        this.doorKeyToUse = keyToUse;
        this.hardness(5.0f);
        this.sounds(sounds);
        ((ExTile) this).setTextureNum(3);
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public HitResult raycast(Level world, int x, int y, int z, Vec3d vec3d, Vec3d vec3d1) {
        int metadata = world.getTileMeta(x, y, z);
        if (!DebugMode.active && metadata == 1) {
            return null;
        }
        return super.raycast(world, x, y, z, vec3d, vec3d1);
    }

    @Override
    public Box getCollisionShape(Level level, int x, int y, int z) {
        int metadata = level.getTileMeta(x, y, z);
        if (DebugMode.active || metadata == 1) {
            return null;
        }
        return super.getCollisionShape(level, x, y, z);
    }

    @Override
    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        int metadata = blockAccess.getTileMeta(i, j, k);
        return DebugMode.active || metadata == 0;
    }

    @Override
    public int method_1626(TileView world, int i, int j, int k, int side) {
        if (side == 0 || side == 1) {
            return this.tex;
        }
        int height = 1;
        while (world.getTileId(i, j + height, k) == this.id) {
            ++height;
        }
        int offset = 1;
        while (world.getTileId(i, j - offset, k) == this.id) {
            ++height;
            ++offset;
        }
        int textureToReturn = this.tex;
        if (height > 2) {
            if (height / 2 == offset - 1) {
                int width = 1;
                offset = 1;
                while (world.getTileId(i + width, j, k) == this.id) {
                    ++width;
                }
                while (world.getTileId(i - offset, j, k) == this.id) {
                    ++width;
                    ++offset;
                }
                if (width == 1) {
                    while (world.getTileId(i, j, k + width) == this.id) {
                        ++width;
                    }
                    while (world.getTileId(i, j, k - offset) == this.id) {
                        ++width;
                        ++offset;
                    }
                }
                if (width / 2 == offset - 1) {
                    ++textureToReturn;
                }
            }
        } else {
            textureToReturn += 16;
            if (world.getTileId(i, j - 1, k) != this.id) {
                textureToReturn += 16;
            }
            if (side == 2) {
                if (world.getTileId(i + 1, j, k) == this.id) {
                    ++textureToReturn;
                }
            } else if (side == 3) {
                if (world.getTileId(i - 1, j, k) == this.id) {
                    ++textureToReturn;
                }
            } else if (side == 4) {
                if (world.getTileId(i, j, k - 1) == this.id) {
                    ++textureToReturn;
                }
            } else if (side == 5 && world.getTileId(i, j, k + 1) == this.id) {
                ++textureToReturn;
            }
        }
        return textureToReturn;
    }

    @Override
    public void onPunched(Level level, int x, int y, int z, Player player) {
        if (player.inventory.decreaseAmountOfItem(this.doorKeyToUse)) {
            int offsetX;
            level.playSound((double) x + 0.5, (double) y + 0.5, (double) z + 0.5, "random.door_open", 1.0f, level.rand.nextFloat() * 0.1f + 0.9f);
            int offsetY = 0;
            while (level.getTileId(x, y + offsetY, z) == this.id) {
                offsetX = 0;
                while (level.getTileId(x + offsetX, y + offsetY, z) == this.id) {
                    level.setTileMeta(x + offsetX, y + offsetY, z, 1);
                    level.method_243(x + offsetX, y + offsetY, z);
                    ++offsetX;
                }
                offsetX = 1;
                while (level.getTileId(x - offsetX, y + offsetY, z) == this.id) {
                    level.setTileMeta(x - offsetX, y + offsetY, z, 1);
                    level.method_243(x - offsetX, y + offsetY, z);
                    ++offsetX;
                }
                offsetX = 1;
                while (level.getTileId(x, y + offsetY, z + offsetX) == this.id) {
                    level.setTileMeta(x, y + offsetY, z + offsetX, 1);
                    level.method_243(x, y + offsetY, z + offsetX);
                    ++offsetX;
                }
                offsetX = 1;
                while (level.getTileId(x, y + offsetY, z - offsetX) == this.id) {
                    level.setTileMeta(x, y + offsetY, z - offsetX, 1);
                    level.method_243(x, y + offsetY, z - offsetX);
                    ++offsetX;
                }
                ++offsetY;
            }
            offsetY = -1;
            while (level.getTileId(x, y + offsetY, z) == this.id) {
                offsetX = 0;
                while (level.getTileId(x + offsetX, y + offsetY, z) == this.id) {
                    level.setTileMeta(x + offsetX, y + offsetY, z, 1);
                    level.method_243(x + offsetX, y + offsetY, z);
                    ++offsetX;
                }
                offsetX = 1;
                while (level.getTileId(x - offsetX, y + offsetY, z) == this.id) {
                    level.setTileMeta(x - offsetX, y + offsetY, z, 1);
                    level.method_243(x - offsetX, y + offsetY, z);
                    ++offsetX;
                }
                offsetX = 1;
                while (level.getTileId(x, y + offsetY, z + offsetX) == this.id) {
                    level.setTileMeta(x, y + offsetY, z + offsetX, 1);
                    level.method_243(x, y + offsetY, z + offsetX);
                    ++offsetX;
                }
                offsetX = 1;
                while (level.getTileId(x, y + offsetY, z - offsetX) == this.id) {
                    level.setTileMeta(x, y + offsetY, z - offsetX, 1);
                    level.method_243(x, y + offsetY, z - offsetX);
                    ++offsetX;
                }
                --offsetY;
            }
        }
    }

    @Override
    public void reset(Level world, int i, int j, int k, boolean death) {
        if (!death) {
            world.setTileMeta(i, j, k, 0);
        }
    }

    @Override
    public int alwaysUseClick(Level world, int i, int j, int k) {
        return 0;
    }
}
