package io.github.ryuu.adventurecraft.items;

import net.minecraft.client.Minecraft;

public class ItemCursor extends MixinItemType {

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

    static {
        firstPosition = true;
        bothSet = false;
    }

    protected ItemCursor(int id) {
        super(id);
    }

    @Override
    public boolean onItemUseLeftClick(MixinItemInstance itemstack, MixinPlayer entityplayer, MixinLevel world, int i, int j, int k, int l) {
        return this.useOnTile(itemstack, entityplayer, world, i, j, k, l);
    }

    @Override
    public boolean useOnTile(MixinItemInstance item, MixinPlayer player, MixinLevel level, int x, int y, int z, int facing) {
        if (firstPosition) {
            Minecraft.minecraftInstance.overlay.addChatMessage(String.format("Setting Cursor Position 1 (%d, %d, %d)", new Object[]{x, y, z}));
            oneX = x;
            oneY = y;
            oneZ = z;
        } else {
            Minecraft.minecraftInstance.overlay.addChatMessage(String.format("Setting Cursor Position 2 (%d, %d, %d)", new Object[]{x, y, z}));
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
