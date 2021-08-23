package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.items.Items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;

public final class ItemInstance {
    public int a;

    public int b;

    public int c;

    private int d;

    public int timeLeft;

    public boolean isReloading;

    public boolean justReloaded;

    public ItemInstance(Tile block) {
        this(block, 1);
    }

    public ItemInstance(Tile block, int i) {
        this(block.bn, i, 0);
    }

    public ItemInstance(Tile block, int i, int j) {
        this(block.bn, i, j);
    }

    public ItemInstance(ItemType item) {
        this(item.bf, 1, 0);
    }

    public ItemInstance(ItemType item, int i) {
        this(item.bf, i, 0);
    }

    public ItemInstance(ItemType item, int i, int j) {
        this(item.bf, i, j);
    }

    public ItemInstance(int i, int j, int k) {
        this.isReloading = false;
        this.justReloaded = false;
        this.a = 0;
        this.c = i;
        this.a = j;
        this.d = k;
    }

    public ItemInstance(CompoundTag nbttagcompound) {
        this.isReloading = false;
        this.justReloaded = false;
        this.a = 0;
        b(nbttagcompound);
    }

    public ItemInstance a(int i) {
        this.a -= i;
        return new ItemInstance(this.c, i, this.d);
    }

    public ItemType a() {
        return ItemType.c[this.c];
    }

    public int b() {
        return a().b(this);
    }

    public boolean a(Player entityplayer, Level world, int i, int j, int k, int l) {
        boolean flag = a().a(this, entityplayer, world, i, j, k, l);
        if (flag)
            entityplayer.a(jl.E[this.c], 1);
        return flag;
    }

    public boolean useItemLeftClick(Player entityplayer, Level world, int i, int j, int k, int l) {
        return a().onItemUseLeftClick(this, entityplayer, world, i, j, k, l);
    }

    public float a(Tile block) {
        return a().a(this, block);
    }

    public ItemInstance a(Level world, Player entityplayer) {
        return a().a(this, world, entityplayer);
    }

    public nu a(CompoundTag nbttagcompound) {
        nbttagcompound.put("id", (short) this.c);
        nbttagcompound.put("Count", this.a);
        nbttagcompound.put("Damage", (short) this.d);
        return nbttagcompound;
    }

    public void b(CompoundTag nbttagcompound) {
        this.c = nbttagcompound.d("id");
        this.a = nbttagcompound.e("Count");
        this.d = nbttagcompound.d("Damage");
        if (this.c == Items.boomerang.bf)
            this.d = 0;
    }

    public int c() {
        return a().d();
    }

    public boolean d() {
        return (c() > 1 && (!e() || !g()));
    }

    public boolean e() {
        return (ItemType.c[this.c].f() > 0);
    }

    public boolean f() {
        return ItemType.c[this.c].e();
    }

    public boolean g() {
        return (e() && this.d > 0);
    }

    public int h() {
        return this.d;
    }

    public int i() {
        return this.d;
    }

    public void b(int i) {
        this.d = i;
    }

    public int j() {
        return ItemType.c[this.c].f();
    }

    public void a(int i, sn entity) {
        if (!e())
            return;
        this.d += i;
        if (this.d > j()) {
            if (entity instanceof Player)
                ((Player) entity).a(jl.F[this.c], 1);
            this.a--;
            if (this.a < 0)
                this.a = 0;
            this.d = 0;
        }
    }

    public void a(LivingEntity entityliving, Player entityplayer) {
        boolean flag = ItemType.c[this.c].a(this, entityliving, entityplayer);
        if (flag)
            entityplayer.a(jl.E[this.c], 1);
    }

    public void a(int i, int j, int k, int l, Player entityplayer) {
        boolean flag = ItemType.c[this.c].a(this, i, j, k, l, entityplayer);
        if (flag)
            entityplayer.a(jl.E[this.c], 1);
    }

    public int a(Entity entity) {
        return ItemType.c[this.c].a(entity);
    }

    public boolean b(Tile block) {
        return ItemType.c[this.c].a(block);
    }

    public void a(Player entityplayer) {
    }

    public void a(LivingEntity entityliving) {
        ItemType.c[this.c].a(this, entityliving);
    }

    public ItemInstance k() {
        return new ItemInstance(this.c, this.a, this.d);
    }

    public static boolean a(ItemInstance itemstack, ItemInstance itemstack1) {
        if (itemstack == null && itemstack1 == null)
            return true;
        if (itemstack == null || itemstack1 == null)
            return false;
        return itemstack.d(itemstack1);
    }

    private boolean d(ItemInstance itemstack) {
        if (this.a != itemstack.a)
            return false;
        if (this.c != itemstack.c)
            return false;
        return (this.d == itemstack.d);
    }

    public boolean a(ItemInstance itemstack) {
        return (this.c == itemstack.c && this.d == itemstack.d);
    }

    public String l() {
        return ItemType.c[this.c].a(this);
    }

    public static ItemInstance b(ItemInstance itemstack) {
        return (itemstack != null) ? itemstack.k() : null;
    }

    public String toString() {
        return this.a + "x" + ItemType.c[this.c].a() + "@" + this.d;
    }

    public void a(Level world, sn entity, int i, boolean flag) {
        if (this.b > 0)
            this.b--;
        ItemType.c[this.c].a(this, world, entity, i, flag);
    }

    public void b(Level world, Player entityplayer) {
        entityplayer.a(jl.D[this.c], this.a);
        ItemType.c[this.c].b(this, world, entityplayer);
    }

    public boolean c(ItemInstance itemstack) {
        return (this.c == itemstack.c && this.a == itemstack.a && this.d == itemstack.d);
    }
}
