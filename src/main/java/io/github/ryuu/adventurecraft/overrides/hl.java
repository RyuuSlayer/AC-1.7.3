package io.github.ryuu.adventurecraft.overrides;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;

public class hl extends Entity {
    public ItemInstance a;

    private int e;

    public int b;

    public int c;

    private int f;

    public float d;

    public hl(Level world, double d, double d1, double d2, ItemInstance itemstack) {
        super(world);
        this.b = 0;
        this.f = 5;
        this.d = (float) (Math.random() * Math.PI * 2.0D);
        b(0.25F, 0.25F);
        this.bf = this.bh / 2.0F;
        e(d, d1, d2);
        this.a = itemstack;
        this.aS = (float) (Math.random() * 360.0D);
        this.aP = (float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D);
        this.aQ = 0.20000000298023224D;
        this.aR = (float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D);
        if (ItemType.c[this.a.c] == null)
            K();
    }

    protected boolean n() {
        return false;
    }

    public hl(Level world) {
        super(world);
        this.b = 0;
        this.f = 5;
        this.d = (float) (Math.random() * Math.PI * 2.0D);
        b(0.25F, 0.25F);
        this.bf = this.bh / 2.0F;
    }

    protected void b() {
    }

    public void w_() {
        super.w_();
        if (this.c > 0)
            this.c--;
        this.aJ = this.aM;
        this.aK = this.aN;
        this.aL = this.aO;
        this.aQ -= 0.03999999910593033D;
        if (this.aI.f(in.b(this.aM), in.b(this.aN), in.b(this.aO)) == ln.h) {
            this.aQ = 0.20000000298023224D;
            this.aP = ((this.bs.nextFloat() - this.bs.nextFloat()) * 0.2F);
            this.aR = ((this.bs.nextFloat() - this.bs.nextFloat()) * 0.2F);
            this.aI.a(this, "random.fizz", 0.4F, 2.0F + this.bs.nextFloat() * 0.4F);
        }
        c(this.aM, (this.aW.b + this.aW.e) / 2.0D, this.aO);
        b(this.aP, this.aQ, this.aR);
        float f = 0.98F;
        if (this.aX) {
            f = 0.5880001F;
            int i = this.aI.a(in.b(this.aM), in.b(this.aW.b) - 1, in.b(this.aO));
            if (i > 0)
                f = (Tile.m[i]).bB * 0.98F;
        }
        this.aP *= f;
        this.aQ *= 0.9800000190734863D;
        this.aR *= f;
        if (this.aX)
            this.aQ *= -0.5D;
        this.e++;
        this.b++;
        if (this.b >= 6000)
            K();
    }

    public boolean k_() {
        return this.aI.a(this.aW, ln.g, this);
    }

    protected void a(int i) {
        a(null, i);
    }

    public boolean a(Entity entity, int i) {
        ai();
        this.f -= i;
        if (this.f <= 0)
            K();
        return false;
    }

    public void b(CompoundTag nbttagcompound) {
        nbttagcompound.put("Health", (short) (byte) this.f);
        nbttagcompound.put("Age", (short) this.b);
        nbttagcompound.put("Item", this.a.a(new CompoundTag()));
    }

    public void a(CompoundTag nbttagcompound) {
        this.f = nbttagcompound.d("Health") & 0xFF;
        this.b = nbttagcompound.d("Age");
        CompoundTag nbttagcompound1 = nbttagcompound.k("Item");
        this.a = new ItemInstance(nbttagcompound1);
    }

    public void b(Player entityplayer) {
        if (this.aI.B)
            return;
        int i = this.a.a;
        if (this.c == 0 && entityplayer.c.a(this.a)) {
            if (this.a.c == Tile.K.bn)
                entityplayer.a((vr) ep.g);
            if (this.a.c == ItemType.aD.bf)
                entityplayer.a((vr) ep.t);
            this.aI.a(this, "random.pop", 0.2F, ((this.bs.nextFloat() - this.bs.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            entityplayer.b(this, i);
            if (this.a.a <= 0)
                K();
        }
    }
}
