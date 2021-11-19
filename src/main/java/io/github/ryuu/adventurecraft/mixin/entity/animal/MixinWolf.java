/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.List
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.mixin.entity.animal;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_61;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.animal.Animal;
import net.minecraft.entity.animal.Sheep;
import net.minecraft.entity.animal.Wolf;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.item.food.FoodItem;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import io.github.ryuu.adventurecraft.mixin.item.MixinPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.mixin.item.MixinWolf;
import io.github.ryuu.adventurecraft.mixin.item.MixinLevel;
import io.github.ryuu.adventurecraft.mixin.item.MixinLivingEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinCompoundTag;
import io.github.ryuu.adventurecraft.mixin.item.MixinEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinArrow;
import io.github.ryuu.adventurecraft.mixin.item.MixinItemInstance;
import io.github.ryuu.adventurecraft.mixin.item.MixinClass_61;

@Mixin(Wolf.class)
public class MixinWolf extends Animal {

    @Shadow()
    private boolean begging = false;

    private float begAnimationProgress;

    private float lastBegAnimationProgress;

    private boolean wet;

    private boolean canShakeWaterOff;

    private float shakeProgress;

    private float lastShakeProgress;

    public int attackStrength;

    public MixinWolf(MixinLevel world) {
        super(world);
        this.texture = "/mob/wolf.png";
        this.setSize(0.8f, 0.8f);
        this.movementSpeed = 1.1f;
        this.health = 8;
        this.attackStrength = -1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(16, (byte) 0);
        this.dataTracker.startTracking(17, "");
        this.dataTracker.startTracking(18, new Integer(this.health));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected boolean canClimb() {
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public String method_1314() {
        if (this.isTamed()) {
            return "/mob/wolf_tame.png";
        }
        if (this.isAngry()) {
            return "/mob/wolf_angry.png";
        }
        return super.method_1314();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void writeCustomDataToTag(MixinCompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.put("Angry", this.isAngry());
        tag.put("Sitting", this.isSitting());
        if (this.getOwner() == null) {
            tag.put("Owner", "");
        } else {
            tag.put("Owner", this.getOwner());
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void readCustomDataFromTag(MixinCompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.setAngry(tag.getBoolean("Angry"));
        this.setSitting(tag.getBoolean("Sitting"));
        String s = tag.getString("Owner");
        if (s.length() > 0) {
            this.setOwner(s);
            this.setHasOwner(true);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected boolean method_940() {
        return !this.isTamed();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected String getAmbientSound() {
        if (this.isAngry()) {
            return "mob.wolf.growl";
        }
        if (this.rand.nextInt(3) == 0) {
            if (this.isTamed() && this.dataTracker.getInt(18) < 10) {
                return "mob.wolf.whine";
            }
            return "mob.wolf.panting";
        }
        return "mob.wolf.bark";
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected String getHurtSound() {
        return "mob.wolf.hurt";
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected String getDeathSound() {
        return "mob.wolf.death";
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected float getSoundVolume() {
        return 0.4f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected int getMobDrops() {
        return -1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void tickHandSwing() {
        List list;
        super.tickHandSwing();
        if (!this.field_663 && !this.method_633() && this.isTamed() && this.vehicle == null) {
            MixinPlayer entityplayer = this.level.getPlayerByName(this.getOwner());
            if (entityplayer != null) {
                float f = entityplayer.distanceTo(this);
                if (f > 5.0f) {
                    this.method_429(entityplayer, f);
                }
            } else if (!this.method_1334()) {
                this.setSitting(true);
            }
        } else if (!(this.entity != null || this.method_633() || this.isTamed() || this.level.rand.nextInt(100) != 0 || (list = this.level.getEntities(Sheep.class, Box.getOrCreate(this.x, this.y, this.z, this.x + 1.0, this.y + 1.0, this.z + 1.0).expand(16.0, 4.0, 16.0))).isEmpty())) {
            this.method_636((MixinEntity) list.get(this.level.rand.nextInt(list.size())));
        }
        if (this.method_1334()) {
            this.setSitting(false);
        }
        if (!this.level.isClient) {
            this.dataTracker.setData(18, this.health);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void updateDespawnCounter() {
        MixinEntity entity;
        super.updateDespawnCounter();
        this.begging = false;
        if (this.method_921() && !this.method_633() && !this.isAngry() && (entity = this.method_922()) instanceof MixinPlayer) {
            MixinPlayer entityplayer = (MixinPlayer) entity;
            MixinItemInstance itemstack = entityplayer.inventory.getHeldItem();
            if (itemstack != null) {
                if (!this.isTamed() && itemstack.itemId == ItemType.bone.id) {
                    this.begging = true;
                } else if (this.isTamed() && ItemType.byId[itemstack.itemId] instanceof FoodItem) {
                    this.begging = ((FoodItem) ItemType.byId[itemstack.itemId]).canWolvesEat();
                }
            }
        }
        if (!this.field_1026 && this.wet && !this.canShakeWaterOff && !this.method_633() && this.onGround) {
            this.canShakeWaterOff = true;
            this.shakeProgress = 0.0f;
            this.lastShakeProgress = 0.0f;
            this.level.method_185(this, (byte) 8);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void tick() {
        super.tick();
        this.lastBegAnimationProgress = this.begAnimationProgress;
        this.begAnimationProgress = this.begging ? (this.begAnimationProgress += (1.0f - this.begAnimationProgress) * 0.4f) : (this.begAnimationProgress += (0.0f - this.begAnimationProgress) * 0.4f);
        if (this.begging) {
            this.field_1034 = 10;
        }
        if (this.isTouchingWater()) {
            this.wet = true;
            this.canShakeWaterOff = false;
            this.shakeProgress = 0.0f;
            this.lastShakeProgress = 0.0f;
        } else if ((this.wet || this.canShakeWaterOff) && this.canShakeWaterOff) {
            if (this.shakeProgress == 0.0f) {
                this.level.playSound(this, "mob.wolf.shake", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
            this.lastShakeProgress = this.shakeProgress;
            this.shakeProgress += 0.05f;
            if (this.lastShakeProgress >= 2.0f) {
                this.wet = false;
                this.canShakeWaterOff = false;
                this.lastShakeProgress = 0.0f;
                this.shakeProgress = 0.0f;
            }
            if (this.shakeProgress > 0.4f) {
                float f = (float) this.boundingBox.minY;
                int i = (int) (MathsHelper.sin((this.shakeProgress - 0.4f) * 3.141593f) * 7.0f);
                for (int j = 0; j < i; ++j) {
                    float f1 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width * 0.5f;
                    float f2 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width * 0.5f;
                    this.level.addParticle("splash", this.x + (double) f1, f + 0.8f, this.z + (double) f2, this.velocityX, this.velocityY, this.velocityZ);
                }
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isWet() {
        return this.wet;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float getWetBrightnessMultiplier(float f) {
        return 0.75f + (this.lastShakeProgress + (this.shakeProgress - this.lastShakeProgress) * f) / 2.0f * 0.25f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float getShakeAnimationProgress(float f, float f1) {
        float f2 = (this.lastShakeProgress + (this.shakeProgress - this.lastShakeProgress) * f + f1) / 1.8f;
        if (f2 < 0.0f) {
            f2 = 0.0f;
        } else if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        return MathsHelper.sin(f2 * 3.141593f) * MathsHelper.sin(f2 * 3.141593f * 11.0f) * 0.15f * 3.141593f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float getBegAnimationProgress(float f) {
        return (this.lastBegAnimationProgress + (this.begAnimationProgress - this.lastBegAnimationProgress) * f) * 0.15f * 3.141593f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public float getStandingEyeHeight() {
        return this.height * 0.8f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected int getLookPitchSpeed() {
        if (this.isSitting()) {
            return 20;
        }
        return super.getLookPitchSpeed();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_429(MixinEntity entity, float f) {
        MixinClass_61 pathentity = this.level.method_192(this, entity, 16.0f);
        if (pathentity == null && f > 12.0f) {
            int i = MathsHelper.floor(entity.x) - 2;
            int j = MathsHelper.floor(entity.z) - 2;
            int k = MathsHelper.floor(entity.boundingBox.minY);
            for (int l = 0; l <= 4; ++l) {
                for (int i1 = 0; i1 <= 4; ++i1) {
                    if (l >= 1 && i1 >= 1 && l <= 3 && i1 <= 3 || !this.level.canSuffocate(i + l, k - 1, j + i1) || this.level.canSuffocate(i + l, k, j + i1) || this.level.canSuffocate(i + l, k + 1, j + i1))
                        continue;
                    this.setPositionAndAngles((float) (i + l) + 0.5f, k, (float) (j + i1) + 0.5f, this.yaw, this.pitch);
                    return;
                }
            }
        } else {
            this.setTarget(pathentity);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected boolean method_640() {
        return this.isSitting() || this.canShakeWaterOff;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean damage(MixinEntity target, int amount) {
        this.setSitting(false);
        if (target != null && !(target instanceof MixinPlayer) && !(target instanceof MixinArrow)) {
            amount = (amount + 1) / 2;
        }
        if (super.damage(target, amount)) {
            if (!this.isTamed() && !this.isAngry()) {
                if (target instanceof MixinPlayer) {
                    this.setAngry(true);
                    this.entity = target;
                }
                if (target instanceof MixinArrow && ((MixinArrow) target).field_1576 != null) {
                    target = ((MixinArrow) target).field_1576;
                }
                if (target instanceof MixinLivingEntity) {
                    List list = this.level.getEntities(MixinWolf.class, Box.getOrCreate(this.x, this.y, this.z, this.x + 1.0, this.y + 1.0, this.z + 1.0).expand(16.0, 4.0, 16.0));
                    for (MixinEntity entity1 : list) {
                        MixinWolf entitywolf = (MixinWolf) entity1;
                        if (entitywolf.isTamed() || entitywolf.entity != null)
                            continue;
                        entitywolf.entity = target;
                        if (!(target instanceof MixinPlayer))
                            continue;
                        entitywolf.setAngry(true);
                    }
                }
            } else if (target != this && target != null) {
                if (this.isTamed() && target instanceof MixinPlayer && ((MixinPlayer) target).name.equalsIgnoreCase(this.getOwner())) {
                    return true;
                }
                this.entity = target;
            }
            return true;
        }
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected MixinEntity method_638() {
        if (this.isAngry()) {
            return this.level.getClosestPlayerTo(this, 16.0);
        }
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void method_637(MixinEntity entity, float f) {
        if (f > 2.0f && f < 6.0f && this.rand.nextInt(10) == 0) {
            if (this.onGround) {
                double d = entity.x - this.x;
                double d1 = entity.z - this.z;
                float f1 = MathsHelper.sqrt(d * d + d1 * d1);
                this.velocityX = d / (double) f1 * 0.5 * (double) 0.8f + this.velocityX * (double) 0.2f;
                this.velocityZ = d1 / (double) f1 * 0.5 * (double) 0.8f + this.velocityZ * (double) 0.2f;
                this.velocityY = 0.4f;
            }
        } else if ((double) f < 1.5 && entity.boundingBox.maxY > this.boundingBox.minY && entity.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 20;
            int byte0 = 2;
            if (this.isTamed()) {
                byte0 = 4;
            }
            if (this.attackStrength != -1) {
                byte0 = this.attackStrength;
            }
            entity.damage(this, byte0);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean interact(MixinPlayer entityplayer) {
        MixinItemInstance itemstack = entityplayer.inventory.getHeldItem();
        if (!this.isTamed()) {
            if (itemstack != null && itemstack.itemId == ItemType.bone.id && !this.isAngry()) {
                --itemstack.count;
                if (itemstack.count <= 0) {
                    entityplayer.inventory.setInvItem(entityplayer.inventory.selectedHotbarSlot, null);
                }
                if (!this.level.isClient) {
                    if (this.rand.nextInt(3) == 0) {
                        this.setHasOwner(true);
                        this.setTarget(null);
                        this.setSitting(true);
                        this.health = 20;
                        this.setOwner(entityplayer.name);
                        this.spawnBoneParticles(true);
                        this.level.method_185(this, (byte) 7);
                    } else {
                        this.spawnBoneParticles(false);
                        this.level.method_185(this, (byte) 6);
                    }
                }
                return true;
            }
        } else {
            FoodItem itemfood;
            if (itemstack != null && ItemType.byId[itemstack.itemId] instanceof FoodItem && (itemfood = (FoodItem) ItemType.byId[itemstack.itemId]).canWolvesEat() && this.dataTracker.getInt(18) < 20) {
                --itemstack.count;
                if (itemstack.count <= 0) {
                    entityplayer.inventory.setInvItem(entityplayer.inventory.selectedHotbarSlot, null);
                }
                this.addHealth(((FoodItem) ItemType.porkchopRaw).getHealAmount());
                return true;
            }
            if (entityplayer.name.equalsIgnoreCase(this.getOwner())) {
                if (!this.level.isClient) {
                    this.setSitting(!this.isSitting());
                    this.jumping = false;
                    this.setTarget(null);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    void spawnBoneParticles(boolean tamed) {
        String s = "heart";
        if (!tamed) {
            s = "smoke";
        }
        for (int i = 0; i < 7; ++i) {
            double d = this.rand.nextGaussian() * 0.02;
            double d1 = this.rand.nextGaussian() * 0.02;
            double d2 = this.rand.nextGaussian() * 0.02;
            this.level.addParticle(s, this.x + (double) (this.rand.nextFloat() * this.width * 2.0f) - (double) this.width, this.y + 0.5 + (double) (this.rand.nextFloat() * this.height), this.z + (double) (this.rand.nextFloat() * this.width * 2.0f) - (double) this.width, d, d1, d2);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void handleStatus(byte byte0) {
        if (byte0 == 7) {
            this.spawnBoneParticles(true);
        } else if (byte0 == 6) {
            this.spawnBoneParticles(false);
        } else if (byte0 == 8) {
            this.canShakeWaterOff = true;
            this.shakeProgress = 0.0f;
            this.lastShakeProgress = 0.0f;
        } else {
            super.handleStatus(byte0);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float method_424() {
        if (this.isAngry()) {
            return 1.53938f;
        }
        if (this.isTamed()) {
            return (0.55f - (float) (20 - this.dataTracker.getInt(18)) * 0.02f) * 3.141593f;
        }
        return 0.6283185f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getLimitPerChunk() {
        return 8;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public String getOwner() {
        return this.dataTracker.getString(17);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setOwner(String s) {
        this.dataTracker.setData(17, s);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isSitting() {
        return (this.dataTracker.getByte(16) & 1) != 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setSitting(boolean flag) {
        byte byte0 = this.dataTracker.getByte(16);
        if (flag) {
            this.dataTracker.setData(16, (byte) (byte0 | 1));
        } else {
            this.dataTracker.setData(16, (byte) (byte0 & 0xFFFFFFFE));
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isAngry() {
        return (this.dataTracker.getByte(16) & 2) != 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setAngry(boolean flag) {
        byte byte0 = this.dataTracker.getByte(16);
        if (flag) {
            this.dataTracker.setData(16, (byte) (byte0 | 2));
        } else {
            this.dataTracker.setData(16, (byte) (byte0 & 0xFFFFFFFD));
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isTamed() {
        return (this.dataTracker.getByte(16) & 4) != 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setHasOwner(boolean flag) {
        byte byte0 = this.dataTracker.getByte(16);
        if (flag) {
            this.dataTracker.setData(16, (byte) (byte0 | 4));
        } else {
            this.dataTracker.setData(16, (byte) (byte0 & 0xFFFFFFFB));
        }
    }
}
