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

    public void w_() {
        super.tick();
        this.fuse--;
        if (this.fuse == 0) {
            EntityBomb.explode(this, this.c, this.level, this.x, this.y, this.z); // c is probably field_1576 but not 100% sure
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
        this.k = 0;
    }

    public void b(CompoundTag nbttagcompound) {
        super.writeCustomDataToTag(nbttagcompound);
        nbttagcompound.put("fuse", (byte) this.fuse);
    }

    public void a(CompoundTag nbttagcompound) {
        super.fromTag(nbttagcompound);
        this.fuse = nbttagcompound.getByte("fuse") & 0xFF;
    }

    public void b(Player entityplayer) {
    }

    public boolean a(Entity entity, int i) {
        if (!this.removed) {
            method_1336();
            EntityBomb.explode(this, this.c, this.level, this.x, this.y, this.z); // c is probably field_1576 but not 100% sure
        }
        return false;
    }
}
