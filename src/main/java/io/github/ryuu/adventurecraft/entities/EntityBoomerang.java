package io.github.ryuu.adventurecraft.entities;

import net.minecraft.tile.Tile;
import net.minecraft.util.maths.MathsHelper;

import java.util.ArrayList;
import java.util.List;

public class EntityBoomerang extends MixinEntity {

    double bounceFactor;

    float prevBoomerangRotation;

    float boomerangRotation;

    int timeBeforeTurnAround;

    boolean turningAround;

    MixinEntity returnsTo;

    List<net.minecraft.src.MixinEntity> itemsPickedUp;

    MixinItemInstance item;

    int chunkX;

    int chunkY;

    int chunkZ;

    public EntityBoomerang(MixinLevel world) {
        super(world);
        this.setSize(0.5f, 0.0625f);
        this.standingEyeHeight = 0.03125f;
        this.bounceFactor = 0.85;
        this.boomerangRotation = 0.0f;
        this.turningAround = true;
        this.timeBeforeTurnAround = 0;
        this.itemsPickedUp = new ArrayList();
        this.collidesWithClipBlocks = false;
    }

    public EntityBoomerang(MixinLevel world, MixinEntity entity, MixinItemInstance b) {
        this(world);
        this.item = b;
        this.setRotation(entity.yaw, entity.pitch);
        double xHeading = -MathsHelper.sin(entity.yaw * 3.141593f / 180.0f);
        double zHeading = MathsHelper.cos(entity.yaw * 3.141593f / 180.0f);
        this.velocityX = 0.5 * xHeading * (double) MathsHelper.cos(entity.pitch / 180.0f * 3.141593f);
        this.velocityY = -0.5 * (double) MathsHelper.sin(entity.pitch / 180.0f * 3.141593f);
        this.velocityZ = 0.5 * zHeading * (double) MathsHelper.cos(entity.pitch / 180.0f * 3.141593f);
        this.setPosition(entity.x, entity.y, entity.z);
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        this.timeBeforeTurnAround = 30;
        this.turningAround = false;
        this.returnsTo = entity;
        this.chunkX = (int) Math.floor((double) this.x);
        this.chunkY = (int) Math.floor((double) this.y);
        this.chunkZ = (int) Math.floor((double) this.z);
    }

    @Override
    public void tick() {
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        this.prevYaw = this.yaw;
        this.prevPitch = this.pitch;
        if (!this.turningAround) {
            double prevVelX = this.velocityX;
            double prevVelY = this.velocityY;
            double prevVelZ = this.velocityZ;
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            boolean bounced = false;
            if (this.velocityX != prevVelX) {
                this.velocityX = -prevVelX;
                bounced = true;
            }
            if (this.velocityY != prevVelY) {
                this.velocityY = -prevVelY;
                bounced = true;
            }
            if (this.velocityZ != prevVelZ) {
                this.velocityZ = -prevVelZ;
                bounced = true;
            }
            if (bounced) {
                this.velocityX *= this.bounceFactor;
                this.velocityY *= this.bounceFactor;
                this.velocityZ *= this.bounceFactor;
            }
            if (this.timeBeforeTurnAround-- <= 0) {
                this.turningAround = true;
            }
        } else if (this.returnsTo != null) {
            double deltaX = this.returnsTo.x - this.x;
            double deltaY = this.returnsTo.y - this.y;
            double deltaZ = this.returnsTo.z - this.z;
            double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
            if (length < 1.5) {
                this.remove();
            }
            this.velocityX = 0.5 * deltaX / length;
            this.velocityY = 0.5 * deltaY / length;
            this.velocityZ = 0.5 * deltaZ / length;
            this.setPosition(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
        } else {
            this.remove();
        }
        this.determineRotation();
        this.prevBoomerangRotation = this.boomerangRotation;
        this.boomerangRotation += 36.0f;
        while (this.boomerangRotation > 360.0f) {
            this.boomerangRotation -= 360.0f;
        }
        List entitiesWithin = this.level.getEntities(this, this.boundingBox.expand(0.5, 0.5, 0.5));
        for (int i = 0; i < entitiesWithin.size(); ++i) {
            MixinEntity e = (MixinEntity) entitiesWithin.get(i);
            if (e instanceof MixinItemEntity) {
                this.itemsPickedUp.add(e);
                continue;
            }
            if (!(e instanceof MixinLivingEntity) || e == this.returnsTo) continue;
            e.stunned = 20;
            e.prevX = e.x;
            e.prevY = e.y;
            e.prevZ = e.z;
            e.prevYaw = e.yaw;
            e.prevPitch = e.pitch;
        }
        for (MixinEntity e : this.itemsPickedUp) {
            if (e.removed) continue;
            e.setPosition(this.x, this.y, this.z);
        }
        int curChunkX = (int) Math.floor((double) this.x);
        int curChunkY = (int) Math.floor((double) this.y);
        int curChunkZ = (int) Math.floor((double) this.z);
        if (curChunkX != this.chunkX || curChunkY != this.chunkY || curChunkZ != this.chunkZ) {
            this.chunkX = curChunkX;
            this.chunkY = curChunkY;
            this.chunkZ = curChunkZ;
            int blockID = this.level.getTileId(this.chunkX, this.chunkY, this.chunkZ);
            if (blockID == Tile.LEVER.id && this.returnsTo instanceof MixinPlayer) {
                Tile.LEVER.activate(this.level, this.chunkX, this.chunkY, this.chunkZ, (MixinPlayer) this.returnsTo);
            }
        }
    }

    @Override
    public void remove() {
        super.remove();
        if (this.item != null) {
            this.item.setDamage(0);
        }
    }

    public void determineRotation() {
        this.yaw = -57.29578f * (float) Math.atan2((double) this.velocityX, (double) this.velocityZ);
        double xzLength = Math.sqrt((double) (this.velocityZ * this.velocityZ + this.velocityX * this.velocityX));
        this.pitch = -57.29578f * (float) Math.atan2((double) this.velocityY, xzLength);
    }

    @Override
    protected void writeCustomDataToTag(MixinCompoundTag tag) {
    }

    @Override
    public void readCustomDataFromTag(MixinCompoundTag tag) {
        this.remove();
    }

    @Override
    public void onPlayerCollision(MixinPlayer entityplayer) {
    }

    @Override
    public boolean damage(MixinEntity target, int amount) {
        return false;
    }

    @Override
    protected void initDataTracker() {
    }
}
