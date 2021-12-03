package io.github.ryuu.adventurecraft.mixin.entity;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.extensions.entity.ExLivingEntity;
import io.github.ryuu.adventurecraft.extensions.tile.ExLadderTile;
import io.github.ryuu.adventurecraft.items.Items;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileSounds;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3f;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends MixinEntity implements AccessLivingEntity, ExLivingEntity {

    @Shadow
    public int field_1009;

    @Shadow
    protected String texture;

    @Shadow
    public int health;

    @Shadow
    public int field_1037;

    @Shadow
    public int hurtTime;

    @Shadow
    public int field_1039;

    @Shadow
    public float field_1040;

    @Shadow
    public float field_1044;

    @Shadow
    public float field_1048;

    @Shadow
    public float limbDistance;

    @Shadow
    public float field_1050;

    @Shadow
    protected int field_1058;

    @Shadow
    protected boolean jumping;

    @Shadow
    protected float movementSpeed;

    @Shadow
    private Entity field_1061;

    @Shadow
    protected int despawnCounter;

    @Shadow
    protected float perpendicularMovement;

    @Shadow
    protected float parallelMovement;

    @Shadow
    protected float field_1030;

    @Shadow
    protected float field_1032;

    @Shadow
    protected int field_1034;

    private long hurtTick;
    private long tickBeforeNextJump;

    public ItemInstance heldItem;
    public int timesCanJumpInAir = 0;
    public int jumpsLeft = 0;
    public boolean canWallJump = false;
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
    public int maxHealth = 10;

    @Shadow
    protected abstract void shadow$applyDamage(int i);

    @Shadow
    public abstract void addHealth(int i);

    @Shadow
    protected abstract int getLookPitchSpeed();

    @Shadow
    protected abstract float getSoundVolume();

    @Shadow
    protected abstract String getHurtSound();

    @Shadow
    protected abstract String getDeathSound();

    @Shadow
    public abstract void onKilledBy(Entity arg);

    @Shadow
    protected abstract void method_920();

    @Shadow
    public abstract void method_924(Entity arg, float f, float f1);

    @Shadow
    public abstract void method_925(Entity arg, int i, double d, double d1);

    @Shadow
    public abstract float method_930(float f);

    @Shadow
    public abstract void tick();

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public boolean method_928(Entity entity) {
        double angleOffset = -180.0 * Math.atan2(entity.x - this.x, entity.z - this.z) / Math.PI;
        double diffAngle = angleOffset - this.yaw;
        while (diffAngle < -180.0) {
            diffAngle += 360.0;
        }
        while (diffAngle > 180.0) {
            diffAngle -= 360.0;
        }
        if (Math.abs(diffAngle) > (double) (this.fov / 2.0f + this.extraFov)) {
            return false;
        }
        return this.level.raycast(Vec3f.from(this.x, this.y + (double) this.getStandingEyeHeight(), this.z), Vec3f.from(entity.x, entity.y + (double) entity.getStandingEyeHeight(), entity.z)) == null;
    }

    @Inject(method = "baseTick", at = @At("TAIL"))
    private void afterBaseTick(CallbackInfo ci) {
        afterBaseTick();
    }

    protected void afterBaseTick() {
        if (this.extraFov > 0.0f) {
            this.extraFov -= 5.0f;
            if (this.extraFov < 0.0f) {
                this.extraFov = 0.0f;
            }
        }
    }

    @ModifyConstant(method = "addHealth", constant = @Constant(intValue = 20))
    public int changeAddHealthMaxHealth(int oldValue) {
        return this.maxHealth;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public boolean damage(Entity target, int amount) {
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
            this.level.method_185((Entity) (Object) this, (byte) 2);
            this.method_1336();
            if (target != null) {
                double d = target.x - this.x;
                double d1 = target.z - this.z;
                while (d * d + d1 * d1 < 1.0E-4) {
                    d = (Math.random() - Math.random()) * 0.01;
                    d1 = (Math.random() - Math.random()) * 0.01;
                }
                this.field_1040 = (float) (Math.atan2(d1, d) * 180.0 / 3.1415927410125732) - this.yaw;
                this.method_925(target, amount, d, d1);
            } else {
                this.field_1040 = (int) (Math.random() * 2.0) * 180;
            }
        }
        if (this.health <= 0) {
            if (flag) {
                this.level.playSound((Entity) (Object) this, this.getDeathSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
            this.onKilledBy(target);
        } else if (flag) {
            this.level.playSound((Entity) (Object) this, this.getHurtSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        }
        return true;
    }

    @Override
    public boolean attackEntityFromMulti(Entity entity, int i) {
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
            this.level.method_185((Entity) (Object) this, (byte) 2);
            this.method_1336();
            if (entity != null) {
                double d = entity.x - this.x;
                double d1 = entity.z - this.z;
                while (d * d + d1 * d1 < 1.0E-4) {
                    d = (Math.random() - Math.random()) * 0.01;
                    d1 = (Math.random() - Math.random()) * 0.01;
                }
                this.field_1040 = (float) (Math.atan2(d1, d) * 180.0 / 3.1415927410125732) - this.yaw;
                this.method_925(entity, i, d, d1);
            } else {
                this.field_1040 = (int) (Math.random() * 2.0) * 180;
            }
        }
        if (this.health <= 0) {
            if (flag) {
                this.level.playSound((Entity) (Object) this, this.getDeathSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
            this.invokeOnKilledBy(entity);
        } else if (flag) {
            this.level.playSound((Entity) (Object) this, this.getHurtSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        }
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Inject(method = "onKilledBy", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;dropLoot()V"))
    private void dropHeartOnKilledBy(Entity entity, CallbackInfo ci) {
        if (entity instanceof LivingEntity && ((LivingEntity) entity).health < ((ExLivingEntity) entity).getMaxHealth() && this.rand.nextInt(3) != 0) {
            ItemEntity heart = new ItemEntity(this.level, this.x, this.y, this.z, new ItemInstance(Items.heart.id, 1, 0));
            this.level.spawnEntity(heart);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    protected void handleFallDamage(float f) {
        float pre = Math.max(f - 0.8f, 0.0f) / 0.08f;
        int i = (int) Math.ceil(Math.pow(pre, 1.5));
        if (!this.isFlying() && i > 0) {
            this.damage(null, i);
            int j = this.level.getTileId(MathsHelper.floor(this.x), MathsHelper.floor(this.y - (double) 0.2f - (double) this.standingEyeHeight), MathsHelper.floor(this.z));
            if (j > 0) {
                TileSounds stepsound = Tile.BY_ID[j].sounds;
                this.level.playSound((Entity) (Object) this, stepsound.getWalkSound(), stepsound.getVolume() * 0.5f, stepsound.getPitch() * 0.75f);
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void travel(float f, float f1) {
        if (this.isFlying()) {
            double l = Math.sqrt(f * f + f1 * f1);
            double ySpeed = (double) (-0.1f * f1) * Math.sin(this.pitch * 3.141593f / 180.0f);
            if (l < 1.0) {
                ySpeed *= l;
            }
            this.velocityY += ySpeed;
            float speed = (float) ((double) 0.1f * (Math.abs((double) f1 * Math.cos(this.pitch * 3.141593f / 180.0f)) + (double) Math.abs(f)));
            this.movementInputToVelocity(f, f1, speed);
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.fallDistance = 0.0f;
            this.velocityX *= 0.8;
            this.velocityY *= 0.8;
            this.velocityZ *= 0.8;
            if (Math.abs(this.velocityX) < 0.01) {
                this.velocityX = 0.0;
            }
            if (Math.abs(this.velocityY) < 0.01) {
                this.velocityY = 0.0;
            }
            if (Math.abs(this.velocityZ) < 0.01) {
                this.velocityZ = 0.0;
            }
        } else if (this.method_1334()) {
            if (this.velocityY < -0.4) {
                this.velocityY *= 0.8;
            }
            double d = this.y;
            this.movementInputToVelocity(f, f1, 0.02f);
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.velocityX *= 0.8f;
            this.velocityY *= 0.8f;
            this.velocityZ *= 0.8f;
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
            this.velocityY *= 0.98f;
            this.velocityX *= f2;
            this.velocityZ *= f2;
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
    @Overwrite
    public boolean isOnLadder() {
        int i = MathsHelper.floor(this.x);
        int j = MathsHelper.floor(this.boundingBox.minY);
        int k = MathsHelper.floor(this.z);
        int blockID = this.level.getTileId(i, j, k);
        int blockIDAbove = this.level.getTileId(i, j + 1, k);
        boolean v = this.level.getTileMeta(i, j, k) % 3 == 0;
        boolean vAbove = this.level.getTileMeta(i, j + 1, k) % 3 == 0;
        return ExLadderTile.isLadderID(blockID) || ExLadderTile.isLadderID(blockIDAbove) || blockID == Blocks.ropes1.id && v || blockIDAbove == Blocks.ropes1.id && vAbove || blockID == Blocks.ropes2.id && v || blockIDAbove == Blocks.ropes2.id && vAbove || blockID == Blocks.chains.id && v || blockIDAbove == Blocks.chains.id && vAbove;
    }

    protected abstract void writeACDataToTag(CompoundTag tag);

    protected abstract void readACDataFromTag(CompoundTag tag);

    @Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
    private void writeACDataToTag(CompoundTag tag, CallbackInfo ci) {
        tag.put("MaxHealth", (short) this.maxHealth);
        tag.put("EntityID", this.id);
        tag.put("timesCanJumpInAir", this.timesCanJumpInAir);
        tag.put("canWallJump", this.canWallJump);
        tag.put("fov", this.fov);
        tag.put("canLookRandomly", this.canLookRandomly);
        tag.put("randomLookVelocity", this.randomLookVelocity);
        tag.put("randomLookRate", this.randomLookRate);
        tag.put("randomLookRateVariation", this.randomLookRateVariation);
        this.writeACDataToTag(tag);
    }

    @Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
    private void readACDataFromTag(CompoundTag tag, CallbackInfo ci) {
        this.maxHealth = !tag.containsKey("MaxHealth") ? 10 : (int) tag.getShort("MaxHealth");
        if (tag.containsKey("EntityID")) {
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
        this.readACDataFromTag(tag);
    }

    @Inject(method = "updateDespawnCounter", at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/entity/LivingEntity;method_1335()Z",
            shift = At.Shift.AFTER))
    private void changeMoveBehavior(CallbackInfo ci) {
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
    }

    @Redirect(method = "updateDespawnCounter", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/entity/LivingEntity;jumping:Z",
            opcode = Opcodes.GETFIELD
    ))
    private boolean changeJumpBehavior(LivingEntity instance) {
        if (((AccessLivingEntity) instance).isJumping()) {
            if (this.method_1334()) {
                this.velocityY += 0.04f;
            } else if (this.method_1335()) {
                this.velocityY += 0.04f;
            } else if (this.onGround) {
                this.jump();
            } else if (this.level.getLevelTime() >= this.tickBeforeNextJump) {
                if (this.canWallJump && (this.collisionX != 0 || this.collisionZ != 0)) {
                    this.jump();
                    this.velocityY *= this.jumpWallMultiplier;
                    this.velocityX += (double) (-this.collisionX) * 0.325;
                    this.velocityZ += (double) (-this.collisionZ) * 0.325;
                    this.moveYawOffset = (float) (180.0 * Math.atan2(-this.velocityX, this.velocityZ) / Math.PI) - this.yaw;
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
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    protected void jump() {
        this.tickBeforeNextJump = this.level.getLevelTime() + 5L;
        this.velocityY = this.jumpVelocity;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    protected void tickHandSwing() {
        ++this.despawnCounter;
        this.level.getClosestPlayerTo((Entity) (Object) this, -1.0);
        this.method_920();
        this.perpendicularMovement = 0.0f;
        this.parallelMovement = 0.0f;
        float f = 8.0f;
        if (this.rand.nextFloat() < 0.02f) {
            Player entityplayer1 = this.level.getClosestPlayerTo((Entity) (Object) this, f);
            if (entityplayer1 != null && this.method_928(entityplayer1)) {
                this.field_1061 = entityplayer1;
                this.field_1034 = 10 + this.rand.nextInt(20);
            }
        }
        if (this.field_1061 != null) {
            this.method_924(this.field_1061, 10.0f, this.getLookPitchSpeed());
            if (this.field_1034-- <= 0 || this.field_1061.removed || this.field_1061.method_1352((Entity) (Object) this) > (double) (f * f)) {
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
            if (Math.abs(this.field_1030) < 1.0f) {
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
    @Environment(EnvType.CLIENT)
    @Overwrite
    public ItemInstance method_909() {
        return this.heldItem;
    }

    public boolean protectedByShield(double x, double y, double z) {
        if (!this.protectedByShield() || this.method_930(1.0f) > 0.0f) {
            return false;
        }
        double diffX = this.x - x;
        double diffZ = this.z - z;
        float angle = -57.29578f * (float) Math.atan2(diffX, diffZ) + 180.0f;
        float diff = Math.abs(angle - this.yaw);
        while (diff > 180.0f) {
            diff -= 360.0f;
        }
        while (diff < -180.0f) {
            diff += 360.0f;
        }
        return diff < 50.0f;
    }

    @Override
    public void applyDamage(int damage) {
        shadow$applyDamage(damage);
    }

    @Override
    public float getFov() {
        return this.fov;
    }

    @Override
    public float getExtraFov() {
        return this.extraFov;
    }

    @Override
    public int getStunned() {
        return this.stunned;
    }

    @Override
    public void setStunned(int stunned) {
        this.stunned = stunned;
    }

    @Override
    public double getGravity() {
        return this.gravity;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void setMaxHealth(int i) {
        maxHealth = i;
    }

    @Override
    public ItemInstance getHeldItem() {
        return heldItem;
    }

    @Override
    public void setHeldItem(ItemInstance heldItem) {
        this.heldItem = heldItem;
    }
}
