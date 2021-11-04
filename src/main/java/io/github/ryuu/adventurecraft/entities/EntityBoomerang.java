package io.github.ryuu.adventurecraft.entities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathsHelper;

public class EntityBoomerang extends Entity {
    double bounceFactor;

    float prevBoomerangRotation;

    float boomerangRotation;

    int timeBeforeTurnAround;

    boolean turningAround;

    Entity returnsTo;

    List<Entity> itemsPickedUp;

    ItemInstance item;

    int chunkX;

    int chunkY;

    int chunkZ;

    public EntityBoomerang(Level world) {
        super(world);
        setSize(0.5F, 0.0625F);
        this.standingEyeHeight = 0.03125F;
        this.bounceFactor = 0.85D;
        this.boomerangRotation = 0.0F;
        this.turningAround = true;
        this.timeBeforeTurnAround = 0;
        this.itemsPickedUp = new ArrayList<Entity>();
        this.collidesWithClipBlocks = false;
    }

    public EntityBoomerang(Level world, Entity entity, ItemInstance b) {
        this(world);
        this.item = b;
        setRotation(entity.yaw, entity.pitch);
        double xHeading = -MathsHelper.sin(entity.yaw * 3.141593F / 180.0F);
        double zHeading = MathsHelper.cos(entity.yaw * 3.141593F / 180.0F);
        this.velocityX = 0.5D * xHeading * MathsHelper.cos(entity.pitch / 180.0F * 3.141593F);
        this.velocityY = -0.5D * MathsHelper.sin(entity.pitch / 180.0F * 3.141593F);
        this.velocityZ = 0.5D * zHeading * MathsHelper.cos(entity.pitch / 180.0F * 3.141593F);
        setPosition(entity.x, entity.y, entity.z);
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

    public void w_() {
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        this.prevYaw = this.yaw;
        this.prevPitch = this.pitch;
        if (!this.turningAround) {
            double prevVelX = this.velocityX;
            double prevVelY = this.velocityY;
            double prevVelZ = this.velocityZ;
            move(this.velocityX, this.velocityY, this.velocityZ);
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
            if (this.timeBeforeTurnAround-- <= 0)
                this.turningAround = true;
        } else if (this.returnsTo != null) {
            double deltaX = this.returnsTo.x - this.x;
            double deltaY = this.returnsTo.y - this.y;
            double deltaZ = this.returnsTo.z - this.z;
            double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
            if (length < 1.5D)
                K();
            this.velocityX = 0.5D * deltaX / length;
            this.velocityZ = 0.5D * deltaY / length;
            this.velocityY = 0.5D * deltaZ / length;
            setPosition(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
        } else {
            K();
        }
        determineRotation();
        this.prevBoomerangRotation = this.boomerangRotation;
        this.boomerangRotation += 36.0F;
        while (this.boomerangRotation > 360.0F)
            this.boomerangRotation -= 360.0F;
        List<Entity> entitiesWithin = this.level.getEntities(this, this.boundingBox.b(0.5D, 0.5D, 0.5D));
        for (int i = 0; i < entitiesWithin.size(); i++) {
            Entity e = entitiesWithin.get(i);
            if (e instanceof ItemEntity) {
                this.itemsPickedUp.add(e);
            } else if (e instanceof LivingEntity && e != this.returnsTo) {
                e.stunned = 20;
                e.prevX = e.x;
                e.prevY = e.y;
                e.prevZ = e.z;
                e.prevYaw = e.yaw;
                e.prevPitch = e.pitch;
            }
        }
        for (Entity e : this.itemsPickedUp) {
            if (!e.removed)
                e.e(this.x, this.y, this.z);
        }
        int curChunkX = (int) Math.floor(this.x);
        int curChunkY = (int) Math.floor(this.y);
        int curChunkZ = (int) Math.floor(this.z);
        if (curChunkX != this.chunkX || curChunkY != this.chunkY || curChunkZ != this.chunkZ) {
            this.chunkX = curChunkX;
            this.chunkY = curChunkY;
            this.chunkZ = curChunkZ;
            int blockID = this.level.getTileId(this.chunkX, this.chunkY, this.chunkZ);
            if (blockID == Tile.LEVER.id)
                if (this.returnsTo instanceof Player)
                    Tile.LEVER.activate(this.level, this.chunkX, this.chunkY, this.chunkZ, (Player) this.returnsTo);
        }
    }

    public void K() {
        super.remove();
        if (this.item != null)
            this.item.setDamage(0);
    }

    public void determineRotation() {
        this.yaw = -57.29578F * (float) Math.atan2(this.velocityX, this.velocityZ);
        double xzLength = Math.sqrt(this.velocityZ * this.velocityZ + this.velocityX * this.velocityX);
        this.pitch = -57.29578F * (float) Math.atan2(this.velocityY, xzLength);
    }

    protected void writeCustomDataToTag(CompoundTag nbttagcompound) {
    }

    public void readCustomDataFromTag(CompoundTag nbttagcompound) {
        K();
    }

    public void b(Player entityplayer) {
    }

    public boolean a(Entity entity, int i) {
        return false;
    }

    protected void initDataTracker() {
    }
}