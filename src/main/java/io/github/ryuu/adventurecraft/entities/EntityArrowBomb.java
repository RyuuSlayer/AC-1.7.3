package io.github.ryuu.adventurecraft.entities;

import net.minecraft.entity.player.Player;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.level.Level;

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

    public EntityArrowBomb(Level world, ls entityliving) {
        super(world, entityliving);
        this.fuse = 45;
    }

    public void w_() {
        super.w_();
        this.fuse--;
        if (this.fuse == 0) {
            EntityBomb.explode(this, this.c, this.aI, this.aM, this.aN, this.aO);
            K();
        } else {
            this.aI.a("smoke", this.aM, this.aN, this.aO, 0.0D, 0.0D, 0.0D);
        }
    }

    public void handleHitEntity(vf movingobjectposition) {
        this.aP *= -0.10000000149011612D;
        this.aQ *= -0.10000000149011612D;
        this.aR *= -0.10000000149011612D;
        this.aS += 180.0F;
        this.aU += 180.0F;
        this.k = 0;
    }

    public void b(nu nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("fuse", (byte) this.fuse);
    }

    public void a(nu nbttagcompound) {
        super.a(nbttagcompound);
        this.fuse = nbttagcompound.c("fuse") & 0xFF;
    }

    public void b(Player entityplayer) {
    }

    public boolean a(sn entity, int i) {
        if (!this.be) {
            ai();
            EntityBomb.explode(this, this.c, this.aI, this.aM, this.aN, this.aO);
        }
        return false;
    }
}
