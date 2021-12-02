package io.github.ryuu.adventurecraft.mixin.entity;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.extensions.entity.ExEntity;
import net.minecraft.entity.Entity;
import net.minecraft.level.Level;
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

    @Shadow
    public abstract void move(double d, double d1, double d2);

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

    @Shadow public abstract int hashCode();

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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    protected void method_1374(double d, boolean flag) {
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
}
