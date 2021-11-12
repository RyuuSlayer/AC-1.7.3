package io.github.ryuu.adventurecraft.mixin.overrides;

import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;

public class ck extends ItemType {
    private final int a;

    public ck(int i) {
        super(i);
        this.a = i + 256;
        c(Tile.m[i + 256].a(2));
    }

    public boolean a(ItemInstance itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        if (!DebugMode.active)
            return false;
        if (world.a(i, j, k) == Tile.aT.bn) {
            l = 0;
        } else {
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
        }
        if (itemstack.a == 0)
            return false;
        if (j == 127 && (Tile.m[this.a]).bA.a())
            return false;
        if (world.a(this.a, i, j, k, false, l)) {
            Tile block = Tile.m[this.a];
            if (world.b(i, j, k, this.a, b(itemstack.i()))) {
                Tile.m[this.a].e(world, i, j, k, l);
                Tile.m[this.a].a(world, i, j, k, entityplayer);
                world.a((i + 0.5F), (j + 0.5F), (k + 0.5F), block.by.d(), (block.by.b() + 1.0F) / 2.0F, block.by.c() * 0.8F);
                itemstack.a--;
            }
            return true;
        }
        return false;
    }

    public String a(ItemInstance itemstack) {
        return Tile.m[this.a].o();
    }

    public String a() {
        return Tile.m[this.a].o();
    }
}
