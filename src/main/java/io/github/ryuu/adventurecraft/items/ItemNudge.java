package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.mixin.level.AccessLevel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Vec3d;

public class ItemNudge extends ItemType implements LeftClickItemType {

    public ItemNudge(int id) {
        super(id);
    }

    @Override
    public ItemInstance use(ItemInstance item, Level level, Player player) {
        if (ItemCursor.bothSet) {
            int metadata;
            int blockID;
            int k;
            int j;
            int i;
            LivingEntity camera = AccessMinecraft.getInstance().cameraEntity;
            Vec3d lookDir = camera.getRotation();
            int width = ItemCursor.maxX - ItemCursor.minX + 1;
            int height = ItemCursor.maxY - ItemCursor.minY + 1;
            int depth = ItemCursor.maxZ - ItemCursor.minZ + 1;
            int[] blocks = new int[width * height * depth];
            int[] meta = new int[width * height * depth];
            for (int i2 = 0; i2 < width; ++i2) {
                for (int j2 = 0; j2 < height; ++j2) {
                    for (int k2 = 0; k2 < depth; ++k2) {
                        int blockID2 = level.getTileId(i2 + ItemCursor.minX, j2 + ItemCursor.minY, k2 + ItemCursor.minZ);
                        int metadata2 = level.getTileMeta(i2 + ItemCursor.minX, j2 + ItemCursor.minY, k2 + ItemCursor.minZ);
                        int i1 = depth * (height * i2 + j2) + k2;
                        blocks[i1] = blockID2;
                        meta[i1] = metadata2;
                        level.setTileInChunk(i2 + ItemCursor.minX, j2 + ItemCursor.minY, k2 + ItemCursor.minZ, 0);
                    }
                }
            }
            double absX = Math.abs(lookDir.x);
            double absY = Math.abs(lookDir.y);
            double absZ = Math.abs(lookDir.z);
            int xOffset = ItemCursor.minX;
            int yOffset = ItemCursor.minY;
            int zOffset = ItemCursor.minZ;
            if (absX > absY && absX > absZ) {
                if (lookDir.x < 0.0) {
                    ++xOffset;
                    ++ItemCursor.minX;
                    ++ItemCursor.maxX;
                    ++ItemCursor.oneX;
                    ++ItemCursor.twoX;
                } else {
                    --xOffset;
                    --ItemCursor.minX;
                    --ItemCursor.maxX;
                    --ItemCursor.oneX;
                    --ItemCursor.twoX;
                }
            } else if (absY > absZ) {
                if (lookDir.y < 0.0) {
                    ++yOffset;
                    ++ItemCursor.minY;
                    ++ItemCursor.maxY;
                    ++ItemCursor.oneY;
                    ++ItemCursor.twoY;
                } else {
                    --yOffset;
                    --ItemCursor.minY;
                    --ItemCursor.maxY;
                    --ItemCursor.oneY;
                    --ItemCursor.twoY;
                }
            } else if (lookDir.z < 0.0) {
                ++zOffset;
                ++ItemCursor.minZ;
                ++ItemCursor.maxZ;
                ++ItemCursor.oneZ;
                ++ItemCursor.twoZ;
            } else {
                --zOffset;
                --ItemCursor.minZ;
                --ItemCursor.maxZ;
                --ItemCursor.oneZ;
                --ItemCursor.twoZ;
            }
            for (i = 0; i < width; ++i) {
                for (j = 0; j < height; ++j) {
                    for (k = 0; k < depth; ++k) {
                        blockID = blocks[depth * (height * i + j) + k];
                        metadata = meta[depth * (height * i + j) + k];
                        level.setTileWithMetadata(xOffset + i, yOffset + j, zOffset + k, blockID, metadata);
                    }
                }
            }
            for (i = 0; i < width; ++i) {
                for (j = 0; j < height; ++j) {
                    for (k = 0; k < depth; ++k) {
                        blockID = blocks[depth * (height * i + j) + k];
                        metadata = meta[depth * (height * i + j) + k];
                        level.setTileWithMetadata(xOffset + i, yOffset + j, zOffset + k, blockID, metadata);
                    }
                }
            }
            for (i = 0; i < width; ++i) {
                for (j = 0; j < height; ++j) {
                    for (k = 0; k < depth; ++k) {
                        blockID = blocks[depth * (height * i + j) + k];
                        ((AccessLevel) level).invokeMethod_235(xOffset + i, yOffset + j, zOffset + k, blockID);
                    }
                }
            }
        }
        return item;
    }

    @Override
    public void onItemLeftClick(ItemInstance itemstack, Level level, Player entityplayer) {
        if (ItemCursor.bothSet) {
            int blockID;
            int k;
            int j;
            int i;
            LivingEntity camera = AccessMinecraft.getInstance().cameraEntity;
            Vec3d lookDir = camera.getRotation();
            int width = ItemCursor.maxX - ItemCursor.minX + 1;
            int height = ItemCursor.maxY - ItemCursor.minY + 1;
            int depth = ItemCursor.maxZ - ItemCursor.minZ + 1;
            int[] blocks = new int[width * height * depth];
            int[] meta = new int[width * height * depth];
            for (int i2 = 0; i2 < width; ++i2) {
                for (int j2 = 0; j2 < height; ++j2) {
                    for (int k2 = 0; k2 < depth; ++k2) {
                        int blockID2 = level.getTileId(i2 + ItemCursor.minX, j2 + ItemCursor.minY, k2 + ItemCursor.minZ);
                        int metadata = level.getTileMeta(i2 + ItemCursor.minX, j2 + ItemCursor.minY, k2 + ItemCursor.minZ);
                        int i1 = depth * (height * i2 + j2) + k2;
                        blocks[i1] = blockID2;
                        meta[i1] = metadata;
                        level.setTileInChunk(i2 + ItemCursor.minX, j2 + ItemCursor.minY, k2 + ItemCursor.minZ, 0);
                    }
                }
            }
            double absX = Math.abs(lookDir.x);
            double absY = Math.abs(lookDir.y);
            double absZ = Math.abs(lookDir.z);
            int xOffset = ItemCursor.minX;
            int yOffset = ItemCursor.minY;
            int zOffset = ItemCursor.minZ;
            if (absX > absY && absX > absZ) {
                if (lookDir.x > 0.0) {
                    ++xOffset;
                    ++ItemCursor.minX;
                    ++ItemCursor.maxX;
                    ++ItemCursor.oneX;
                    ++ItemCursor.twoX;
                } else {
                    --xOffset;
                    --ItemCursor.minX;
                    --ItemCursor.maxX;
                    --ItemCursor.oneX;
                    --ItemCursor.twoX;
                }
            } else if (absY > absZ) {
                if (lookDir.y > 0.0) {
                    ++yOffset;
                    ++ItemCursor.minY;
                    ++ItemCursor.maxY;
                    ++ItemCursor.oneY;
                    ++ItemCursor.twoY;
                } else {
                    --yOffset;
                    --ItemCursor.minY;
                    --ItemCursor.maxY;
                    --ItemCursor.oneY;
                    --ItemCursor.twoY;
                }
            } else if (lookDir.z > 0.0) {
                ++zOffset;
                ++ItemCursor.minZ;
                ++ItemCursor.maxZ;
                ++ItemCursor.oneZ;
                ++ItemCursor.twoZ;
            } else {
                --zOffset;
                --ItemCursor.minZ;
                --ItemCursor.maxZ;
                --ItemCursor.oneZ;
                --ItemCursor.twoZ;
            }
            for (i = 0; i < width; ++i) {
                for (j = 0; j < height; ++j) {
                    for (k = 0; k < depth; ++k) {
                        blockID = blocks[depth * (height * i + j) + k];
                        int metadata = meta[depth * (height * i + j) + k];
                        level.setTileWithMetadata(xOffset + i, yOffset + j, zOffset + k, blockID, metadata);
                    }
                }
            }
            for (i = 0; i < width; ++i) {
                for (j = 0; j < height; ++j) {
                    for (k = 0; k < depth; ++k) {
                        blockID = blocks[depth * (height * i + j) + k];
                        ((AccessLevel) level).invokeMethod_235(xOffset + i, yOffset + j, zOffset + k, blockID);
                    }
                }
            }
        }
    }
}
