package io.github.ryuu.adventurecraft.overrides;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;
import net.minecraft.level.chunk.ChunkIO;
import net.minecraft.level.source.LevelSource;
import net.minecraft.util.ProgressListener;

public class ClientChunkCache implements LevelSource {
    private final Chunk c;

    private final LevelSource d;

    private final ChunkIO e;
    private final Level g;
    int a;
    int b;
    boolean isVeryFar;
    int mask;
    int chunksWide;
    private Chunk[] f;
    private Chunk h;
    private int i;
    private int j;

    public ClientChunkCache(Level world, ChunkIO ichunkloader, LevelSource ichunkprovider) {
        this.isVeryFar = (Minecraft.minecraftInstance.z.e != 0);
        updateVeryFar();
        this.a = -999999999;
        this.b = -999999999;
        this.c = (Chunk) new li(world, new byte[32768], 0, 0);
        this.g = world;
        this.e = ichunkloader;
        this.d = ichunkprovider;
    }

    public void updateVeryFar() {
        boolean curVeryFar = (Minecraft.minecraftInstance.z.e == 0);
        if (this.isVeryFar != curVeryFar) {
            this.isVeryFar = curVeryFar;
            this.a = -999999999;
            this.b = -999999999;
            if (this.f != null)
                a(true, null);
            Chunk[] oldChunks = this.f;
            if (this.isVeryFar) {
                this.f = new Chunk[4096];
                this.mask = 63;
                this.chunksWide = 64;
            } else {
                this.f = new Chunk[1024];
                this.mask = 31;
                this.chunksWide = 32;
            }
            if (oldChunks != null)
                for (int i = 0; i < oldChunks.length; i++) {
                    Chunk c = oldChunks[i];
                    if (c != null && e(c.j, c.k)) {
                        int k = c.j & this.mask;
                        int l = c.k & this.mask;
                        int i1 = k + l * this.chunksWide;
                        this.f[i1] = c;
                    }
                }
        }
    }

    public void d(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public boolean e(int i, int j) {
        return (i >= this.i - this.mask && j >= this.j - this.mask && i <= this.i + this.mask && j <= this.j + this.mask);
    }

    public boolean a(int i, int j) {
        if (!e(i, j))
            return false;
        if (i == this.a && j == this.b && this.h != null)
            return true;
        int k = i & this.mask;
        int l = j & this.mask;
        int i1 = k + l * this.chunksWide;
        return (this.f[i1] != null && (this.f[i1] == this.c || this.f[i1].a(i, j)));
    }

    public Chunk c(int i, int j) {
        return b(i, j);
    }

    public Chunk b(int i, int j) {
        if (i == this.a && j == this.b && this.h != null)
            return this.h;
        if (!this.g.y && !e(i, j))
            return this.c;
        int k = i & this.mask;
        int l = j & this.mask;
        int i1 = k + l * this.chunksWide;
        if (!a(i, j)) {
            if (this.f[i1] != null) {
                this.f[i1].f();
                b(this.f[i1]);
                a(this.f[i1]);
            }
            Chunk chunk = f(i, j);
            if (chunk == null)
                if (this.d == null) {
                    chunk = this.c;
                } else {
                    chunk = this.d.b(i, j);
                    chunk.i();
                }
            this.f[i1] = chunk;
            chunk.d();
            if (this.f[i1] != null)
                this.f[i1].e();
            if (!(this.f[i1]).n && a(i + 1, j + 1) && a(i, j + 1) && a(i + 1, j))
                a(this, i, j);
            if (a(i - 1, j) && !(b(i - 1, j)).n && a(i - 1, j + 1) && a(i, j + 1) && a(i - 1, j))
                a(this, i - 1, j);
            if (a(i, j - 1) && !(b(i, j - 1)).n && a(i + 1, j - 1) && a(i, j - 1) && a(i + 1, j))
                a(this, i, j - 1);
            if (a(i - 1, j - 1) && !(b(i - 1, j - 1)).n && a(i - 1, j - 1) && a(i, j - 1) && a(i - 1, j))
                a(this, i - 1, j - 1);
        }
        this.a = i;
        this.b = j;
        this.h = this.f[i1];
        return this.f[i1];
    }

    private Chunk f(int i, int j) {
        if (this.e == null)
            return this.c;
        try {
            Chunk chunk = this.e.a(this.g, i, j);
            if (chunk != null)
                chunk.r = this.g.t();
            return chunk;
        } catch (Exception exception) {
            exception.printStackTrace();
            return this.c;
        }
    }

    private void a(Chunk chunk) {
        if (this.e == null)
            return;
        try {
            this.e.b(this.g, chunk);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void b(Chunk chunk) {
        if (this.e == null)
            return;
        try {
            chunk.r = this.g.t();
            this.e.a(this.g, chunk);
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    public void a(LevelSource ichunkprovider, int i, int j) {
        Chunk chunk = b(i, j);
        if (!chunk.n) {
            chunk.n = true;
            if (this.d != null) {
                Chunk.isNotPopulating = false;
                this.d.a(ichunkprovider, i, j);
                chunk.o = false;
                Chunk.isNotPopulating = true;
            }
        }
    }

    public boolean a(boolean flag, ProgressListener iprogressupdate) {
        int i = 0;
        int j = 0;
        if (iprogressupdate != null)
            for (int k = 0; k < this.f.length; k++) {
                if (this.f[k] != null && this.f[k].a(flag))
                    j++;
            }
        int l = 0;
        for (int i1 = 0; i1 < this.f.length; i1++) {
            if (this.f[i1] != null) {
                if (flag && !(this.f[i1]).p)
                    a(this.f[i1]);
                if (this.f[i1].a(flag)) {
                    b(this.f[i1]);
                    (this.f[i1]).o = false;
                    if (++i == 2 && !flag)
                        return false;
                    if (iprogressupdate != null && ++l % 10 == 0)
                        iprogressupdate.a(l * 100 / j);
                }
            }
        }
        if (flag) {
            if (this.e == null)
                return true;
            this.e.b();
        }
        return true;
    }

    public boolean a() {
        if (this.e != null)
            this.e.a();
        return this.d.a();
    }

    public boolean b() {
        return true;
    }

    public String c() {
        return "ChunkCache: " + this.f.length;
    }
}
