package io.github.ryuu.adventurecraft.mixin.entity.projectile;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.level.Level;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Snowball.class)
public class MixinSnowball extends Entity {

    @Shadow()
    private int xTile = -1;

    private int yTile = -1;

    private int zTile = -1;

    private int inTile = 0;

    private boolean inGround = false;

    public int shake = 0;

    public LivingEntity field_2193;

    private int field_2202;

    private int field_2203 = 0;

    public double field_2194;

    public double field_2195;

    public double field_2196;

    private float radius;

    public MixinSnowball(Level world) {
        super(world);
        this.setSize(1.0f, 1.0f);
        this.collidesWithClipBlocks = false;
        this.radius = 1.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean shouldRenderAtDistance(double d) {
        double d1 = this.boundingBox.averageDimension() * 4.0;
        return d < (d1 *= 64.0) * d1;
    }

    public MixinSnowball(Level level, double d, double d1, double d2, double d3, double d4, double d5) {
        super(level);
        this.setSize(1.0f, 1.0f);
        this.setPositionAndAngles(d, d1, d2, this.yaw, this.pitch);
        this.setPosition(d, d1, d2);
        double d6 = MathsHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
        this.field_2194 = d3 / d6 * 0.1;
        this.field_2195 = d4 / d6 * 0.1;
        this.field_2196 = d5 / d6 * 0.1;
        this.radius = 1.0f;
    }

    public MixinSnowball(Level world, LivingEntity entityliving, double d, double d1, double d2) {
        super(world);
        this.field_2193 = entityliving;
        this.setSize(1.0f, 1.0f);
        this.setPositionAndAngles(entityliving.x, entityliving.y, entityliving.z, entityliving.yaw, entityliving.pitch);
        this.setPosition(this.x, this.y, this.z);
        this.standingEyeHeight = 0.0f;
        this.velocityZ = 0.0;
        this.velocityY = 0.0;
        this.velocityX = 0.0;
        double d3 = MathsHelper.sqrt((d += this.rand.nextGaussian() * 0.4) * d + (d1 += this.rand.nextGaussian() * 0.4) * d1 + (d2 += this.rand.nextGaussian() * 0.4) * d2);
        this.field_2194 = d / d3 * 0.1;
        this.field_2195 = d1 / d3 * 0.1;
        this.field_2196 = d2 / d3 * 0.1;
        this.radius = 1.0f;
    }

    public MixinSnowball(Level world, LivingEntity entityliving, double d, double d1, double d2, float r) {
        super(world);
        this.field_2193 = entityliving;
        this.setSize(1.0f, 1.0f);
        this.setPositionAndAngles(entityliving.x, entityliving.y, entityliving.z, entityliving.yaw, entityliving.pitch);
        this.setPosition(this.x, this.y, this.z);
        this.standingEyeHeight = 0.0f;
        this.velocityZ = 0.0;
        this.velocityY = 0.0;
        this.velocityX = 0.0;
        double d3 = MathsHelper.sqrt((d += this.rand.nextGaussian() * 0.4) * d + (d1 += this.rand.nextGaussian() * 0.4) * d1 + (d2 += this.rand.nextGaussian() * 0.4) * d2);
        this.field_2194 = d / d3 * 0.1;
        this.field_2195 = d1 / d3 * 0.1;
        this.field_2196 = d2 / d3 * 0.1;
        this.radius = r;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void tick() {
        block17: {
            super.tick();
            this.fire = 10;
            if (this.shake > 0) {
                --this.shake;
            }
            if (this.inGround) {
                int i = this.level.getTileId(this.xTile, this.yTile, this.zTile);
                if (i != this.inTile) {
                    this.inGround = false;
                    this.velocityX *= (double) (this.rand.nextFloat() * 0.2f);
                    this.velocityY *= (double) (this.rand.nextFloat() * 0.2f);
                    this.velocityZ *= (double) (this.rand.nextFloat() * 0.2f);
                    this.field_2202 = 0;
                    this.field_2203 = 0;
                    break block17;
                } else {
                    ++this.field_2202;
                    if (this.field_2202 == 1200) {
                        this.remove();
                    }
                    return;
                }
            }
            ++this.field_2203;
        }
        Vec3f vec3d = Vec3f.from(this.x, this.y, this.z);
        Vec3f vec3d1 = Vec3f.from(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
        HitResult movingobjectposition = this.level.rayTraceBlocks2(vec3d, vec3d1, false, true, false);
        vec3d = Vec3f.from(this.x, this.y, this.z);
        vec3d1 = Vec3f.from(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
        if (movingobjectposition != null) {
            vec3d1 = Vec3f.from(movingobjectposition.field_1988.x, movingobjectposition.field_1988.y, movingobjectposition.field_1988.z);
        }
        Entity entity = null;
        List list = this.level.getEntities(this, this.boundingBox.add(this.velocityX, this.velocityY, this.velocityZ).expand(1.0, 1.0, 1.0));
        double d = 0.0;
        for (int j = 0; j < list.size(); ++j) {
            double d1;
            float f2;
            Box axisalignedbb;
            HitResult movingobjectposition1;
            Entity entity1 = (Entity) list.get(j);
            if (!entity1.method_1356() || entity1 == this.field_2193 && this.field_2203 < 25 || (movingobjectposition1 = (axisalignedbb = entity1.boundingBox.expand(f2 = 0.3f, f2, f2)).method_89(vec3d, vec3d1)) == null || !((d1 = vec3d.method_1294(movingobjectposition1.field_1988)) < d) && d != 0.0)
                continue;
            entity = entity1;
            d = d1;
        }
        if (entity != null) {
            movingobjectposition = new HitResult(entity);
        }
        if (movingobjectposition != null) {
            if (!this.level.isClient) {
                if (movingobjectposition.field_1989 == null || !movingobjectposition.field_1989.damage(this.field_2193, 0)) {
                }
                this.level.createExplosion(null, this.x, this.y, this.z, this.radius, true);
            }
            this.remove();
        }
        this.x += this.velocityX;
        this.y += this.velocityY;
        this.z += this.velocityZ;
        float f = MathsHelper.sqrt(this.velocityX * this.velocityX + this.velocityZ * this.velocityZ);
        this.yaw = (float) (Math.atan2((double) this.velocityX, (double) this.velocityZ) * 180.0 / 3.1415927410125732);
        this.pitch = (float) (Math.atan2((double) this.velocityY, (double) f) * 180.0 / 3.1415927410125732);
        while (this.pitch - this.prevPitch < -180.0f) {
            this.prevPitch -= 360.0f;
        }
        while (this.pitch - this.prevPitch >= 180.0f) {
            this.prevPitch += 360.0f;
        }
        while (this.yaw - this.prevYaw < -180.0f) {
            this.prevYaw -= 360.0f;
        }
        while (this.yaw - this.prevYaw >= 180.0f) {
            this.prevYaw += 360.0f;
        }
        this.pitch = this.prevPitch + (this.pitch - this.prevPitch) * 0.2f;
        this.yaw = this.prevYaw + (this.yaw - this.prevYaw) * 0.2f;
        float f1 = 0.95f;
        if (this.method_1334()) {
            for (int k = 0; k < 4; ++k) {
                float f3 = 0.25f;
                this.level.addParticle("bubble", this.x - this.velocityX * (double) f3, this.y - this.velocityY * (double) f3, this.z - this.velocityZ * (double) f3, this.velocityX, this.velocityY, this.velocityZ);
            }
            f1 = 0.8f;
        }
        this.velocityX += this.field_2194;
        this.velocityY += this.field_2195;
        this.velocityZ += this.field_2196;
        this.velocityX *= (double) f1;
        this.velocityY *= (double) f1;
        this.velocityZ *= (double) f1;
        this.level.addParticle("smoke", this.x, this.y + 0.5, this.z, 0.0, 0.0, 0.0);
        this.setPosition(this.x, this.y, this.z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void writeCustomDataToTag(CompoundTag tag) {
        tag.put("xTile", (short) this.xTile);
        tag.put("yTile", (short) this.yTile);
        tag.put("zTile", (short) this.zTile);
        tag.put("inTile", (byte) this.inTile);
        tag.put("shake", (byte) this.shake);
        tag.put("inGround", (byte) (this.inGround ? 1 : 0));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void readCustomDataFromTag(CompoundTag tag) {
        this.xTile = tag.getShort("xTile");
        this.yTile = tag.getShort("yTile");
        this.zTile = tag.getShort("zTile");
        this.inTile = tag.getByte("inTile") & 0xFF;
        this.shake = tag.getByte("shake") & 0xFF;
        this.inGround = tag.getByte("inGround") == 1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean damage(Entity target, int amount) {
        this.method_1336();
        if (target != null) {
            Vec3f vec3d = target.method_1320();
            if (vec3d != null) {
                this.velocityX = vec3d.x;
                this.velocityY = vec3d.y;
                this.velocityZ = vec3d.z;
                this.field_2194 = this.velocityX * 0.1;
                this.field_2195 = this.velocityY * 0.1;
                this.field_2196 = this.velocityZ * 0.1;
            }
            return true;
        }
        return false;
    }
}
