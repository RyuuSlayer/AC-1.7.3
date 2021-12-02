package io.github.ryuu.adventurecraft.entities;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.extensions.entity.ExEntity;
import io.github.ryuu.adventurecraft.extensions.entity.player.ExPlayerInventory;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.mixin.entity.AccessEntity;
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

    public boolean attachedToSurface;
    public boolean mainHand;
    int timeBeforeTurnAround;
    public boolean turningAround;
    public LivingEntity returnsTo;

    public Entity entityGrabbed;

    ItemInstance item;

    public EntityHookshot(Level world) {
        super(world);
        this.setSize(0.5f, 0.5f);
        this.turningAround = true;
        this.timeBeforeTurnAround = 0;
        ((ExEntity) this).setCollidesWithClipBlocks(false);
    }

    public EntityHookshot(Level world, LivingEntity entity, boolean main, ItemInstance i) {
        this(world);
        this.mainHand = main;
        this.setRotation(entity.yaw, entity.pitch);
        double xHeading = -MathsHelper.sin(entity.yaw * 3.141593f / 180.0f);
        double zHeading = MathsHelper.cos(entity.yaw * 3.141593f / 180.0f);
        this.velocityX = xHeading * (double) MathsHelper.cos(entity.pitch / 180.0f * 3.141593f);
        this.velocityY = -MathsHelper.sin(entity.pitch / 180.0f * 3.141593f);
        this.velocityZ = zHeading * (double) MathsHelper.cos(entity.pitch / 180.0f * 3.141593f);
        this.setHeading();
        this.setPosition(entity.x, entity.y, entity.z);
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
        this.prevYaw = this.yaw = (float) (Math.atan2(this.velocityX, this.velocityZ) * 180.0 / 3.1415927410125732);
        this.prevPitch = this.pitch = (float) (Math.atan2(this.velocityY, f3) * 180.0 / 3.1415927410125732);
    }

    public void setHeadingReverse() {
        float f3 = MathsHelper.sqrt(this.velocityX * this.velocityX + this.velocityZ * this.velocityZ);
        this.prevYaw = this.yaw = (float) (Math.atan2(-this.velocityX, -this.velocityZ) * 180.0 / 3.1415927410125732);
        this.prevPitch = this.pitch = (float) (Math.atan2(-this.velocityY, f3) * 180.0 / 3.1415927410125732);
    }

    @Override
    public void tick() {
        if (this.item != null && this.returnsTo instanceof Player) {
            Player player = (Player) this.returnsTo;
            if (this.mainHand && this.item != player.inventory.getHeldItem()) {
                Items.hookshot.releaseHookshot(this);
            }
            if (!this.mainHand && this.item != ((ExPlayerInventory) player.inventory).getOffhandItem()) {
                Items.hookshot.releaseHookshot(this);
            }
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
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            if (this.velocityX != prevVelX || this.velocityY != prevVelY || this.velocityZ != prevVelZ) {
                Vec3f pos1 = Vec3f.method_1293(this.prevX, this.prevY, this.prevZ);
                HitResult hit = this.level.raycast(pos1, Vec3f.method_1293(this.prevX + 10.0 * prevVelX, this.prevY + 10.0 * prevVelY, this.prevZ + 10.0 * prevVelZ));
                if (hit != null && hit.type == HitType.TILE) {
                    int blockID = this.level.getTileId(hit.x, hit.y, hit.z);
                    if (blockID == Tile.LOG.id || blockID == Tile.WOOD.id || blockID == Blocks.woodBlocks.id || blockID == Blocks.halfSteps3.id) {
                        this.attachedToSurface = true;
                        this.setPosition(hit.field_1988.x, hit.field_1988.y, hit.field_1988.z);
                        this.level.playSound(this, "random.drr", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
                    } else if (blockID != 0) {
                        this.level.playSound(this, Tile.BY_ID[blockID].sounds.getWalkSound(), 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
                    }
                }
                this.turningAround = true;
            } else if (this.timeBeforeTurnAround-- <= 0) {
                this.turningAround = true;
            }
        } else if (this.returnsTo != null) {
            if (this.returnsTo.removed) {
                this.remove();
                return;
            }
            double deltaX = this.returnsTo.x - this.x;
            double deltaY = this.returnsTo.y - this.y;
            double deltaZ = this.returnsTo.z - this.z;
            double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
            this.velocityX = 0.75 * deltaX / length;
            this.velocityY = 0.75 * deltaY / length;
            this.velocityZ = 0.75 * deltaZ / length;
            if (this.attachedToSurface) {
                if (length > 1.2) {
                    this.returnsTo.method_1322(-0.15 * this.velocityX, -0.15 * this.velocityY, -0.15 * this.velocityZ);
                    ((AccessEntity) this.returnsTo).setFallDistance(0.0f);
                } else {
                    this.returnsTo.setVelocity(0.0, 0.0, 0.0);
                }
            } else {
                if (length <= 1.2) {
                    this.remove();
                }
                this.setPosition(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
                this.setHeadingReverse();
            }
        } else {
            this.remove();
        }
        if (!this.turningAround) {
            List entitiesWithin = this.level.getEntities(this, this.boundingBox.expand(0.5, 0.5, 0.5));
            for (Object o : entitiesWithin) {
                Entity e = (Entity) o;
                boolean isItem = e instanceof ItemEntity;
                if (!isItem && (!(e instanceof LivingEntity) || e == this.returnsTo)) continue;
                if (isItem) {
                    this.level.playSound(this, "damage.fallsmall", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
                } else {
                    this.level.playSound(this, "damage.hurtflesh", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
                }
                this.entityGrabbed = e;
                this.turningAround = true;
                break;
            }
        }
        if (this.entityGrabbed != null && !this.entityGrabbed.removed) {
            ((AccessEntity) this.entityGrabbed).setFallDistance(0.0f);
            this.entityGrabbed.setPosition(this.x, this.y, this.z);
        }
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

    @Override
    public void remove() {
        if (this.item != null) {
            this.item.setDamage(0);
        }
        super.remove();
    }
}
