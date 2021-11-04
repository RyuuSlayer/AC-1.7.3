package io.github.ryuu.adventurecraft.overrides;

import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;

import java.util.List;

public class ZombiePigman extends Zombie {
    private int a;

    private int b;

    public ZombiePigman(Level world) {
        super(world);
        this.a = 0;
        this.b = 0;
        this.O = "/mob/pigzombie.png";
        this.aB = 0.5F;
        this.c = 5;
        this.bC = true;
        this.heldItem = new ItemInstance(ItemType.E, 1);
    }

    public void w_() {
        this.aB = (this.d == null) ? 0.5F : 0.95F;
        if (this.b > 0 && --this.b == 0)
            this.aI.a((Entity) this, "mob.zombiepig.zpigangry", k() * 2.0F, ((this.bs.nextFloat() - this.bs.nextFloat()) * 0.2F + 1.0F) * 1.8F);
        super.w_();
    }

    public boolean d() {
        return (this.aI.q > 0 && this.aI.a(this.aW) && this.aI.a((Entity) this, this.aW).size() == 0 && !this.aI.b(this.aW));
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Anger", (short) this.a);
    }

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        this.a = nbttagcompound.d("Anger");
    }

    protected Entity g_() {
        if (this.a == 0)
            return null;
        return super.g_();
    }

    public void o() {
        super.o();
    }

    public boolean a(Entity entity, int i) {
        if (entity instanceof Player) {
            List<net.minecraft.entity.Entity> list = this.aI.b((net.minecraft.entity.Entity) this, this.aW.b(32.0D, 32.0D, 32.0D));
            for (int j = 0; j < list.size(); j++) {
                net.minecraft.entity.Entity entity1 = list.get(j);
                if (entity1 instanceof ZombiePigman) {
                    ZombiePigman entitypigzombie = (ZombiePigman) entity1;
                    entitypigzombie.d(entity);
                }
            }
            d(entity);
        }
        return super.a(entity, i);
    }

    private void d(Entity entity) {
        this.d = entity;
        this.a = 400 + this.bs.nextInt(400);
        this.b = this.bs.nextInt(40);
    }

    protected String g() {
        return "mob.zombiepig.zpig";
    }

    protected String j_() {
        return "mob.zombiepig.zpighurt";
    }

    protected String i() {
        return "mob.zombiepig.zpigdeath";
    }

    protected int j() {
        return ItemType.ap.bf;
    }
}
