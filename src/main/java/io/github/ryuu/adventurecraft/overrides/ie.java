package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.util.DebugMode;

public class ie extends gm {
    public ie(int i) {
        super(i);
    }

    public boolean a(iz itemstack, gs entityplayer, fd world, int i, int j, int k, int l) {
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
