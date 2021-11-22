package io.github.ryuu.adventurecraft.mixin;

import java.util.LinkedList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_108;
import net.minecraft.class_109;
import net.minecraft.class_61;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.level.TileView;
import net.minecraft.tile.DoorTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.Int2ObjectLinkedHashMap;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(class_108.class)
public class MixinClass_108 {

    @Shadow()
    private TileView field_333;

    private class_109 field_334 = new class_109();

    private Int2ObjectLinkedHashMap field_335 = new Int2ObjectLinkedHashMap();

    private Vec3i[] field_336 = new Vec3i[32];

    public MixinClass_108(TileView iblockaccess) {
        this.field_333 = iblockaccess;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public class_61 method_407(Entity entity, Entity entity1, float f) {
        return this.method_402(entity, entity1.x, entity1.boundingBox.minY, entity1.z, f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public class_61 method_403(Entity entity, int i, int j, int k, float f) {
        return this.method_402(entity, (float) i + 0.5f, (float) j + 0.5f, (float) k + 0.5f, f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private class_61 method_402(Entity entity, double d, double d1, double d2, float f) {
        this.field_334.method_841();
        this.field_335.clear();
        Vec3i pathpoint = this.method_400(MathsHelper.floor(entity.boundingBox.minX), MathsHelper.floor(entity.boundingBox.minY), MathsHelper.floor(entity.boundingBox.minZ));
        Vec3i pathpoint1 = this.method_400(MathsHelper.floor(d - (double) (entity.width / 2.0f)), MathsHelper.floor(d1), MathsHelper.floor(d2 - (double) (entity.width / 2.0f)));
        Vec3i pathpoint2 = new Vec3i(MathsHelper.floor(entity.width + 1.25f), MathsHelper.floor(entity.height + 1.0f), MathsHelper.floor(entity.width + 1.25f));
        class_61 pathentity = this.method_406(entity, pathpoint, pathpoint1, pathpoint2, f);
        pathentity = this.simplifyPath(pathentity, pathpoint2);
        return pathentity;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private class_61 method_406(Entity entity, Vec3i pathpoint, Vec3i pathpoint1, Vec3i pathpoint2, float f) {
        pathpoint.field_144 = 0.0f;
        pathpoint.field_146 = pathpoint.field_145 = pathpoint.getSquaredDistance(pathpoint1);
        this.field_334.method_841();
        this.field_334.method_843(pathpoint);
        Vec3i pathpoint3 = pathpoint;
        while (!this.field_334.method_847()) {
            Vec3i pathpoint4 = this.field_334.method_845();
            if (pathpoint4.equals(pathpoint1)) {
                return this.method_401(pathpoint, pathpoint1);
            }
            if (pathpoint4.getSquaredDistance(pathpoint1) < pathpoint3.getSquaredDistance(pathpoint1)) {
                pathpoint3 = pathpoint4;
            }
            pathpoint4.field_148 = true;
            int i = this.method_408(entity, pathpoint4, pathpoint2, pathpoint1, f);
            for (int j = 0; j < i; ++j) {
                Vec3i pathpoint5 = this.field_336[j];
                float f1 = pathpoint4.field_144 + pathpoint4.getSquaredDistance(pathpoint5);
                if (pathpoint5.method_112() && !(f1 < pathpoint5.field_144))
                    continue;
                pathpoint5.field_147 = pathpoint4;
                pathpoint5.field_144 = f1;
                pathpoint5.field_145 = pathpoint5.getSquaredDistance(pathpoint1);
                if (pathpoint5.method_112()) {
                    this.field_334.method_844(pathpoint5, pathpoint5.field_144 + pathpoint5.field_145);
                    continue;
                }
                pathpoint5.field_146 = pathpoint5.field_144 + pathpoint5.field_145;
                this.field_334.method_843(pathpoint5);
            }
        }
        if (pathpoint3 == pathpoint) {
            return null;
        }
        return this.method_401(pathpoint, pathpoint3);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private int method_408(Entity entity, Vec3i pathpoint, Vec3i pathpoint1, Vec3i pathpoint2, float f) {
        int i = 0;
        int j = 0;
        if (this.method_404(entity, pathpoint.x, pathpoint.y + 1, pathpoint.z, pathpoint1) == 1) {
            j = 1;
        }
        Vec3i pathpoint3 = this.method_405(entity, pathpoint.x, pathpoint.y, pathpoint.z + 1, pathpoint1, j);
        Vec3i pathpoint4 = this.method_405(entity, pathpoint.x - 1, pathpoint.y, pathpoint.z, pathpoint1, j);
        Vec3i pathpoint5 = this.method_405(entity, pathpoint.x + 1, pathpoint.y, pathpoint.z, pathpoint1, j);
        Vec3i pathpoint6 = this.method_405(entity, pathpoint.x, pathpoint.y, pathpoint.z - 1, pathpoint1, j);
        if (pathpoint3 != null && !pathpoint3.field_148 && pathpoint3.getSquaredDistance(pathpoint2) < f) {
            this.field_336[i++] = pathpoint3;
        }
        if (pathpoint4 != null && !pathpoint4.field_148 && pathpoint4.getSquaredDistance(pathpoint2) < f) {
            this.field_336[i++] = pathpoint4;
        }
        if (pathpoint5 != null && !pathpoint5.field_148 && pathpoint5.getSquaredDistance(pathpoint2) < f) {
            this.field_336[i++] = pathpoint5;
        }
        if (pathpoint6 != null && !pathpoint6.field_148 && pathpoint6.getSquaredDistance(pathpoint2) < f) {
            this.field_336[i++] = pathpoint6;
        }
        return i;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private Vec3i method_405(Entity entity, int i, int j, int k, Vec3i pathpoint, int l) {
        Vec3i pathpoint1 = null;
        if (this.method_404(entity, i, j, k, pathpoint) == 1) {
            pathpoint1 = this.method_400(i, j, k);
        }
        if (pathpoint1 == null && l > 0 && this.method_404(entity, i, j + l, k, pathpoint) == 1) {
            pathpoint1 = this.method_400(i, j + l, k);
            j += l;
        }
        if (pathpoint1 != null) {
            int i1 = 0;
            int j1 = 0;
            while (j > 0 && (j1 = this.method_404(entity, i, j - 1, k, pathpoint)) == 1) {
                if (++i1 >= 4) {
                    return null;
                }
                if (--j <= 0)
                    continue;
                pathpoint1 = this.method_400(i, j, k);
            }
            if (j1 == -2) {
                return null;
            }
        }
        return pathpoint1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private final Vec3i method_400(int i, int j, int k) {
        int l = Vec3i.createHashcode(i, j, k);
        Vec3i pathpoint = (Vec3i) this.field_335.getByHash(l);
        if (pathpoint == null) {
            pathpoint = new Vec3i(i, j, k);
            this.field_335.put(l, pathpoint);
        }
        return pathpoint;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private int method_404(Entity entity, int i, int j, int k, Vec3i pathpoint) {
        for (int l = i; l < i + pathpoint.x; ++l) {
            for (int i1 = j; i1 < j + pathpoint.y; ++i1) {
                for (int j1 = k; j1 < k + pathpoint.z; ++j1) {
                    Box collision;
                    int potentialFence;
                    if (i1 > 1 && (potentialFence = this.field_333.getTileId(l, i1 - 1, j1)) == Tile.FENCE.id) {
                        return 0;
                    }
                    int k1 = this.field_333.getTileId(l, i1, j1);
                    if (k1 <= 0)
                        continue;
                    if (k1 == Tile.DOOR_IRON.id || k1 == Tile.DOOR_WOOD.id) {
                        int l1 = this.field_333.getTileMeta(l, i1, j1);
                        if (DoorTile.method_840(l1))
                            continue;
                        return 0;
                    }
                    Material material = Tile.BY_ID[k1].material;
                    if (Tile.BY_ID[k1].method_1576() && (collision = Tile.BY_ID[k1].getCollisionShape(Minecraft.minecraftInstance.level, l, i1, j1)) != null) {
                        return 0;
                    }
                    if (material == Material.WATER) {
                        return -1;
                    }
                    if (material != Material.LAVA)
                        continue;
                    return -2;
                }
            }
        }
        return 1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private class_61 method_401(Vec3i pathpoint, Vec3i pathpoint1) {
        int i = 1;
        Vec3i pathpoint2 = pathpoint1;
        while (pathpoint2.field_147 != null) {
            ++i;
            pathpoint2 = pathpoint2.field_147;
        }
        Vec3i[] apathpoint = new Vec3i[i];
        Vec3i pathpoint3 = pathpoint1;
        apathpoint[--i] = pathpoint3;
        while (pathpoint3.field_147 != null) {
            pathpoint3 = pathpoint3.field_147;
            apathpoint[--i] = pathpoint3;
        }
        return new class_61(apathpoint);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public class_61 simplifyPath(class_61 p, Vec3i clearSize) {
        if (p == null) {
            return p;
        }
        LinkedList points = new LinkedList();
        Vec3i prevPoint = null;
        Vec3i potentialPoint = null;
        Vec3i lastPoint = null;
        int index = 0;
        boolean addRestThePoints = false;
        for (Vec3i point : p.field_2691) {
            int sign;
            if (index++ < p.field_2692)
                continue;
            if (addRestThePoints) {
                points.add((Object) point);
                continue;
            }
            if (prevPoint == null) {
                prevPoint = point;
                points.add((Object) point);
                continue;
            }
            if (potentialPoint == null) {
                if (prevPoint.y != point.y) {
                    prevPoint = point;
                    points.add((Object) point);
                    addRestThePoints = true;
                    continue;
                }
                potentialPoint = point;
                lastPoint = point;
                continue;
            }
            if (lastPoint.y != point.y) {
                points.add(lastPoint);
                points.add((Object) point);
                prevPoint = point;
                potentialPoint = null;
                lastPoint = null;
                addRestThePoints = true;
                continue;
            }
            int dX = point.x - prevPoint.x;
            int dZ = point.z - prevPoint.z;
            if (Math.abs((int) dX) < Math.abs((int) dZ)) {
                float xOffset = 0.0f;
                float xChange = (float) dX / (float) Math.abs((int) dZ);
                sign = 1;
                if (dZ < 0) {
                    sign = -1;
                }
                for (int zOffset = 1; zOffset < Math.abs((int) dZ); ++zOffset) {
                    if (this.method_404(null, prevPoint.x + (int) xOffset, prevPoint.y, prevPoint.z + zOffset * sign, clearSize) != 1 || this.method_404(null, prevPoint.x + (int) xOffset, prevPoint.y - 1, prevPoint.z + zOffset * sign, clearSize) == 1 || this.method_404(null, prevPoint.x + (int) xOffset + 1, prevPoint.y, prevPoint.z + zOffset * sign, clearSize) != 1 || this.method_404(null, prevPoint.x + (int) xOffset + 1, prevPoint.y - 1, prevPoint.z + zOffset * sign, clearSize) == 1 || this.method_404(null, prevPoint.x + (int) xOffset - 1, prevPoint.y, prevPoint.z + zOffset * sign, clearSize) != 1 || this.method_404(null, prevPoint.x + (int) xOffset - 1, prevPoint.y - 1, prevPoint.z + zOffset * sign, clearSize) == 1) {
                        points.add((Object) potentialPoint);
                        points.add((Object) point);
                        addRestThePoints = true;
                        continue;
                    }
                    xOffset += xChange;
                }
            } else {
                float zOffset = 0.0f;
                float zChange = (float) dZ / (float) Math.abs((int) dX);
                sign = 1;
                if (dX < 0) {
                    sign = -1;
                }
                for (int xOffset = 1; xOffset < Math.abs((int) dX); ++xOffset) {
                    if (this.method_404(null, prevPoint.x + xOffset * sign, prevPoint.y, prevPoint.z + (int) zOffset, clearSize) != 1 || this.method_404(null, prevPoint.x + xOffset * sign, prevPoint.y - 1, prevPoint.z + (int) zOffset, clearSize) == 1 || this.method_404(null, prevPoint.x + xOffset * sign, prevPoint.y, prevPoint.z + (int) zOffset + 1, clearSize) != 1 || this.method_404(null, prevPoint.x + xOffset * sign, prevPoint.y - 1, prevPoint.z + (int) zOffset + 1, clearSize) == 1 || this.method_404(null, prevPoint.x + xOffset * sign, prevPoint.y, prevPoint.z + (int) zOffset - 1, clearSize) != 1 || this.method_404(null, prevPoint.x + xOffset * sign, prevPoint.y - 1, prevPoint.z + (int) zOffset - 1, clearSize) == 1) {
                        prevPoint = potentialPoint;
                        points.add((Object) potentialPoint);
                        points.add((Object) point);
                        addRestThePoints = true;
                        continue;
                    }
                    zOffset += zChange;
                }
            }
            lastPoint = point;
        }
        if (!addRestThePoints) {
            if (lastPoint != null) {
                points.add(lastPoint);
            } else if (potentialPoint != null) {
                points.add(potentialPoint);
            }
        }
        int i = 0;
        Vec3i[] pointArray = new Vec3i[points.size()];
        for (Vec3i point : points) {
            pointArray[i++] = point;
        }
        p.field_2692 = 0;
        p.field_2691 = pointArray;
        p.p = this;
        p.clearSize = clearSize;
        p.field_2690 = points.size();
        return p;
    }
}
