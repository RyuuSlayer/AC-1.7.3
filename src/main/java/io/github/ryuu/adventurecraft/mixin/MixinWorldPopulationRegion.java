package io.github.ryuu.adventurecraft.mixin;

import io.github.ryuu.adventurecraft.blocks.BlockStairMulti;
import io.github.ryuu.adventurecraft.util.LightCache;
import io.github.ryuu.adventurecraft.util.PlayerTorch;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.gen.BiomeSource;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;

public class MixinWorldPopulationRegion implements TileView {
    private final int a;

    private final int b;

    private final Chunk[][] c;

    private final Level d;

    public MixinWorldPopulationRegion(Level world, int i, int j, int k, int l, int i1, int j1) {
        this.d = world;
        this.a = i >> 4;
        this.b = k >> 4;
        int k1 = l >> 4;
        int l1 = j1 >> 4;
        this.c = new Chunk[k1 - this.a + 1][l1 - this.b + 1];
        for (int i2 = this.a; i2 <= k1; i2++) {
            for (int j2 = this.b; j2 <= l1; j2++)
                this.c[i2 - this.a][j2 - this.b] = world.c(i2, j2);
        }
    }

    public int a(int i, int j, int k) {
        if (j < 0)
            return 0;
        if (j >= 128)
            return 0;
        int l = (i >> 4) - this.a;
        int i1 = (k >> 4) - this.b;
        if (l < 0 || l >= this.c.length || i1 < 0 || i1 >= (this.c[l]).length)
            return 0;
        Chunk chunk = this.c[l][i1];
        if (chunk == null)
            return 0;
        return chunk.a(i & 0xF, j, k & 0xF);
    }

    public MixinTileEntity b(int i, int j, int k) {
        int l = (i >> 4) - this.a;
        int i1 = (k >> 4) - this.b;
        return this.c[l][i1].d(i & 0xF, j, k & 0xF);
    }

    public float a(int i, int j, int k, int l) {
        float light = LightCache.cache.getLightValue(i, j, k);
        if (light >= 0.0F)
            return light;
        int lightValue = d(i, j, k);
        if (lightValue < l)
            lightValue = l;
        float torchLight = PlayerTorch.getTorchLight(this.d, i, j, k);
        if (lightValue < torchLight) {
            int floorValue = (int) Math.floor(torchLight);
            if (floorValue == 15)
                return this.d.t.f[15];
            int ceilValue = (int) Math.ceil(torchLight);
            float lerpValue = torchLight - floorValue;
            return (1.0F - lerpValue) * this.d.t.f[floorValue] + lerpValue * this.d.t.f[ceilValue];
        }
        light = this.d.t.f[lightValue];
        LightCache.cache.setLightValue(i, j, k, light);
        return light;
    }

    public float c(int i, int j, int k) {
        float l = LightCache.cache.getLightValue(i, j, k);
        if (l >= 0.0F)
            return l;
        int lightValue = d(i, j, k);
        float torchLight = PlayerTorch.getTorchLight(this.d, i, j, k);
        if (lightValue < torchLight) {
            int floorValue = (int) Math.floor(torchLight);
            if (floorValue == 15)
                return this.d.t.f[15];
            int ceilValue = (int) Math.ceil(torchLight);
            float lerpValue = torchLight - floorValue;
            return (1.0F - lerpValue) * this.d.t.f[floorValue] + lerpValue * this.d.t.f[ceilValue];
        }
        l = this.d.t.f[lightValue];
        LightCache.cache.setLightValue(i, j, k, l);
        return l;
    }

    public int d(int i, int j, int k) {
        return a(i, j, k, true);
    }

    public int a(int i, int j, int k, boolean flag) {
        if (i < -32000000 || k < -32000000 || i >= 32000000 || k > 32000000)
            return 15;
        if (flag) {
            int l = a(i, j, k);
            if (l != 0 && (l == Tile.al.bn || l == Tile.aB.bn || l == Tile.au.bn || l == Tile.aI.bn || Tile.BY_ID[l] instanceof BlockStairMulti)) {
                int k1 = a(i, j + 1, k, false);
                int i2 = a(i + 1, j, k, false);
                int j2 = a(i - 1, j, k, false);
                int k2 = a(i, j, k + 1, false);
                int l2 = a(i, j, k - 1, false);
                if (i2 > k1)
                    k1 = i2;
                if (j2 > k1)
                    k1 = j2;
                if (k2 > k1)
                    k1 = k2;
                if (l2 > k1)
                    k1 = l2;
                return k1;
            }
        }
        if (j < 0)
            return 0;
        if (j >= 128) {
            int i1 = 15 - this.d.f;
            if (i1 < 0)
                i1 = 0;
            return i1;
        }
        int j1 = (i >> 4) - this.a;
        int l1 = (k >> 4) - this.b;
        return this.c[j1][l1].c(i & 0xF, j, k & 0xF, this.d.f);
    }

    public int e(int i, int j, int k) {
        if (j < 0)
            return 0;
        if (j >= 128)
            return 0;
        int l = (i >> 4) - this.a;
        int i1 = (k >> 4) - this.b;
        return this.c[l][i1].b(i & 0xF, j, k & 0xF);
    }

    public Material f(int i, int j, int k) {
        int l = a(i, j, k);
        if (l == 0)
            return Material.AIR;
        return (Tile.BY_ID[l]).bA;
    }

    public BiomeSource a() {
        return this.d.a();
    }

    public boolean g(int i, int j, int k) {
        Tile block = Tile.BY_ID[a(i, j, k)];
        if (block == null)
            return false;
        return block.c();
    }

    public boolean h(int i, int j, int k) {
        Tile block = Tile.BY_ID[a(i, j, k)];
        if (block == null)
            return false;
        return (block.bA.c() && block.d());
    }
}
