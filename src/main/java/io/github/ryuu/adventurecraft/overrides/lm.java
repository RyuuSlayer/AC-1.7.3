package io.github.ryuu.adventurecraft.overrides;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class lm {
    public static boolean a;

    public byte[] b;

    public boolean c;

    public fd d;

    public wi e;

    public wi f;

    public wi g;

    public byte[] h;

    public int i;

    public final int j;

    public final int k;

    public Map l;

    public List[] m;

    public boolean n;

    public boolean o;

    public boolean p;

    public boolean q;

    public long r;

    public double[] temperatures;

    public long lastUpdated;

    public lm(fd world, int i, int j, boolean createHeightMap) {
        this.l = new HashMap<Object, Object>();
        this.m = new List[8];
        this.n = false;
        this.o = false;
        this.q = false;
        this.r = 0L;
        this.d = world;
        this.j = i;
        this.k = j;
        if (createHeightMap)
            this.h = new byte[256];
        for (int k = 0; k < this.m.length; k++)
            this.m[k] = new ArrayList();
    }

    public lm(fd world, int i, int j) {
        this(world, i, j, true);
    }

    public lm(fd world, byte[] abyte0, int i, int j) {
        this(world, i, j);
        this.b = abyte0;
        this.e = new wi(abyte0.length);
        this.f = new wi(abyte0.length);
        this.g = new wi(abyte0.length);
    }

    public boolean a(int i, int j) {
        return (i == this.j && j == this.k);
    }

    public int b(int i, int j) {
        return this.h[j << 4 | i] & 0xFF;
    }

    public void a() {
    }

    public void b() {
        int i = 127;
        for (int j = 0; j < 16; j++) {
            for (int k = 0; k < 16; k++) {
                int l = 127;
                for (int i1 = j << 11 | k << 7; l > 0 && Tile.q[translate256(this.b[i1 + l - 1])] == 0; l--) ;
                this.h[k << 4 | j] = (byte) l;
                if (l < i)
                    i = l;
            }
        }
        this.i = i;
    }

    public void c() {
        int i = 127;
        for (int j = 0; j < 16; j++) {
            for (int l = 0; l < 16; l++) {
                int j1 = 127;
                int k1;
                for (k1 = j << 11 | l << 7; j1 > 0 && Tile.q[translate256(this.b[k1 + j1 - 1])] == 0; j1--) ;
                this.h[l << 4 | j] = (byte) j1;
                if (j1 < i)
                    i = j1;
                if (!this.d.t.e) {
                    int l1 = 15;
                    int i2 = 127;
                    do {
                        l1 -= Tile.q[translate256(this.b[k1 + i2])];
                        if (l1 <= 0)
                            continue;
                        this.f.a(j, i2, l, l1);
                    } while (--i2 > 0 && l1 > 0);
                }
            }
        }
        this.i = i;
        for (int k = 0; k < 16; k++) {
            for (int i1 = 0; i1 < 16; i1++)
                c(k, i1);
        }
    }

    public void d() {
    }

    private void c(int i, int j) {
        int k = b(i, j);
        int l = this.j * 16 + i;
        int i1 = this.k * 16 + j;
        f(l - 1, i1, k);
        f(l + 1, i1, k);
        f(l, i1 - 1, k);
        f(l, i1 + 1, k);
    }

    private void f(int i, int j, int k) {
        int l = this.d.d(i, j);
        if (l > k) {
            this.d.a(eb.a, i, k, j, i, l, j);
        } else if (l < k) {
            this.d.a(eb.a, i, l, j, i, k, j);
        }
    }

    private void g(int i, int j, int k) {
        int l = this.h[k << 4 | i] & 0xFF;
        int i1 = l;
        if (j > l)
            i1 = j;
        for (int j1 = i << 11 | k << 7; i1 > 0 && Tile.q[translate256(this.b[j1 + i1 - 1])] == 0; i1--) ;
        if (i1 == l)
            return;
        this.d.h(i, k, i1, l);
        this.h[k << 4 | i] = (byte) i1;
        if (i1 < this.i) {
            this.i = i1;
        } else {
            int k1 = 127;
            for (int i2 = 0; i2 < 16; i2++) {
                for (int k2 = 0; k2 < 16; k2++) {
                    if ((this.h[k2 << 4 | i2] & 0xFF) < k1)
                        k1 = this.h[k2 << 4 | i2] & 0xFF;
                }
            }
            this.i = k1;
        }
        int l1 = this.j * 16 + i;
        int j2 = this.k * 16 + k;
        if (i1 < l) {
            for (int l2 = i1; l2 < l; l2++)
                this.f.a(i, l2, k, 15);
        } else {
            this.d.a(eb.a, l1, l, j2, l1, i1, j2);
            for (int i3 = l; i3 < i1; i3++)
                this.f.a(i, i3, k, 0);
        }
        int j3 = 15;
        int k3 = i1;
        while (i1 > 0 && j3 > 0) {
            i1--;
            int l3 = Tile.q[a(i, i1, k)];
            if (l3 == 0)
                l3 = 1;
            j3 -= l3;
            if (j3 < 0)
                j3 = 0;
            this.f.a(i, i1, k, j3);
        }
        for (; i1 > 0 && Tile.q[a(i, i1 - 1, k)] == 0; i1--) ;
        if (i1 != k3)
            this.d.a(eb.a, l1 - 1, i1, j2 - 1, l1 + 1, k3, j2 + 1);
    }

    public int a(int i, int j, int k) {
        return translate256(this.b[i << 11 | k << 7 | j]);
    }

    public boolean a(int i, int j, int k, int l, int i1) {
        if (this.d.undoStack.isRecording()) {
            int prevBlockID = a(i, j, k);
            int prevMetadata = b(i, j, k);
            ow te = getChunkBlockTileEntityDontCreate(i, j, k);
            nu prevTag = null;
            if (te != null) {
                prevTag = new nu();
                te.b(prevTag);
            }
            this.d.undoStack.recordChange(i + (this.j << 4), j, k + (this.k << 4), prevBlockID, prevMetadata, prevTag, l, i1, null);
        }
        int byte0 = translate256(l);
        int j1 = this.h[k << 4 | i] & 0xFF;
        int k1 = translate256(this.b[i << 11 | k << 7 | j]) & 0xFF;
        if (k1 == l && this.e.a(i, j, k) == i1)
            return false;
        int l1 = this.j * 16 + i;
        int i2 = this.k * 16 + k;
        this.b[i << 11 | k << 7 | j] = (byte) translate128(byte0);
        if (k1 != 0 && !this.d.B)
            Tile.m[k1].b(this.d, l1, j, i2);
        this.e.a(i, j, k, i1);
        if (!this.d.t.e) {
            if (Tile.q[byte0 & 0xFF] != 0) {
                if (j >= j1)
                    g(i, j + 1, k);
            } else if (j == j1 - 1) {
                g(i, j, k);
            }
            this.d.a(eb.a, l1, j, i2, l1, j, i2);
        }
        this.d.a(eb.b, l1, j, i2, l1, j, i2);
        c(i, k);
        this.e.a(i, j, k, i1);
        if (l != 0)
            Tile.m[l].c(this.d, l1, j, i2);
        if (isNotPopulating)
            this.o = true;
        return true;
    }

    public boolean setBlockIDWithMetadataTemp(int i, int j, int k, int l, int i1) {
        int byte0 = translate256(l);
        this.b[i << 11 | k << 7 | j] = (byte) translate128(byte0);
        this.e.a(i, j, k, i1);
        return true;
    }

    public boolean a(int i, int j, int k, int l) {
        if (this.d.undoStack.isRecording()) {
            int prevBlockID = a(i, j, k);
            int prevMetadata = b(i, j, k);
            ow te = getChunkBlockTileEntityDontCreate(i, j, k);
            nu prevTag = null;
            if (te != null) {
                prevTag = new nu();
                te.b(prevTag);
            }
            this.d.undoStack.recordChange(i + (this.j << 4), j, k + (this.k << 4), prevBlockID, prevMetadata, prevTag, l, 0, null);
        }
        int byte0 = translate256(l);
        int i1 = this.h[k << 4 | i] & 0xFF;
        int j1 = translate256(this.b[i << 11 | k << 7 | j]) & 0xFF;
        if (j1 == l)
            return false;
        int k1 = this.j * 16 + i;
        int l1 = this.k * 16 + k;
        this.b[i << 11 | k << 7 | j] = (byte) translate128(byte0);
        if (j1 != 0)
            Tile.m[j1].b(this.d, k1, j, l1);
        this.e.a(i, j, k, 0);
        if (Tile.q[byte0 & 0xFF] != 0) {
            if (j >= i1)
                g(i, j + 1, k);
        } else if (j == i1 - 1) {
            g(i, j, k);
        }
        this.d.a(eb.a, k1, j, l1, k1, j, l1);
        this.d.a(eb.b, k1, j, l1, k1, j, l1);
        c(i, k);
        if (l != 0 && !this.d.B)
            Tile.m[l].c(this.d, k1, j, l1);
        if (isNotPopulating)
            this.o = true;
        return true;
    }

    public int b(int i, int j, int k) {
        return this.e.a(i, j, k);
    }

    public void b(int i, int j, int k, int l) {
        if (this.d.undoStack.isRecording()) {
            int prevBlockID = a(i, j, k);
            int prevMetadata = b(i, j, k);
            ow te = getChunkBlockTileEntityDontCreate(i, j, k);
            nu prevTag = null;
            if (te != null) {
                prevTag = new nu();
                te.b(prevTag);
            }
            this.d.undoStack.recordChange(i + (this.j << 4), j, k + (this.k << 4), prevBlockID, prevMetadata, prevTag, prevBlockID, l, null);
        }
        if (isNotPopulating)
            this.o = true;
        this.e.a(i, j, k, l);
    }

    public int a(eb enumskyblock, int i, int j, int k) {
        if (enumskyblock == eb.a)
            return this.f.a(i, j, k);
        if (enumskyblock == eb.b)
            return this.g.a(i, j, k);
        return 0;
    }

    public void a(eb enumskyblock, int i, int j, int k, int l) {
        if (enumskyblock == eb.a) {
            this.f.a(i, j, k, l);
        } else if (enumskyblock == eb.b) {
            this.g.a(i, j, k, l);
        } else {
            return;
        }
    }

    public int c(int i, int j, int k, int l) {
        int i1 = this.f.a(i, j, k);
        if (i1 > 0)
            a = true;
        i1 -= l;
        int j1 = this.g.a(i, j, k);
        if (j1 > i1)
            i1 = j1;
        return i1;
    }

    public void a(sn entity) {
        if (!(entity instanceof gs))
            this.q = true;
        int i = in.b(entity.aM / 16.0D);
        int j = in.b(entity.aO / 16.0D);
        if (i != this.j || j != this.k) {
            System.out.println("Wrong location! " + entity);
            Thread.dumpStack();
        }
        int k = in.b(entity.aN / 16.0D);
        if (k < 0)
            k = 0;
        if (k >= this.m.length)
            k = this.m.length - 1;
        entity.bF = true;
        entity.bG = this.j;
        entity.bH = k;
        entity.bI = this.k;
        this.m[k].add(entity);
    }

    public void b(sn entity) {
        a(entity, entity.bH);
    }

    public void a(sn entity, int i) {
        if (i < 0)
            i = 0;
        if (i >= this.m.length)
            i = this.m.length - 1;
        this.m[i].remove(entity);
    }

    public boolean c(int i, int j, int k) {
        return (j >= (this.h[k << 4 | i] & 0xFF));
    }

    public ow d(int i, int j, int k) {
        wf chunkposition = new wf(i, j, k);
        ow tileentity = (ow) this.l.get(chunkposition);
        if (tileentity == null) {
            int l = a(i, j, k);
            if (!Tile.p[l])
                return null;
            rw blockcontainer = (rw) Tile.m[l];
            blockcontainer.c(this.d, this.j * 16 + i, j, this.k * 16 + k);
            tileentity = (ow) this.l.get(chunkposition);
        }
        if (tileentity != null && tileentity.g()) {
            this.l.remove(chunkposition);
            return null;
        }
        return tileentity;
    }

    public ow getChunkBlockTileEntityDontCreate(int i, int j, int k) {
        wf chunkposition = new wf(i, j, k);
        ow tileentity = (ow) this.l.get(chunkposition);
        return tileentity;
    }

    public void a(ow tileentity) {
        int i = tileentity.e - this.j * 16;
        int j = tileentity.f;
        int k = tileentity.g - this.k * 16;
        a(i, j, k, tileentity);
        if (this.c)
            this.d.c.add(tileentity);
    }

    public void a(int i, int j, int k, ow tileentity) {
        wf chunkposition = new wf(i, j, k);
        tileentity.d = this.d;
        tileentity.e = this.j * 16 + i;
        tileentity.f = j;
        tileentity.g = this.k * 16 + k;
        if (a(i, j, k) == 0 || !(Tile.m[a(i, j, k)] instanceof rw)) {
            System.out.printf("No container :( BlockID: %d Tile Entity: %s Coord: %d, %d, %d\n", new Object[]{Integer.valueOf(a(i, j, k)), tileentity.getClassName(), Integer.valueOf(tileentity.e), Integer.valueOf(tileentity.f), Integer.valueOf(tileentity.g)});
            return;
        }
        tileentity.j();
        this.l.put(chunkposition, tileentity);
    }

    public void e(int i, int j, int k) {
        wf chunkposition = new wf(i, j, k);
        if (this.c) {
            ow tileentity = (ow) this.l.remove(chunkposition);
            if (tileentity != null)
                tileentity.i();
        }
    }

    public void e() {
        this.c = true;
        this.d.a(this.l.values());
        for (int i = 0; i < this.m.length; i++)
            this.d.a(this.m[i]);
        this.temperatures = this.d.a().a(this.temperatures, this.j * 16, this.k * 16, 16, 16);
    }

    public void f() {
        this.c = false;
        for (Iterator<ow> iterator = this.l.values().iterator(); iterator.hasNext(); tileentity.i()) {
            ow tileentity = iterator.next();
            tileentity.killedFromSaving = true;
        }
        for (int i = 0; i < this.m.length; i++)
            this.d.b(this.m[i]);
    }

    public void g() {
        this.o = true;
    }

    public void a(sn entity, eq axisalignedbb, List<sn> list) {
        int i = in.b((axisalignedbb.b - 2.0D) / 16.0D);
        int j = in.b((axisalignedbb.e + 2.0D) / 16.0D);
        if (i < 0)
            i = 0;
        if (j >= this.m.length)
            j = this.m.length - 1;
        for (int k = i; k <= j; k++) {
            List<sn> list1 = this.m[k];
            for (int l = 0; l < list1.size(); l++) {
                sn entity1 = list1.get(l);
                if (entity1 != entity && entity1.aW.a(axisalignedbb))
                    list.add(entity1);
            }
        }
    }

    public void a(Class class1, eq axisalignedbb, List<sn> list) {
        int i = in.b((axisalignedbb.b - 2.0D) / 16.0D);
        int j = in.b((axisalignedbb.e + 2.0D) / 16.0D);
        if (i < 0)
            i = 0;
        if (j >= this.m.length)
            j = this.m.length - 1;
        for (int k = i; k <= j; k++) {
            List<sn> list1 = this.m[k];
            for (int l = 0; l < list1.size(); l++) {
                sn entity = list1.get(l);
                if (class1.isAssignableFrom(entity.getClass()) && entity.aW.a(axisalignedbb))
                    list.add(entity);
            }
        }
    }

    public boolean a(boolean flag) {
        if (this.p)
            return false;
        if (flag) {
            if (this.q && this.d.t() != this.r)
                return true;
        } else if (this.q && this.d.t() >= this.r + 600L) {
            return true;
        }
        return this.o;
    }

    public int a(byte[] abyte0, int i, int j, int k, int l, int i1, int j1, int k1) {
        for (int l1 = i; l1 < l; l1++) {
            for (int l2 = k; l2 < j1; l2++) {
                int l3 = l1 << 11 | l2 << 7 | j;
                int l4 = i1 - j;
                System.arraycopy(abyte0, k1, this.b, l3, l4);
                k1 += l4;
            }
        }
        b();
        for (int i2 = i; i2 < l; i2++) {
            for (int i3 = k; i3 < j1; i3++) {
                int i4 = (i2 << 11 | i3 << 7 | j) >> 1;
                int i5 = (i1 - j) / 2;
                System.arraycopy(abyte0, k1, this.e.a, i4, i5);
                k1 += i5;
            }
        }
        for (int j2 = i; j2 < l; j2++) {
            for (int j3 = k; j3 < j1; j3++) {
                int j4 = (j2 << 11 | j3 << 7 | j) >> 1;
                int j5 = (i1 - j) / 2;
                System.arraycopy(abyte0, k1, this.g.a, j4, j5);
                k1 += j5;
            }
        }
        for (int k2 = i; k2 < l; k2++) {
            for (int k3 = k; k3 < j1; k3++) {
                int k4 = (k2 << 11 | k3 << 7 | j) >> 1;
                int k5 = (i1 - j) / 2;
                System.arraycopy(abyte0, k1, this.f.a, k4, k5);
                k1 += k5;
            }
        }
        return k1;
    }

    public Random a(long l) {
        return new Random(this.d.s() + (this.j * this.j * 4987142) + (this.j * 5947611) + (this.k * this.k) * 4392871L + (this.k * 389711) ^ l);
    }

    public boolean h() {
        return false;
    }

    public void i() {
        vi.a(this.b);
    }

    public double getTemperatureValue(int x, int z) {
        if (this.temperatures == null)
            this.temperatures = this.d.a().a(this.temperatures, this.j * 16, this.k * 16, 16, 16);
        return this.temperatures[z << 4 | x];
    }

    public void setTemperatureValue(int x, int z, double temp) {
        this.temperatures[z << 4 | x] = temp;
    }

    public static int translate128(int bID) {
        if (bID > 127)
            return -129 + bID - 127;
        return bID;
    }

    public static int translate256(int bID) {
        if (bID < 0)
            return bID + 256;
        return bID;
    }

    public static boolean isNotPopulating = true;
}
