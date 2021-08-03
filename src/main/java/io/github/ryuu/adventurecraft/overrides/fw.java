package io.github.ryuu.adventurecraft.overrides;

import java.util.LinkedList;

import net.minecraft.client.Minecraft;
import net.minecraft.src.PathPoint;

public class fw {
    private final xp a;

    private final lf b;

    private final jx c;

    private final d[] d;

    public fw(xp iblockaccess) {
        this.b = new lf();
        this.c = new jx();
        this.d = new d[32];
        this.a = iblockaccess;
    }

    public dh a(sn entity, sn entity1, float f) {
        return a(entity, entity1.aM, entity1.aW.b, entity1.aO, f);
    }

    public dh a(sn entity, int i, int j, int k, float f) {
        return a(entity, (i + 0.5F), (j + 0.5F), (k + 0.5F), f);
    }

    private dh a(sn entity, double d3, double d1, double d2, float f) {
        this.b.a();
        this.c.a();
        d pathpoint = a(in.b(entity.aW.a), in.b(entity.aW.b), in.b(entity.aW.c));
        d pathpoint1 = a(in.b(d3 - (entity.bg / 2.0F)), in.b(d1), in.b(d2 - (entity.bg / 2.0F)));
        d pathpoint2 = new d(in.d(entity.bg + 1.25F), in.d(entity.bh + 1.0F), in.d(entity.bg + 1.25F));
        dh pathentity = a(entity, pathpoint, pathpoint1, pathpoint2, f);
        pathentity = simplifyPath(pathentity, pathpoint2);
        return pathentity;
    }

    private dh a(sn entity, d pathpoint, d pathpoint1, d pathpoint2, float f) {
        pathpoint.e = 0.0F;
        pathpoint.f = pathpoint.a(pathpoint1);
        pathpoint.g = pathpoint.f;
        this.b.a();
        this.b.a(pathpoint);
        d pathpoint3 = pathpoint;
        while (!this.b.c()) {
            d pathpoint4 = this.b.b();
            if (pathpoint4.equals(pathpoint1))
                return a(pathpoint, pathpoint1);
            if (pathpoint4.a(pathpoint1) < pathpoint3.a(pathpoint1))
                pathpoint3 = pathpoint4;
            pathpoint4.i = true;
            int i = b(entity, pathpoint4, pathpoint2, pathpoint1, f);
            int j = 0;
            while (j < i) {
                d pathpoint5 = this.d[j];
                float f1 = pathpoint4.e + pathpoint4.a(pathpoint5);
                if (!pathpoint5.a() || f1 < pathpoint5.e) {
                    pathpoint5.h = pathpoint4;
                    pathpoint5.e = f1;
                    pathpoint5.f = pathpoint5.a(pathpoint1);
                    if (pathpoint5.a()) {
                        this.b.a(pathpoint5, pathpoint5.e + pathpoint5.f);
                    } else {
                        pathpoint5.g = pathpoint5.e + pathpoint5.f;
                        this.b.a(pathpoint5);
                    }
                }
                j++;
            }
        }
        if (pathpoint3 == pathpoint)
            return null;
        return a(pathpoint, pathpoint3);
    }

    private int b(sn entity, d pathpoint, d pathpoint1, d pathpoint2, float f) {
        int i = 0;
        int j = 0;
        if (a(entity, pathpoint.a, pathpoint.b + 1, pathpoint.c, pathpoint1) == 1)
            j = 1;
        d pathpoint3 = a(entity, pathpoint.a, pathpoint.b, pathpoint.c + 1, pathpoint1, j);
        d pathpoint4 = a(entity, pathpoint.a - 1, pathpoint.b, pathpoint.c, pathpoint1, j);
        d pathpoint5 = a(entity, pathpoint.a + 1, pathpoint.b, pathpoint.c, pathpoint1, j);
        d pathpoint6 = a(entity, pathpoint.a, pathpoint.b, pathpoint.c - 1, pathpoint1, j);
        if (pathpoint3 != null && !pathpoint3.i && pathpoint3.a(pathpoint2) < f)
            this.d[i++] = pathpoint3;
        if (pathpoint4 != null && !pathpoint4.i && pathpoint4.a(pathpoint2) < f)
            this.d[i++] = pathpoint4;
        if (pathpoint5 != null && !pathpoint5.i && pathpoint5.a(pathpoint2) < f)
            this.d[i++] = pathpoint5;
        if (pathpoint6 != null && !pathpoint6.i && pathpoint6.a(pathpoint2) < f)
            this.d[i++] = pathpoint6;
        return i;
    }

    private d a(sn entity, int i, int j, int k, d pathpoint, int l) {
        d pathpoint1 = null;
        if (a(entity, i, j, k, pathpoint) == 1)
            pathpoint1 = a(i, j, k);
        if (pathpoint1 == null && l > 0 && a(entity, i, j + l, k, pathpoint) == 1) {
            pathpoint1 = a(i, j + l, k);
            j += l;
        }
        if (pathpoint1 != null) {
            int i1 = 0;
            int j1 = 0;
            while (j > 0 && (j1 = a(entity, i, j - 1, k, pathpoint)) == 1) {
                if (++i1 >= 4)
                    return null;
                if (--j > 0)
                    pathpoint1 = a(i, j, k);
            }
            if (j1 == -2)
                return null;
        }
        return pathpoint1;
    }

    private final d a(int i, int j, int k) {
        int l = d.a(i, j, k);
        d pathpoint = (d) this.c.a(l);
        if (pathpoint == null) {
            pathpoint = new d(i, j, k);
            this.c.a(l, pathpoint);
        }
        return pathpoint;
    }

    private int a(sn entity, int i, int j, int k, d pathpoint) {
        for (int l = i; l < i + pathpoint.a; l++) {
            for (int i1 = j; i1 < j + pathpoint.b; i1++) {
                for (int j1 = k; j1 < k + pathpoint.c; j1++) {
                    if (i1 > 1) {
                        int potentialFence = this.a.a(l, i1 - 1, j1);
                        if (potentialFence == Tile.ba.bn)
                            return 0;
                    }
                    int k1 = this.a.a(l, i1, j1);
                    if (k1 > 0)
                        if (k1 == Tile.aM.bn || k1 == Tile.aF.bn) {
                            int l1 = this.a.e(l, i1, j1);
                            if (!le.f(l1))
                                return 0;
                        } else {
                            ln material = (Tile.m[k1]).bA;
                            if (Tile.m[k1].v_()) {
                                eq collision = Tile.m[k1].e(Minecraft.minecraftInstance.f, l, i1, j1);
                                if (collision != null)
                                    return 0;
                            }
                            if (material == ln.g)
                                return -1;
                            if (material == ln.h)
                                return -2;
                        }
                }
            }
        }
        return 1;
    }

    private dh a(d pathpoint, d pathpoint1) {
        int i = 1;
        for (d pathpoint2 = pathpoint1; pathpoint2.h != null; pathpoint2 = pathpoint2.h)
            i++;
        d[] apathpoint = new d[i];
        d pathpoint3 = pathpoint1;
        for (apathpoint[--i] = pathpoint3; pathpoint3.h != null; apathpoint[--i] = pathpoint3)
            pathpoint3 = pathpoint3.h;
        return new dh(apathpoint);
    }

    public dh simplifyPath(dh p, d clearSize) {
        if (p == null)
            return p;
        LinkedList<PathPoint> points = new LinkedList<PathPoint>();
        d prevPoint = null;
        d potentialPoint = null;
        d lastPoint = null;
        int index = 0;
        boolean addRestThePoints = false;
        for (d point : p.b) {
            if (index++ >= p.c)
                if (addRestThePoints) {
                    points.add(point);
                } else if (prevPoint == null) {
                    prevPoint = point;
                    points.add(point);
                } else if (potentialPoint == null) {
                    if (prevPoint.b != point.b) {
                        prevPoint = point;
                        points.add(point);
                        addRestThePoints = true;
                    } else {
                        potentialPoint = point;
                        lastPoint = point;
                    }
                } else if (lastPoint.b != point.b) {
                    points.add(lastPoint);
                    points.add(point);
                    prevPoint = point;
                    potentialPoint = null;
                    lastPoint = null;
                    addRestThePoints = true;
                } else {
                    int dX = point.a - prevPoint.a;
                    int dZ = point.c - prevPoint.c;
                    if (Math.abs(dX) < Math.abs(dZ)) {
                        float xOffset = 0.0F;
                        float xChange = dX / Math.abs(dZ);
                        int sign = 1;
                        if (dZ < 0)
                            sign = -1;
                        for (int zOffset = 1; zOffset < Math.abs(dZ); zOffset++) {
                            if (a((sn) null, prevPoint.a + (int) xOffset, prevPoint.b, prevPoint.c + zOffset * sign, clearSize) != 1 || a((sn) null, prevPoint.a + (int) xOffset, prevPoint.b - 1, prevPoint.c + zOffset * sign, clearSize) == 1 || a((sn) null, prevPoint.a + (int) xOffset + 1, prevPoint.b, prevPoint.c + zOffset * sign, clearSize) != 1 || a((sn) null, prevPoint.a + (int) xOffset + 1, prevPoint.b - 1, prevPoint.c + zOffset * sign, clearSize) == 1 || a((sn) null, prevPoint.a + (int) xOffset - 1, prevPoint.b, prevPoint.c + zOffset * sign, clearSize) != 1 || a((sn) null, prevPoint.a + (int) xOffset - 1, prevPoint.b - 1, prevPoint.c + zOffset * sign, clearSize) == 1) {
                                points.add(potentialPoint);
                                points.add(point);
                                addRestThePoints = true;
                            } else {
                                xOffset += xChange;
                            }
                        }
                    } else {
                        float zOffset = 0.0F;
                        float zChange = dZ / Math.abs(dX);
                        int sign = 1;
                        if (dX < 0)
                            sign = -1;
                        for (int xOffset = 1; xOffset < Math.abs(dX); xOffset++) {
                            if (a((sn) null, prevPoint.a + xOffset * sign, prevPoint.b, prevPoint.c + (int) zOffset, clearSize) != 1 || a((sn) null, prevPoint.a + xOffset * sign, prevPoint.b - 1, prevPoint.c + (int) zOffset, clearSize) == 1 || a((sn) null, prevPoint.a + xOffset * sign, prevPoint.b, prevPoint.c + (int) zOffset + 1, clearSize) != 1 || a((sn) null, prevPoint.a + xOffset * sign, prevPoint.b - 1, prevPoint.c + (int) zOffset + 1, clearSize) == 1 || a((sn) null, prevPoint.a + xOffset * sign, prevPoint.b, prevPoint.c + (int) zOffset - 1, clearSize) != 1 || a((sn) null, prevPoint.a + xOffset * sign, prevPoint.b - 1, prevPoint.c + (int) zOffset - 1, clearSize) == 1) {
                                prevPoint = potentialPoint;
                                points.add(potentialPoint);
                                points.add(point);
                                addRestThePoints = true;
                            } else {
                                zOffset += zChange;
                            }
                        }
                    }
                    lastPoint = point;
                }
        }
        if (!addRestThePoints)
            if (lastPoint != null) {
                points.add(lastPoint);
            } else if (potentialPoint != null) {
                points.add(potentialPoint);
            }
        int i = 0;
        d[] pointArray = new d[points.size()];
        for (d point : points)
            pointArray[i++] = point;
        p.c = 0;
        p.b = pointArray;
        p.p = this;
        p.clearSize = clearSize;
        p.a = points.size();
        return p;
    }
}
