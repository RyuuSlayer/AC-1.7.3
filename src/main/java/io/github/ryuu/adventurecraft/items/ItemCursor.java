package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

public class ItemCursor extends ItemType {

    public static boolean bothSet;
    public static int oneX;
    public static int oneY;
    public static int oneZ;
    public static int twoX;
    public static int twoY;
    public static int twoZ;
    public static int minX;
    public static int minY;
    public static int minZ;
    public static int maxX;
    public static int maxY;
    public static int maxZ;
    static boolean firstPosition;

    static {
        firstPosition = true;
        bothSet = false;
    }

    protected ItemCursor(int id) {
        super(id);
    }

    public boolean onItemUseLeftClick(ItemInstance itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        return this.useOnTile(itemstack, entityplayer, world, i, j, k, l);
    }

    @Override
    public boolean useOnTile(ItemInstance item, Player player, Level level, int x, int y, int z, int facing) {
        if (firstPosition) {
            AccessMinecraft.getInstance().overlay.addChatMessage(String.format("Setting Cursor Position 1 (%d, %d, %d)", x, y, z));
            oneX = x;
            oneY = y;
            oneZ = z;
        } else {
            AccessMinecraft.getInstance().overlay.addChatMessage(String.format("Setting Cursor Position 2 (%d, %d, %d)", x, y, z));
            twoX = x;
            twoY = y;
            twoZ = z;
            bothSet = true;
        }
        minX = Math.min(oneX, twoX);
        minY = Math.min(oneY, twoY);
        minZ = Math.min(oneZ, twoZ);
        maxX = Math.max(oneX, twoX);
        maxY = Math.max(oneY, twoY);
        maxZ = Math.max(oneZ, twoZ);
        firstPosition = !firstPosition;
        return false;
    }
}
