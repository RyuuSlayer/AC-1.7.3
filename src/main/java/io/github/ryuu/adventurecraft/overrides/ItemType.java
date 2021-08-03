package io.github.ryuu.adventurecraft.overrides;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;
import org.mozilla.javascript.Scriptable;

public class ItemType {
    protected ItemType(int i) {
        this.itemUseDelay = 5;
        this.bg = 64;
        this.a = 0;
        this.bi = false;
        this.bj = false;
        this.bk = null;
        this.bf = 256 + i;
        if (c[256 + i] != null)
            System.out.println("CONFLICT @ " + i);
        c[256 + i] = this;
    }

    public ItemType c(int i) {
        this.bh = i;
        return this;
    }

    public ItemType d(int i) {
        this.bg = i;
        return this;
    }

    public ItemType a(int i, int j) {
        this.bh = i + j * 16;
        return this;
    }

    public int a(int i) {
        return this.bh;
    }

    public final int b(iz itemstack) {
        int damage = 0;
        if (itemstack != null)
            damage = itemstack.i();
        return a(damage);
    }

    public boolean a(iz itemstack, gs entityplayer, Level world, int i, int j, int k, int l) {
        return false;
    }

    public boolean onItemUseLeftClick(iz itemstack, gs entityplayer, Level world, int i, int j, int k, int l) {
        return false;
    }

    public float a(iz itemstack, Tile block) {
        return 1.0F;
    }

    public void onItemLeftClick(iz itemstack, Level world, gs entityplayer) {
    }

    public iz a(iz itemstack, Level world, gs entityplayer) {
        return itemstack;
    }

    public int d() {
        return this.bg;
    }

    public int b(int i) {
        return 0;
    }

    public boolean e() {
        return this.bj;
    }

    protected ItemType a(boolean flag) {
        this.bj = flag;
        return this;
    }

    public int f() {
        return this.a;
    }

    protected ItemType e(int i) {
        this.a = i;
        return this;
    }

    public boolean g() {
        return (this.a > 0 && !this.bj);
    }

    public boolean a(iz itemstack, ls entityliving, ls entityliving1) {
        return false;
    }

    public boolean a(iz itemstack, int i, int j, int k, int l, ls entityliving) {
        return false;
    }

    public int a(sn entity) {
        return 1;
    }

    public boolean a(Tile block) {
        return false;
    }

    public void a(iz itemstack, ls entityliving) {
    }

    public ItemType h() {
        this.bi = true;
        return this;
    }

    public boolean b() {
        return this.bi;
    }

    public boolean c() {
        return false;
    }

    public ItemType a(String s) {
        this.bl = "item." + s;
        return this;
    }

    public String a() {
        return this.bl;
    }

    public String a(iz itemstack) {
        return this.bl;
    }

    public ItemType a(ItemType item) {
        if (this.bg > 1)
            throw new IllegalArgumentException("Max stack size must be 1 for items with crafting results");
        this.bk = item;
        return this;
    }

    public ItemType i() {
        return this.bk;
    }

    public boolean j() {
        return (this.bk != null);
    }

    public String k() {
        return do.a(a() + ".name");
    }

    public int f(int i) {
        return 16777215;
    }

    public void a(iz itemstack, Level world, sn entity, int i, boolean flag) {
    }

    public void b(iz itemstack, Level world, gs entityplayer) {
    }

    public boolean mainActionLeftClick() {
        return false;
    }

    public boolean isLighting(iz itemstack) {
        return false;
    }

    public boolean isMuzzleFlash(iz itemstack) {
        return false;
    }

    public void onAddToSlot(gs entityPlayer, int slot, int damage) {
        Scriptable scope = Minecraft.minecraftInstance.f.scope;
        scope.put("slotID", scope, new Integer(slot));
        if (e()) {
            Minecraft.minecraftInstance.f.scriptHandler.runScript(String.format("item_onAddToSlot_%d_%d.js", new Object[]{Integer.valueOf(this.bf), Integer.valueOf(damage)}), scope, false);
        } else {
            Minecraft.minecraftInstance.f.scriptHandler.runScript(String.format("item_onAddToSlot_%d.js", new Object[]{Integer.valueOf(this.bf)}), scope, false);
        }
    }

    public void onRemovedFromSlot(gs entityPlayer, int slot, int damage) {
        Scriptable scope = Minecraft.minecraftInstance.f.scope;
        scope.put("slotID", scope, new Integer(slot));
        if (e()) {
            Minecraft.minecraftInstance.f.scriptHandler.runScript(String.format("item_onRemovedFromSlot_%d_%d.js", new Object[]{Integer.valueOf(this.bf), Integer.valueOf(damage)}), scope, false);
        } else {
            Minecraft.minecraftInstance.f.scriptHandler.runScript(String.format("item_onRemovedFromSlot_%d.js", new Object[]{Integer.valueOf(this.bf)}), scope, false);
        }
    }

    protected static Random b = new Random();

    public static ItemType[] c = new ItemType[32000];

    public static ItemType d;

    public static ItemType e;

    public static ItemType f;

    public static ItemType g = (new ye(3)).a(5, 0).a("flintAndSteel");

    public static ItemType h = (new yw(4, 4, false)).a(10, 0).a("apple");

    public static ItemType i = (new qz(5)).a(5, 1).a("bow");

    public static ItemType j = (new ItemType(6)).a(5, 2).a("arrow");

    public static ItemType k = (new rx(7)).a(7, 0).a("coal");

    public static ItemType l = (new ItemType(8)).a(7, 3).a("emerald");

    public static ItemType m = (new ItemType(9)).a(7, 1).a("ingotIron");

    public static ItemType n = (new ItemType(10)).a(7, 2).a("ingotGold");

    public static ItemType o;

    public static ItemType p;

    public static ItemType q;

    public static ItemType r;

    public static ItemType s;

    public static ItemType t;

    public static ItemType u;

    public static ItemType v;

    public static ItemType w;

    public static ItemType x;

    public static ItemType y;

    public static ItemType z;

    public static ItemType A;

    public static ItemType B = (new ItemType(24)).a(5, 3).h().a("stick");

    public static ItemType C = (new ItemType(25)).a(7, 4).a("bowl");

    public static ItemType D = (new bz(26, 10)).a(8, 4).a("mushroomStew");

    public static ItemType E;

    public static ItemType F;

    public static ItemType G;

    public static ItemType H;

    public static ItemType I = (new ItemType(31)).a(8, 0).a("string");

    public static ItemType J = (new ItemType(32)).a(8, 1).a("feather");

    public static ItemType K = (new ItemType(33)).a(8, 2).a("sulphur");

    public static ItemType L;

    public static ItemType M;

    public static ItemType N;

    public static ItemType O;

    public static ItemType P;

    public static ItemType Q;

    public static ItemType R = (new ItemType(40)).a(9, 1).a("wheat");

    public static ItemType S = (new yw(41, 5, false)).a(9, 2).a("bread");

    public static ItemType T = (new wa(42, 0, 0, 0)).a(0, 0).a("helmetCloth");

    public static ItemType U = (new wa(43, 0, 0, 1)).a(0, 1).a("chestplateCloth");

    public static ItemType V = (new wa(44, 0, 0, 2)).a(0, 2).a("leggingsCloth");

    public static ItemType W = (new wa(45, 0, 0, 3)).a(0, 3).a("bootsCloth");

    public static ItemType X = (new wa(46, 1, 1, 0)).a(1, 0).a("helmetChain");

    public static ItemType Y = (new wa(47, 1, 1, 1)).a(1, 1).a("chestplateChain");

    public static ItemType Z = (new wa(48, 1, 1, 2)).a(1, 2).a("leggingsChain");

    public static ItemType aa = (new wa(49, 1, 1, 3)).a(1, 3).a("bootsChain");

    public static ItemType ab = (new wa(50, 2, 2, 0)).a(2, 0).a("helmetIron");

    public static ItemType ac = (new wa(51, 2, 2, 1)).a(2, 1).a("chestplateIron");

    public static ItemType ad = (new wa(52, 2, 2, 2)).a(2, 2).a("leggingsIron");

    public static ItemType ae = (new wa(53, 2, 2, 3)).a(2, 3).a("bootsIron");

    public static ItemType af = (new wa(54, 3, 3, 0)).a(3, 0).a("helmetDiamond");

    public static ItemType ag = (new wa(55, 3, 3, 1)).a(3, 1).a("chestplateDiamond");

    public static ItemType ah = (new wa(56, 3, 3, 2)).a(3, 2).a("leggingsDiamond");

    public static ItemType ai = (new wa(57, 3, 3, 3)).a(3, 3).a("bootsDiamond");

    public static ItemType aj = (new wa(58, 1, 4, 0)).a(4, 0).a("helmetGold");

    public static ItemType ak = (new wa(59, 1, 4, 1)).a(4, 1).a("chestplateGold");

    public static ItemType al = (new wa(60, 1, 4, 2)).a(4, 2).a("leggingsGold");

    public static ItemType am = (new wa(61, 1, 4, 3)).a(4, 3).a("bootsGold");

    public static ItemType an = (new ItemType(62)).a(6, 0).a("flint");

    public static ItemType ao = (new yw(63, 3, true)).a(7, 5).a("porkchopRaw");

    public static ItemType ap = (new yw(64, 8, true)).a(8, 5).a("porkchopCooked");

    public static ItemType aq = (new ym(65)).a(10, 1).a("painting");

    public static ItemType ar = (new yw(66, 42, false)).a(11, 0).a("appleGold");

    public static ItemType as = (new vc(67)).a(10, 2).a("sign");

    public static ItemType at;

    public static ItemType au;

    public static ItemType av;

    public static ItemType aw;

    public static ItemType ax = (new rl(72, 0)).a(7, 8).a("minecart");

    public static ItemType ay = (new sa(73)).a(8, 6).a("saddle");

    public static ItemType az;

    public static ItemType aA = (new ie(75)).a(8, 3).a("redstone");

    public static ItemType aB = (new dm(76)).a(14, 0).a("snowball");

    public static ItemType aC = (new vd(77)).a(8, 8).a("boat");

    public static ItemType aD = (new ItemType(78)).a(7, 6).a("leather");

    public static ItemType aE;

    public static ItemType aF = (new ItemType(80)).a(6, 1).a("brick");

    public static ItemType aG = (new ItemType(81)).a(9, 3).a("clay");

    public static ItemType aH;

    public static ItemType aI = (new ItemType(83)).a(10, 3).a("paper");

    public static ItemType aJ = (new ItemType(84)).a(11, 3).a("book");

    public static ItemType aK = (new ItemType(85)).a(14, 1).a("slimeball");

    public static ItemType aL = (new rl(86, 1)).a(7, 9).a("minecartChest");

    public static ItemType aM = (new rl(87, 2)).a(7, 10).a("minecartFurnace");

    public static ItemType aN = (new mo(88)).a(12, 0).a("egg");

    public static ItemType aO = (new ItemType(89)).a(6, 3).a("compass");

    public static ItemType aP = (new cs(90)).a(5, 4).a("fishingRod");

    public static ItemType aQ = (new ItemType(91)).a(6, 4).a("clock");

    public static ItemType aR = (new ItemType(92)).a(9, 4).a("yellowDust");

    public static ItemType aS = (new yw(93, 2, false)).a(9, 5).a("fishRaw");

    public static ItemType aT = (new yw(94, 5, false)).a(10, 5).a("fishCooked");

    public static ItemType aU = (new km(95)).a(14, 4).a("dyePowder");

    public static ItemType aV = (new ItemType(96)).a(12, 1).a("bone").h();

    public static ItemType aW = (new ItemType(97)).a(13, 0).a("sugar").h();

    public static ItemType aX;

    public static ItemType aY = (new lp(99)).d(1).a(13, 2).a("bed");

    public static ItemType aZ;

    public static ItemType ba = (new qk(101, 1, false, 8)).a(12, 5).a("cookie");

    public static wr bb = (wr) (new wr(102)).a(12, 3).a("map");

    public static bl bc = (bl) (new bl(103)).a(13, 5).a("shears");

    public static ItemType bd = (new tr(2000, "13")).a(0, 15).a("record");

    public static ItemType be = (new tr(2001, "cat")).a(1, 15).a("record");

    public final int bf;

    protected int bg;

    protected int a;

    protected int bh;

    protected boolean bi;

    protected boolean bj;

    private ItemType bk;

    private String bl;

    public boolean decrementDamage;

    public int itemUseDelay;

    static {
        d = (new wc(0, bu.c)).a(2, 5).a("shovelIron");
        e = (new au(1, bu.c)).a(2, 6).a("pickaxeIron");
        f = (new ta(2, bu.c)).a(2, 7).a("hatchetIron");
        o = (new qd(11, bu.c)).a(2, 4).a("swordIron");
        p = (new qd(12, bu.a)).a(0, 4).a("swordWood");
        q = (new wc(13, bu.a)).a(0, 5).a("shovelWood");
        r = (new au(14, bu.a)).a(0, 6).a("pickaxeWood");
        s = (new ta(15, bu.a)).a(0, 7).a("hatchetWood");
        t = (new qd(16, bu.b)).a(1, 4).a("swordStone");
        u = (new wc(17, bu.b)).a(1, 5).a("shovelStone");
        v = (new au(18, bu.b)).a(1, 6).a("pickaxeStone");
        w = (new ta(19, bu.b)).a(1, 7).a("hatchetStone");
        x = (new qd(20, bu.d)).a(3, 4).a("swordDiamond");
        y = (new wc(21, bu.d)).a(3, 5).a("shovelDiamond");
        z = (new au(22, bu.d)).a(3, 6).a("pickaxeDiamond");
        A = (new ta(23, bu.d)).a(3, 7).a("hatchetDiamond");
        E = (new qd(27, bu.e)).a(4, 4).a("swordGold");
        F = (new wc(28, bu.e)).a(4, 5).a("shovelGold");
        G = (new au(29, bu.e)).a(4, 6).a("pickaxeGold");
        H = (new ta(30, bu.e)).a(4, 7).a("hatchetGold");
        L = (new la(34, bu.a)).a(0, 8).a("hoeWood");
        M = (new la(35, bu.b)).a(1, 8).a("hoeStone");
        N = (new la(36, bu.c)).a(2, 8).a("hoeIron");
        O = (new la(37, bu.d)).a(3, 8).a("hoeDiamond");
        P = (new la(38, bu.e)).a(4, 8).a("hoeGold");
        Q = (new rk(39, Tile.aA.bn)).a(9, 0).a("seeds");
        at = (new hu(68, ln.d)).a(11, 2).a("doorWood");
        au = (new bc(69, 0)).a(10, 4).a("bucket");
        av = (new bc(70, Tile.B.bn)).a(11, 4).a("bucketWater").a(au);
        aw = (new bc(71, Tile.D.bn)).a(12, 4).a("bucketLava").a(au);
        az = (new hu(74, ln.f)).a(12, 2).a("doorIron");
        aE = (new bc(79, -1)).a(13, 4).a("milk").a(au);
        aH = (new lv(82, Tile.aY)).a(11, 1).a("reeds");
        aX = (new lv(98, Tile.bh)).d(1).a(13, 1).a("cake");
        aZ = (new lv(100, Tile.bi)).a(6, 5).a("diode");
        jl.c();
    }
}