package io.github.ryuu.adventurecraft.items;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

public class ItemQuill extends ItemType {
    protected ItemQuill(int i) {
        super(i);
    }

    @Override
    public boolean useOnTile(ItemInstance itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        double yToUse = 128.0D;
        for (int y = j; y <= 128; y++) {
            if (world.a(i, y, k) == 0) {
                yToUse = (y + entityplayer.standingEyeHeight);
                break;
            }
        }
        Minecraft.minecraftInstance.v.a(String.format("Teleporting to (%.1f, %.1f %.1f)", new Object[]{Double.valueOf(i + 0.5D), Double.valueOf(yToUse), Double.valueOf(k + 0.5D)}));
        entityplayer.e(i + 0.5D, yToUse, k + 0.5D);
        return false;
    }
}
