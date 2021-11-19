package io.github.ryuu.adventurecraft.items;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;

public class ItemWrench extends ItemType {
    protected ItemWrench(int i) {
        super(i);
    }

    @Override
    public boolean useOnTile(ItemInstance itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        if (ItemCursor.bothSet) {
            int blockToSwapTo = world.getTileId(i, j, k);
            int metadata = world.getTileMeta(i, j, k);
            Minecraft.minecraftInstance.v.a(String.format("Swapping blocks With BlockID %d", new Object[]{Integer.valueOf(blockToSwapTo)}));
            int minX = Math.min(ItemCursor.oneX, ItemCursor.twoX);
            int maxX = Math.max(ItemCursor.oneX, ItemCursor.twoX);
            int minY = Math.min(ItemCursor.oneY, ItemCursor.twoY);
            int maxY = Math.max(ItemCursor.oneY, ItemCursor.twoY);
            int minZ = Math.min(ItemCursor.oneZ, ItemCursor.twoZ);
            int maxZ = Math.max(ItemCursor.oneZ, ItemCursor.twoZ);
            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    for (int z = minZ; z <= maxZ; z++) {
                        int blockID = world.getTileId(x, y, z);
                        switch (blockID) {
                            default:
                                world.setTileWithMetadata(x, y, z, blockToSwapTo, metadata);
                                break;
                            case 0:
                            case 23:
                            case 25:
                            case 46:
                            case 55:
                            case 61:
                            case 62:
                            case 64:
                            case 69:
                            case 70:
                            case 71:
                            case 72:
                            case 75:
                            case 76:
                            case 77:
                            case 93:
                            case 94:
                            case 101:
                            case 102:
                            case 103:
                            case 104:
                            case 107:
                            case 108:
                            case 109:
                            case 110:
                            case 111:
                            case 115:
                            case 117:
                            case 118:
                                break;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public float method_438(ItemInstance itemstack, Tile block) {
        return 32.0F;
    }

    @Override
    public boolean isEffectiveOn(Tile block) {
        return true;
    }

    public boolean c() {
        return true;
    }
}