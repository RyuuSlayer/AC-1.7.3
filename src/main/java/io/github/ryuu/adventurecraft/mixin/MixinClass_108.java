package io.github.ryuu.adventurecraft.mixin;

import io.github.ryuu.adventurecraft.extensions.ExClass_108;
import io.github.ryuu.adventurecraft.extensions.ExClass_61;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.class_108;
import net.minecraft.class_61;
import net.minecraft.entity.Entity;
import net.minecraft.level.TileView;
import net.minecraft.tile.DoorTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.LinkedList;

@Mixin(class_108.class)
public abstract class MixinClass_108 implements ExClass_108 {

    @Shadow
    private TileView field_333;

    @Inject(method = "method_402", locals = LocalCapture.CAPTURE_FAILHARD, at = @At("RETURN"), cancellable = true)
    private void simplifyOnMethod_402(Entity d, double d1, double d2, double f, float par5, CallbackInfoReturnable<class_61> cir, Vec3i var11) {
        class_61 simplified = this.simplifyPath(cir.getReturnValue(), var11);
        cir.setReturnValue(simplified);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    private int method_404(Entity entity, int i, int j, int k, Vec3i pathpoint) {
        for (int l = i; l < i + pathpoint.x; ++l) {
            for (int i1 = j; i1 < j + pathpoint.y; ++i1) {
                for (int j1 = k; j1 < k + pathpoint.z; ++j1) {
                    if (i1 > 1 && this.field_333.getTileId(l, i1 - 1, j1) == Tile.FENCE.id) {
                        return 0;
                    }
                    int k1 = this.field_333.getTileId(l, i1, j1);
                    if (k1 <= 0) continue;
                    if (k1 == Tile.DOOR_IRON.id || k1 == Tile.DOOR_WOOD.id) {
                        int l1 = this.field_333.getTileMeta(l, i1, j1);
                        if (DoorTile.method_840(l1)) continue;
                        return 0;
                    }
                    Material material = Tile.BY_ID[k1].material;
                    if (Tile.BY_ID[k1].method_1576() && Tile.BY_ID[k1].getCollisionShape(AccessMinecraft.getInstance().level, l, i1, j1) != null) {
                        return 0;
                    }
                    if (material == Material.WATER) {
                        return -1;
                    }
                    if (material != Material.LAVA) continue;
                    return -2;
                }
            }
        }
        return 1;
    }

    @Override
    public class_61 simplifyPath(class_61 p, Vec3i clearSize) {
        if (p == null) {
            return null;
        }
        LinkedList<Vec3i> points = new LinkedList<>();
        Vec3i prevPoint = null;
        Vec3i potentialPoint = null;
        Vec3i lastPoint = null;
        int index = 0;
        boolean addRestThePoints = false;
        for (Vec3i point : ((AccessClass_61) p).getField_2691()) {
            int sign;
            if (index++ < ((AccessClass_61) p).getField_2692()) continue;
            if (addRestThePoints) {
                points.add(point);
                continue;
            }
            if (prevPoint == null) {
                prevPoint = point;
                points.add(point);
                continue;
            }
            if (potentialPoint == null) {
                if (prevPoint.y != point.y) {
                    prevPoint = point;
                    points.add(point);
                    addRestThePoints = true;
                    continue;
                }
                potentialPoint = point;
                lastPoint = point;
                continue;
            }
            if (lastPoint.y != point.y) {
                points.add(lastPoint);
                points.add(point);
                prevPoint = point;
                potentialPoint = null;
                lastPoint = null;
                addRestThePoints = true;
                continue;
            }
            int dX = point.x - prevPoint.x;
            int dZ = point.z - prevPoint.z;
            if (Math.abs(dX) < Math.abs(dZ)) {
                float xOffset = 0.0f;
                float xChange = (float) dX / (float) Math.abs(dZ);
                sign = 1;
                if (dZ < 0) {
                    sign = -1;
                }
                for (int zOffset = 1; zOffset < Math.abs(dZ); ++zOffset) {
                    if (this.method_404(null, prevPoint.x + (int) xOffset, prevPoint.y, prevPoint.z + zOffset * sign, clearSize) != 1 || this.method_404(null, prevPoint.x + (int) xOffset, prevPoint.y - 1, prevPoint.z + zOffset * sign, clearSize) == 1 || this.method_404(null, prevPoint.x + (int) xOffset + 1, prevPoint.y, prevPoint.z + zOffset * sign, clearSize) != 1 || this.method_404(null, prevPoint.x + (int) xOffset + 1, prevPoint.y - 1, prevPoint.z + zOffset * sign, clearSize) == 1 || this.method_404(null, prevPoint.x + (int) xOffset - 1, prevPoint.y, prevPoint.z + zOffset * sign, clearSize) != 1 || this.method_404(null, prevPoint.x + (int) xOffset - 1, prevPoint.y - 1, prevPoint.z + zOffset * sign, clearSize) == 1) {
                        points.add(potentialPoint);
                        points.add(point);
                        addRestThePoints = true;
                        continue;
                    }
                    xOffset += xChange;
                }
            } else {
                float zOffset = 0.0f;
                float zChange = (float) dZ / (float) Math.abs(dX);
                sign = 1;
                if (dX < 0) {
                    sign = -1;
                }
                for (int xOffset = 1; xOffset < Math.abs(dX); ++xOffset) {
                    if (this.method_404(null, prevPoint.x + xOffset * sign, prevPoint.y, prevPoint.z + (int) zOffset, clearSize) != 1 || this.method_404(null, prevPoint.x + xOffset * sign, prevPoint.y - 1, prevPoint.z + (int) zOffset, clearSize) == 1 || this.method_404(null, prevPoint.x + xOffset * sign, prevPoint.y, prevPoint.z + (int) zOffset + 1, clearSize) != 1 || this.method_404(null, prevPoint.x + xOffset * sign, prevPoint.y - 1, prevPoint.z + (int) zOffset + 1, clearSize) == 1 || this.method_404(null, prevPoint.x + xOffset * sign, prevPoint.y, prevPoint.z + (int) zOffset - 1, clearSize) != 1 || this.method_404(null, prevPoint.x + xOffset * sign, prevPoint.y - 1, prevPoint.z + (int) zOffset - 1, clearSize) == 1) {
                        prevPoint = potentialPoint;
                        points.add(potentialPoint);
                        points.add(point);
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
        ((AccessClass_61) p).setField_2692(0);
        ((AccessClass_61) p).setField_2691(pointArray);
        ((ExClass_61) p).setP((class_108) (Object) this);
        ((ExClass_61) p).setClearSize(clearSize);
        ((AccessClass_61) p).setField_2690(points.size());
        return p;
    }
}
