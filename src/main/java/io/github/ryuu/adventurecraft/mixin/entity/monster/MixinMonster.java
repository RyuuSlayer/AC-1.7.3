package io.github.ryuu.adventurecraft.mixin.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MonsterEntityType;
import net.minecraft.entity.WalkingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.LightType;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathsHelper;

public class MixinMonster extends WalkingEntity implements MonsterEntityType {
    public int attackDamage = 2;

    public MixinMonster(Level world) {
        super(world);
        this.health = 20;
    }

    public void updateDespawnCounter() {
        float f = this.getBrightnessAtEyes(1.0f);
        if (f > 0.5f) {
            this.despawnCounter += 2;
        }
        super.updateDespawnCounter();
    }

    public void tick() {
        super.tick();
        if (!this.level.isClient && this.level.difficulty == 0) {
            this.remove();
        }
    }

    protected Entity method_638() {
        Player entityplayer = this.level.getClosestPlayerTo(this, 16.0);
        if (entityplayer != null && this.method_928(entityplayer)) {
            return entityplayer;
        }
        return null;
    }

    public boolean damage(Entity target, int amount) {
        this.timeBeforeForget = 40;
        if (super.damage(target, amount)) {
            if (this.passenger == target || this.vehicle == target) {
                return true;
            }
            if (target != this) {
                this.entity = target;
            }
            return true;
        }
        return false;
    }

    public boolean attackEntityFromMulti(Entity entity, int i) {
        this.timeBeforeForget = 40;
        if (super.attackEntityFromMulti(entity, i)) {
            if (this.passenger == entity || this.vehicle == entity) {
                return true;
            }
            if (entity != this) {
                this.entity = entity;
            }
            return true;
        }
        return false;
    }

    protected void method_637(Entity entity, float f) {
        if (this.attackTime <= 0 && f < 2.0f && entity.boundingBox.maxY > this.boundingBox.minY && entity.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 20;
            entity.damage(this, this.attackDamage);
        }
    }

    protected float getPathfindingFavour(int i, int j, int k) {
        return 0.5f - this.level.getBrightness(i, j, k);
    }

    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
    }

    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
    }

    public boolean canSpawn() {
        int k;
        int j;
        int i = MathsHelper.floor(this.x);
        if (this.level.getLightLevel(LightType.Sky, i, j = MathsHelper.floor(this.boundingBox.minY), k = MathsHelper.floor(this.z)) > this.rand.nextInt(32)) {
            return false;
        }
        int l = this.level.getLightLevel(i, j, k);
        if (this.level.thundering()) {
            int i1 = this.level.field_202;
            this.level.field_202 = 10;
            l = this.level.getLightLevel(i, j, k);
            this.level.field_202 = i1;
        }
        return l <= this.rand.nextInt(8) && super.canSpawn();
    }
}