package io.github.ryuu.adventurecraft.entities;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.level.Level;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.io.CompoundTag;

public class EntityArrowBomb extends Arrow {

    private int fuse = 45;

    public EntityArrowBomb(Level world) {
        super(world);
    }

    public EntityArrowBomb(Level world, double d, double d1, double d2) {
        super(world, d, d1, d2);
    }

    public EntityArrowBomb(Level world, LivingEntity entityliving) {
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
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.put("fuse", (byte) this.fuse);
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.fuse = tag.getByte("fuse") & 0xFF;
    }

    @Override
    public void onPlayerCollision(Player entityplayer) {
    }

    @Override
    public boolean damage(Entity target, int amount) {
        if (!this.removed) {
            this.method_1336();
            EntityBomb.explode(this, this.field_1576, this.level, this.x, this.y, this.z);
        }
        return false;
    }
}
