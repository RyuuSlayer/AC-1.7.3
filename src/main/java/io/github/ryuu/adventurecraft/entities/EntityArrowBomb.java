package io.github.ryuu.adventurecraft.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.level.Level;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.io.CompoundTag;

public class EntityArrowBomb extends Arrow {
    private int fuse;

    public EntityArrowBomb(Level world) {
        super(world);
        this.fuse = 45;
    }

    public EntityArrowBomb(Level world, double d, double d1, double d2) {
        super(world, d, d1, d2);
        this.fuse = 45;
    }

    public EntityArrowBomb(Level world, LivingEntity entityliving) {
        super(world, entityliving);
        this.fuse = 45;
    }

    @Override
    public void tick() {
        super.tick();
        this.fuse--;
        if (this.fuse == 0) {
            EntityBomb.explode(this, this.field_1576, this.level, this.x, this.y, this.z);
            remove();
        } else {
            this.level.addParticle("smoke", this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
        }
    }

    public void handleHitEntity(HitResult movingobjectposition) {
        this.velocityX *= -0.10000000149011612D;
        this.velocityY *= -0.10000000149011612D;
        this.velocityZ *= -0.10000000149011612D;
        this.yaw += 180.0F;
        this.prevYaw += 180.0F;
        this.field_1584 = 0;
    }

    @Override
    public void writeCustomDataToTag(CompoundTag nbttagcompound) {
        super.writeCustomDataToTag(nbttagcompound);
        nbttagcompound.put("fuse", (byte) this.fuse);
    }

    @Override
    public void readCustomDataFromTag(CompoundTag nbttagcompound) {
        super.readCustomDataFromTag(nbttagcompound);
        this.fuse = nbttagcompound.getByte("fuse") & 0xFF;
    }

    @Override
    public void onPlayerCollision(Player entityplayer) {
    }

    @Override
    public boolean damage(Entity entity, int i) {
        if (!this.removed) {
            method_1336();
            EntityBomb.explode(this, this.field_1576, this.level, this.x, this.y, this.z);
        }
        return false;
    }
}
