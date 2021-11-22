package io.github.ryuu.adventurecraft.mixin.entity.projectile;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ThrownSnowball.class)
public class MixinThrownSnowball extends Entity {

    @Shadow()
    private int xTile = -1;

    private int yTile = -1;

    private int zTile = -1;

    private int inTile = 0;

    private boolean inGround = false;

    public int shake = 0;

    private LivingEntity field_1996;

    private int field_1997;

    private int field_1998 = 0;

    public MixinThrownSnowball(Level world) {
        super(world);
        this.setSize(0.25f, 0.25f);
        this.collidesWithClipBlocks = false;
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

    public MixinThrownSnowball(Level world, LivingEntity entityliving) {
        super(world);
        this.field_1996 = entityliving;
        this.setSize(0.25f, 0.25f);
        this.setPositionAndAngles(entityliving.x, entityliving.y + (double) entityliving.getStandingEyeHeight(), entityliving.z, entityliving.yaw, entityliving.pitch);
        this.x -= (double) (MathsHelper.cos(this.yaw / 180.0f * 3.141593f) * 0.16f);
        this.y -= (double) 0.1f;
        this.z -= (double) (MathsHelper.sin(this.yaw / 180.0f * 3.141593f) * 0.16f);
        this.setPosition(this.x, this.y, this.z);
        this.standingEyeHeight = 0.0f;
        float f = 0.4f;
        this.velocityX = -MathsHelper.sin(this.yaw / 180.0f * 3.141593f) * MathsHelper.cos(this.pitch / 180.0f * 3.141593f) * f;
        this.velocityZ = MathsHelper.cos(this.yaw / 180.0f * 3.141593f) * MathsHelper.cos(this.pitch / 180.0f * 3.141593f) * f;
        this.velocityY = -MathsHelper.sin(this.pitch / 180.0f * 3.141593f) * f;
        this.method_1656(this.velocityX, this.velocityY, this.velocityZ, 1.5f, 1.0f);
    }

    public MixinThrownSnowball(Level world, double d, double d1, double d2) {
        super(world);
        this.field_1997 = 0;
        this.setSize(0.25f, 0.25f);
        this.setPosition(d, d1, d2);
        this.standingEyeHeight = 0.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1656(double d, double d1, double d2, float f, float f1) {
        float f2 = MathsHelper.sqrt(d * d + d1 * d1 + d2 * d2);
        d /= (double) f2;
        d1 /= (double) f2;
        d2 /= (double) f2;
        d += this.rand.nextGaussian() * (double) 0.0075f * (double) f1;
        d1 += this.rand.nextGaussian() * (double) 0.0075f * (double) f1;
        d2 += this.rand.nextGaussian() * (double) 0.0075f * (double) f1;
        this.velocityX = d *= (double) f;
        this.velocityY = d1 *= (double) f;
        this.velocityZ = d2 *= (double) f;
        float f3 = MathsHelper.sqrt(d * d + d2 * d2);
        this.prevYaw = this.yaw = (float) (Math.atan2((double) d, (double) d2) * 180.0 / 3.1415927410125732);
        this.prevPitch = this.pitch = (float) (Math.atan2((double) d1, (double) f3) * 180.0 / 3.1415927410125732);
        this.field_1997 = 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void setVelocity(double d, double d1, double d2) {
        this.velocityX = d;
        this.velocityY = d1;
        this.velocityZ = d2;
        if (this.prevPitch == 0.0f && this.prevYaw == 0.0f) {
            float f = MathsHelper.sqrt(d * d + d2 * d2);
            this.prevYaw = this.yaw = (float) (Math.atan2((double) d, (double) d2) * 180.0 / 3.1415927410125732);
            this.prevPitch = this.pitch = (float) (Math.atan2((double) d1, (double) f) * 180.0 / 3.1415927410125732);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void tick() {
        block18: {
            this.prevRenderX = this.x;
            this.prevRenderY = this.y;
            this.prevRenderZ = this.z;
            super.tick();
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
                    this.field_1997 = 0;
                    this.field_1998 = 0;
                    break block18;
                } else {
                    ++this.field_1997;
                    if (this.field_1997 == 1200) {
                        this.remove();
                    }
                    return;
                }
            }
            ++this.field_1998;
        }
        Vec3f vec3d = Vec3f.from(this.x, this.y, this.z);
        Vec3f vec3d1 = Vec3f.from(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
        HitResult movingobjectposition = this.level.rayTraceBlocks2(vec3d, vec3d1, false, true, false);
        vec3d = Vec3f.from(this.x, this.y, this.z);
        vec3d1 = Vec3f.from(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
        if (movingobjectposition != null) {
            vec3d1 = Vec3f.from(movingobjectposition.field_1988.x, movingobjectposition.field_1988.y, movingobjectposition.field_1988.z);
        }
        if (!this.level.isClient) {
            Entity entity = null;
            List list = this.level.getEntities(this, this.boundingBox.add(this.velocityX, this.velocityY, this.velocityZ).expand(1.0, 1.0, 1.0));
            double d = 0.0;
            for (int l = 0; l < list.size(); ++l) {
                double d1;
                float f4;
                Box axisalignedbb;
                HitResult movingobjectposition1;
                Entity entity1 = (Entity) list.get(l);
                if (!entity1.method_1356() || entity1 == this.field_1996 && this.field_1998 < 5 || (movingobjectposition1 = (axisalignedbb = entity1.boundingBox.expand(f4 = 0.3f, f4, f4)).method_89(vec3d, vec3d1)) == null || !((d1 = vec3d.method_1294(movingobjectposition1.field_1988)) < d) && d != 0.0)
                    continue;
                entity = entity1;
                d = d1;
            }
            if (entity != null) {
                movingobjectposition = new HitResult(entity);
            }
        }
        if (movingobjectposition != null) {
            if (movingobjectposition.field_1989 == null || !movingobjectposition.field_1989.damage(this.field_1996, 0)) {
            }
            for (int j = 0; j < 8; ++j) {
                this.level.addParticle("snowballpoof", this.x, this.y, this.z, 0.0, 0.0, 0.0);
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
        float f1 = 0.99f;
        float f2 = 0.03f;
        if (this.method_1334()) {
            for (int k = 0; k < 4; ++k) {
                float f3 = 0.25f;
                this.level.addParticle("bubble", this.x - this.velocityX * (double) f3, this.y - this.velocityY * (double) f3, this.z - this.velocityZ * (double) f3, this.velocityX, this.velocityY, this.velocityZ);
            }
            f1 = 0.8f;
        }
        this.velocityX *= (double) f1;
        this.velocityY *= (double) f1;
        this.velocityZ *= (double) f1;
        this.velocityY -= (double) f2;
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
    public void onPlayerCollision(Player entityplayer) {
        if (this.inGround && this.field_1996 == entityplayer && this.shake <= 0 && entityplayer.inventory.pickupItem(new ItemInstance(ItemType.arrow, 1))) {
            this.level.playSound(this, "random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            entityplayer.onEntityCollision(this, 1);
            this.remove();
        }
    }
}
