package io.github.ryuu.adventurecraft.overrides;

public final class iz {
    public int a;

    public int b;

    public int c;

    private int d;

    public int timeLeft;

    public boolean isReloading;

    public boolean justReloaded;

    public iz(Tile block) {
        this(block, 1);
    }

    public iz(Tile block, int i) {
        this(block.bn, i, 0);
    }

    public iz(Tile block, int i, int j) {
        this(block.bn, i, j);
    }

    public iz(ItemType item) {
        this(item.bf, 1, 0);
    }

    public iz(ItemType item, int i) {
        this(item.bf, i, 0);
    }

    public iz(ItemType item, int i, int j) {
        this(item.bf, i, j);
    }

    public iz(int i, int j, int k) {
        this.isReloading = false;
        this.justReloaded = false;
        this.a = 0;
        this.c = i;
        this.a = j;
        this.d = k;
    }

    public iz(nu nbttagcompound) {
        this.isReloading = false;
        this.justReloaded = false;
        this.a = 0;
        b(nbttagcompound);
    }

    public iz a(int i) {
        this.a -= i;
        return new iz(this.c, i, this.d);
    }

    public ItemType a() {
        return ItemType.c[this.c];
    }

    public int b() {
        return a().b(this);
    }

    public boolean a(gs entityplayer, fd world, int i, int j, int k, int l) {
        boolean flag = a().a(this, entityplayer, world, i, j, k, l);
        if (flag)
            entityplayer.a(jl.E[this.c], 1);
        return flag;
    }

    public boolean useItemLeftClick(gs entityplayer, fd world, int i, int j, int k, int l) {
        return a().onItemUseLeftClick(this, entityplayer, world, i, j, k, l);
    }

    public float a(Tile block) {
        return a().a(this, block);
    }

    public iz a(fd world, gs entityplayer) {
        return a().a(this, world, entityplayer);
    }

    public nu a(nu nbttagcompound) {
        nbttagcompound.a("id", (short) this.c);
        nbttagcompound.a("Count", this.a);
        nbttagcompound.a("Damage", (short) this.d);
        return nbttagcompound;
    }

    public void b(nu nbttagcompound) {
        this.c = nbttagcompound.d("id");
        this.a = nbttagcompound.e("Count");
        this.d = nbttagcompound.d("Damage");
        if (this.c == AC_Items.boomerang.bf)
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
            if (entity instanceof gs)
                ((gs) entity).a(jl.F[this.c], 1);
            this.a--;
            if (this.a < 0)
                this.a = 0;
            this.d = 0;
        }
    }

    public void a(ls entityliving, gs entityplayer) {
        boolean flag = ItemType.c[this.c].a(this, entityliving, entityplayer);
        if (flag)
            entityplayer.a(jl.E[this.c], 1);
    }

    public void a(int i, int j, int k, int l, gs entityplayer) {
        boolean flag = ItemType.c[this.c].a(this, i, j, k, l, entityplayer);
        if (flag)
            entityplayer.a(jl.E[this.c], 1);
    }

    public int a(sn entity) {
        return ItemType.c[this.c].a(entity);
    }

    public boolean b(Tile block) {
        return ItemType.c[this.c].a(block);
    }

    public void a(gs entityplayer) {
    }

    public void a(ls entityliving) {
        ItemType.c[this.c].a(this, entityliving);
    }

    public iz k() {
        return new iz(this.c, this.a, this.d);
    }

    public static boolean a(iz itemstack, iz itemstack1) {
        if (itemstack == null && itemstack1 == null)
            return true;
        if (itemstack == null || itemstack1 == null)
            return false;
        return itemstack.d(itemstack1);
    }

    private boolean d(iz itemstack) {
        if (this.a != itemstack.a)
            return false;
        if (this.c != itemstack.c)
            return false;
        return (this.d == itemstack.d);
    }

    public boolean a(iz itemstack) {
        return (this.c == itemstack.c && this.d == itemstack.d);
    }

    public String l() {
        return ItemType.c[this.c].a(this);
    }

    public static iz b(iz itemstack) {
        return (itemstack != null) ? itemstack.k() : null;
    }

    public String toString() {
        return this.a + "x" + ItemType.c[this.c].a() + "@" + this.d;
    }

    public void a(fd world, sn entity, int i, boolean flag) {
        if (this.b > 0)
            this.b--;
        ItemType.c[this.c].a(this, world, entity, i, flag);
    }

    public void b(fd world, gs entityplayer) {
        entityplayer.a(jl.D[this.c], this.a);
        ItemType.c[this.c].b(this, world, entityplayer);
    }

    public boolean c(iz itemstack) {
        return (this.c == itemstack.c && this.a == itemstack.a && this.d == itemstack.d);
    }
}
