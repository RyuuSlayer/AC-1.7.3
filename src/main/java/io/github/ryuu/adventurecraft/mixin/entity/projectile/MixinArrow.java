package io.github.ryuu.adventurecraft.mixin.entity.projectile;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.tile.LadderTile;
import net.minecraft.tile.Tile;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3f;

import java.util.List;

public class MixinArrow extends Entity {
    public int xTile = -1;
    public int yTile = -1;
    public int zTile = -1;
    public int inTile = 0;
    private int inData = 0;
    private boolean inGround = false;
    public boolean player = false;
    public int shake = 0;
    public LivingEntity field_1576;
    private int field_1583;
    protected int field_1584 = 0;
    private int attackStrength;

    public MixinArrow(Level world) {
        super(world);
        this.setSize(0.5f, 0.5f);
        this.attackStrength = 2;
    }

    public MixinArrow(Level world, double d, double d1, double d2) {
        super(world);
        this.setSize(0.5f, 0.5f);
        this.setPosition(d, d1, d2);
        this.standingEyeHeight = 0.0f;
        this.attackStrength = 2;
    }

    public MixinArrow(Level world, LivingEntity entityliving) {
        super(world);
        this.field_1576 = entityliving;
        this.player = entityliving instanceof Player;
        this.setSize(0.5f, 0.5f);
        this.setPositionAndAngles(entityliving.x, entityliving.y + (double)entityliving.getStandingEyeHeight(), entityliving.z, entityliving.yaw, entityliving.pitch);
        this.x -= MathsHelper.cos(this.yaw / 180.0f * 3.141593f) * 0.16f;
        this.y -= 0.1f;
        this.z -= MathsHelper.sin(this.yaw / 180.0f * 3.141593f) * 0.16f;
        this.setPosition(this.x, this.y, this.z);
        this.standingEyeHeight = 0.0f;
        this.velocityX = -MathsHelper.sin(this.yaw / 180.0f * 3.141593f) * MathsHelper.cos(this.pitch / 180.0f * 3.141593f);
        this.velocityZ = MathsHelper.cos(this.yaw / 180.0f * 3.141593f) * MathsHelper.cos(this.pitch / 180.0f * 3.141593f);
        this.velocityY = -MathsHelper.sin(this.pitch / 180.0f * 3.141593f);
        this.method_1291(this.velocityX, this.velocityY, this.velocityZ, 1.5f, 1.0f);
        this.attackStrength = 2;
    }

    public Arrow(Level world, LivingEntity entityliving, int damage) {
        super(world);
        this.field_1576 = entityliving;
        this.player = entityliving instanceof Player;
        this.setSize(0.5f, 0.5f);
        this.setPositionAndAngles(entityliving.x, entityliving.y + (double)entityliving.getStandingEyeHeight(), entityliving.z, entityliving.yaw, entityliving.pitch);
        this.x -= MathsHelper.cos(this.yaw / 180.0f * 3.141593f) * 0.16f;
        this.y -= 0.1f;
        this.z -= MathsHelper.sin(this.yaw / 180.0f * 3.141593f) * 0.16f;
        this.setPosition(this.x, this.y, this.z);
        this.standingEyeHeight = 0.0f;
        this.velocityX = -MathsHelper.sin(this.yaw / 180.0f * 3.141593f) * MathsHelper.cos(this.pitch / 180.0f * 3.141593f);
        this.velocityZ = MathsHelper.cos(this.yaw / 180.0f * 3.141593f) * MathsHelper.cos(this.pitch / 180.0f * 3.141593f);
        this.velocityY = -MathsHelper.sin(this.pitch / 180.0f * 3.141593f);
        this.method_1291(this.velocityX, this.velocityY, this.velocityZ, 1.5f, 1.0f);
        this.attackStrength = damage;
    }

    protected void initDataTracker() {
        this.collidesWithClipBlocks = false;
    }

    public void method_1291(double d, double d1, double d2, float f, float f1) {
        float f2 = MathsHelper.sqrt(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d += this.rand.nextGaussian() * (double)0.0075f * (double)f1;
        d1 += this.rand.nextGaussian() * (double)0.0075f * (double)f1;
        d2 += this.rand.nextGaussian() * (double)0.0075f * (double)f1;
        this.velocityX = d *= f;
        this.velocityY = d1 *= f;
        this.velocityZ = d2 *= f;
        float f3 = MathsHelper.sqrt(d * d + d2 * d2);
        this.prevYaw = this.yaw = (float)(Math.atan2(d, d2) * 180.0 / 3.1415927410125732);
        this.prevPitch = this.pitch = (float)(Math.atan2(d1, f3) * 180.0 / 3.1415927410125732);
        this.field_1583 = 0;
    }

    public void setVelocity(double d, double d1, double d2) {
        this.velocityX = d;
        this.velocityY = d1;
        this.velocityZ = d2;
        if (this.prevPitch == 0.0f && this.prevYaw == 0.0f) {
            float f = MathsHelper.sqrt(d * d + d2 * d2);
            this.prevYaw = this.yaw = (float)(Math.atan2(d, d2) * 180.0 / 3.1415927410125732);
            this.prevPitch = this.pitch = (float)(Math.atan2(d1, f) * 180.0 / 3.1415927410125732);
            this.prevPitch = this.pitch;
            this.prevYaw = this.yaw;
            this.setPositionAndAngles(this.x, this.y, this.z, this.yaw, this.pitch);
            this.field_1583 = 0;
        }
    }

    public void tick() {
        int i;
        super.tick();
        if (this.prevPitch == 0.0f && this.prevYaw == 0.0f) {
            float f = MathsHelper.sqrt(this.velocityX * this.velocityX + this.velocityZ * this.velocityZ);
            this.prevYaw = this.yaw = (float)(Math.atan2(this.velocityX, this.velocityZ) * 180.0 / 3.1415927410125732);
            this.prevPitch = this.pitch = (float)(Math.atan2(this.velocityY, f) * 180.0 / 3.1415927410125732);
        }
        if ((i = this.level.getTileId(this.xTile, this.yTile, this.zTile)) > 0 && i != Blocks.clipBlock.id && !LadderTile.isLadderID(i)) {
            Tile.BY_ID[i].method_1616(this.level, this.xTile, this.yTile, this.zTile);
            Box axisalignedbb = Tile.BY_ID[i].getCollisionShape(this.level, this.xTile, this.yTile, this.zTile);
            if (axisalignedbb != null && axisalignedbb.method_88(Vec3f.from(this.x, this.y, this.z))) {
                this.inGround = true;
            }
        }
        if (this.shake > 0) {
            --this.shake;
        }
        if (this.inGround) {
            int j = this.level.getTileId(this.xTile, this.yTile, this.zTile);
            int k = this.level.getTileMeta(this.xTile, this.yTile, this.zTile);
            if (j != this.inTile || k != this.inData) {
                this.inGround = false;
                this.velocityX *= this.rand.nextFloat() * 0.2f;
                this.velocityY *= this.rand.nextFloat() * 0.2f;
                this.velocityZ *= this.rand.nextFloat() * 0.2f;
                this.field_1583 = 0;
                this.field_1584 = 0;
                return;
            }
            ++this.field_1583;
            if (this.field_1583 == 1200) {
                this.remove();
            }
            return;
        }
        ++this.field_1584;
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
        for (int l = 0; l < list.size(); ++l) {
            double d1;
            float f4;
            Box axisalignedbb1;
            HitResult movingobjectposition1;
            Entity entity1 = (Entity)list.get(l);
            if (!entity1.method_1356() || entity1 == this.field_1576 && this.field_1584 < 5 || (movingobjectposition1 = (axisalignedbb1 = entity1.boundingBox.expand(f4 = 0.3f, f4, f4)).method_89(vec3d, vec3d1)) == null || !((d1 = vec3d.method_1294(movingobjectposition1.field_1988)) < d) && d != 0.0) continue;
            entity = entity1;
            d = d1;
        }
        if (entity != null) {
            movingobjectposition = new HitResult(entity);
        }
        if (movingobjectposition != null) {
            if (movingobjectposition.field_1989 != null) {
                this.handleHitEntity(movingobjectposition);
            } else {
                this.xTile = movingobjectposition.x;
                this.yTile = movingobjectposition.y;
                this.zTile = movingobjectposition.z;
                this.inTile = this.level.getTileId(this.xTile, this.yTile, this.zTile);
                this.inData = this.level.getTileMeta(this.xTile, this.yTile, this.zTile);
                this.velocityX = (float)(movingobjectposition.field_1988.x - this.x);
                this.velocityY = (float)(movingobjectposition.field_1988.y - this.y);
                this.velocityZ = (float)(movingobjectposition.field_1988.z - this.z);
                float f1 = MathsHelper.sqrt(this.velocityX * this.velocityX + this.velocityY * this.velocityY + this.velocityZ * this.velocityZ);
                this.x -= this.velocityX / (double)f1 * (double)0.05f;
                this.y -= this.velocityY / (double)f1 * (double)0.05f;
                this.z -= this.velocityZ / (double)f1 * (double)0.05f;
                this.level.playSound(this, "random.drr", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
                this.inGround = true;
                this.shake = 7;
            }
        }
        this.x += this.velocityX;
        this.y += this.velocityY;
        this.z += this.velocityZ;
        float f2 = MathsHelper.sqrt(this.velocityX * this.velocityX + this.velocityZ * this.velocityZ);
        this.yaw = (float)(Math.atan2(this.velocityX, this.velocityZ) * 180.0 / 3.1415927410125732);
        this.pitch = (float)(Math.atan2(this.velocityY, f2) * 180.0 / 3.1415927410125732);
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
        float f3 = 0.99f;
        float f5 = 0.03f;
        if (this.method_1334()) {
            for (int i1 = 0; i1 < 4; ++i1) {
                float f6 = 0.25f;
                this.level.addParticle("bubble", this.x - this.velocityX * (double)f6, this.y - this.velocityY * (double)f6, this.z - this.velocityZ * (double)f6, this.velocityX, this.velocityY, this.velocityZ);
            }
            f3 = 0.8f;
        }
        this.velocityX *= f3;
        this.velocityY *= f3;
        this.velocityZ *= f3;
        this.velocityY -= f5;
        this.setPosition(this.x, this.y, this.z);
    }

    public void handleHitEntity(HitResult movingobjectposition) {
        if (movingobjectposition.field_1989 instanceof LivingEntity && ((LivingEntity)movingobjectposition.field_1989).protectedByShield(this.prevX, this.prevY, this.prevZ)) {
            this.level.playSound(this, "random.drr", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
            this.remove();
        } else if (movingobjectposition.field_1989.damage(this.field_1576, this.attackStrength)) {
            this.level.playSound(this, "random.drr", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
            this.remove();
        } else {
            this.velocityX *= -0.1f;
            this.velocityY *= -0.1f;
            this.velocityZ *= -0.1f;
            this.yaw += 180.0f;
            this.prevYaw += 180.0f;
            this.field_1584 = 0;
        }
    }

    public void writeCustomDataToTag(CompoundTag tag) {
        tag.put("xTile", (short)this.xTile);
        tag.put("yTile", (short)this.yTile);
        tag.put("zTile", (short)this.zTile);
        tag.put("inTile", (byte)this.inTile);
        tag.put("inData", (byte)this.inData);
        tag.put("shake", (byte)this.shake);
        tag.put("inGround", (byte)(this.inGround ? 1 : 0));
        tag.put("player", this.player);
    }

    public void readCustomDataFromTag(CompoundTag tag) {
        this.xTile = tag.getShort("xTile");
        this.yTile = tag.getShort("yTile");
        this.zTile = tag.getShort("zTile");
        this.inTile = tag.getByte("inTile") & 0xFF;
        this.inData = tag.getByte("inData") & 0xFF;
        this.shake = tag.getByte("shake") & 0xFF;
        this.inGround = tag.getByte("inGround") == 1;
        this.player = tag.getBoolean("player");
    }

    public void onPlayerCollision(Player entityplayer) {
        if (this.level.isClient) {
            return;
        }
        if (this.inGround && this.player && this.shake <= 0 && entityplayer.inventory.pickupItem(new ItemInstance(ItemType.arrow, 1))) {
            this.level.playSound(this, "random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            entityplayer.onEntityCollision(this, 1);
            this.remove();
        }
    }

    public float getEyeHeight() {
        return 0.0f;
    }
}