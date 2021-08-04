package io.github.ryuu.adventurecraft.entities;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.items.Items;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.util.io.CompoundTag;

import java.util.List;

public class EntityHookshot extends sn {
    int timeBeforeTurnAround;

    boolean turningAround;

    public boolean attachedToSurface;

    public boolean mainHand;

    ls returnsTo;

    sn entityGrabbed;

    ItemInstance item;

    public EntityHookshot(Level world) {
        super(world);
        b(0.5F, 0.5F);
        this.turningAround = true;
        this.timeBeforeTurnAround = 0;
        this.collidesWithClipBlocks = false;
    }

    public EntityHookshot(Level world, ls entity, boolean main, ItemInstance i) {
        this(world);
        this.mainHand = main;
        c(entity.aS, entity.aT);
        double xHeading = -in.a(entity.aS * 3.141593F / 180.0F);
        double zHeading = in.b(entity.aS * 3.141593F / 180.0F);
        this.aP = xHeading * in.b(entity.aT / 180.0F * 3.141593F);
        this.aQ = -in.a(entity.aT / 180.0F * 3.141593F);
        this.aR = zHeading * in.b(entity.aT / 180.0F * 3.141593F);
        setHeading();
        e(entity.aM, entity.aN, entity.aO);
        this.aJ = this.aM;
        this.aK = this.aN;
        this.aL = this.aO;
        this.timeBeforeTurnAround = 20;
        this.turningAround = false;
        this.returnsTo = entity;
        this.attachedToSurface = false;
        this.item = i;
    }

    public void setHeading() {
        float f3 = in.a(this.aP * this.aP + this.aR * this.aR);
        this.aU = this.aS = (float) (Math.atan2(this.aP, this.aR) * 180.0D / 3.1415927410125732D);
        this.aV = this.aT = (float) (Math.atan2(this.aQ, f3) * 180.0D / 3.1415927410125732D);
    }

    public void setHeadingReverse() {
        float f3 = in.a(this.aP * this.aP + this.aR * this.aR);
        this.aU = this.aS = (float) (Math.atan2(-this.aP, -this.aR) * 180.0D / 3.1415927410125732D);
        this.aV = this.aT = (float) (Math.atan2(-this.aQ, f3) * 180.0D / 3.1415927410125732D);
    }

    public void w_() {
        if (this.item != null && this.returnsTo instanceof gs) {
            Player player = (Player) this.returnsTo;
            if (this.mainHand && this.item != player.c.b())
                Items.hookshot.releaseHookshot(this);
            if (!this.mainHand && this.item != player.c.getOffhandItem())
                Items.hookshot.releaseHookshot(this);
        }
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
            if (this.aP != prevVelX || this.aQ != prevVelY || this.aR != prevVelZ) {
                bt pos1 = bt.a(this.aJ, this.aK, this.aL);
                bt pos2 = bt.a(this.aJ + 10.0D * prevVelX, this.aK + 10.0D * prevVelY, this.aL + 10.0D * prevVelZ);
                vf hit = this.aI.a(pos1, pos2);
                if (hit != null && hit.a == jg.a) {
                    int blockID = this.aI.a(hit.b, hit.c, hit.d);
                    if (blockID == Tile.K.bn || blockID == Tile.y.bn || blockID == Blocks.woodBlocks.bn || blockID == Blocks.halfSteps3.bn) {
                        this.attachedToSurface = true;
                        e(hit.f.a, hit.f.b, hit.f.c);
                        this.aI.a(this, "random.drr", 1.0F, 1.2F / (this.bs.nextFloat() * 0.2F + 0.9F));
                    } else if (blockID != 0) {
                        this.aI.a(this, (Tile.m[blockID]).by.d(), 1.0F, 1.2F / (this.bs.nextFloat() * 0.2F + 0.9F));
                    }
                }
                this.turningAround = true;
            } else if (this.timeBeforeTurnAround-- <= 0) {
                this.turningAround = true;
            }
        } else if (this.returnsTo != null) {
            if (this.returnsTo.be) {
                K();
                return;
            }
            double deltaX = this.returnsTo.aM - this.aM;
            double deltaY = this.returnsTo.aN - this.aN;
            double deltaZ = this.returnsTo.aO - this.aO;
            double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
            this.aP = 0.75D * deltaX / length;
            this.aQ = 0.75D * deltaY / length;
            this.aR = 0.75D * deltaZ / length;
            if (this.attachedToSurface) {
                if (length > 1.2D) {
                    this.returnsTo.d(-0.15D * this.aP, -0.15D * this.aQ, -0.15D * this.aR);
                    this.returnsTo.bk = 0.0F;
                } else {
                    this.returnsTo.a(0.0D, 0.0D, 0.0D);
                }
            } else {
                if (length <= 1.2D)
                    K();
                e(this.aM + this.aP, this.aN + this.aQ, this.aO + this.aR);
                setHeadingReverse();
            }
        } else {
            K();
        }
        if (!this.turningAround) {
            List<sn> entitiesWithin = this.aI.b(this, this.aW.b(0.5D, 0.5D, 0.5D));
            for (int i = 0; i < entitiesWithin.size(); i++) {
                sn e = entitiesWithin.get(i);
                boolean isItem = e instanceof hl;
                if (isItem || (e instanceof ls && e != this.returnsTo)) {
                    if (isItem) {
                        this.aI.a(this, "damage.fallsmall", 1.0F, 1.2F / (this.bs.nextFloat() * 0.2F + 0.9F));
                    } else {
                        this.aI.a(this, "damage.hurtflesh", 1.0F, 1.2F / (this.bs.nextFloat() * 0.2F + 0.9F));
                    }
                    this.entityGrabbed = e;
                    this.turningAround = true;
                    break;
                }
            }
        }
        if (this.entityGrabbed != null && !this.entityGrabbed.be) {
            this.entityGrabbed.bk = 0.0F;
            this.entityGrabbed.e(this.aM, this.aN, this.aO);
        }
    }

    protected void b(CompoundTag nbttagcompound) {
    }

    public void a(CompoundTag nbttagcompound) {
        K();
    }

    public void b(Player entityplayer) {
    }

    public boolean a(sn entity, int i) {
        return false;
    }

    protected void b() {
    }

    public void K() {
        if (this.item != null)
            this.item.b(0);
        super.K();
    }
}