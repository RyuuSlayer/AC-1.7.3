package io.github.ryuu.adventurecraft.entities;

import io.github.ryuu.adventurecraft.extensions.entity.ExEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathsHelper;

import java.util.ArrayList;
import java.util.List;

public class EntityBoomerang extends Entity {

    public float prevBoomerangRotation;
    public float boomerangRotation;
    double bounceFactor;
    int timeBeforeTurnAround;

    boolean turningAround;

    Entity returnsTo;

    List<ItemEntity> itemsPickedUp;

    ItemInstance item;

    int chunkX;

    int chunkY;

    int chunkZ;

    public EntityBoomerang(Level world) {
        super(world);
        this.setSize(0.5f, 0.0625f);
        this.standingEyeHeight = 0.03125f;
        this.bounceFactor = 0.85;
        this.boomerangRotation = 0.0f;
        this.turningAround = true;
        this.timeBeforeTurnAround = 0;
        this.itemsPickedUp = new ArrayList<>();
        ((ExEntity) this).setCollidesWithClipBlocks(false);
    }

    public EntityBoomerang(Level world, Entity entity, ItemInstance b) {
        this(world);
        this.item = b;
        this.setRotation(entity.yaw, entity.pitch);
        double xHeading = -MathsHelper.sin(entity.yaw * 3.141593f / 180.0f);
        double zHeading = MathsHelper.cos(entity.yaw * 3.141593f / 180.0f);
        this.velocityX = 0.5 * xHeading * MathsHelper.cos(entity.pitch / 180.0f * 3.141593f);
        this.velocityY = -0.5 * MathsHelper.sin(entity.pitch / 180.0f * 3.141593f);
        this.velocityZ = 0.5 * zHeading * MathsHelper.cos(entity.pitch / 180.0f * 3.141593f);
        this.setPosition(entity.x, entity.y, entity.z);
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        this.timeBeforeTurnAround = 30;
        this.turningAround = false;
        this.returnsTo = entity;
        this.chunkX = (int) Math.floor(this.x);
        this.chunkY = (int) Math.floor(this.y);
        this.chunkZ = (int) Math.floor(this.z);
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
        for (Object o : entitiesWithin) {
            Entity e = (Entity) o;
            if (e instanceof ItemEntity) {
                this.itemsPickedUp.add((ItemEntity) e);
                continue;
            }
            if (!(e instanceof LivingEntity) || e == this.returnsTo) continue;
            ((ExEntity) e).setStunned(20);
            e.prevX = e.x;
            e.prevY = e.y;
            e.prevZ = e.z;
            e.prevYaw = e.yaw;
            e.prevPitch = e.pitch;
        }
        for (Entity e : this.itemsPickedUp) {
            if (e.removed) continue;
            e.setPosition(this.x, this.y, this.z);
        }
        int curChunkX = (int) Math.floor(this.x);
        int curChunkY = (int) Math.floor(this.y);
        int curChunkZ = (int) Math.floor(this.z);
        if (curChunkX != this.chunkX || curChunkY != this.chunkY || curChunkZ != this.chunkZ) {
            this.chunkX = curChunkX;
            this.chunkY = curChunkY;
            this.chunkZ = curChunkZ;
            int blockID = this.level.getTileId(this.chunkX, this.chunkY, this.chunkZ);
            if (blockID == Tile.LEVER.id && this.returnsTo instanceof Player) {
                Tile.LEVER.activate(this.level, this.chunkX, this.chunkY, this.chunkZ, (Player) this.returnsTo);
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
        this.yaw = -57.29578f * (float) Math.atan2(this.velocityX, this.velocityZ);
        double xzLength = Math.sqrt(this.velocityZ * this.velocityZ + this.velocityX * this.velocityX);
        this.pitch = -57.29578f * (float) Math.atan2(this.velocityY, xzLength);
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        this.remove();
    }

    @Override
    public void onPlayerCollision(Player entityplayer) {
    }

    @Override
    public boolean damage(Entity target, int amount) {
        return false;
    }

    @Override
    protected void initDataTracker() {
    }
}
