package io.github.ryuu.adventurecraft.entities;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.UtilBullet;

public class EntitySkeletonShotgun extends Skeleton {

    private static final Random bs = new Random();

    public EntitySkeletonShotgun(Level world) {
        super(world);
        this.attackDamage = 2;
        this.heldItem = new ItemInstance(Items.shotgun, 1);
    }

    @Override
    protected void method_637(Entity entity, float f) {
        if ((double) f < 10.0 && bs.nextBoolean()) {
            this.method_924(entity, 30.0f, 30.0f);
            if (this.attackTime == 0) {
                this.method_924(entity, 60.0f, 90.0f);
                this.yaw = (float) ((double) this.yaw + 12.0 * bs.nextGaussian());
                this.pitch = (float) ((double) this.pitch + 6.0 * bs.nextGaussian());
                for (int i = 0; i < 8; ++i) {
                    UtilBullet.fireBullet(this.level, this, 0.12f, this.attackDamage);
                }
                this.attackTime = 50;
                this.level.playSound(this, "items.shotgun.fire_and_pump", 1.0f, 1.0f);
            }
            double d = entity.x - this.x;
            double d1 = entity.z - this.z;
            this.yaw = (float) (Math.atan2((double) d1, (double) d) * 180.0 / 3.1415927410125732) - 90.0f;
            this.field_663 = true;
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.heldItem.timeLeft = this.health <= 0 ? 0 : this.attackTime;
    }

    @Override
    protected int getMobDrops() {
        return Items.shotgunAmmo.id;
    }
}
