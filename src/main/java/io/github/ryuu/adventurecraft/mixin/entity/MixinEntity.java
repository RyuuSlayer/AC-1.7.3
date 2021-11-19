/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  java.util.Random
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.mixin.entity;

import java.util.List;
import java.util.Random;

import io.github.ryuu.adventurecraft.mixin.level.MixinLevel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.tile.FluidTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileSounds;
import net.minecraft.tile.material.Material;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.DoubleTag;
import net.minecraft.util.io.FloatTag;
import net.minecraft.util.io.ListTag;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3f;
import io.github.ryuu.adventurecraft.mixin.item.MixinPlayer;
import io.github.ryuu.adventurecraft.mixin.item.MixinLevel;
import io.github.ryuu.adventurecraft.mixin.item.MixinLivingEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinCompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.mixin.item.MixinEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinItemInstance;
import io.github.ryuu.adventurecraft.mixin.item.MixinItemEntity;

@Mixin(Entity.class)
public abstract class MixinEntity {

    @Shadow()
    public static int field_1590 = 0;

    public int id;

    public double renderDistanceMultiplier = 1.0;

    public boolean field_1593 = false;

    public MixinEntity passenger;

    public MixinEntity vehicle;

    public MixinLevel level;

    public double prevX;

    public double prevY;

    public double prevZ;

    public double x;

    public double y;

    public double z;

    public double velocityX;

    public double velocityY;

    public double velocityZ;

    public float yaw;

    public float pitch;

    public float prevYaw;

    public float prevPitch;

    public final Box boundingBox = Box.create(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

    public boolean onGround = false;

    public boolean field_1624;

    public boolean field_1625;

    public boolean field_1626 = false;

    public boolean shouldSendVelocityUpdate = false;

    public boolean inCobweb;

    public boolean field_1629 = true;

    public boolean removed = false;

    public float standingEyeHeight = 0.0f;

    public float width = 0.6f;

    public float height = 1.8f;

    public float field_1634 = 0.0f;

    public float field_1635 = 0.0f;

    protected float fallDistance = 0.0f;

    private int field_1611 = 1;

    public double prevRenderX;

    public double prevRenderY;

    public double prevRenderZ;

    public float field_1640 = 0.0f;

    public float field_1641 = 0.0f;

    public boolean field_1642 = false;

    public float field_1643 = 0.0f;

    protected Random rand;

    public int field_1645 = 0;

    public int field_1646 = 1;

    public int fire = 0;

    public int field_1648 = 300;

    protected boolean field_1612 = false;

    public int field_1613 = 0;

    public int air = 300;

    private boolean field_1649 = true;

    public String skinUrl;

    public String cloakUrl;

    public boolean immuneToFire = false;

    protected DataTracker dataTracker;

    public float field_1617 = 0.0f;

    private double field_1650;

    private double field_1651;

    public boolean shouldTick = false;

    public int chunkX;

    public int chunkIndex;

    public int chunkZ;

    public int field_1654;

    public int field_1655;

    public int field_1656;

    public boolean field_1622;

    public boolean isFlying;

    public int stunned;

    public boolean collidesWithClipBlocks = true;

    public int collisionX;

    public int collisionZ;

    public float moveYawOffset = 0.0f;

    public MixinEntity(MixinLevel world) {
        this.id = field_1590++;
        this.rand = new Random();
        this.dataTracker = new DataTracker();
        this.level = world;
        this.setPosition(0.0, 0.0, 0.0);
        this.dataTracker.startTracking(0, (byte) 0);
        this.initDataTracker();
        this.isFlying = false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected abstract void initDataTracker();

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public DataTracker getDataTracker() {
        return this.dataTracker;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean equals(Object object) {
        if (object instanceof MixinEntity) {
            return ((MixinEntity) object).id == this.id;
        }
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int hashCode() {
        return this.id;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void afterSpawn() {
        if (this.level == null) {
            return;
        }
        while (!(this.y <= 0.0)) {
            this.setPosition(this.x, this.y, this.z);
            if (this.level.method_190(this, this.boundingBox).size() == 0)
                break;
            this.y += 1.0;
        }
        this.velocityZ = 0.0;
        this.velocityY = 0.0;
        this.velocityX = 0.0;
        this.pitch = 0.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void remove() {
        this.removed = true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setRotation(float f, float f1) {
        this.yaw = f % 360.0f;
        this.pitch = f1 % 360.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setPosition(double d, double d1, double d2) {
        this.x = d;
        this.y = d1;
        this.z = d2;
        float f = this.width / 2.0f;
        float f1 = this.height;
        this.boundingBox.set(d - (double) f, d1 - (double) this.standingEyeHeight + (double) this.field_1640, d2 - (double) f, d + (double) f, d1 - (double) this.standingEyeHeight + (double) this.field_1640 + (double) f1, d2 + (double) f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1362(float f, float f1) {
        float f2 = this.pitch;
        float f3 = this.yaw;
        this.yaw = (float) ((double) this.yaw + (double) f * 0.15);
        this.pitch = (float) ((double) this.pitch - (double) f1 * 0.15);
        if (this.pitch < -90.0f) {
            this.pitch = -90.0f;
        }
        if (this.pitch > 90.0f) {
            this.pitch = 90.0f;
        }
        this.prevPitch += this.pitch - f2;
        this.prevYaw += this.yaw - f3;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void tick() {
        this.baseTick();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void baseTick() {
        if (this.vehicle != null && this.vehicle.removed) {
            this.vehicle = null;
        }
        ++this.field_1645;
        this.field_1634 = this.field_1635;
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        this.prevPitch = this.pitch;
        this.prevYaw = this.yaw;
        if (this.method_1393()) {
            if (!this.field_1612 && !this.field_1649) {
                float f = MathsHelper.sqrt(this.velocityX * this.velocityX * (double) 0.2f + this.velocityY * this.velocityY + this.velocityZ * this.velocityZ * (double) 0.2f) * 0.2f;
                if (f > 1.0f) {
                    f = 1.0f;
                }
                this.level.playSound(this, "random.splash", f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                float f1 = MathsHelper.floor(this.boundingBox.minY);
                int i = 0;
                while ((float) i < 1.0f + this.width * 20.0f) {
                    float f2 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    float f4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    this.level.addParticle("bubble", this.x + (double) f2, f1 + 1.0f, this.z + (double) f4, this.velocityX, this.velocityY - (double) (this.rand.nextFloat() * 0.2f), this.velocityZ);
                    ++i;
                }
                int j = 0;
                while ((float) j < 1.0f + this.width * 20.0f) {
                    float f3 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    float f5 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    this.level.addParticle("splash", this.x + (double) f3, f1 + 1.0f, this.z + (double) f5, this.velocityX, this.velocityY, this.velocityZ);
                    ++j;
                }
            }
            this.fallDistance = 0.0f;
            this.field_1612 = true;
            this.fire = 0;
        } else {
            this.field_1612 = false;
        }
        if (this.level.isClient) {
            this.fire = 0;
        } else if (this.fire > 0) {
            if (this.immuneToFire) {
                this.fire -= 4;
                if (this.fire < 0) {
                    this.fire = 0;
                }
            } else {
                if (this.fire % 20 == 0) {
                    this.damage(null, 1);
                }
                --this.fire;
            }
        }
        if (this.method_1335()) {
            this.method_1332();
        }
        if (this.y < -64.0) {
            this.destroy();
        }
        if (!this.level.isClient) {
            this.method_1326(0, this.fire > 0);
            this.method_1326(2, this.vehicle != null);
        }
        this.field_1649 = false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void method_1332() {
        if (!this.immuneToFire) {
            this.damage(null, 4);
            this.fire = 600;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void destroy() {
        this.remove();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_1344(double d, double d1, double d2) {
        Box axisalignedbb = this.boundingBox.move(d, d1, d2);
        List list = this.level.method_190(this, axisalignedbb);
        if (list.size() > 0) {
            return false;
        }
        return !this.level.method_218(axisalignedbb);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
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
            d1 *= (double) 0.05f;
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
            while (d != 0.0 && this.level.method_190(this, this.boundingBox.move(d, -1.0, 0.0)).size() == 0) {
                d = d < d8 && d >= -d8 ? 0.0 : (d > 0.0 ? (d -= d8) : (d += d8));
                d5 = d;
            }
            while (d2 != 0.0 && this.level.method_190(this, this.boundingBox.move(0.0, -1.0, d2)).size() == 0) {
                d2 = d2 < d8 && d2 >= -d8 ? 0.0 : (d2 > 0.0 ? (d2 -= d8) : (d2 += d8));
                d7 = d2;
            }
        }
        List list = this.level.method_190(this, this.boundingBox.add(d, d1, d2));
        for (int i3 = 0; i3 < list.size(); ++i3) {
            d1 = ((Box) list.get(i3)).method_97(this.boundingBox, d1);
        }
        this.boundingBox.method_102(0.0, d1, 0.0);
        if (!this.field_1629 && d6 != d1) {
            d2 = 0.0;
            d1 = 0.0;
            d = 0.0;
        }
        boolean flag1 = this.onGround || d6 != d1 && d6 < 0.0;
        for (int j = 0; j < list.size(); ++j) {
            d = ((Box) list.get(j)).method_91(this.boundingBox, d);
        }
        this.boundingBox.method_102(d, 0.0, 0.0);
        if (!this.field_1629 && d5 != d) {
            d2 = 0.0;
            d1 = 0.0;
            d = 0.0;
        }
        for (int k = 0; k < list.size(); ++k) {
            d2 = ((Box) list.get(k)).method_101(this.boundingBox, d2);
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
            List list1 = this.level.method_190(this, this.boundingBox.add(d, d1, d2));
            for (int j2 = 0; j2 < list1.size(); ++j2) {
                d1 = ((Box) list1.get(j2)).method_97(this.boundingBox, d1);
            }
            this.boundingBox.method_102(0.0, d1, 0.0);
            if (!this.field_1629 && d6 != d1) {
                d2 = 0.0;
                d1 = 0.0;
                d = 0.0;
            }
            for (int k2 = 0; k2 < list1.size(); ++k2) {
                d = ((Box) list1.get(k2)).method_91(this.boundingBox, d);
            }
            this.boundingBox.method_102(d, 0.0, 0.0);
            if (!this.field_1629 && d5 != d) {
                d2 = 0.0;
                d1 = 0.0;
                d = 0.0;
            }
            for (int l2 = 0; l2 < list1.size(); ++l2) {
                d2 = ((Box) list1.get(l2)).method_101(this.boundingBox, d2);
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
                for (int i3 = 0; i3 < list1.size(); ++i3) {
                    d1 = ((Box) list1.get(i3)).method_97(this.boundingBox, d1);
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
        this.collisionX = d5 == d ? 0 : (d5 < d ? -1 : 1);
        if (this.collisionX != 0) {
            boolean nonClipFound = false;
            i = 0;
            while ((double) i < (double) this.height + this.y - (double) this.standingEyeHeight - Math.floor((double) (this.y - (double) this.standingEyeHeight))) {
                int blockID = this.level.getTileId((int) Math.floor((double) this.x) + this.collisionX, (int) Math.floor((double) (this.y + (double) i - (double) this.standingEyeHeight)), (int) Math.floor((double) this.z));
                if (blockID != 0 && blockID != Blocks.clipBlock.id) {
                    nonClipFound = true;
                }
                ++i;
            }
            if (!nonClipFound) {
                this.collisionX = 0;
            }
        }
        this.collisionZ = d7 == d2 ? 0 : (d7 < d2 ? -1 : 1);
        if (this.collisionZ != 0) {
            boolean nonClipFound = false;
            i = 0;
            while ((double) i < (double) this.height + this.y - (double) this.standingEyeHeight - Math.floor((double) (this.y - (double) this.standingEyeHeight))) {
                int blockID = this.level.getTileId((int) Math.floor((double) this.x), (int) Math.floor((double) (this.y + (double) i - (double) this.standingEyeHeight)), (int) Math.floor((double) this.z) + this.collisionZ);
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
                this.field_1611 = (int) ((double) this.field_1611 + Math.ceil((double) (this.field_1635 - (float) this.field_1611)));
                TileSounds stepsound = Tile.BY_ID[j3].sounds;
                if (this.level.getTileId(l, j1 + 1, l1) == Tile.SNOW.id) {
                    stepsound = Tile.SNOW.sounds;
                    this.level.playSound(this, stepsound.getWalkSound(), stepsound.getVolume() * 0.15f, stepsound.getPitch());
                } else if (!Tile.BY_ID[j3].material.isLiquid()) {
                    this.level.playSound(this, stepsound.getWalkSound(), stepsound.getVolume() * 0.15f, stepsound.getPitch());
                }
                Tile.BY_ID[j3].method_1560(this.level, l, j1, l1, this);
            }
        }
        if (this.level.isRegionLoaded(i1 = MathsHelper.floor(this.boundingBox.minX + 0.001), k1 = MathsHelper.floor(this.boundingBox.minY + 0.001), i2 = MathsHelper.floor(this.boundingBox.minZ + 0.001), k3 = MathsHelper.floor(this.boundingBox.maxX - 0.001), l3 = MathsHelper.floor(this.boundingBox.maxY - 0.001), i4 = MathsHelper.floor(this.boundingBox.maxZ - 0.001))) {
            for (int j4 = i1; j4 <= k3; ++j4) {
                for (int k4 = k1; k4 <= l3; ++k4) {
                    for (int l4 = i2; l4 <= i4; ++l4) {
                        int i5 = this.level.getTileId(j4, k4, l4);
                        if (i5 <= 0)
                            continue;
                        Tile.BY_ID[i5].onEntityCollision(this.level, j4, k4, l4, this);
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
            this.level.playSound(this, "random.fizz", 0.7f, 1.6f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
            this.fire = -this.field_1646;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected boolean canClimb() {
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
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
    @Overwrite()
    public Box method_1381() {
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void method_1392(int i) {
        if (!this.immuneToFire) {
            this.damage(null, i);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void handleFallDamage(float f) {
        if (this.passenger != null) {
            this.passenger.handleFallDamage(f);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isTouchingWater() {
        return this.field_1612 || this.level.canRainAt(MathsHelper.floor(this.x), MathsHelper.floor(this.y), MathsHelper.floor(this.z));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_1334() {
        return this.field_1612;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_1393() {
        return this.level.method_170(this.boundingBox.expand(0.0, -0.4f, 0.0).method_104(0.001, 0.001, 0.001), Material.WATER, this);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isInFluid(Material material) {
        int k;
        int j;
        double d = this.y + (double) this.getStandingEyeHeight();
        int i = MathsHelper.floor(this.x);
        int l = this.level.getTileId(i, j = MathsHelper.floor(MathsHelper.floor(d)), k = MathsHelper.floor(this.z));
        if (l != 0 && Tile.BY_ID[l].material == material) {
            float f = FluidTile.method_1218(this.level.getTileMeta(i, j, k)) - 0.1111111f;
            float f1 = (float) (j + 1) - f;
            return d < (double) f1;
        }
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float getStandingEyeHeight() {
        return 0.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean handleFlying() {
        return this.isFlying;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_1335() {
        return this.level.method_169(this.boundingBox.expand(-0.1f, -0.4f, -0.1f), Material.LAVA);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void movementInputToVelocity(float f, float f1, float f2) {
        float f3 = f * f + f1 * f1;
        if (f3 < 1.0E-4f) {
            return;
        }
        float f4 = MathsHelper.sin((this.yaw + this.moveYawOffset) * 3.141593f / 180.0f);
        float f5 = MathsHelper.cos((this.yaw + this.moveYawOffset) * 3.141593f / 180.0f);
        this.velocityX += (double) ((f *= f2) * f5 - (f1 *= f2) * f4);
        this.velocityZ += (double) (f1 * f5 + f * f4);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float getBrightnessAtEyes(float f) {
        int i = MathsHelper.floor(this.x);
        double d = (this.boundingBox.maxY - this.boundingBox.minY) * 0.66;
        int j = MathsHelper.floor(this.y - (double) this.standingEyeHeight + d);
        int k = MathsHelper.floor(this.z);
        if (this.level.isRegionLoaded(MathsHelper.floor(this.boundingBox.minX), MathsHelper.floor(this.boundingBox.minY), MathsHelper.floor(this.boundingBox.minZ), MathsHelper.floor(this.boundingBox.maxX), MathsHelper.floor(this.boundingBox.maxY), MathsHelper.floor(this.boundingBox.maxZ))) {
            float f1 = this.level.getBrightness(i, j, k);
            if (f1 < this.field_1617) {
                f1 = this.field_1617;
            }
            return f1;
        }
        return this.field_1617;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setLevel(MixinLevel level) {
        this.level = level;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1338(double d, double d1, double d2, float f, float f1) {
        this.prevX = this.x = d;
        this.prevY = this.y = d1;
        this.prevZ = this.z = d2;
        this.prevYaw = this.yaw = f;
        this.prevPitch = this.pitch = f1;
        this.field_1640 = 0.0f;
        double d3 = this.prevYaw - f;
        if (d3 < -180.0) {
            this.prevYaw += 360.0f;
        }
        if (d3 >= 180.0) {
            this.prevYaw -= 360.0f;
        }
        this.setPosition(this.x, this.y, this.z);
        this.setRotation(f, f1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setPositionAndAngles(double d, double d1, double d2, float f, float f1) {
        this.prevX = this.x = d;
        this.prevRenderX = this.x;
        this.prevY = this.y = d1 + (double) this.standingEyeHeight;
        this.prevRenderY = this.y;
        this.prevZ = this.z = d2;
        this.prevRenderZ = this.z;
        this.yaw = f;
        this.pitch = f1;
        this.setPosition(this.x, this.y, this.z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float distanceTo(MixinEntity entity) {
        float f = (float) (this.x - entity.x);
        float f1 = (float) (this.y - entity.y);
        float f2 = (float) (this.z - entity.z);
        return MathsHelper.sqrt(f * f + f1 * f1 + f2 * f2);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public double squaredDistanceTo(double d, double d1, double d2) {
        double d3 = this.x - d;
        double d4 = this.y - d1;
        double d5 = this.z - d2;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public double method_1350(double d, double d1, double d2) {
        double d3 = this.x - d;
        double d4 = this.y - d1;
        double d5 = this.z - d2;
        return MathsHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public double method_1352(MixinEntity entity) {
        double d = this.x - entity.x;
        double d1 = this.y - entity.y;
        double d2 = this.z - entity.z;
        return d * d + d1 * d1 + d2 * d2;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onPlayerCollision(MixinPlayer entityplayer) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1353(MixinEntity entity) {
        if (entity.passenger == this || entity.vehicle == this) {
            return;
        }
        double d = entity.x - this.x;
        double d1 = entity.z - this.z;
        double d2 = MathsHelper.absMax(d, d1);
        if (d2 >= (double) 0.01f) {
            d2 = MathsHelper.sqrt(d2);
            d /= d2;
            d1 /= d2;
            double d3 = 1.0 / d2;
            if (d3 > 1.0) {
                d3 = 1.0;
            }
            d *= d3;
            d1 *= d3;
            d *= (double) 0.05f;
            d1 *= (double) 0.05f;
            this.method_1322(-(d *= (double) (1.0f - this.field_1643)), 0.0, -(d1 *= (double) (1.0f - this.field_1643)));
            if (entity.method_1380()) {
                entity.method_1322(d, 0.0, d1);
            } else {
                this.method_1322(-d, 0.0, -d1);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1322(double d, double d1, double d2) {
        this.velocityX += d;
        this.velocityY += d1;
        this.velocityZ += d2;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void method_1336() {
        this.shouldSendVelocityUpdate = true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean damage(MixinEntity target, int amount) {
        this.method_1336();
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean attackEntityFromMulti(MixinEntity entity, int i) {
        return this.damage(entity, i);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_1356() {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_1380() {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onKilledOther(MixinEntity entity, int i) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean shouldRenderFrom(Vec3f vec3d) {
        double d = this.x - vec3d.x;
        double d1 = this.y - vec3d.y;
        double d2 = this.z - vec3d.z;
        double d3 = d * d + d1 * d1 + d2 * d2;
        return this.shouldRenderAtDistance(d3);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean shouldRenderAtDistance(double d) {
        double d1 = this.boundingBox.averageDimension();
        return d < (d1 *= 64.0 * this.renderDistanceMultiplier) * d1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public String method_1314() {
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_1343(MixinCompoundTag nbttagcompound) {
        String s = this.method_1337();
        if (this.removed || s == null) {
            return false;
        }
        nbttagcompound.put("id", s);
        this.toTag(nbttagcompound);
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void toTag(MixinCompoundTag nbttagcompound) {
        nbttagcompound.put("Pos", this.method_1329(new double[] { this.x, this.y + (double) this.field_1640, this.z }));
        nbttagcompound.put("Motion", this.method_1329(new double[] { this.velocityX, this.velocityY, this.velocityZ }));
        nbttagcompound.put("Rotation", this.method_1330(new float[] { this.yaw, this.pitch }));
        nbttagcompound.put("FallDistance", this.fallDistance);
        nbttagcompound.put("Fire", (short) this.fire);
        nbttagcompound.put("Air", (short) this.air);
        nbttagcompound.put("OnGround", this.onGround);
        this.writeCustomDataToTag(nbttagcompound);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void fromTag(MixinCompoundTag nbttagcompound) {
        ListTag nbttaglist = nbttagcompound.getListTag("Pos");
        ListTag nbttaglist1 = nbttagcompound.getListTag("Motion");
        ListTag nbttaglist2 = nbttagcompound.getListTag("Rotation");
        this.velocityX = ((DoubleTag) nbttaglist1.get((int) 0)).data;
        this.velocityY = ((DoubleTag) nbttaglist1.get((int) 1)).data;
        this.velocityZ = ((DoubleTag) nbttaglist1.get((int) 2)).data;
        if (Math.abs((double) this.velocityX) > 10.0) {
            this.velocityX = 0.0;
        }
        if (Math.abs((double) this.velocityY) > 10.0) {
            this.velocityY = 0.0;
        }
        if (Math.abs((double) this.velocityZ) > 10.0) {
            this.velocityZ = 0.0;
        }
        this.prevRenderX = this.x = ((DoubleTag) nbttaglist.get((int) 0)).data;
        this.prevX = this.x;
        this.prevRenderY = this.y = ((DoubleTag) nbttaglist.get((int) 1)).data;
        this.prevY = this.y;
        this.prevRenderZ = this.z = ((DoubleTag) nbttaglist.get((int) 2)).data;
        this.prevZ = this.z;
        this.prevYaw = this.yaw = ((FloatTag) nbttaglist2.get((int) 0)).data;
        this.prevPitch = this.pitch = ((FloatTag) nbttaglist2.get((int) 1)).data;
        this.fallDistance = nbttagcompound.getFloat("FallDistance");
        this.fire = nbttagcompound.getShort("Fire");
        this.air = nbttagcompound.getShort("Air");
        this.onGround = nbttagcompound.getBoolean("OnGround");
        this.setPosition(this.x, this.y, this.z);
        this.setRotation(this.yaw, this.pitch);
        this.readCustomDataFromTag(nbttagcompound);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected final String method_1337() {
        return EntityRegistry.getStringId(this);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected abstract void readCustomDataFromTag(MixinCompoundTag var1);

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected abstract void writeCustomDataToTag(MixinCompoundTag var1);

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected ListTag method_1329(double[] ad) {
        ListTag nbttaglist = new ListTag();
        for (double d : ad) {
            nbttaglist.add(new DoubleTag(d));
        }
        return nbttaglist;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected ListTag method_1330(float[] af) {
        ListTag nbttaglist = new ListTag();
        for (float f : af) {
            nbttaglist.add(new FloatTag(f));
        }
        return nbttaglist;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float getEyeHeight() {
        return this.height / 2.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemEntity dropItem(int i, int j) {
        return this.dropItem(i, j, 0.0f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemEntity dropItem(int i, int j, float f) {
        return this.dropItem(new MixinItemInstance(i, j, 0), f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemEntity dropItem(MixinItemInstance itemstack, float f) {
        MixinItemEntity entityitem = new MixinItemEntity(this.level, this.x, this.y + (double) f, this.z, itemstack);
        entityitem.pickupDelay = 10;
        this.level.spawnEntity(entityitem);
        return entityitem;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isAlive() {
        return !this.removed;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isInsideWall() {
        for (int i = 0; i < 8; ++i) {
            int l;
            int k;
            float f = ((float) ((i >> 0) % 2) - 0.5f) * this.width * 0.9f;
            float f1 = ((float) ((i >> 1) % 2) - 0.5f) * 0.1f;
            float f2 = ((float) ((i >> 2) % 2) - 0.5f) * this.width * 0.9f;
            int j = MathsHelper.floor(this.x + (double) f);
            if (!this.level.canSuffocate(j, k = MathsHelper.floor(this.y + (double) this.getStandingEyeHeight() + (double) f1), l = MathsHelper.floor(this.z + (double) f2)) || !this.level.isFullOpaque(j, k, l))
                continue;
            return true;
        }
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean interact(MixinPlayer entityplayer) {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Box method_1379(MixinEntity entity) {
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void tickRiding() {
        if (this.vehicle.removed) {
            this.vehicle = null;
            return;
        }
        this.velocityX = 0.0;
        this.velocityY = 0.0;
        this.velocityZ = 0.0;
        this.tick();
        if (this.vehicle == null) {
            return;
        }
        this.vehicle.method_1382();
        this.field_1651 += (double) (this.vehicle.yaw - this.vehicle.prevYaw);
        this.field_1650 += (double) (this.vehicle.pitch - this.vehicle.prevPitch);
        while (this.field_1651 >= 180.0) {
            this.field_1651 -= 360.0;
        }
        while (this.field_1651 < -180.0) {
            this.field_1651 += 360.0;
        }
        while (this.field_1650 >= 180.0) {
            this.field_1650 -= 360.0;
        }
        while (this.field_1650 < -180.0) {
            this.field_1650 += 360.0;
        }
        double d = this.field_1651 * 0.5;
        double d1 = this.field_1650 * 0.5;
        float f = 10.0f;
        if (d > (double) f) {
            d = f;
        }
        if (d < (double) (-f)) {
            d = -f;
        }
        if (d1 > (double) f) {
            d1 = f;
        }
        if (d1 < (double) (-f)) {
            d1 = -f;
        }
        this.field_1651 -= d;
        this.field_1650 -= d1;
        this.yaw = (float) ((double) this.yaw + d);
        this.pitch = (float) ((double) this.pitch + d1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1382() {
        this.passenger.setPosition(this.x, this.y + this.getMountedHeightOffset() + this.passenger.getHeightOffset(), this.z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public double getHeightOffset() {
        return this.standingEyeHeight;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public double getMountedHeightOffset() {
        return (double) this.height * 0.75;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void startRiding(MixinEntity entity) {
        this.field_1650 = 0.0;
        this.field_1651 = 0.0;
        if (entity == null) {
            if (this.vehicle != null) {
                this.setPositionAndAngles(this.vehicle.x, this.vehicle.boundingBox.minY + (double) this.vehicle.height, this.vehicle.z, this.yaw, this.pitch);
                this.vehicle.passenger = null;
            }
            this.vehicle = null;
            return;
        }
        if (this.vehicle == entity) {
            this.vehicle.passenger = null;
            this.vehicle = null;
            this.setPositionAndAngles(entity.x, entity.boundingBox.minY + (double) entity.height, entity.z, this.yaw, this.pitch);
            return;
        }
        if (this.vehicle != null) {
            this.vehicle.passenger = null;
        }
        if (entity.passenger != null) {
            entity.passenger.vehicle = null;
        }
        this.vehicle = entity;
        entity.passenger = this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1311(double d, double d1, double d2, float f, float f1, int i) {
        this.setPosition(d, d1, d2);
        this.setRotation(f, f1);
        List list = this.level.method_190(this, this.boundingBox.method_104(0.03125, 0.0, 0.03125));
        if (list.size() > 0) {
            double d3 = 0.0;
            for (int j = 0; j < list.size(); ++j) {
                Box axisalignedbb = (Box) list.get(j);
                if (!(axisalignedbb.maxY > d3))
                    continue;
                d3 = axisalignedbb.maxY;
            }
            this.setPosition(d, d1 += d3 - this.boundingBox.minY, d2);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float method_1369() {
        return 0.1f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Vec3f method_1320() {
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1388() {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setVelocity(double d, double d1, double d2) {
        this.velocityX = d;
        this.velocityY = d1;
        this.velocityZ = d2;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void handleStatus(byte byte0) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1312() {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void initCloak() {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1361(int i, int j, int k) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_1359() {
        return this.fire > 0 || this.method_1345(0);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_1360() {
        return this.vehicle != null || this.method_1345(2);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemInstance[] getInventory() {
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_1373() {
        return this.method_1345(1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1349(boolean bl) {
        this.method_1326(1, bl);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected boolean method_1345(int i) {
        return (this.dataTracker.getByte(0) & 1 << i) != 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void method_1326(int i, boolean flag) {
        byte byte0 = this.dataTracker.getByte(0);
        if (flag) {
            this.dataTracker.setData(0, (byte) (byte0 | 1 << i));
        } else {
            this.dataTracker.setData(0, (byte) (byte0 & ~(1 << i)));
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onStruckByLightning(Lightning entitylightningbolt) {
        this.method_1392(5);
        ++this.fire;
        if (this.fire == 0) {
            this.fire = 300;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void handleKilledEntity(MixinLivingEntity entityliving) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected boolean method_1372(double d, double d1, double d2) {
        int i = MathsHelper.floor(d);
        int j = MathsHelper.floor(d1);
        int k = MathsHelper.floor(d2);
        double d3 = d - (double) i;
        double d4 = d1 - (double) j;
        double d5 = d2 - (double) k;
        if (this.level.canSuffocate(i, j, k)) {
            boolean flag = !this.level.canSuffocate(i - 1, j, k);
            boolean flag1 = !this.level.canSuffocate(i + 1, j, k);
            boolean flag2 = !this.level.canSuffocate(i, j - 1, k);
            boolean flag3 = !this.level.canSuffocate(i, j + 1, k);
            boolean flag4 = !this.level.canSuffocate(i, j, k - 1);
            boolean flag5 = !this.level.canSuffocate(i, j, k + 1);
            int byte0 = -1;
            double d6 = 9999.0;
            if (flag && d3 < d6) {
                d6 = d3;
                byte0 = 0;
            }
            if (flag1 && 1.0 - d3 < d6) {
                d6 = 1.0 - d3;
                byte0 = 1;
            }
            if (flag2 && d4 < d6) {
                d6 = d4;
                byte0 = 2;
            }
            if (flag3 && 1.0 - d4 < d6) {
                d6 = 1.0 - d4;
                byte0 = 3;
            }
            if (flag4 && d5 < d6) {
                d6 = d5;
                byte0 = 4;
            }
            if (flag5 && 1.0 - d5 < d6) {
                double d7 = 1.0 - d5;
                byte0 = 5;
            }
            float f = this.rand.nextFloat() * 0.2f + 0.1f;
            if (byte0 == 0) {
                this.velocityX = -f;
            }
            if (byte0 == 1) {
                this.velocityX = f;
            }
            if (byte0 == 2) {
                this.velocityY = -f;
            }
            if (byte0 == 3) {
                this.velocityY = f;
            }
            if (byte0 == 4) {
                this.velocityZ = -f;
            }
            if (byte0 == 5) {
                this.velocityZ = f;
            }
        }
        return false;
    }
}
