package io.github.ryuu.adventurecraft.mixin.entity;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.stat.Stats;
import net.minecraft.tile.material.Material;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3f;

public class MixinFishHook extends Entity {
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private int inTile = 0;
    private boolean inGround = false;
    public int shake = 0;
    public Player field_1067;
    private int field_1074;
    private int field_1075 = 0;
    private int field_1076 = 0;
    public Entity field_1068 = null;
    private int field_1077;
    private double field_1078;
    private double field_1079;
    private double field_1080;
    private double field_1081;
    private double field_1082;
    private double field_1083;
    private double field_1084;
    private double field_1085;

    public MixinFishHook(Level world) {
        super(world);
        this.setSize(0.25f, 0.25f);
        this.field_1622 = true;
    }

    public MixinFishHook(Level world, double d, double d1, double d2) {
        this(world);
        this.setPosition(d, d1, d2);
        this.field_1622 = true;
    }

    public MixinFishHook(Level world, Player entityplayer) {
        super(world);
        this.field_1622 = true;
        this.field_1067 = entityplayer;
        this.field_1067.fishHook = this;
        this.setSize(0.25f, 0.25f);
        this.setPositionAndAngles(entityplayer.x, entityplayer.y + 1.62 - (double)entityplayer.standingEyeHeight, entityplayer.z, entityplayer.yaw, entityplayer.pitch);
        this.x -= MathsHelper.cos(this.yaw / 180.0f * 3.141593f) * 0.16f;
        this.y -= 0.1f;
        this.z -= MathsHelper.sin(this.yaw / 180.0f * 3.141593f) * 0.16f;
        this.setPosition(this.x, this.y, this.z);
        this.standingEyeHeight = 0.0f;
        float f = 0.4f;
        this.velocityX = -MathsHelper.sin(this.yaw / 180.0f * 3.141593f) * MathsHelper.cos(this.pitch / 180.0f * 3.141593f) * f;
        this.velocityZ = MathsHelper.cos(this.yaw / 180.0f * 3.141593f) * MathsHelper.cos(this.pitch / 180.0f * 3.141593f) * f;
        this.velocityY = -MathsHelper.sin(this.pitch / 180.0f * 3.141593f) * f;
        this.method_955(this.velocityX, this.velocityY, this.velocityZ, 1.5f, 1.0f);
    }

    protected void initDataTracker() {
    }

    public boolean shouldRenderAtDistance(double d) {
        double d1 = this.boundingBox.averageDimension() * 4.0;
        return d < (d1 *= 64.0) * d1;
    }

    public void method_955(double d, double d1, double d2, float f, float f1) {
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
        this.field_1074 = 0;
    }

    public void method_1311(double d, double d1, double d2, float f, float f1, int i) {
        this.field_1078 = d;
        this.field_1079 = d1;
        this.field_1080 = d2;
        this.field_1081 = f;
        this.field_1082 = f1;
        this.field_1077 = i;
        this.velocityX = this.field_1083;
        this.velocityY = this.field_1084;
        this.velocityZ = this.field_1085;
    }

    public void setVelocity(double d, double d1, double d2) {
        this.field_1083 = this.velocityX = d;
        this.field_1084 = this.velocityY = d1;
        this.field_1085 = this.velocityZ = d2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void tick() {
        block37: {
            if (this.field_1067 == null) {
                this.field_1067 = Minecraft.minecraftInstance.player;
                Minecraft.minecraftInstance.player.fishHook = this;
            }
            super.tick();
            if (this.field_1077 > 0) {
                double d4;
                double d = this.x + (this.field_1078 - this.x) / (double)this.field_1077;
                double d1 = this.y + (this.field_1079 - this.y) / (double)this.field_1077;
                double d2 = this.z + (this.field_1080 - this.z) / (double)this.field_1077;
                for (d4 = this.field_1081 - (double)this.yaw; d4 < -180.0; d4 += 360.0) {
                }
                while (true) {
                    if (!(d4 >= 180.0)) {
                        this.yaw = (float)((double)this.yaw + d4 / (double)this.field_1077);
                        this.pitch = (float)((double)this.pitch + (this.field_1082 - (double)this.pitch) / (double)this.field_1077);
                        --this.field_1077;
                        this.setPosition(d, d1, d2);
                        this.setRotation(this.yaw, this.pitch);
                        return;
                    }
                    d4 -= 360.0;
                }
            }
            if (!this.level.isClient) {
                ItemInstance itemstack = this.field_1067.getHeldItem();
                if (this.field_1067.removed || !this.field_1067.isAlive() || itemstack == null || itemstack.getType() != ItemType.fishingRod || this.method_1352(this.field_1067) > 1024.0) {
                    this.remove();
                    this.field_1067.fishHook = null;
                    return;
                }
                if (this.field_1068 != null) {
                    if (!this.field_1068.removed) {
                        this.x = this.field_1068.x;
                        this.y = this.field_1068.boundingBox.minY + (double)this.field_1068.height * 0.8;
                        this.z = this.field_1068.z;
                        return;
                    }
                    this.field_1068 = null;
                }
            }
            if (this.shake > 0) {
                --this.shake;
            }
            if (this.inGround) {
                int i = this.level.getTileId(this.xTile, this.yTile, this.zTile);
                if (i != this.inTile) {
                    this.inGround = false;
                    this.velocityX *= this.rand.nextFloat() * 0.2f;
                    this.velocityY *= this.rand.nextFloat() * 0.2f;
                    this.velocityZ *= this.rand.nextFloat() * 0.2f;
                    this.field_1074 = 0;
                    this.field_1075 = 0;
                    break block37;
                } else {
                    ++this.field_1074;
                    if (this.field_1074 == 1200) {
                        this.remove();
                    }
                    return;
                }
            }
            ++this.field_1075;
        }
        Vec3f vec3d = Vec3f.from(this.x, this.y, this.z);
        Vec3f vec3d1 = Vec3f.from(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
        HitResult movingobjectposition = this.level.raycast(vec3d, vec3d1);
        vec3d = Vec3f.from(this.x, this.y, this.z);
        vec3d1 = Vec3f.from(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
        if (movingobjectposition != null) {
            vec3d1 = Vec3f.from(movingobjectposition.field_1988.x, movingobjectposition.field_1988.y, movingobjectposition.field_1988.z);
        }
        Entity entity = null;
        List list = this.level.getEntities(this, this.boundingBox.add(this.velocityX, this.velocityY, this.velocityZ).expand(1.0, 1.0, 1.0));
        double d3 = 0.0;
        for (int j = 0; j < list.size(); ++j) {
            double d6;
            float f2;
            Box axisalignedbb;
            HitResult movingobjectposition1;
            Entity entity1 = (Entity)list.get(j);
            if (!entity1.method_1356() || entity1 == this.field_1067 && this.field_1075 < 5 || (movingobjectposition1 = (axisalignedbb = entity1.boundingBox.expand(f2 = 0.3f, f2, f2)).method_89(vec3d, vec3d1)) == null || !((d6 = vec3d.method_1294(movingobjectposition1.field_1988)) < d3) && d3 != 0.0) continue;
            entity = entity1;
            d3 = d6;
        }
        if (entity != null) {
            movingobjectposition = new HitResult(entity);
        }
        if (movingobjectposition != null) {
            if (movingobjectposition.field_1989 != null) {
                if (movingobjectposition.field_1989.damage(this.field_1067, 0)) {
                    this.field_1068 = movingobjectposition.field_1989;
                }
            } else {
                this.inGround = true;
            }
        }
        if (this.inGround) {
            return;
        }
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        float f = MathsHelper.sqrt(this.velocityX * this.velocityX + this.velocityZ * this.velocityZ);
        this.yaw = (float)(Math.atan2(this.velocityX, this.velocityZ) * 180.0 / 3.1415927410125732);
        this.pitch = (float)(Math.atan2(this.velocityY, f) * 180.0 / 3.1415927410125732);
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
        float f1 = 0.92f;
        if (this.onGround || this.field_1624) {
            f1 = 0.5f;
        }
        int k = 5;
        double d5 = 0.0;
        for (int l = 0; l < k; ++l) {
            double d8 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(l + 0) / (double)k - 0.125 + 0.125;
            double d9 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(l + 1) / (double)k - 0.125 + 0.125;
            Box axisalignedbb1 = Box.getOrCreate(this.boundingBox.minX, d8, this.boundingBox.minZ, this.boundingBox.maxX, d9, this.boundingBox.maxZ);
            if (!this.level.method_207(axisalignedbb1, Material.WATER)) continue;
            d5 += 1.0 / (double)k;
        }
        if (d5 > 0.0) {
            if (this.field_1076 > 0) {
                --this.field_1076;
            } else {
                int c = 500;
                if (this.level.canRainAt(MathsHelper.floor(this.x), MathsHelper.floor(this.y) + 1, MathsHelper.floor(this.z))) {
                    c = 300;
                }
                if (this.rand.nextInt(c) == 0) {
                    this.field_1076 = this.rand.nextInt(30) + 10;
                    this.velocityY -= (double)0.2f;
                    this.level.playSound(this, "random.splash", 0.25f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                    float f3 = MathsHelper.floor(this.boundingBox.minY);
                    int i1 = 0;
                    while ((float)i1 < 1.0f + this.width * 20.0f) {
                        float f4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                        float f6 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                        this.level.addParticle("bubble", this.x + (double)f4, f3 + 1.0f, this.z + (double)f6, this.velocityX, this.velocityY - (double)(this.rand.nextFloat() * 0.2f), this.velocityZ);
                        ++i1;
                    }
                    int j1 = 0;
                    while ((float)j1 < 1.0f + this.width * 20.0f) {
                        float f5 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                        float f7 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                        this.level.addParticle("splash", this.x + (double)f5, f3 + 1.0f, this.z + (double)f7, this.velocityX, this.velocityY, this.velocityZ);
                        ++j1;
                    }
                }
            }
        }
        if (this.field_1076 > 0) {
            this.velocityY -= (double)(this.rand.nextFloat() * this.rand.nextFloat() * this.rand.nextFloat()) * 0.2;
        }
        double d7 = d5 * 2.0 - 1.0;
        this.velocityY += (double)0.04f * d7;
        if (d5 > 0.0) {
            f1 = (float)((double)f1 * 0.9);
            this.velocityY *= 0.8;
        }
        this.velocityX *= f1;
        this.velocityY *= f1;
        this.velocityZ *= f1;
        this.setPosition(this.x, this.y, this.z);
    }

    public void writeCustomDataToTag(CompoundTag tag) {
        tag.put("xTile", (short)this.xTile);
        tag.put("yTile", (short)this.yTile);
        tag.put("zTile", (short)this.zTile);
        tag.put("inTile", (byte)this.inTile);
        tag.put("shake", (byte)this.shake);
        tag.put("inGround", (byte)(this.inGround ? 1 : 0));
    }

    public void readCustomDataFromTag(CompoundTag tag) {
        this.xTile = tag.getShort("xTile");
        this.yTile = tag.getShort("yTile");
        this.zTile = tag.getShort("zTile");
        this.inTile = tag.getByte("inTile") & 0xFF;
        this.shake = tag.getByte("shake") & 0xFF;
        this.inGround = tag.getByte("inGround") == 1;
    }

    public float getEyeHeight() {
        return 0.0f;
    }

    public int method_956() {
        int byte0 = 0;
        if (this.field_1068 != null) {
            double d = this.field_1067.x - this.x;
            double d2 = this.field_1067.y - this.y;
            double d4 = this.field_1067.z - this.z;
            double d6 = MathsHelper.sqrt(d * d + d2 * d2 + d4 * d4);
            double d8 = 0.1;
            this.field_1068.velocityX += d * d8;
            this.field_1068.velocityY += d2 * d8 + (double)MathsHelper.sqrt(d6) * 0.08;
            this.field_1068.velocityZ += d4 * d8;
            byte0 = 3;
        } else if (this.field_1076 > 0) {
            ItemEntity entityitem = new ItemEntity(this.level, this.x, this.y, this.z, new ItemInstance(ItemType.fishRaw));
            double d1 = this.field_1067.x - this.x;
            double d3 = this.field_1067.y - this.y;
            double d5 = this.field_1067.z - this.z;
            double d7 = MathsHelper.sqrt(d1 * d1 + d3 * d3 + d5 * d5);
            double d9 = 0.1;
            entityitem.velocityX = d1 * d9;
            entityitem.velocityY = d3 * d9 + (double)MathsHelper.sqrt(d7) * 0.08;
            entityitem.velocityZ = d5 * d9;
            this.level.spawnEntity(entityitem);
            this.field_1067.increaseStat(Stats.fishCaught, 1);
            byte0 = 1;
        }
        if (this.inGround) {
            byte0 = 2;
        }
        this.remove();
        this.field_1067.fishHook = null;
        return byte0;
    }
}