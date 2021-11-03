package io.github.ryuu.adventurecraft.overrides;

import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;

public class uw extends ls implements ff {
    public float a;

    public float b;

    private int c;

    public int attackStrength;

    public uw(Level world) {
        super(world);
        this.c = 0;
        this.O = "/mob/slime.png";
        int i = 1 << this.bs.nextInt(3);
        this.bf = 0.0F;
        this.c = this.bs.nextInt(20) + 10;
        e(i);
        this.attackStrength = -1;
    }

    protected void b() {
        super.b();
        this.bD.a(16, new Byte((byte) 1));
    }

    public void e(int i) {
        this.bD.b(16, new Byte((byte) i));
        b(0.6F * i, 0.6F * i);
        this.Y = i * i;
        e(this.aM, this.aN, this.aO);
    }

    public int v() {
        return this.bD.a(16);
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.put("Size", v() - 1);
    }

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        e(nbttagcompound.e("Size") + 1);
    }

    public void w_() {
        this.b = this.a;
        boolean flag = this.aX;
        super.w_();
        if (this.aX && !flag) {
            int i = v();
            for (int j = 0; j < i * 8; j++) {
                float f = this.bs.nextFloat() * 3.141593F * 2.0F;
                float f1 = this.bs.nextFloat() * 0.5F + 0.5F;
                float f2 = in.a(f) * i * 0.5F * f1;
                float f3 = in.b(f) * i * 0.5F * f1;
                this.aI.a("slime", this.aM + f2, this.aW.b, this.aO + f3, 0.0D, 0.0D, 0.0D);
            }
            if (i > 2)
                this.aI.a((Entity) this, "mob.slime", k(), ((this.bs.nextFloat() - this.bs.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            this.a = -0.5F;
        }
        this.a *= 0.6F;
    }

    protected void f_() {
        X();
        Player entityplayer = this.aI.a((Entity) this, 16.0D);
        if (entityplayer != null)
            a(entityplayer, 10.0F, 20.0F);
        if (this.aX && this.c-- <= 0) {
            this.c = this.bs.nextInt(20) + 10;
            if (entityplayer != null)
                this.c /= 3;
            this.az = true;
            if (v() > 1)
                this.aI.a((net.minecraft.entity.Entity) this, "mob.slime", k(), ((this.bs.nextFloat() - this.bs.nextFloat()) * 0.2F + 1.0F) * 0.8F);
            this.a = 1.0F;
            this.aw = 1.0F - this.bs.nextFloat() * 2.0F;
            this.ax = (1 * v());
            float length = (float) Math.sqrt((this.aw * this.aw + this.ax * this.ax));
            this.aw /= length;
            this.ax /= length;
        } else {
            this.az = false;
            if (this.aX)
                this.aw = this.ax = 0.0F;
        }
    }

    public void K() {
        super.K();
    }

    public void b(Player entityplayer) {
        int i = v();
        int j = i;
        if (this.attackStrength != -1)
            j = this.attackStrength;
        if ((i > 1 || this.attackStrength != -1) && e(entityplayer) && f(entityplayer) < 0.6D * i && entityplayer.a(this, j))
            this.aI.a((Entity) this, "mob.slimeattack", 1.0F, (this.bs.nextFloat() - this.bs.nextFloat()) * 0.2F + 1.0F);
    }

    protected String j_() {
        return "mob.slime";
    }

    protected String i() {
        return "mob.slime";
    }

    protected int j() {
        if (v() == 1)
            return ItemType.aK.bf;
        return 0;
    }

    public boolean d() {
        lm chunk = this.aI.b(in.b(this.aM), in.b(this.aO));
        return ((v() == 1 || this.aI.q > 0) && this.bs.nextInt(10) == 0 && chunk.a(987234911L).nextInt(10) == 0 && this.aN < 16.0D);
    }

    protected float k() {
        return 0.6F;
    }
}
