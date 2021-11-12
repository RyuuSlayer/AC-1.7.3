package io.github.ryuu.adventurecraft.mixin.overrides;

import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;

public class ie extends ItemType {
    public ie(int i) {
        super(i);
    }

    public boolean a(ItemInstance itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        if (world.a(i, j, k) != Tile.aT.bn) {
            if (l == 0)
                j--;
            if (l == 1)
                j++;
            if (l == 2)
                k--;
            if (l == 3)
                k++;
            if (l == 4)
                i--;
            if (l == 5)
                i++;
            if (!world.d(i, j, k))
                return false;
        }
        if (DebugMode.active && Tile.aw.a(world, i, j, k)) {
            itemstack.a--;
            world.f(i, j, k, Tile.aw.bn);
        }
        return true;
    }
}
