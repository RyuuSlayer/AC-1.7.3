package io.github.ryuu.adventurecraft.items;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

public class ItemCursor extends ItemType {
    public static int minX;
    public static int minY;
    public static int minZ;
    public static int maxX;
    public static int maxY;
    public static int maxZ;
    static boolean bothSet;
    static boolean firstPosition = true;
    static int oneX;
    static int oneY;
    static int oneZ;
    static int twoX;
    static int twoY;
    static int twoZ;

    static {
        bothSet = false;
    }

    protected ItemCursor(int i) {
        super(i);
    }

    public boolean onItemUseLeftClick(ItemInstance itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        return useOnTile(itemstack, entityplayer, world, i, j, k, l);
    }

    @Override
    public boolean useOnTile(ItemInstance itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        if (firstPosition) {
            Minecraft.minecraftInstance.v.a(String.format("Setting Cursor Position 1 (%d, %d, %d)", new Object[]{Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k)}));
            oneX = i;
            oneY = j;
            oneZ = k;
        } else {
            Minecraft.minecraftInstance.v.a(String.format("Setting Cursor Position 2 (%d, %d, %d)", new Object[]{Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k)}));
            twoX = i;
            twoY = j;
            twoZ = k;
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
