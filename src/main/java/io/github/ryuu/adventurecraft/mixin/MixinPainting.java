package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathsHelper;

import java.util.ArrayList;
import java.util.List;

public class MixinPainting extends Entity {
    private final int f;

    public int a;

    public int b;

    public int c;

    public int d;

    public iq e;

    public MixinPainting(Level world) {
        super(world);
        this.f = 0;
        this.a = 0;
        this.bf = 0.0F;
        b(0.5F, 0.5F);
    }

    public MixinPainting(Level world, int i, int j, int k, int l) {
        this(world);
        this.b = i;
        this.c = j;
        this.d = k;
        ArrayList<iq> arraylist = new ArrayList();
        iq[] aenumart = iq.values();
        int i1 = aenumart.length;
        for (int j1 = 0; j1 < i1; j1++) {
            iq enumart = aenumart[j1];
            this.e = enumart;
            b(l);
            if (k())
                arraylist.add(enumart);
        }
        if (arraylist.size() > 0)
            this.e = arraylist.get(this.bs.nextInt(arraylist.size()));
        b(l);
    }

    public MixinPainting(Level world, int i, int j, int k, int l, String s) {
        this(world);
        this.b = i;
        this.c = j;
        this.d = k;
        iq[] aenumart = iq.values();
        int i1 = aenumart.length;
        int j1 = 0;
        while (j1 < i1) {
            iq enumart = aenumart[j1];
            if (enumart.A.equals(s)) {
                this.e = enumart;
                break;
            }
            j1++;
        }
        b(l);
    }

    protected void b() {
    }

    public void b(int i) {
        this.a = i;
        this.aU = this.aS = (i * 90);
        float f = this.e.B;
        float f1 = this.e.C;
        float f2 = this.e.B;
        if (i == 0 || i == 2) {
            f2 = 0.5F;
        } else {
            f = 0.5F;
        }
        f /= 32.0F;
        f1 /= 32.0F;
        f2 /= 32.0F;
        float f3 = this.b + 0.5F;
        float f4 = this.c + 0.5F;
        float f5 = this.d + 0.5F;
        float f6 = 0.5625F;
        if (i == 0)
            f5 -= f6;
        if (i == 1)
            f3 -= f6;
        if (i == 2)
            f5 += f6;
        if (i == 3)
            f3 += f6;
        if (i == 0)
            f3 -= c(this.e.B);
        if (i == 1)
            f5 += c(this.e.B);
        if (i == 2)
            f3 += c(this.e.B);
        if (i == 3)
            f5 -= c(this.e.B);
        f4 += c(this.e.C);
        e(f3, f4, f5);
        float f7 = -0.00625F;
        this.aW.c((f3 - f - f7), (f4 - f1 - f7), (f5 - f2 - f7), (f3 + f + f7), (f4 + f1 + f7), (f5 + f2 + f7));
    }

    private float c(int i) {
        if (i == 32)
            return 0.5F;
        return (i != 64) ? 0.0F : 0.5F;
    }

    public void w_() {
    }

    public boolean k() {
        if (this.aI.a(this, this.aW).size() > 0)
            return false;
        int i = this.e.B / 16;
        int j = this.e.C / 16;
        int k = this.b;
        int l = this.c;
        int i1 = this.d;
        if (this.a == 0)
            k = MathsHelper.b(this.aM - (this.e.B / 32.0F));
        if (this.a == 1)
            i1 = MathsHelper.b(this.aO - (this.e.B / 32.0F));
        if (this.a == 2)
            k = MathsHelper.b(this.aM - (this.e.B / 32.0F));
        if (this.a == 3)
            i1 = MathsHelper.b(this.aO - (this.e.B / 32.0F));
        l = MathsHelper.b(this.aN - (this.e.C / 32.0F));
        for (int j1 = 0; j1 < i; j1++) {
            for (int k1 = 0; k1 < j; k1++) {
                ln material;
                if (this.a == 0 || this.a == 2) {
                    material = this.aI.f(k + j1, l + k1, this.d);
                } else {
                    material = this.aI.f(this.b, l + k1, i1 + j1);
                }
                if (!material.a())
                    return false;
            }
        }
        List list = this.aI.b(this, this.aW);
        for (int l1 = 0; l1 < list.size(); l1++) {
            if (list.get(l1) instanceof Painting)
                return false;
        }
        return true;
    }

    public boolean h_() {
        return true;
    }

    public boolean a(net.minecraft.entity.Entity entity, int i) {
        if (!this.be && !this.aI.B) {
            K();
            ai();
            this.aI.b((Entity) new ItemEntity(this.aI, this.aM, this.aN, this.aO, new ItemInstance(ItemType.aq)));
        }
        return true;
    }

    public void b(CompoundTag nbttagcompound) {
        nbttagcompound.put("Dir", (byte) this.a);
        nbttagcompound.put("Motive", this.e.A);
        nbttagcompound.put("TileX", this.b);
        nbttagcompound.put("TileY", this.c);
        nbttagcompound.put("TileZ", this.d);
    }

    public void a(CompoundTag nbttagcompound) {
        this.a = nbttagcompound.c("Dir");
        this.b = nbttagcompound.e("TileX");
        this.c = nbttagcompound.e("TileY");
        this.d = nbttagcompound.e("TileZ");
        String s = nbttagcompound.i("Motive");
        iq[] aenumart = iq.values();
        int i = aenumart.length;
        for (int j = 0; j < i; j++) {
            iq enumart = aenumart[j];
            if (enumart.A.equals(s))
                this.e = enumart;
        }
        if (this.e == null)
            this.e = iq.a;
        b(this.a);
    }

    public void b(double d, double d1, double d2) {
        if (!this.aI.B && d * d + d1 * d1 + d2 * d2 > 0.0D) {
            K();
            this.aI.b((Entity) new ItemEntity(this.aI, this.aM, this.aN, this.aO, new ItemInstance(ItemType.aq)));
        }
    }

    public void d(double d, double d1, double d2) {
        if (!this.aI.B && d * d + d1 * d1 + d2 * d2 > 0.0D) {
            K();
            this.aI.b((Entity) new ItemEntity(this.aI, this.aM, this.aN, this.aO, new ItemInstance(ItemType.aq)));
        }
    }
}
