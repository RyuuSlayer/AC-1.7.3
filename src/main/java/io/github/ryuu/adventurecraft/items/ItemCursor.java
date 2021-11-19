package io.github.ryuu.adventurecraft.items;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

public class ItemCursor extends ItemType {

    static boolean bothSet;

    static boolean firstPosition;

    static int oneX;

    static int oneY;

    static int oneZ;

    static int twoX;

    static int twoY;

    static int twoZ;

    static int minX;

    static int minY;

    static int minZ;

    static int maxX;

    static int maxY;

    static int maxZ;

    protected ItemCursor(int id) {
        super(id);
    }

    @Override
    public boolean onItemUseLeftClick(ItemInstance itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        return this.useOnTile(itemstack, entityplayer, world, i, j, k, l);
    }

    @Override
    public boolean useOnTile(ItemInstance item, Player player, Level level, int x, int y, int z, int facing) {
        if (firstPosition) {
            Minecraft.minecraftInstance.overlay.addChatMessage(String.format((String) "Setting Cursor Position 1 (%d, %d, %d)", (Object[]) new Object[] { x, y, z }));
            oneX = x;
            oneY = y;
            oneZ = z;
        } else {
            Minecraft.minecraftInstance.overlay.addChatMessage(String.format((String) "Setting Cursor Position 2 (%d, %d, %d)", (Object[]) new Object[] { x, y, z }));
            twoX = x;
            twoY = y;
            twoZ = z;
            bothSet = true;
        }
        minX = Math.min((int) oneX, (int) twoX);
        minY = Math.min((int) oneY, (int) twoY);
        minZ = Math.min((int) oneZ, (int) twoZ);
        maxX = Math.max((int) oneX, (int) twoX);
        maxY = Math.max((int) oneY, (int) twoY);
        maxZ = Math.max((int) oneZ, (int) twoZ);
        firstPosition = !firstPosition;
        return false;
    }

    static {
        firstPosition = true;
        bothSet = false;
    }
}
