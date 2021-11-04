package io.github.ryuu.adventurecraft.entities;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.items.Items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitType;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3f;

import java.util.List;

public class EntityHookshot extends Entity {
    int timeBeforeTurnAround;

    boolean turningAround;

    public boolean attachedToSurface;

    public boolean mainHand;

    LivingEntity returnsTo;

    Entity entityGrabbed;

    ItemInstance item;

    public EntityHookshot(Level world) {
        super(world);
        setSize(0.5F, 0.5F);
        this.turningAround = true;
        this.timeBeforeTurnAround = 0;
        this.collidesWithClipBlocks = false;
    }

    public EntityHookshot(Level world, LivingEntity entity, boolean main, ItemInstance i) {
        this(world);
        this.mainHand = main;
        setRotation(entity.yaw, entity.pitch);
        double xHeading = -MathsHelper.sin(entity.yaw * 3.141593F / 180.0F);
        double zHeading = MathsHelper.cos(entity.yaw * 3.141593F / 180.0F);
        this.velocityX = xHeading * MathsHelper.cos(entity.pitch / 180.0F * 3.141593F);
        this.velocityY = -MathsHelper.sin(entity.pitch / 180.0F * 3.141593F);
        this.velocityZ = zHeading * MathsHelper.cos(entity.pitch / 180.0F * 3.141593F);
        setHeading();
        setPosition(entity.x, entity.y, entity.z);
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        this.timeBeforeTurnAround = 20;
        this.turningAround = false;
        this.returnsTo = entity;
        this.attachedToSurface = false;
        this.item = i;
    }

    public void setHeading() {
        float f3 = MathsHelper.sqrt(this.velocityX * this.velocityX + this.velocityZ * this.velocityZ);
        this.prevYaw = this.yaw = (float) (Math.atan2(this.velocityX, this.velocityZ) * 180.0D / 3.1415927410125732D);
        this.prevPitch = this.pitch = (float) (Math.atan2(this.velocityY, f3) * 180.0D / 3.1415927410125732D);
    }

    public void setHeadingReverse() {
        float f3 = MathsHelper.sqrt(this.velocityX * this.velocityX + this.velocityZ * this.velocityZ);
        this.prevYaw = this.yaw = (float) (Math.atan2(-this.velocityX, -this.velocityZ) * 180.0D / 3.1415927410125732D);
        this.prevPitch = this.pitch = (float) (Math.atan2(-this.velocityY, f3) * 180.0D / 3.1415927410125732D);
    }

    @Override
    public void tick() {
        if (this.item != null && this.returnsTo instanceof Player) {
            Player player = (Player) this.returnsTo;
            if (this.mainHand && this.item != player.inventory.getHeldItem())
                Items.hookshot.releaseHookshot(this);
            if (!this.mainHand && this.item != player.inventory.getOffhandItem())
                Items.hookshot.releaseHookshot(this);
        }
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
            if (this.velocityX != prevVelX || this.velocityY != prevVelY || this.velocityZ != prevVelZ) {
                Vec3f pos1 = Vec3f.method_1293(this.prevX, this.prevY, this.prevZ);
                Vec3f pos2 = Vec3f.method_1293(this.prevX + 10.0D * prevVelX, this.prevY + 10.0D * prevVelY, this.prevZ + 10.0D * prevVelZ);
                HitResult hit = this.level.raycast(pos1, pos2);
                if (hit != null && hit.type == HitType.TILE) { // TODO: this HitType had some strange mapping
                    int blockID = this.level.getTileId(hit.x, hit.y, hit.z);
                    if (blockID == Tile.LOG.id || blockID == Tile.WOOD.id || blockID == Blocks.woodBlocks.id || blockID == Blocks.halfSteps3.id) {
                        this.attachedToSurface = true;
                        setPosition(hit.field_1988.x, hit.field_1988.y, hit.field_1988.z);
                        this.level.playSound(this, "random.drr", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                    } else if (blockID != 0) {
                        this.level.playSound(this, (Tile.BY_ID[blockID]).sounds.getWalkSound(), 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                    }
                }
                this.turningAround = true;
            } else if (this.timeBeforeTurnAround-- <= 0) {
                this.turningAround = true;
            }
        } else if (this.returnsTo != null) {
            if (this.returnsTo.removed) {
                remove();
                return;
            }
            double deltaX = this.returnsTo.x - this.x;
            double deltaY = this.returnsTo.y - this.y;
            double deltaZ = this.returnsTo.z - this.z;
            double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
            this.velocityX = 0.75D * deltaX / length;
            this.velocityY = 0.75D * deltaY / length;
            this.velocityZ = 0.75D * deltaZ / length;
            if (this.attachedToSurface) {
                if (length > 1.2D) {
                    this.returnsTo.method_1322(-0.15D * this.velocityX, -0.15D * this.velocityY, -0.15D * this.velocityZ);
                    this.returnsTo.fallDistance = 0.0F;
                } else {
                    this.returnsTo.setVelocity(0.0D, 0.0D, 0.0D);
                }
            } else {
                if (length <= 1.2D)
                    remove();
                setPosition(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
                setHeadingReverse();
            }
        } else {
            remove();
        }
        if (!this.turningAround) {
            List<Entity> entitiesWithin = this.level.getEntities(this, this.boundingBox.expand(0.5D, 0.5D, 0.5D));
            for (int i = 0; i < entitiesWithin.size(); i++) {
                Entity e = entitiesWithin.get(i);
                boolean isItem = e instanceof ItemEntity;
                if (isItem || (e instanceof LivingEntity && e != this.returnsTo)) {
                    if (isItem) {
                        this.level.playSound(this, "damage.fallsmall", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                    } else {
                        this.level.playSound(this, "damage.hurtflesh", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                    }
                    this.entityGrabbed = e;
                    this.turningAround = true;
                    break;
                }
            }
        }
        if (this.entityGrabbed != null && !this.entityGrabbed.removed) {
            this.entityGrabbed.fallDistance = 0.0F;
            this.entityGrabbed.setPosition(this.x, this.y, this.z);
        }
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag nbttagcompound) {
    }

    @Override
    public void readCustomDataFromTag(CompoundTag nbttagcompound) {
        remove();
    }

    @Override
    public void onPlayerCollision(Player entityplayer) {
    }

    @Override
    public boolean damage(Entity entity, int i) {
        return false;
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    public void remove() {
        if (this.item != null)
            this.item.setDamage(0);
        super.remove();
    }
}