package io.github.ryuu.adventurecraft.entities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.level.Level;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.io.CompoundTag;

public class EntityArrowBomb extends MixinArrow {

    private int fuse = 45;

    public EntityArrowBomb(MixinLevel world) {
        super(world);
    }

    public EntityArrowBomb(MixinLevel world, double d, double d1, double d2) {
        super(world, d, d1, d2);
    }

    public EntityArrowBomb(MixinLevel world, MixinLivingEntity entityliving) {
        super(world, entityliving);
    }

    @Override
    public void tick() {
        super.tick();
        --this.fuse;
        if (this.fuse == 0) {
            EntityBomb.explode(this, this.field_1576, this.level, this.x, this.y, this.z);
            this.remove();
        } else {
            this.level.addParticle("smoke", this.x, this.y, this.z, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void handleHitEntity(HitResult movingobjectposition) {
        this.velocityX *= (double) -0.1f;
        this.velocityY *= (double) -0.1f;
        this.velocityZ *= (double) -0.1f;
        this.yaw += 180.0f;
        this.prevYaw += 180.0f;
        this.field_1584 = 0;
    }

    @Override
    public void writeCustomDataToTag(MixinCompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.put("fuse", (byte) this.fuse);
    }

    @Override
    public void readCustomDataFromTag(MixinCompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.fuse = tag.getByte("fuse") & 0xFF;
    }

    @Override
    public void onPlayerCollision(MixinPlayer entityplayer) {
    }

    @Override
    public boolean damage(MixinEntity target, int amount) {
        if (!this.removed) {
            this.method_1336();
            EntityBomb.explode(this, this.field_1576, this.level, this.x, this.y, this.z);
        }
        return false;
    }
}
