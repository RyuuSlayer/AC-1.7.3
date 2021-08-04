package io.github.ryuu.adventurecraft.overrides;

import net.minecraft.entity.monster.Monster;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;

public class Creeper extends Monster {
    int a;

    int b;

    public Creeper(Level world) {
        super(world);
        this.O = "/mob/creeper.png";
        this.c = 3;
    }

    protected void b() {
        super.b();
        this.bD.a(16, Byte.valueOf((byte) -1));
        this.bD.a(17, Byte.valueOf((byte) 0));
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        if (this.bD.a(17) == 1)
            nbttagcompound.a("powered", true);
    }

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        this.bD.b(17, Byte.valueOf((byte) (nbttagcompound.m("powered") ? 1 : 0)));
    }

    protected void b(sn entity, float f) {
        if (this.aI.B)
            return;
        if (this.a > 0) {
            e(-1);
            this.a--;
            if (this.a < 0)
                this.a = 0;
        }
    }

    public void w_() {
        this.b = this.a;
        if (this.aI.B) {
            int i = v();
            if (i > 0 && this.a == 0)
                this.aI.a(this, "random.fuse", 1.0F, 0.5F);
            this.a += i;
            if (this.a < 0)
                this.a = 0;
            if (this.a >= 30)
                this.a = 30;
        }
        super.w_();
        if (this.d == null && this.a > 0) {
            e(-1);
            this.a--;
            if (this.a < 0)
                this.a = 0;
        }
    }

    protected String j_() {
        return "mob.creeper";
    }

    protected String i() {
        return "mob.creeperdeath";
    }

    public void b(sn entity) {
        super.b(entity);
        if (entity instanceof fr)
            b(ItemType.bd.bf + this.bs.nextInt(2), 1);
    }

    protected void a(sn entity, float f) {
        if (this.aI.B)
            return;
        int i = v();
        if ((i <= 0 && f < 3.0F) || (i > 0 && f < 7.0F)) {
            if (this.a == 0)
                this.aI.a(this, "random.fuse", 1.0F, 0.5F);
            e(1);
            this.a++;
            if (this.a >= 30) {
                if (s()) {
                    this.aI.a(this, this.aM, this.aN, this.aO, this.c * 2.0F);
                } else {
                    this.aI.a(this, this.aM, this.aN, this.aO, this.c);
                }
                K();
            }
            this.e = true;
        } else {
            e(-1);
            this.a--;
            if (this.a < 0)
                this.a = 0;
        }
    }

    public boolean s() {
        return (this.bD.a(17) == 1);
    }

    public float a_(float f) {
        return (this.b + (this.a - this.b) * f) / 28.0F;
    }

    protected int j() {
        return ItemType.K.bf;
    }

    private int v() {
        return this.bD.a(16);
    }

    private void e(int i) {
        this.bD.b(16, Byte.valueOf((byte) i));
    }

    public void a(c entitylightningbolt) {
        super.a(entitylightningbolt);
        this.bD.b(17, Byte.valueOf((byte) 1));
    }
}
