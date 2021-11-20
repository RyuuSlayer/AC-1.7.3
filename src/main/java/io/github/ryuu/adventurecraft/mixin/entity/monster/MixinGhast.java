package io.github.ryuu.adventurecraft.mixin.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.MonsterEntityType;
import net.minecraft.entity.monster.Ghast;
import net.minecraft.entity.projectile.Snowball;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Ghast.class)
public class MixinGhast extends FlyingEntity implements MonsterEntityType {

    @Shadow()
    public int field_1376 = 0;

    public double field_1377;

    public double field_1378;

    public double field_1379;

    private Entity field_1382 = null;

    private int field_1383 = 0;

    public int field_1380 = 0;

    public int field_1381 = 0;

    public MixinGhast(Level world) {
        super(world);
        this.texture = "/mob/ghast.png";
        this.setSize(4.0f, 4.0f);
        this.immuneToFire = true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(16, (byte) 0);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void tick() {
        super.tick();
        byte byte0 = this.dataTracker.getByte(16);
        this.texture = byte0 != 1 ? "/mob/ghast.png" : "/mob/ghast_fire.png";
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void tickHandSwing() {
        byte byte1;
        byte byte0;
        if (!this.level.isClient && this.level.difficulty == 0) {
            this.remove();
        }
        this.method_920();
        this.field_1380 = this.field_1381;
        double d = this.field_1377 - this.x;
        double d1 = this.field_1378 - this.y;
        double d2 = this.field_1379 - this.z;
        double d3 = MathsHelper.sqrt(d * d + d1 * d1 + d2 * d2);
        if (d3 < 1.0 || d3 > 60.0) {
            this.field_1377 = this.x + (double) ((this.rand.nextFloat() * 2.0f - 1.0f) * 16.0f);
            this.field_1378 = this.y + (double) ((this.rand.nextFloat() * 2.0f - 1.0f) * 16.0f);
            this.field_1379 = this.z + (double) ((this.rand.nextFloat() * 2.0f - 1.0f) * 16.0f);
        }
        if (this.field_1376-- <= 0) {
            this.field_1376 += this.rand.nextInt(5) + 2;
            if (this.method_1190(this.field_1377, this.field_1378, this.field_1379, d3)) {
                this.velocityX += d / d3 * 0.1;
                this.velocityY += d1 / d3 * 0.1;
                this.velocityZ += d2 / d3 * 0.1;
            } else {
                this.field_1377 = this.x;
                this.field_1378 = this.y;
                this.field_1379 = this.z;
            }
        }
        if (this.field_1382 != null && this.field_1382.removed) {
            this.field_1382 = null;
        }
        if (this.field_1382 == null || this.field_1383-- <= 0) {
            this.field_1382 = this.level.getClosestPlayerTo(this, 100.0);
            if (this.field_1382 != null) {
                this.field_1383 = 20;
            }
        }
        double d4 = 64.0;
        if (this.field_1382 != null && this.field_1382.method_1352(this) < d4 * d4) {
            double d5 = this.field_1382.x - this.x;
            double d6 = this.field_1382.boundingBox.minY + (double) (this.field_1382.height / 2.0f) - (this.y + (double) (this.height / 2.0f));
            double d7 = this.field_1382.z - this.z;
            this.field_1012 = this.yaw = -((float) Math.atan2((double) d5, (double) d7)) * 180.0f / 3.141593f;
            if (this.method_928(this.field_1382)) {
                if (this.field_1381 == 10) {
                    this.level.playSound(this, "mob.ghast.charge", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
                }
                ++this.field_1381;
                if (this.field_1381 == 20) {
                    this.level.playSound(this, "mob.ghast.fireball", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
                    Snowball entityfireball = new Snowball(this.level, this, d5, d6, d7, this.attackStrength);
                    double d8 = 4.0;
                    Vec3f vec3d = this.method_926(1.0f);
                    entityfireball.x = this.x + vec3d.x * d8;
                    entityfireball.y = this.y + (double) (this.height / 2.0f) + 0.5;
                    entityfireball.z = this.z + vec3d.z * d8;
                    this.level.spawnEntity(entityfireball);
                    this.field_1381 = -40;
                }
            } else if (this.field_1381 > 0) {
                --this.field_1381;
            }
        } else {
            this.field_1012 = this.yaw = -((float) Math.atan2((double) this.velocityX, (double) this.velocityZ)) * 180.0f / 3.141593f;
            if (this.field_1381 > 0) {
                --this.field_1381;
            }
        }
        if (!this.level.isClient && (byte0 = this.dataTracker.getByte(16)) != (byte1 = (byte) (this.field_1381 > 10 ? 1 : 0))) {
            this.dataTracker.setData(16, byte1);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private boolean method_1190(double d, double d1, double d2, double d3) {
        double d4 = (this.field_1377 - this.x) / d3;
        double d5 = (this.field_1378 - this.y) / d3;
        double d6 = (this.field_1379 - this.z) / d3;
        Box axisalignedbb = this.boundingBox.method_92();
        int i = 1;
        while ((double) i < d3) {
            axisalignedbb.method_102(d4, d5, d6);
            if (this.level.method_190(this, axisalignedbb).size() > 0) {
                return false;
            }
            ++i;
        }
        return true;
    }
}
