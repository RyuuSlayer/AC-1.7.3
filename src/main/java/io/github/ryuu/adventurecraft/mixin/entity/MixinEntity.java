package io.github.ryuu.adventurecraft.mixin.entity;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.extensions.entity.ExEntity;
import net.minecraft.entity.Entity;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileSounds;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Random;

@Mixin(Entity.class)
public abstract class MixinEntity implements ExEntity {

    @Final
    @Shadow
    public Box boundingBox;

    @Shadow
    public int id;

    @Shadow
    public Entity passenger;

    @Shadow
    public Entity vehicle;

    @Shadow
    public Level level;

    @Shadow
    public double prevX;

    @Shadow
    public double prevY;

    @Shadow
    public double prevZ;

    @Shadow
    public double x;

    @Shadow
    public double y;

    @Shadow
    public double z;

    @Shadow
    public double velocityX;

    @Shadow
    public double velocityY;

    @Shadow
    public double velocityZ;

    @Shadow
    public float yaw;

    @Shadow
    public float pitch;

    @Shadow
    public float prevYaw;

    @Shadow
    public float prevPitch;

    @Shadow
    public boolean onGround;

    @Shadow
    public boolean field_1624;

    @Shadow
    public boolean removed;

    @Shadow
    public float standingEyeHeight;

    @Shadow
    public float width;

    @Shadow
    public float height;

    @Shadow
    public int field_1645;

    @Shadow
    public int field_1646;

    @Shadow
    public int fire;

    @Shadow
    public int field_1613;

    @Shadow
    public int air;

    @Shadow
    protected float fallDistance;

    @Shadow
    protected Random rand;

    public boolean isFlying;
    public int stunned;
    public boolean collidesWithClipBlocks = true;
    public int collisionX;
    public int collisionZ;
    public float moveYawOffset = 0.0f;

    @Shadow
    public abstract float getStandingEyeHeight();

    @Shadow
    public abstract boolean damage(Entity arg, int i);

    @Shadow
    protected abstract void handleFallDamage(float f);

    //@Shadow
    //public abstract void move(double d, double d1, double d2);

    @Shadow
    public abstract void remove();

    @Shadow
    public abstract void method_1322(double d, double d1, double d2);

    @Shadow
    public abstract boolean method_1334();

    @Shadow
    public abstract boolean method_1335();

    @Shadow
    protected abstract void method_1336();

    @Shadow
    public abstract boolean method_1344(double d, double d1, double d2);

    @Shadow
    public abstract boolean method_1373();

    @Shadow
    public abstract int hashCode();


    @Shadow
    public abstract boolean isTouchingWater();

    @Shadow
    protected abstract boolean canClimb();

    @Shadow
    protected abstract void method_1392(int i);

    @Shadow
    private int field_1611;

    @Shadow
    public boolean field_1625;

    @Shadow
    public boolean field_1626;

    @Shadow
    public boolean field_1629;

    @Shadow
    public float field_1635;

    @Shadow
    public float field_1640;

    @Shadow
    public float field_1641;

    @Shadow
    public boolean field_1642;

    @Shadow
    public boolean inCobweb;

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void move(double d, double d1, double d2) {
        int i4;
        int l3;
        int k3;
        int i2;
        int k1;
        int i1;
        int i;
        boolean flag;
        if (this.field_1642) {
            this.boundingBox.method_102(d, d1, d2);
            this.x = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0;
            this.y = this.boundingBox.minY + (double) this.standingEyeHeight - (double) this.field_1640;
            this.z = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0;
            return;
        }
        this.field_1640 *= 0.4f;
        double d3 = this.x;
        double d4 = this.z;
        if (this.inCobweb) {
            this.inCobweb = false;
            d *= 0.25;
            d1 *= 0.05f;
            d2 *= 0.25;
            this.velocityX = 0.0;
            this.velocityY = 0.0;
            this.velocityZ = 0.0;
        }
        double d5 = d;
        double d6 = d1;
        double d7 = d2;
        Box axisalignedbb = this.boundingBox.method_92();
        boolean bl = flag = this.onGround && this.method_1373();
        if (flag) {
            double d8 = 0.05;
            while (d != 0.0 && this.level.method_190((Entity) (Object) this, this.boundingBox.move(d, -1.0, 0.0)).size() == 0) {
                d = d < d8 && d >= -d8 ? 0.0 : (d > 0.0 ? (d -= d8) : (d += d8));
                d5 = d;
            }
            while (d2 != 0.0 && this.level.method_190((Entity) (Object) this, this.boundingBox.move(0.0, -1.0, d2)).size() == 0) {
                d2 = d2 < d8 && d2 >= -d8 ? 0.0 : (d2 > 0.0 ? (d2 -= d8) : (d2 += d8));
                d7 = d2;
            }
        }
        List list = this.level.method_190((Entity) (Object) this, this.boundingBox.add(d, d1, d2));
        for (Object o : list) {
            d1 = ((Box) o).method_97(this.boundingBox, d1);
        }
        this.boundingBox.method_102(0.0, d1, 0.0);
        if (!this.field_1629 && d6 != d1) {
            d2 = 0.0;
            d1 = 0.0;
            d = 0.0;
        }
        boolean flag1 = this.onGround || d6 != d1 && d6 < 0.0;
        for (Object o : list) {
            d = ((Box) o).method_91(this.boundingBox, d);
        }
        this.boundingBox.method_102(d, 0.0, 0.0);
        if (!this.field_1629 && d5 != d) {
            d2 = 0.0;
            d1 = 0.0;
            d = 0.0;
        }
        for (Object o : list) {
            d2 = ((Box) o).method_101(this.boundingBox, d2);
        }
        this.boundingBox.method_102(0.0, 0.0, d2);
        if (!this.field_1629 && d7 != d2) {
            d2 = 0.0;
            d1 = 0.0;
            d = 0.0;
        }
        if (this.field_1641 > 0.0f && flag1 && (flag || this.field_1640 < 0.05f) && (d5 != d || d7 != d2)) {
            double d9 = d;
            double d11 = d1;
            double d13 = d2;
            d = d5;
            d1 = this.field_1641;
            d2 = d7;
            Box axisalignedbb1 = this.boundingBox.method_92();
            this.boundingBox.method_96(axisalignedbb);
            List list1 = this.level.method_190((Entity) (Object) this, this.boundingBox.add(d, d1, d2));
            for (Object o : list1) {
                d1 = ((Box) o).method_97(this.boundingBox, d1);
            }
            this.boundingBox.method_102(0.0, d1, 0.0);
            if (!this.field_1629 && d6 != d1) {
                d2 = 0.0;
                d1 = 0.0;
                d = 0.0;
            }
            for (Object o : list1) {
                d = ((Box) o).method_91(this.boundingBox, d);
            }
            this.boundingBox.method_102(d, 0.0, 0.0);
            if (!this.field_1629 && d5 != d) {
                d2 = 0.0;
                d1 = 0.0;
                d = 0.0;
            }
            for (Object o : list1) {
                d2 = ((Box) o).method_101(this.boundingBox, d2);
            }
            this.boundingBox.method_102(0.0, 0.0, d2);
            if (!this.field_1629 && d7 != d2) {
                d2 = 0.0;
                d1 = 0.0;
                d = 0.0;
            }
            if (!this.field_1629 && d6 != d1) {
                d2 = 0.0;
                d1 = 0.0;
                d = 0.0;
            } else {
                d1 = -this.field_1641;
                for (Object o : list1) {
                    d1 = ((Box) o).method_97(this.boundingBox, d1);
                }
                this.boundingBox.method_102(0.0, d1, 0.0);
            }
            if (d9 * d9 + d13 * d13 >= d * d + d2 * d2) {
                d = d9;
                d1 = d11;
                d2 = d13;
                this.boundingBox.method_96(axisalignedbb1);
            } else {
                double d14 = this.boundingBox.minY - (double) ((int) this.boundingBox.minY);
                if (d14 > 0.0) {
                    this.field_1640 = (float) ((double) this.field_1640 + (d14 + 0.01));
                }
            }
        }
        this.x = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0;
        this.y = this.boundingBox.minY + (double) this.standingEyeHeight - (double) this.field_1640;
        this.z = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0;
        this.collisionX = Double.compare(d5, d);
        if (this.collisionX != 0) {
            boolean nonClipFound = false;
            i = 0;
            while ((double) i < (double) this.height + this.y - (double) this.standingEyeHeight - Math.floor(this.y - (double) this.standingEyeHeight)) {
                int blockID = this.level.getTileId((int) Math.floor(this.x) + this.collisionX, (int) Math.floor(this.y + (double) i - (double) this.standingEyeHeight), (int) Math.floor(this.z));
                if (blockID != 0 && blockID != Blocks.clipBlock.id) {
                    nonClipFound = true;
                }
                ++i;
            }
            if (!nonClipFound) {
                this.collisionX = 0;
            }
        }
        this.collisionZ = Double.compare(d7, d2);
        if (this.collisionZ != 0) {
            boolean nonClipFound = false;
            i = 0;
            while ((double) i < (double) this.height + this.y - (double) this.standingEyeHeight - Math.floor(this.y - (double) this.standingEyeHeight)) {
                int blockID = this.level.getTileId((int) Math.floor(this.x), (int) Math.floor(this.y + (double) i - (double) this.standingEyeHeight), (int) Math.floor(this.z) + this.collisionZ);
                if (blockID != 0 && blockID != Blocks.clipBlock.id) {
                    nonClipFound = true;
                }
                ++i;
            }
            if (!nonClipFound) {
                this.collisionZ = 0;
            }
        }
        this.field_1624 = d5 != d || d7 != d2;
        this.field_1625 = d6 != d1;
        this.onGround = d6 != d1 && d6 < 0.0;
        this.field_1626 = this.field_1624 || this.field_1625;
        this.method_1374(d1, this.onGround);
        if (d5 != d) {
            this.velocityX = 0.0;
        }
        if (d6 != d1) {
            this.velocityY = 0.0;
        }
        if (d7 != d2) {
            this.velocityZ = 0.0;
        }
        double d10 = this.x - d3;
        double d12 = this.z - d4;
        if (this.canClimb() && !flag && this.vehicle == null) {
            this.field_1635 = (float) ((double) this.field_1635 + (double) MathsHelper.sqrt(d10 * d10 + d12 * d12) * 0.6);
            int l = MathsHelper.floor(this.x);
            int j1 = MathsHelper.floor(this.y - (double) 0.2f - (double) this.standingEyeHeight);
            int l1 = MathsHelper.floor(this.z);
            int j3 = this.level.getTileId(l, j1, l1);
            if (this.level.getTileId(l, j1 - 1, l1) == Tile.FENCE.id) {
                j3 = this.level.getTileId(l, j1 - 1, l1);
            }
            if (this.field_1635 > (float) this.field_1611 && j3 > 0) {
                this.field_1611 = (int) ((double) this.field_1611 + Math.ceil(this.field_1635 - (float) this.field_1611));
                TileSounds stepsound = Tile.BY_ID[j3].sounds;
                if (this.level.getTileId(l, j1 + 1, l1) == Tile.SNOW.id) {
                    stepsound = Tile.SNOW.sounds;
                    this.level.playSound((Entity) (Object) this, stepsound.getWalkSound(), stepsound.getVolume() * 0.15f, stepsound.getPitch());
                } else if (!Tile.BY_ID[j3].material.isLiquid()) {
                    this.level.playSound((Entity) (Object) this, stepsound.getWalkSound(), stepsound.getVolume() * 0.15f, stepsound.getPitch());
                }
                Tile.BY_ID[j3].method_1560(this.level, l, j1, l1, (Entity) (Object) this);
            }
        }
        if (this.level.isRegionLoaded(i1 = MathsHelper.floor(this.boundingBox.minX + 0.001), k1 = MathsHelper.floor(this.boundingBox.minY + 0.001), i2 = MathsHelper.floor(this.boundingBox.minZ + 0.001), k3 = MathsHelper.floor(this.boundingBox.maxX - 0.001), l3 = MathsHelper.floor(this.boundingBox.maxY - 0.001), i4 = MathsHelper.floor(this.boundingBox.maxZ - 0.001))) {
            for (int j4 = i1; j4 <= k3; ++j4) {
                for (int k4 = k1; k4 <= l3; ++k4) {
                    for (int l4 = i2; l4 <= i4; ++l4) {
                        int i5 = this.level.getTileId(j4, k4, l4);
                        if (i5 <= 0) continue;
                        Tile.BY_ID[i5].onEntityCollision(this.level, j4, k4, l4, (Entity) (Object) this);
                    }
                }
            }
        }
        boolean flag2 = this.isTouchingWater();
        if (this.level.method_225(this.boundingBox.method_104(0.001, 0.001, 0.001))) {
            this.method_1392(1);
            if (!flag2) {
                ++this.fire;
                if (this.fire == 0) {
                    this.fire = 300;
                }
            }
        } else if (this.fire <= 0) {
            this.fire = -this.field_1646;
        }
        if (flag2 && this.fire > 0) {
            this.level.playSound((Entity) (Object) this, "random.fizz", 0.7f, 1.6f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
            this.fire = -this.field_1646;
        }
    }

    /*
    @Inject(method = "move", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/entity/Entity;z:D",
            shift = At.Shift.AFTER,
            opcode = Opcodes.PUTFIELD,
            ordinal = 1))
    private void checkCollisionOnMove(double d, double d1, double d2, CallbackInfo ci, double var11, double var13, double var15) {
        this.collisionX = Double.compare(var11, d);
        if (this.collisionX != 0) {
            boolean nonClipFound = false;
            int i = 0;
            while (i < this.height + this.y - this.standingEyeHeight - Math.floor(this.y - this.standingEyeHeight)) {
                int blockID = this.level.getTileId((int) Math.floor(this.x) + this.collisionX, (int) Math.floor(this.y + i - this.standingEyeHeight), (int) Math.floor(this.z));
                if (blockID != 0 && blockID != Blocks.clipBlock.id) {
                    nonClipFound = true;
                }
                ++i;
            }
            if (!nonClipFound) {
                this.collisionX = 0;
            }
        }
        this.collisionZ = Double.compare(var15, d2);
        if (this.collisionZ != 0) {
            boolean nonClipFound = false;
            int i = 0;
            while (i < this.height + this.y - this.standingEyeHeight - Math.floor(this.y - this.standingEyeHeight)) {
                int blockID = this.level.getTileId((int) Math.floor(this.x), (int) Math.floor(this.y + i - this.standingEyeHeight), (int) Math.floor(this.z) + this.collisionZ);
                if (blockID != 0 && blockID != Blocks.clipBlock.id) {
                    nonClipFound = true;
                }
                ++i;
            }
            if (!nonClipFound) {
                this.collisionZ = 0;
            }
        }
    }
*/

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void method_1374(double d, boolean flag) {
        if (flag) {
            if (this.velocityY < 0.0) {
                this.handleFallDamage(-((float) this.velocityY));
            }
        } else if (d < 0.0) {
            this.fallDistance = (float) ((double) this.fallDistance - d);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void movementInputToVelocity(float f, float f1, float f2) {
        float f3 = f * f + f1 * f1;
        if (f3 < 1.0E-4f) {
            return;
        }
        float f4 = MathsHelper.sin((this.yaw + this.moveYawOffset) * 3.141593f / 180.0f);
        float f5 = MathsHelper.cos((this.yaw + this.moveYawOffset) * 3.141593f / 180.0f);
        this.velocityX += (f *= f2) * f5 - (f1 *= f2) * f4;
        this.velocityZ += f1 * f5 + f * f4;
    }

    @Redirect(method = "method_1353", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/Entity;method_1322(DDD)V",
            ordinal = 1))
    public void method_1353(Entity entity, double d, double c, double d1) {
        if (entity.method_1380()) {
            entity.method_1322(d, c, d1);
        } else {
            this.method_1322(-d, c, -d1);
        }
    }

    @Override
    public boolean attackEntityFromMulti(Entity entity, int i) {
        return this.damage(entity, i);
    }

    @Redirect(method = "isInsideWall", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/level/Level;canSuffocate(III)Z"))
    public boolean isInsideWallOrOpaque(Level instance, int j, int k, int l) {
        return this.level.canSuffocate(j, k, l) && this.level.isFullOpaque(j, k, l);
    }

    @Override
    public boolean isFlying() {
        return this.isFlying;
    }

    @Override
    public void setFlying(boolean flying) {
        this.isFlying = flying;
    }

    @Override
    public int getCollisionX() {
        return this.collisionX;
    }

    @Override
    public int getCollisionZ() {
        return this.collisionZ;
    }

    @Override
    public boolean getCollidesWithClipBlocks() {
        return this.collidesWithClipBlocks;
    }

    @Override
    public void setCollidesWithClipBlocks(boolean collidesWithClipBlocks) {
        this.collidesWithClipBlocks = collidesWithClipBlocks;
    }

    @Override
    public int getStunned() {
        return this.stunned;
    }

    @Override
    public void setStunned(int stunned) {
        this.stunned = stunned;
    }
}
