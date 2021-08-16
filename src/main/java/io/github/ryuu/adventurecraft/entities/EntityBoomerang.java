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
        b(0.5F, 0.0625F);
        this.bf = 0.03125F;
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
        c(entity.aS, entity.aT);
        double xHeading = -in.a(entity.aS * 3.141593F / 180.0F);
        double zHeading = in.b(entity.aS * 3.141593F / 180.0F);
        this.aP = 0.5D * xHeading * in.b(entity.aT / 180.0F * 3.141593F);
        this.aQ = -0.5D * in.a(entity.aT / 180.0F * 3.141593F);
        this.aR = 0.5D * zHeading * in.b(entity.aT / 180.0F * 3.141593F);
        e(entity.aM, entity.aN, entity.aO);
        this.aJ = this.aM;
        this.aK = this.aN;
        this.aL = this.aO;
        this.timeBeforeTurnAround = 30;
        this.turningAround = false;
        this.returnsTo = entity;
        this.chunkX = (int) Math.floor(this.aM);
        this.chunkY = (int) Math.floor(this.aN);
        this.chunkZ = (int) Math.floor(this.aO);
    }

    public void w_() {
        this.aJ = this.aM;
        this.aK = this.aN;
        this.aL = this.aO;
        this.aU = this.aS;
        this.aV = this.aT;
        if (!this.turningAround) {
            double prevVelX = this.aP;
            double prevVelY = this.aQ;
            double prevVelZ = this.aR;
            b(this.aP, this.aQ, this.aR);
            boolean bounced = false;
            if (this.aP != prevVelX) {
                this.aP = -prevVelX;
                bounced = true;
            }
            if (this.aQ != prevVelY) {
                this.aQ = -prevVelY;
                bounced = true;
            }
            if (this.aR != prevVelZ) {
                this.aR = -prevVelZ;
                bounced = true;
            }
            if (bounced) {
                this.aP *= this.bounceFactor;
                this.aQ *= this.bounceFactor;
                this.aR *= this.bounceFactor;
            }
            if (this.timeBeforeTurnAround-- <= 0)
                this.turningAround = true;
        } else if (this.returnsTo != null) {
            double deltaX = this.returnsTo.aM - this.aM;
            double deltaY = this.returnsTo.aN - this.aN;
            double deltaZ = this.returnsTo.aO - this.aO;
            double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
            if (length < 1.5D)
                K();
            this.aP = 0.5D * deltaX / length;
            this.aQ = 0.5D * deltaY / length;
            this.aR = 0.5D * deltaZ / length;
            e(this.aM + this.aP, this.aN + this.aQ, this.aO + this.aR);
        } else {
            K();
        }
        determineRotation();
        this.prevBoomerangRotation = this.boomerangRotation;
        this.boomerangRotation += 36.0F;
        while (this.boomerangRotation > 360.0F)
            this.boomerangRotation -= 360.0F;
        List<Entity> entitiesWithin = this.aI.b(this, this.aW.b(0.5D, 0.5D, 0.5D));
        for (int i = 0; i < entitiesWithin.size(); i++) {
            Entity e = entitiesWithin.get(i);
            if (e instanceof ItemEntity) {
                this.itemsPickedUp.add(e);
            } else if (e instanceof LivingEntity && e != this.returnsTo) {
                e.stunned = 20;
                e.aJ = e.aM;
                e.aK = e.aN;
                e.aL = e.aO;
                e.aU = e.aS;
                e.aV = e.aT;
            }
        }
        for (Entity e : this.itemsPickedUp) {
            if (!e.be)
                e.e(this.aM, this.aN, this.aO);
        }
        int curChunkX = (int) Math.floor(this.aM);
        int curChunkY = (int) Math.floor(this.aN);
        int curChunkZ = (int) Math.floor(this.aO);
        if (curChunkX != this.chunkX || curChunkY != this.chunkY || curChunkZ != this.chunkZ) {
            this.chunkX = curChunkX;
            this.chunkY = curChunkY;
            this.chunkZ = curChunkZ;
            int blockID = this.aI.a(this.chunkX, this.chunkY, this.chunkZ);
            if (blockID == Tile.aK.bn)
                if (this.returnsTo instanceof Player)
                    Tile.aK.a(this.aI, this.chunkX, this.chunkY, this.chunkZ, (Player) this.returnsTo);
        }
    }

    public void K() {
        super.K();
        if (this.item != null)
            this.item.b(0);
    }

    public void determineRotation() {
        this.aS = -57.29578F * (float) Math.atan2(this.aP, this.aR);
        double xzLength = Math.sqrt(this.aR * this.aR + this.aP * this.aP);
        this.aT = -57.29578F * (float) Math.atan2(this.aQ, xzLength);
    }

    protected void b(CompoundTag nbttagcompound) {
    }

    public void a(CompoundTag nbttagcompound) {
        K();
    }

    public void b(Player entityplayer) {
    }

    public boolean a(Entity entity, int i) {
        return false;
    }

    protected void b() {
    }
}