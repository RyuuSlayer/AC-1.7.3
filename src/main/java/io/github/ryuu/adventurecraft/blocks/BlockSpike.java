package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.Box;

public class BlockSpike extends Tile {
    protected BlockSpike(int i) {
        super(i, 246, ln.f);
    }

    public Box e(Level world, int i, int j, int k) {
        float f = 0.25F;
        return eq.b((i + f), j, (k + f), ((i + 1) - f), ((j + 1) - f), ((k + 1) - f));
    }

    public boolean d() {
        return false;
    }

    public boolean c() {
        return false;
    }

    public int b() {
        return 32;
    }

    public void a(Level world, int i, int j, int k, sn entity) {
        entity.a((sn)null, 10);
    }
}
