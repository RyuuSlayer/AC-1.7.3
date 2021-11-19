/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.List
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.mixin.entity;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.tile.LadderTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileSounds;
import net.minecraft.tile.material.Material;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3f;
import io.github.ryuu.adventurecraft.mixin.item.MixinPlayer;
import io.github.ryuu.adventurecraft.mixin.item.MixinLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.mixin.item.MixinLivingEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinCompoundTag;
import io.github.ryuu.adventurecraft.mixin.item.MixinEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinItemInstance;
import io.github.ryuu.adventurecraft.mixin.item.MixinItemEntity;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends MixinEntity {

    @Shadow()
    public int field_1009 = 20;

    public float field_1010;

    public float field_1011;

    public float field_1012 = 0.0f;

    public float field_1013 = 0.0f;

    protected float field_1014;

    protected float field_1015;

    protected float field_1016;

    protected float field_1017;

    protected boolean field_1018 = true;

    public String texture = "/mob/char.png";

    protected boolean field_1020 = true;

    protected float field_1021 = 0.0f;

    protected String field_1022 = null;

    protected float field_1023 = 1.0f;

    protected int field_1024 = 0;

    protected float field_1025 = 0.0f;

    public boolean field_1026 = false;

    public float lastHandSwingProgress;

    public float handSwingProgress;

    public int health = 10;

    public int maxHealth = 10;

    public int field_1037;

    private int field_1028;

    public int hurtTime;

    public int field_1039;

    public float field_1040 = 0.0f;

    public int deathTime = 0;

    public int attackTime = 0;

    public float field_1043;

    public float field_1044;

    protected boolean field_1045 = false;

    public int field_1046 = -1;

    public float field_1047 = (float) (Math.random() * (double) 0.9f + (double) 0.1f);

    public float field_1048;

    public float limbDistance;

    public float field_1050;

    protected int field_1051;

    protected double field_1052;

    protected double field_1053;

    protected double field_1054;

    protected double field_1055;

    protected double field_1056;

    float field_1057 = 0.0f;

    public int field_1058 = 0;

    protected int despawnCounter = 0;

    protected float perpendicularMovement;

    protected float parallelMovement;

    protected float field_1030;

    public boolean jumping = false;

    protected float field_1032 = 0.0f;

    public float movementSpeed = 0.7f;

    public MixinEntity field_1061;

    protected int field_1034 = 0;

    public MixinItemInstance heldItem;

    private long hurtTick;

    public int timesCanJumpInAir = 0;

    public int jumpsLeft = 0;

    public boolean canWallJump = false;

    private long tickBeforeNextJump;

    public double jumpVelocity = 0.42;

    public double jumpWallMultiplier = 1.0;

    public double jumpInAirMultiplier = 1.0;

    public float airControl = 0.9259f;

    public double gravity = 0.08;

    public float fov = 140.0f;

    public float extraFov = 0.0f;

    public boolean canLookRandomly = true;

    public float randomLookVelocity = 20.0f;

    public int randomLookNext = 0;

    public int randomLookRate = 100;

    public int randomLookRateVariation = 40;

    public MixinLivingEntity(MixinLevel world) {
        super(world);
        this.field_1593 = true;
        this.field_1011 = (float) (Math.random() + 1.0) * 0.01f;
        this.setPosition(this.x, this.y, this.z);
        this.field_1010 = (float) Math.random() * 12398.0f;
        this.yaw = (float) (Math.random() * 3.1415927410125732 * 2.0);
        this.field_1641 = 0.5f;
        this.heldItem = null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void initDataTracker() {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_928(MixinEntity entity) {
        double diffAngle;
        double angleOffset = -180.0 * Math.atan2((double) (entity.x - this.x), (double) (entity.z - this.z)) / Math.PI;
        for (diffAngle = angleOffset - (double) this.yaw; diffAngle < -180.0; diffAngle += 360.0) {
        }
        while (diffAngle > 180.0) {
            diffAngle -= 360.0;
        }
        if (Math.abs((double) diffAngle) > (double) (this.fov / 2.0f + this.extraFov)) {
            return false;
        }
        return this.level.raycast(Vec3f.from(this.x, this.y + (double) this.getStandingEyeHeight(), this.z), Vec3f.from(entity.x, entity.y + (double) entity.getStandingEyeHeight(), entity.z)) == null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public String method_1314() {
        return this.texture;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean method_1356() {
        return !this.removed;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean method_1380() {
        return !this.removed;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public float getStandingEyeHeight() {
        return this.height * 0.85f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_936() {
        return 80;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void playAmbientSound() {
        String s = this.getAmbientSound();
        if (s != null) {
            this.level.playSound(this, s, this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void baseTick() {
        this.lastHandSwingProgress = this.handSwingProgress;
        super.baseTick();
        if (this.rand.nextInt(1000) < this.field_1028++) {
            this.field_1028 = -this.method_936();
            this.playAmbientSound();
        }
        if (this.isAlive() && this.isInsideWall() && !this.field_1642) {
            this.damage(null, 1);
        }
        if (this.immuneToFire || this.level.isClient) {
            this.fire = 0;
        }
        if (this.isAlive() && this.isInFluid(Material.WATER) && !this.method_934()) {
            --this.air;
            if (this.air == -20) {
                this.air = 0;
                for (int i = 0; i < 8; ++i) {
                    float f = this.rand.nextFloat() - this.rand.nextFloat();
                    float f1 = this.rand.nextFloat() - this.rand.nextFloat();
                    float f2 = this.rand.nextFloat() - this.rand.nextFloat();
                    this.level.addParticle("bubble", this.x + (double) f, this.y + (double) f1, this.z + (double) f2, this.velocityX, this.velocityY, this.velocityZ);
                }
                this.damage(null, 2);
            }
            this.fire = 0;
        } else {
            this.air = this.field_1648;
        }
        this.field_1043 = this.field_1044;
        if (this.attackTime > 0) {
            --this.attackTime;
        }
        if (this.hurtTime > 0) {
            --this.hurtTime;
        }
        if (this.field_1613 > 0) {
            --this.field_1613;
        }
        if (this.health <= 0) {
            ++this.deathTime;
            if (this.deathTime > 20) {
                this.method_923();
                this.remove();
                for (int j = 0; j < 20; ++j) {
                    double d = this.rand.nextGaussian() * 0.02;
                    double d1 = this.rand.nextGaussian() * 0.02;
                    double d2 = this.rand.nextGaussian() * 0.02;
                    this.level.addParticle("explode", this.x + (double) (this.rand.nextFloat() * this.width * 2.0f) - (double) this.width, this.y + (double) (this.rand.nextFloat() * this.height), this.z + (double) (this.rand.nextFloat() * this.width * 2.0f) - (double) this.width, d, d1, d2);
                }
            }
        }
        this.field_1017 = this.field_1016;
        this.field_1013 = this.field_1012;
        this.prevYaw = this.yaw;
        this.prevPitch = this.pitch;
        if (this.extraFov > 0.0f) {
            this.extraFov -= 5.0f;
            if (this.extraFov < 0.0f) {
                this.extraFov = 0.0f;
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onSpawnedFromSpawner() {
        for (int i = 0; i < 20; ++i) {
            double d = this.rand.nextGaussian() * 0.02;
            double d1 = this.rand.nextGaussian() * 0.02;
            double d2 = this.rand.nextGaussian() * 0.02;
            double d3 = 10.0;
            this.level.addParticle("explode", this.x + (double) (this.rand.nextFloat() * this.width * 2.0f) - (double) this.width - d * d3, this.y + (double) (this.rand.nextFloat() * this.height) - d1 * d3, this.z + (double) (this.rand.nextFloat() * this.width * 2.0f) - (double) this.width - d2 * d3, d, d1, d2);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void tickRiding() {
        super.tickRiding();
        this.field_1014 = this.field_1015;
        this.field_1015 = 0.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1311(double d, double d1, double d2, float f, float f1, int i) {
        this.standingEyeHeight = 0.0f;
        this.field_1052 = d;
        this.field_1053 = d1;
        this.field_1054 = d2;
        this.field_1055 = f;
        this.field_1056 = f1;
        this.field_1051 = i;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void tick() {
        boolean flag;
        float f5;
        float f4;
        super.tick();
        this.updateDespawnCounter();
        double d = this.x - this.prevX;
        double d1 = this.z - this.prevZ;
        float f = MathsHelper.sqrt(d * d + d1 * d1);
        float f1 = this.field_1012;
        float f2 = 0.0f;
        this.field_1014 = this.field_1015;
        float f3 = 0.0f;
        if (f > 0.05f) {
            f3 = 1.0f;
            f2 = f * 3.0f;
            f1 = (float) Math.atan2((double) d1, (double) d) * 180.0f / 3.141593f - 90.0f;
        }
        if (this.handSwingProgress > 0.0f) {
            f1 = this.yaw;
        }
        if (!this.onGround) {
            f3 = 0.0f;
        }
        this.field_1015 += (f3 - this.field_1015) * 0.3f;
        for (f4 = f1 - this.field_1012; f4 < -180.0f; f4 += 360.0f) {
        }
        while (f4 >= 180.0f) {
            f4 -= 360.0f;
        }
        this.field_1012 += f4 * 0.3f;
        for (f5 = this.yaw - this.field_1012; f5 < -180.0f; f5 += 360.0f) {
        }
        while (f5 >= 180.0f) {
            f5 -= 360.0f;
        }
        boolean bl = flag = f5 < -90.0f || f5 >= 90.0f;
        if (f5 < -75.0f) {
            f5 = -75.0f;
        }
        if (f5 >= 75.0f) {
            f5 = 75.0f;
        }
        this.field_1012 = this.yaw - f5;
        if (f5 * f5 > 2500.0f) {
            this.field_1012 += f5 * 0.2f;
        }
        if (flag) {
            f2 *= -1.0f;
        }
        while (this.yaw - this.prevYaw < -180.0f) {
            this.prevYaw -= 360.0f;
        }
        while (this.yaw - this.prevYaw >= 180.0f) {
            this.prevYaw += 360.0f;
        }
        while (this.field_1012 - this.field_1013 < -180.0f) {
            this.field_1013 -= 360.0f;
        }
        while (this.field_1012 - this.field_1013 >= 180.0f) {
            this.field_1013 += 360.0f;
        }
        while (this.pitch - this.prevPitch < -180.0f) {
            this.prevPitch -= 360.0f;
        }
        while (this.pitch - this.prevPitch >= 180.0f) {
            this.prevPitch += 360.0f;
        }
        this.field_1016 += f2;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void setSize(float width, float height) {
        super.setSize(width, height);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void addHealth(int amount) {
        if (this.health <= 0) {
            return;
        }
        this.health += amount;
        if (this.health > this.maxHealth) {
            this.health = this.maxHealth;
        }
        this.field_1613 = this.field_1009 / 2;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean damage(MixinEntity target, int amount) {
        if (this.level.isClient) {
            return false;
        }
        this.despawnCounter = 0;
        if (this.health <= 0) {
            return false;
        }
        this.extraFov = 180.0f;
        this.limbDistance = 1.5f;
        boolean flag = true;
        if ((float) this.field_1613 > (float) this.field_1009 / 2.0f && this.hurtTime > 0) {
            if (amount <= this.field_1058) {
                return false;
            }
            this.applyDamage(amount - this.field_1058);
            this.field_1058 = amount;
            flag = false;
        } else {
            this.field_1058 = amount;
            this.field_1037 = this.health;
            this.field_1613 = this.field_1009;
            this.applyDamage(amount);
            this.field_1039 = 10;
            this.hurtTime = 10;
            this.hurtTick = this.level.getLevelTime();
        }
        this.field_1040 = 0.0f;
        if (flag) {
            this.level.method_185(this, (byte) 2);
            this.method_1336();
            if (target != null) {
                double d = target.x - this.x;
                double d1 = target.z - this.z;
                while (d * d + d1 * d1 < 1.0E-4) {
                    d = (Math.random() - Math.random()) * 0.01;
                    d1 = (Math.random() - Math.random()) * 0.01;
                }
                this.field_1040 = (float) (Math.atan2((double) d1, (double) d) * 180.0 / 3.1415927410125732) - this.yaw;
                this.method_925(target, amount, d, d1);
            } else {
                this.field_1040 = (int) (Math.random() * 2.0) * 180;
            }
        }
        if (this.health <= 0) {
            if (flag) {
                this.level.playSound(this, this.getDeathSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
            this.onKilledBy(target);
        } else if (flag) {
            this.level.playSound(this, this.getHurtSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        }
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean attackEntityFromMulti(MixinEntity entity, int i) {
        if (this.level.isClient) {
            return false;
        }
        this.despawnCounter = 0;
        if (this.health <= 0) {
            return false;
        }
        this.extraFov = 180.0f;
        this.limbDistance = 1.5f;
        boolean flag = true;
        if ((float) this.field_1613 > (float) this.field_1009 / 2.0f && this.hurtTick != this.level.getLevelTime()) {
            if (i <= this.field_1058) {
                return false;
            }
            this.applyDamage(i - this.field_1058);
            this.field_1058 = i;
            flag = false;
        } else {
            this.field_1058 = i;
            this.field_1037 = this.health;
            this.field_1613 = this.field_1009;
            this.applyDamage(i);
            this.field_1039 = 10;
            this.hurtTime = 10;
            this.hurtTick = this.level.getLevelTime();
        }
        this.field_1040 = 0.0f;
        if (flag) {
            this.level.method_185(this, (byte) 2);
            this.method_1336();
            if (entity != null) {
                double d = entity.x - this.x;
                double d1 = entity.z - this.z;
                while (d * d + d1 * d1 < 1.0E-4) {
                    d = (Math.random() - Math.random()) * 0.01;
                    d1 = (Math.random() - Math.random()) * 0.01;
                }
                this.field_1040 = (float) (Math.atan2((double) d1, (double) d) * 180.0 / 3.1415927410125732) - this.yaw;
                this.method_925(entity, i, d, d1);
            } else {
                this.field_1040 = (int) (Math.random() * 2.0) * 180;
            }
        }
        if (this.health <= 0) {
            if (flag) {
                this.level.playSound(this, this.getDeathSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
            this.onKilledBy(entity);
        } else if (flag) {
            this.level.playSound(this, this.getHurtSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        }
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_1312() {
        this.field_1039 = 10;
        this.hurtTime = 10;
        this.field_1040 = 0.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void applyDamage(int i) {
        this.health -= i;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected float getSoundVolume() {
        return 1.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected String getAmbientSound() {
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected String getHurtSound() {
        return "random.hurt";
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected String getDeathSound() {
        return "random.hurt";
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_925(MixinEntity entity, int i, double d, double d1) {
        float f = MathsHelper.sqrt(d * d + d1 * d1);
        float f1 = 0.4f;
        this.velocityX /= 2.0;
        this.velocityY /= 2.0;
        this.velocityZ /= 2.0;
        this.velocityX -= d / (double) f * (double) f1;
        this.velocityY += (double) 0.4f;
        this.velocityZ -= d1 / (double) f * (double) f1;
        if (this.velocityY > (double) 0.4f) {
            this.velocityY = 0.4f;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void onKilledBy(MixinEntity entity) {
        if (this.field_1024 >= 0 && entity != null) {
            entity.onKilledOther(this, this.field_1024);
        }
        if (entity != null) {
            entity.handleKilledEntity(this);
        }
        this.field_1045 = true;
        if (!this.level.isClient) {
            this.dropLoot();
            if (entity != null && entity instanceof MixinLivingEntity && ((MixinLivingEntity) entity).health < ((MixinLivingEntity) entity).maxHealth && this.rand.nextInt(3) != 0) {
                MixinItemEntity heart = new MixinItemEntity(this.level, this.x, this.y, this.z, new MixinItemInstance(Items.heart.id, 1, 0));
                this.level.spawnEntity(heart);
            }
        }
        this.level.method_185(this, (byte) 3);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void dropLoot() {
        int i = this.getMobDrops();
        if (i > 0) {
            int j = this.rand.nextInt(3);
            for (int k = 0; k < j; ++k) {
                this.dropItem(i, 1);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected int getMobDrops() {
        return 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void handleFallDamage(float f) {
        float pre;
        int i;
        if (!this.handleFlying() && (i = (int) Math.ceil((double) Math.pow((double) (pre = Math.max((float) (f - 0.8f), (float) 0.0f) / 0.08f), (double) 1.5))) > 0) {
            this.damage(null, i);
            int j = this.level.getTileId(MathsHelper.floor(this.x), MathsHelper.floor(this.y - (double) 0.2f - (double) this.standingEyeHeight), MathsHelper.floor(this.z));
            if (j > 0) {
                TileSounds stepsound = Tile.BY_ID[j].sounds;
                this.level.playSound(this, stepsound.getWalkSound(), stepsound.getVolume() * 0.5f, stepsound.getPitch() * 0.75f);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void travel(float f, float f1) {
        if (this.handleFlying()) {
            double l = Math.sqrt((double) (f * f + f1 * f1));
            double ySpeed = (double) (-0.1f * f1) * Math.sin((double) (this.pitch * 3.141593f / 180.0f));
            if (l < 1.0) {
                ySpeed *= l;
            }
            this.velocityY += ySpeed;
            float speed = (float) ((double) 0.1f * (Math.abs((double) ((double) f1 * Math.cos((double) (this.pitch * 3.141593f / 180.0f)))) + (double) Math.abs((float) f)));
            this.movementInputToVelocity(f, f1, speed);
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.fallDistance = 0.0f;
            this.velocityX *= 0.8;
            this.velocityY *= 0.8;
            this.velocityZ *= 0.8;
            if (Math.abs((double) this.velocityX) < 0.01) {
                this.velocityX = 0.0;
            }
            if (Math.abs((double) this.velocityY) < 0.01) {
                this.velocityY = 0.0;
            }
            if (Math.abs((double) this.velocityZ) < 0.01) {
                this.velocityZ = 0.0;
            }
        } else if (this.method_1334()) {
            if (this.velocityY < -0.4) {
                this.velocityY *= 0.8;
            }
            double d = this.y;
            this.movementInputToVelocity(f, f1, 0.02f);
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.velocityX *= (double) 0.8f;
            this.velocityY *= (double) 0.8f;
            this.velocityZ *= (double) 0.8f;
            this.velocityY -= 0.25 * this.getGravity();
            if (this.field_1624 && this.method_1344(this.velocityX, this.velocityY + (double) 0.6f - this.y + d, this.velocityZ)) {
                this.velocityY = 0.3f;
            }
        } else if (this.method_1335()) {
            if (this.velocityY < -0.4) {
                this.velocityY *= 0.5;
            }
            double d1 = this.y;
            this.movementInputToVelocity(f, f1, 0.02f);
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.velocityX *= 0.5;
            this.velocityY *= 0.5;
            this.velocityZ *= 0.5;
            this.velocityY -= 0.25 * this.getGravity();
            if (this.field_1624 && this.method_1344(this.velocityX, this.velocityY + (double) 0.6f - this.y + d1, this.velocityZ)) {
                this.velocityY = 0.3f;
            }
        } else {
            float f2 = 0.91f;
            if (this.onGround) {
                f2 = 0.5460001f;
                int i = this.level.getTileId(MathsHelper.floor(this.x), MathsHelper.floor(this.boundingBox.minY) - 1, MathsHelper.floor(this.z));
                if (i > 0) {
                    f2 = Tile.BY_ID[i].field_1901 * 0.91f;
                }
            }
            float f3 = 0.1627714f / (f2 * f2 * f2);
            this.movementInputToVelocity(f, f1, this.onGround ? 0.1f * f3 : 0.1f * this.airControl * f3);
            f2 = 0.91f;
            if (this.onGround) {
                f2 = 0.5460001f;
                int j = this.level.getTileId(MathsHelper.floor(this.x), MathsHelper.floor(this.boundingBox.minY) - 1, MathsHelper.floor(this.z));
                if (j > 0) {
                    f2 = Tile.BY_ID[j].field_1901 * 0.91f;
                }
            }
            if (this.isOnLadder()) {
                float f4 = 0.15f;
                if (this.velocityX < (double) (-f4)) {
                    this.velocityX = -f4;
                }
                if (this.velocityX > (double) f4) {
                    this.velocityX = f4;
                }
                if (this.velocityZ < (double) (-f4)) {
                    this.velocityZ = -f4;
                }
                if (this.velocityZ > (double) f4) {
                    this.velocityZ = f4;
                }
                this.fallDistance = 0.0f;
                if (this.velocityY < -0.15) {
                    this.velocityY = -0.15;
                }
                if (this.method_1373() && this.velocityY < 0.0) {
                    this.velocityY = 0.0;
                }
            }
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            if ((this.field_1624 || this.jumping) && this.isOnLadder()) {
                this.velocityY = 0.2;
            }
            this.velocityY -= this.getGravity();
            this.velocityY *= (double) 0.98f;
            this.velocityX *= (double) f2;
            this.velocityZ *= (double) f2;
        }
        this.field_1048 = this.limbDistance;
        double d2 = this.x - this.prevX;
        double d3 = this.z - this.prevZ;
        float f5 = MathsHelper.sqrt(d2 * d2 + d3 * d3) * 4.0f;
        if (f5 > 1.0f) {
            f5 = 1.0f;
        }
        this.limbDistance += (f5 - this.limbDistance) * 0.4f;
        this.field_1050 += this.limbDistance;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isOnLadder() {
        int i = MathsHelper.floor(this.x);
        int j = MathsHelper.floor(this.boundingBox.minY);
        int k = MathsHelper.floor(this.z);
        int blockID = this.level.getTileId(i, j, k);
        int blockIDAbove = this.level.getTileId(i, j + 1, k);
        boolean v = this.level.getTileMeta(i, j, k) % 3 == 0;
        boolean vAbove = this.level.getTileMeta(i, j + 1, k) % 3 == 0;
        return LadderTile.isLadderID(blockID) || LadderTile.isLadderID(blockIDAbove) || blockID == Blocks.ropes1.id && v || blockIDAbove == Blocks.ropes1.id && vAbove || blockID == Blocks.ropes2.id && v || blockIDAbove == Blocks.ropes2.id && vAbove || blockID == Blocks.chains.id && v || blockIDAbove == Blocks.chains.id && vAbove;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void writeCustomDataToTag(MixinCompoundTag tag) {
        tag.put("Health", (short) this.health);
        tag.put("MaxHealth", (short) this.maxHealth);
        tag.put("HurtTime", (short) this.hurtTime);
        tag.put("DeathTime", (short) this.deathTime);
        tag.put("AttackTime", (short) this.attackTime);
        tag.put("EntityID", this.id);
        tag.put("timesCanJumpInAir", this.timesCanJumpInAir);
        tag.put("canWallJump", this.canWallJump);
        tag.put("fov", this.fov);
        tag.put("canLookRandomly", this.canLookRandomly);
        tag.put("randomLookVelocity", this.randomLookVelocity);
        tag.put("randomLookRate", this.randomLookRate);
        tag.put("randomLookRateVariation", this.randomLookRateVariation);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void readCustomDataFromTag(MixinCompoundTag tag) {
        this.health = tag.getShort("Health");
        if (!tag.containsKey("Health")) {
            this.health = 10;
        }
        this.maxHealth = !tag.containsKey("MaxHealth") ? 10 : (int) tag.getShort("MaxHealth");
        this.hurtTime = tag.getShort("HurtTime");
        this.deathTime = tag.getShort("DeathTime");
        this.attackTime = tag.getShort("AttackTime");
        if (tag.containsKey("EntityID") && !(this instanceof MixinPlayer)) {
            this.id = tag.getInt("EntityID");
        }
        this.timesCanJumpInAir = tag.getInt("timesCanJumpInAir");
        this.canWallJump = tag.getBoolean("canWallJump");
        if (tag.containsKey("fov")) {
            this.fov = tag.getFloat("fov");
        }
        if (tag.containsKey("canLookRandomly")) {
            this.canLookRandomly = tag.getBoolean("canLookRandomly");
        }
        if (tag.containsKey("randomLookVelocity")) {
            this.randomLookVelocity = tag.getFloat("randomLookVelocity");
        }
        if (tag.containsKey("randomLookRate")) {
            this.randomLookRate = tag.getInt("randomLookRate");
        }
        if (tag.containsKey("randomLookRateVariation")) {
            this.randomLookRateVariation = tag.getInt("randomLookRateVariation");
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean isAlive() {
        return !this.removed && this.health > 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_934() {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void updateDespawnCounter() {
        if (this.field_1051 > 0) {
            double d3;
            double d = this.x + (this.field_1052 - this.x) / (double) this.field_1051;
            double d1 = this.y + (this.field_1053 - this.y) / (double) this.field_1051;
            double d2 = this.z + (this.field_1054 - this.z) / (double) this.field_1051;
            for (d3 = this.field_1055 - (double) this.yaw; d3 < -180.0; d3 += 360.0) {
            }
            while (d3 >= 180.0) {
                d3 -= 360.0;
            }
            this.yaw = (float) ((double) this.yaw + d3 / (double) this.field_1051);
            this.pitch = (float) ((double) this.pitch + (this.field_1056 - (double) this.pitch) / (double) this.field_1051);
            --this.field_1051;
            this.setPosition(d, d1, d2);
            this.setRotation(this.yaw, this.pitch);
            List list1 = this.level.method_190(this, this.boundingBox.method_104(0.03125, 0.0, 0.03125));
            if (list1.size() > 0) {
                double d4 = 0.0;
                for (int j = 0; j < list1.size(); ++j) {
                    Box axisalignedbb = (Box) list1.get(j);
                    if (!(axisalignedbb.maxY > d4))
                        continue;
                    d4 = axisalignedbb.maxY;
                }
                this.setPosition(d, d1 += d4 - this.boundingBox.minY, d2);
            }
        }
        if (this.cannotMove()) {
            this.jumping = false;
            this.perpendicularMovement = 0.0f;
            this.parallelMovement = 0.0f;
            this.field_1030 = 0.0f;
        } else if (!this.field_1026) {
            this.tickHandSwing();
        }
        boolean flag = this.method_1334();
        boolean flag1 = this.method_1335();
        if (this.onGround) {
            this.jumpsLeft = this.timesCanJumpInAir;
        }
        if (this.moveYawOffset != 0.0f) {
            if (this.moveYawOffset > 40.0f) {
                this.moveYawOffset -= 40.0f;
                this.yaw += 40.0f;
            } else if (this.moveYawOffset < -40.0f) {
                this.moveYawOffset += 40.0f;
                this.yaw -= 40.0f;
            } else {
                this.yaw += this.moveYawOffset;
                this.moveYawOffset = 0.0f;
            }
        }
        if (this.jumping) {
            if (flag) {
                this.velocityY += (double) 0.04f;
            } else if (flag1) {
                this.velocityY += (double) 0.04f;
            } else if (this.onGround) {
                this.jump();
            } else if (this.level.getLevelTime() >= this.tickBeforeNextJump) {
                if (this.canWallJump && (this.collisionX != 0 || this.collisionZ != 0)) {
                    this.jump();
                    this.velocityY *= this.jumpWallMultiplier;
                    this.velocityX += (double) (-this.collisionX) * 0.325;
                    this.velocityZ += (double) (-this.collisionZ) * 0.325;
                    this.moveYawOffset = (float) (180.0 * Math.atan2((double) (-this.velocityX), (double) this.velocityZ) / Math.PI) - this.yaw;
                    while ((double) this.moveYawOffset >= 180.0) {
                        this.moveYawOffset = (float) ((double) this.moveYawOffset - 360.0);
                    }
                    while ((double) this.moveYawOffset < -180.0) {
                        this.moveYawOffset = (float) ((double) this.moveYawOffset + 360.0);
                    }
                    for (int i = 0; i < 10; ++i) {
                        this.level.addParticle("reddust", this.x + (double) (this.rand.nextFloat() * this.width * 2.0f) - (double) this.width, this.y - (double) 0.2f, this.z + (double) (this.rand.nextFloat() * this.width * 2.0f) - (double) this.width, 2.5, 2.5, 2.5);
                    }
                } else if (this.jumpsLeft > 0) {
                    --this.jumpsLeft;
                    this.jump();
                    this.velocityY *= this.jumpInAirMultiplier;
                    for (int i = 0; i < 10; ++i) {
                        this.level.addParticle("reddust", this.x + (double) (this.rand.nextFloat() * this.width * 2.0f) - (double) this.width, this.y - (double) 0.2f, this.z + (double) (this.rand.nextFloat() * this.width * 2.0f) - (double) this.width, 2.5, 2.5, 2.5);
                    }
                }
            }
        }
        this.perpendicularMovement *= 0.98f;
        this.parallelMovement *= 0.98f;
        this.field_1030 *= 0.9f;
        this.travel(this.perpendicularMovement, this.parallelMovement);
        List list = this.level.getEntities(this, this.boundingBox.expand(0.2f, 0.0, 0.2f));
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); ++i) {
                MixinEntity entity = (MixinEntity) list.get(i);
                if (!entity.method_1380())
                    continue;
                entity.method_1353(this);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected boolean cannotMove() {
        return this.health <= 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void jump() {
        this.tickBeforeNextJump = this.level.getLevelTime() + 5L;
        this.velocityY = this.jumpVelocity;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected boolean method_940() {
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void method_920() {
        MixinPlayer entityplayer = this.level.getClosestPlayerTo(this, -1.0);
        if (this.method_940() && entityplayer != null) {
            double d = entityplayer.x - this.x;
            double d1 = entityplayer.y - this.y;
            double d2 = entityplayer.z - this.z;
            double d3 = d * d + d1 * d1 + d2 * d2;
            if (d3 > 16384.0) {
                this.remove();
            }
            if (this.despawnCounter > 600 && this.rand.nextInt(800) == 0) {
                if (d3 < 1024.0) {
                    this.despawnCounter = 0;
                } else {
                    this.remove();
                }
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void tickHandSwing() {
        MixinPlayer entityplayer1;
        ++this.despawnCounter;
        MixinPlayer entityplayer = this.level.getClosestPlayerTo(this, -1.0);
        this.method_920();
        this.perpendicularMovement = 0.0f;
        this.parallelMovement = 0.0f;
        float f = 8.0f;
        if (this.rand.nextFloat() < 0.02f && (entityplayer1 = this.level.getClosestPlayerTo(this, f)) != null && this.method_928(entityplayer1)) {
            this.field_1061 = entityplayer1;
            this.field_1034 = 10 + this.rand.nextInt(20);
        }
        if (this.field_1061 != null) {
            this.method_924(this.field_1061, 10.0f, this.getLookPitchSpeed());
            if (this.field_1034-- <= 0 || this.field_1061.removed || this.field_1061.method_1352(this) > (double) (f * f)) {
                this.field_1061 = null;
            }
        } else if (this.canLookRandomly) {
            if (this.randomLookNext-- <= 0) {
                float r = this.rand.nextFloat();
                this.field_1030 = (double) r < 0.5 ? -this.randomLookVelocity * (r + 0.5f) : this.randomLookVelocity * r;
                this.randomLookNext = this.randomLookRate + this.rand.nextInt(this.randomLookRateVariation);
            }
            this.yaw += this.field_1030;
            this.pitch = this.field_1032;
            this.field_1030 *= 0.95f;
            if (Math.abs((float) this.field_1030) < 1.0f) {
                this.field_1030 = 0.0f;
            }
        }
        boolean flag = this.method_1334();
        boolean flag1 = this.method_1335();
        if (flag || flag1) {
            this.jumping = this.rand.nextFloat() < 0.8f;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected int getLookPitchSpeed() {
        return 40;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_924(MixinEntity entity, float f, float f1) {
        double d1;
        double d = entity.x - this.x;
        double d2 = entity.z - this.z;
        if (entity instanceof MixinLivingEntity) {
            MixinLivingEntity entityliving = (MixinLivingEntity) entity;
            d1 = this.y + (double) this.getStandingEyeHeight() - (entityliving.y + (double) entityliving.getStandingEyeHeight());
        } else {
            d1 = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0 - (this.y + (double) this.getStandingEyeHeight());
        }
        double d3 = MathsHelper.sqrt(d * d + d2 * d2);
        float f2 = (float) (Math.atan2((double) d2, (double) d) * 180.0 / 3.1415927410125732) - 90.0f;
        float f3 = (float) (-(Math.atan2((double) d1, (double) d3) * 180.0 / 3.1415927410125732));
        this.pitch = -this.method_927(this.pitch, f3, f1);
        this.yaw = this.method_927(this.yaw, f2, f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean method_921() {
        return this.field_1061 != null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinEntity method_922() {
        return this.field_1061;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private float method_927(float f, float f1, float f2) {
        float f3;
        for (f3 = f1 - f; f3 < -180.0f; f3 += 360.0f) {
        }
        while (f3 >= 180.0f) {
            f3 -= 360.0f;
        }
        if (f3 > f2) {
            f3 = f2;
        }
        if (f3 < -f2) {
            f3 = -f2;
        }
        return f + f3;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_923() {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean canSpawn() {
        return this.level.canSpawnEntity(this.boundingBox) && this.level.method_190(this, this.boundingBox).size() == 0 && !this.level.method_218(this.boundingBox);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void destroy() {
        this.damage(null, 4);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float method_930(float f) {
        float f1 = this.handSwingProgress - this.lastHandSwingProgress;
        if (f1 < 0.0f) {
            f1 += 1.0f;
        }
        return this.lastHandSwingProgress + f1 * f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Vec3f method_931(float f) {
        if (f == 1.0f) {
            return Vec3f.from(this.x, this.y, this.z);
        }
        double d = this.prevX + (this.x - this.prevX) * (double) f;
        double d1 = this.prevY + (this.y - this.prevY) * (double) f;
        double d2 = this.prevZ + (this.z - this.prevZ) * (double) f;
        return Vec3f.from(d, d1, d2);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public Vec3f method_1320() {
        return this.method_926(1.0f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Vec3f method_926(float f) {
        if (f == 1.0f) {
            float f1 = MathsHelper.cos(-this.yaw * 0.01745329f - 3.141593f);
            float f3 = MathsHelper.sin(-this.yaw * 0.01745329f - 3.141593f);
            float f5 = -MathsHelper.cos(-this.pitch * 0.01745329f);
            float f7 = MathsHelper.sin(-this.pitch * 0.01745329f);
            return Vec3f.from(f3 * f5, f7, f1 * f5);
        }
        float f2 = this.prevPitch + (this.pitch - this.prevPitch) * f;
        float f4 = this.prevYaw + (this.yaw - this.prevYaw) * f;
        float f6 = MathsHelper.cos(-f4 * 0.01745329f - 3.141593f);
        float f8 = MathsHelper.sin(-f4 * 0.01745329f - 3.141593f);
        float f9 = -MathsHelper.cos(-f2 * 0.01745329f);
        float f10 = MathsHelper.sin(-f2 * 0.01745329f);
        return Vec3f.from(f8 * f9, f10, f6 * f9);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public HitResult method_929(double d, float f) {
        Vec3f vec3d = this.method_931(f);
        Vec3f vec3d1 = this.method_926(f);
        Vec3f vec3d2 = vec3d.method_1301(vec3d1.x * d, vec3d1.y * d, vec3d1.z * d);
        return this.level.raycast(vec3d, vec3d2);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getLimitPerChunk() {
        return 4;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemInstance method_909() {
        return this.heldItem;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void handleStatus(byte byte0) {
        if (byte0 == 2) {
            this.limbDistance = 1.5f;
            this.field_1613 = this.field_1009;
            this.field_1039 = 10;
            this.hurtTime = 10;
            this.field_1040 = 0.0f;
            this.level.playSound(this, this.getHurtSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            this.damage(null, 0);
        } else if (byte0 == 3) {
            this.level.playSound(this, this.getDeathSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            this.health = 0;
            this.onKilledBy(null);
        } else {
            super.handleStatus(byte0);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isSleeping() {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getItemTexturePosition(MixinItemInstance itemstack) {
        return itemstack.getTexturePosition();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean protectedByShield() {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean protectedByShield(double x, double y, double z) {
        float diff;
        if (!this.protectedByShield() || this.method_930(1.0f) > 0.0f) {
            return false;
        }
        double diffX = this.x - x;
        double diffZ = this.z - z;
        float angle = -57.29578f * (float) Math.atan2((double) diffX, (double) diffZ) + 180.0f;
        for (diff = Math.abs((float) (angle - this.yaw)); diff > 180.0f; diff -= 360.0f) {
        }
        while (diff < -180.0f) {
            diff += 360.0f;
        }
        return diff < 50.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public double getGravity() {
        return this.gravity;
    }
}
