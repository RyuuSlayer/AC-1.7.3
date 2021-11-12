package io.github.ryuu.adventurecraft.mixin;

import java.util.Random;

import io.github.ryuu.adventurecraft.blocks.IBlockColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.colour.GrassColour;
import net.minecraft.level.Level;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.tile.Tile;

public class MixinGrassTile extends Tile implements IBlockColor {
    protected MixinGrassTile(int i) {
        super(i, ln.b);
        this.bm = 3;
        b(true);
    }

    public int a(xp iblockaccess, int i, int j, int k, int l) {
        if (l == 1) {
            int metadata = iblockaccess.e(i, j, k);
            if (metadata == 0)
                return 0;
            return 232 + metadata - 1;
        }
        if (l == 0)
            return 2;
        ln material = iblockaccess.f(i, j + 1, k);
        return (material != ln.t && material != ln.u) ? 3 : 68;
    }

    public int a(int i, int metadata) {
        if (metadata == 0)
            return 0;
        return 232 + metadata - 1;
    }

    public int b(xp iblockaccess, int i, int j, int k) {
        iblockaccess.a().a(i, k, 1, 1);
        double d = (iblockaccess.a()).a[0];
        double d1 = (iblockaccess.a()).b[0];
        return GrassColour.a(d, d1);
    }

    public void a(Level world, int i, int j, int k, Random random) {
        if (world.B)
            return;
        if (world.n(i, j + 1, k) < 4 && Tile.q[world.a(i, j + 1, k)] > 2) {
            if (random.nextInt(4) != 0)
                return;
            Chunk.isNotPopulating = false;
            world.f(i, j, k, Tile.w.bn);
            Chunk.isNotPopulating = true;
        } else if (world.n(i, j + 1, k) >= 9) {
            int l = i + random.nextInt(3) - 1;
            int i1 = j + random.nextInt(5) - 3;
            int j1 = k + random.nextInt(3) - 1;
            int k1 = world.a(l, i1 + 1, j1);
            if (world.a(l, i1, j1) == uu.w.bn && world.n(l, i1 + 1, j1) >= 4 && uu.q[k1] <= 2) {
                Chunk.isNotPopulating = false;
                world.f(l, i1, j1, uu.v.bn);
                Chunk.isNotPopulating = true;
            }
        }
    }

    public int a(int i, Random random) {
        return Tile.w.a(0, random);
    }

    public int b() {
        if (Minecraft.minecraftInstance.z.grass3d)
            return 30;
        return super.b();
    }

    public void incrementColor(Level world, int i, int j, int k) {
        int metadata = world.e(i, j, k);
        world.d(i, j, k, (metadata + 1) % subTypes[this.bn]);
    }

    public float grassMultiplier(int metadata) {
        switch (metadata) {
            case 2:
                return 0.62F;
            case 3:
                return 0.85F;
            case 4:
                return -1.0F;
        }
        return 1.0F;
    }
}
