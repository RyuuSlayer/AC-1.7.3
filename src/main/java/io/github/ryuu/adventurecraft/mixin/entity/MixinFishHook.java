package io.github.ryuu.adventurecraft.mixin.entity;

import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FishHook;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.tile.material.Material;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(FishHook.class)
public abstract class MixinFishHook extends Entity {

    @Shadow
    public int shake = 0;
    @Shadow
    public Player field_1067;
    @Shadow
    public Entity field_1068;
    @Shadow
    private int xTile;
    @Shadow
    private int yTile;
    @Shadow
    private int zTile;
    @Shadow
    private int inTile;
    @Shadow
    private boolean inGround;
    @Shadow
    private int field_1074;
    @Shadow
    private int field_1075 = 0;
    @Shadow
    private int field_1076 = 0;
    @Shadow
    private int field_1077;
    @Shadow
    private double field_1078;
    @Shadow
    private double field_1079;
    @Shadow
    private double field_1080;
    @Shadow
    private double field_1081;
    @Shadow
    private double field_1082;

    public MixinFishHook(Level arg) {
        super(arg);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    public void tick() {
        block37:
        {
            if (this.field_1067 == null) {
                this.field_1067 = AccessMinecraft.getInstance().player;
                AccessMinecraft.getInstance().player.fishHook = (FishHook) (Object) this;
            }
            super.tick();
            if (this.field_1077 > 0) {
                double d4;
                double d = this.x + (this.field_1078 - this.x) / (double) this.field_1077;
                double d1 = this.y + (this.field_1079 - this.y) / (double) this.field_1077;
                double d2 = this.z + (this.field_1080 - this.z) / (double) this.field_1077;
                for (d4 = this.field_1081 - (double) this.yaw; d4 < -180.0; d4 += 360.0) {
                }
                while (true) {
                    if (!(d4 >= 180.0)) {
                        this.yaw = (float) ((double) this.yaw + d4 / (double) this.field_1077);
                        this.pitch = (float) ((double) this.pitch + (this.field_1082 - (double) this.pitch) / (double) this.field_1077);
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
                        this.y = this.field_1068.boundingBox.minY + (double) this.field_1068.height * 0.8;
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
        Vec3d vec3d = Vec3d.getOrCreate(this.x, this.y, this.z);
        Vec3d vec3d1 = Vec3d.getOrCreate(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
        HitResult movingobjectposition = this.level.raycast(vec3d, vec3d1);
        vec3d = Vec3d.getOrCreate(this.x, this.y, this.z);
        vec3d1 = Vec3d.getOrCreate(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
        if (movingobjectposition != null) {
            vec3d1 = Vec3d.getOrCreate(movingobjectposition.pos.x, movingobjectposition.pos.y, movingobjectposition.pos.z);
        }
        Entity entity = null;
        List list = this.level.getEntities(this, this.boundingBox.add(this.velocityX, this.velocityY, this.velocityZ).expand(1.0, 1.0, 1.0));
        double d3 = 0.0;
        for (Object o : list) {
            double d6;
            float f2;
            Box axisalignedbb;
            HitResult movingobjectposition1;
            Entity entity1 = (Entity) o;
            if (!entity1.method_1356() || entity1 == this.field_1067 && this.field_1075 < 5 || (movingobjectposition1 = (axisalignedbb = entity1.boundingBox.expand(f2 = 0.3f, f2, f2)).method_89(vec3d, vec3d1)) == null || !((d6 = vec3d.getDistance(movingobjectposition1.pos)) < d3) && d3 != 0.0)
                continue;
            entity = entity1;
            d3 = d6;
        }
        if (entity != null) {
            movingobjectposition = new HitResult(entity);
        }
        if (movingobjectposition != null) {
            if (movingobjectposition.entity != null) {
                if (movingobjectposition.entity.damage(this.field_1067, 0)) {
                    this.field_1068 = movingobjectposition.entity;
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
        this.yaw = (float) (Math.atan2(this.velocityX, this.velocityZ) * 180.0 / 3.1415927410125732);
        this.pitch = (float) (Math.atan2(this.velocityY, f) * 180.0 / 3.1415927410125732);
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
            double d8 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double) (l + 0) / (double) k - 0.125 + 0.125;
            double d9 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double) (l + 1) / (double) k - 0.125 + 0.125;
            Box axisalignedbb1 = Box.getOrCreate(this.boundingBox.minX, d8, this.boundingBox.minZ, this.boundingBox.maxX, d9, this.boundingBox.maxZ);
            if (!this.level.method_207(axisalignedbb1, Material.WATER)) continue;
            d5 += 1.0 / (double) k;
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
                    this.velocityY -= 0.2f;
                    this.level.playSound(this, "random.splash", 0.25f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                    float f3 = MathsHelper.floor(this.boundingBox.minY);
                    int i1 = 0;
                    while ((float) i1 < 1.0f + this.width * 20.0f) {
                        float f4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                        float f6 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                        this.level.addParticle("bubble", this.x + (double) f4, f3 + 1.0f, this.z + (double) f6, this.velocityX, this.velocityY - (double) (this.rand.nextFloat() * 0.2f), this.velocityZ);
                        ++i1;
                    }
                    int j1 = 0;
                    while ((float) j1 < 1.0f + this.width * 20.0f) {
                        float f5 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                        float f7 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                        this.level.addParticle("splash", this.x + (double) f5, f3 + 1.0f, this.z + (double) f7, this.velocityX, this.velocityY, this.velocityZ);
                        ++j1;
                    }
                }
            }
        }
        if (this.field_1076 > 0) {
            this.velocityY -= (double) (this.rand.nextFloat() * this.rand.nextFloat() * this.rand.nextFloat()) * 0.2;
        }
        double d7 = d5 * 2.0 - 1.0;
        this.velocityY += (double) 0.04f * d7;
        if (d5 > 0.0) {
            f1 = (float) ((double) f1 * 0.9);
            this.velocityY *= 0.8;
        }
        this.velocityX *= f1;
        this.velocityY *= f1;
        this.velocityZ *= f1;
        this.setPosition(this.x, this.y, this.z);
    }
}
