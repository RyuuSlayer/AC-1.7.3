package io.github.ryuu.adventurecraft.mixin;

import io.github.ryuu.adventurecraft.blocks.BlockColor;
import io.github.ryuu.adventurecraft.items.ItemSubtypes;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.stat.Stats;
import net.minecraft.tile.*;
import net.minecraft.tile.material.Material;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.maths.Box;

import java.util.ArrayList;
import java.util.Random;

public class MixinTile {
    public static final boolean[] n = new boolean[256];
    public static final boolean[] o = new boolean[256];
    public static final boolean[] p = new boolean[256];
    public static final int[] q = new int[256];
    public static final int[] subTypes = new int[256];
    public static final int[] s = new int[256];
    public static final boolean[] t = new boolean[256];
    public static final TileSounds d;
    public static final TileSounds e;
    public static final TileSounds f;
    public static final TileSounds g;
    public static final TileSounds h;
    public static final TileSounds i;
    public static final TileSounds j;
    public static final TileSounds k;
    public static final TileSounds l;
    public static final MixinTile[] m;
    public static final boolean[] r;
    public static final MixinTile u;
    public static final GrassTile v;
    public static final MixinTile w;
    public static final MixinTile x;
    public static final MixinTile y;
    public static final MixinTile z;
    public static final MixinTile A;
    public static final MixinTile B;
    public static final MixinTile C;
    public static final MixinTile D;
    public static final MixinTile E;
    public static final MixinTile F;
    public static final MixinTile G;
    public static final MixinTile H;
    public static final MixinTile I;
    public static final MixinTile J;
    public static final MixinTile K;
    public static final bk L;
    public static final MixinTile M;
    public static final MixinTile N;
    public static final MixinTile O;
    public static final MixinTile P;
    public static final MixinTile Q;
    public static final MixinTile R;
    public static final MixinTile S;
    public static final MixinTile T;
    public static final MixinTile U;
    public static final MixinTile V;
    public static final MixinTile W;
    public static final MixinTile X;
    public static final TallGrassTile Y;
    public static final DeadBushTile Z;
    public static final MixinTile aa;
    public static final h ab;
    public static final MixinTile ac;
    public static final MovingPistonTile ad;
    public static final PlantTile ae;
    public static final PlantTile af;
    public static final PlantTile ag;
    public static final PlantTile ah;
    public static final MixinTile ai;
    public static final MixinTile aj;
    public static final MixinTile ak;
    public static final MixinTile al;
    public static final MixinTile am;
    public static final MixinTile an;
    public static final MixinTile ao;
    public static final MixinTile ap;
    public static final MixinTile aq;
    public static final MixinTile ar;
    public static final FireTile as;
    public static final MixinTile at;
    public static final MixinTile au;
    public static final MixinTile av;
    public static final MixinTile aw;
    public static final MixinTile ax;
    public static final MixinTile ay;
    public static final MixinTile az;
    public static final MixinTile aA;
    public static final MixinTile aB;
    public static final MixinTile aC;
    public static final MixinTile aD;
    public static final MixinTile aE;
    public static final MixinTile aF;
    public static final MixinTile aG;
    public static final MixinTile aH;
    public static final MixinTile aI;
    public static final MixinTile aJ;
    public static final MixinTile aK;
    public static final MixinTile aL;
    public static final MixinTile aM;
    public static final MixinTile aN;
    public static final MixinTile aO;
    public static final MixinTile aP;
    public static final MixinTile aQ;
    public static final MixinTile aR;
    public static final MixinTile aS;
    public static final MixinTile aT;
    public static final MixinTile aU;
    public static final MixinTile aV;
    public static final MixinTile aW;
    public static final MixinTile aX;
    public static final MixinTile aY;
    public static final MixinTile aZ;
    public static final MixinTile ba;
    public static final MixinTile bb;
    public static final MixinTile bc;
    public static final MixinTile bd;
    public static final MixinTile be;
    public static final PortalTile bf;
    public static final MixinTile bg;
    public static final MixinTile bh;
    public static final MixinTile bi;
    public static final MixinTile bj;
    public static final MixinTile bk;
    public static final MixinTile bl;
    public static boolean resetActive;

    static {
        d = new TileSounds("stone", 1.0F, 1.0F);
        e = new TileSounds("wood", 1.0F, 1.0F);
        f = new TileSounds("gravel", 1.0F, 1.0F);
        g = new TileSounds("grass", 1.0F, 1.0F);
        h = new TileSounds("stone", 1.0F, 1.0F);
        MixinTile.i = new TileSounds("stone", 1.0F, 1.5F);
        j = (TileSounds) new al("stone", 1.0F, 1.0F);
        k = new TileSounds("cloth", 1.0F, 1.0F);
        l = (TileSounds) new aj("sand", 1.0F, 1.0F);
        m = new MixinTile[256];
        r = new boolean[256];
        u = (new StoneTile(1, 215)).c(1.5F).b(10.0F).a(h).a("stone");
        v = (GrassTile) (new GrassTile(2)).c(0.6F).a(g).a("grass").setSubTypes(5);
        w = (new DirtTile(3, 2)).c(0.5F).a(f).a("dirt");
        x = (new BlockColor(4, 214, Material.e)).c(2.0F).b(10.0F).a(h).a("stonebrick");
        y = (new MixinTile(5, 4, Material.d)).c(2.0F).b(5.0F).a(e).a("wood").j();
        z = (new SaplingTile(6, 15)).c(0.0F).a(g).a("sapling").j();
        A = (new MixinTile(7, 17, Material.e)).l().b(6000000.0F).a(h).a("bedrock").q();
        B = (new FlowingFluidTile(8, Material.g)).c(0.5F).g(3).a("water").q().j();
        C = (new StillFluidTile(9, Material.g)).c(0.5F).g(3).a("water").q().j();
        D = (new FlowingFluidTile(10, Material.h)).c(0.5F).a(1.0F).g(255).a("lava").q().j();
        E = (new StillFluidTile(11, Material.h)).c(0.5F).a(1.0F).g(255).a("lava").q().j();
        F = (new SandTile(12, 18)).c(0.5F).a(l).a("sand").setSubTypes(4);
        G = (new GravelTile(13, 19)).c(0.6F).a(f).a("gravel");
        H = (new mt(14, 32)).c(3.0F).b(5.0F).a(h).a("oreGold");
        I = (new mt(15, 33)).c(3.0F).b(5.0F).a(h).a("oreIron");
        J = (new mt(16, 34)).c(3.0F).b(5.0F).a(h).a("oreCoal");
        K = (new vg(17)).c(2.0F).a(e).a("log").j();
        L = (bk) (new bk(18, 52)).c(0.2F).g(1).a(g).a("leaves").q().j();
        M = (new xf(19)).c(0.6F).a(g).a("sponge");
        N = (new GlassTile(20, 49, Material.p, false)).c(0.3F).a(j).a("glass");
        O = (new mt(21, 160)).c(3.0F).b(5.0F).a(h).a("oreLapis");
        P = (new MixinTile(22, 144, Material.e)).c(3.0F).b(5.0F).a(h).a("blockLapis");
        Q = (new DispenserTile(23)).c(3.5F).a(h).a("dispenser").j();
        R = (new rd(24)).a(h).c(0.8F).a("sandStone");
        S = (new pt(25)).c(0.8F).a("musicBlock").j();
        T = (new ve(26)).c(0.2F).a("bed").q().j();
        U = (new pc(27, 179, true)).c(0.7F).a(MixinTile.i).a("goldenRail").j();
        V = (new ph(28, 195)).c(0.7F).a(MixinTile.i).a("detectorRail").j();
        W = (new jq(29, 106, true)).a("pistonStickyBase").j();
        X = (new rn(30, 11)).g(1).c(4.0F).a("web");
        Y = (TallGrassTile) (new TallGrassTile(31, 39)).c(0.0F).a(g).a("tallgrass");
        Z = (DeadBushTile) (new DeadBushTile(32, 55)).c(0.0F).a(g).a("deadbush");
        aa = (new jq(33, 107, false)).a("pistonBase").j();
        ab = (h) (new h(34, 107)).j();
        ac = (new ee()).c(0.8F).a(k).a("cloth").j();
        ad = new MovingPistonTile(36);
        ae = (PlantTile) (new PlantTile(37, 13)).c(0.0F).a(g).a("flower");
        af = (PlantTile) (new PlantTile(38, 12)).c(0.0F).a(g).a("rose");
        ag = (PlantTile) (new MushroomTile(39, 29)).c(0.0F).a(g).a(0.125F).a("mushroom");
        ah = (PlantTile) (new MushroomTile(40, 28)).c(0.0F).a(g).a("mushroom");
        ai = (new l(41, 23)).c(3.0F).b(10.0F).a(MixinTile.i).a("blockGold");
        aj = (new l(42, 22)).c(5.0F).b(10.0F).a(MixinTile.i).a("blockIron");
        ak = (new StoneSlabTile(43, true)).c(2.0F).b(10.0F).a(h).a("stoneSlab");
        al = (new StoneSlabTile(44, false)).c(2.0F).b(10.0F).a(h).a("stoneSlab");
        am = (new MixinTile(45, 7, Material.e)).c(2.0F).b(10.0F).a(h).a("brick");
        an = (new ah(46, 8)).c(0.0F).a(g).a("tnt");
        ao = (new hb(47, 35)).c(1.5F).a(e).a("bookshelf");
        ap = (new MixinTile(48, 36, Material.e)).c(2.0F).b(10.0F).a(h).a("stoneMoss");
        aq = (new fb(49, 37)).c(10.0F).b(2000.0F).a(h).a("obsidian");
        ar = (new vm(50, 80)).c(0.0F).a(0.9375F).a(e).a("torch").j();
        as = (FireTile) (new FireTile(51, 31)).c(0.0F).a(1.0F).a(e).a("fire").q().j();
        at = (new dd(52, 65)).c(5.0F).a(MixinTile.i).a("mobSpawner").q();
        au = (new StairsTile(53, y)).a("stairsWood").j();
        av = (new e(54)).c(2.5F).a(e).a("chest").j();
        aw = (new sm(55, 164)).c(0.0F).a(d).a("redstoneDust").q().j();
        ax = (new mt(56, 50)).c(3.0F).b(5.0F).a(h).a("oreDiamond");
        ay = (new l(57, 24)).c(5.0F).b(10.0F).a(MixinTile.i).a("blockDiamond");
        az = (new fi(58)).c(2.5F).a(e).a("workbench");
        aA = (new ni(59, 88)).c(0.0F).a(g).a("crops").q().j();
        aB = (new vl(60)).c(0.6F).a(f).a("farmland");
        aC = (new FurnaceTile(61, false)).c(3.5F).a(h).a("furnace").j();
        aD = (new FurnaceTile(62, true)).c(3.5F).a(h).a(0.875F).a("furnace").j();
        aE = (new uj(63, MixinSign.class, true)).c(1.0F).a(e).a("sign").q().j();
        aF = (new DoorTile(64, Material.d)).c(3.0F).a(e).a("doorWood").q().j();
        aG = (new LadderTile(65, 83)).c(0.4F).a(e).a("ladder").j();
        aH = (new pc(66, 128, false)).c(0.7F).a(MixinTile.i).a("rail").j();
        aI = (new StairsTile(67, x)).a("stairsStone").j();
        aJ = (new uj(68, MixinSign.class, false)).c(1.0F).a(e).a("sign").q().j();
        aK = (new xr(69, 96)).c(0.5F).a(e).a("lever").j();
        aL = (new bv(70, u.bm, rt.b, Material.e)).c(0.5F).a(h).a("pressurePlate").j();
        aM = (new DoorTile(71, Material.f)).c(5.0F).a(MixinTile.i).a("doorIron").q().j();
        aN = (new bv(72, y.bm, rt.a, Material.d)).c(0.5F).a(e).a("pressurePlate").j();
        aO = (new bs(73, 51, false)).c(3.0F).b(5.0F).a(h).a("oreRedstone").j();
        aP = (new bs(74, 51, true)).a(0.625F).c(3.0F).b(5.0F).a(h).a("oreRedstone").j();
        aQ = (new db(75, 115, false)).c(0.0F).a(e).a("notGate").j();
        aR = (new db(76, 99, true)).c(0.0F).a(0.5F).a(e).a("notGate").j();
        aS = (new oi(77, u.bm)).c(0.5F).a(h).a("button").j();
        aT = (new jr(78, 66)).c(0.1F).a(k).a("snow");
        aU = (new IceTile(79, 67)).c(0.5F).g(3).a(j).a("ice");
        aV = (new ac(80, 66)).c(0.2F).a(k).a("snow");
        aW = (new CactusTile(81, 70)).c(0.4F).a(k).a("cactus");
        aX = (new rz(82, 72)).c(0.6F).a(f).a("clay");
        aY = (new ri(83, 73)).c(0.0F).a(g).a("reeds").q();
        aZ = (new fo(84, 74)).c(2.0F).b(10.0F).a(h).a("jukebox").j();
        ba = (new jw(85, 4)).c(2.0F).b(5.0F).a(e).a("fence").j();
        bb = (new fc(86, 102, false)).c(1.0F).a(e).a("pumpkin").j();
        bc = (new yi(87, 103)).c(0.4F).a(h).a("hellrock");
        bd = (new oa(88, 104)).c(0.5F).a(l).a("hellsand");
        be = (new ly(89, 105, Material.e)).c(0.3F).a(j).a(1.0F).a("lightgem");
        bf = (PortalTile) (new PortalTile(90, 14)).c(-1.0F).a(j).a(0.75F).a("portal");
        bg = (new fc(91, 102, true)).c(1.0F).a(e).a(1.0F).a("litpumpkin").j();
        bh = (new um(92, 121)).c(0.5F).a(k).a("cake").q().j();
        bi = (new wo(93, false)).c(0.0F).a(e).a("diode").q().j();
        bj = (new wo(94, true)).c(0.0F).a(0.625F).a(e).a("diode").q().j();
        bk = (new ks(95)).c(0.0F).a(1.0F).a(e).a("lockedchest").b(true).j();
        bl = (new oq(96, Material.d)).c(3.0F).a(e).a("trapdoor").q().j();
        ItemType.c[ac.bn] = (new bi(ac.bn - 256)).a("cloth");
        ItemType.c[K.bn] = (new go(K.bn - 256)).a("log");
        ItemType.c[v.bn] = (new ItemSubtypes(v.bn - 256)).a("grass");
        ItemType.c[F.bn] = (new ItemSubtypes(F.bn - 256)).a("sand");
        ItemType.c[Y.bn] = (new ItemSubtypes(Y.bn - 256)).a("tallgrass");
        ItemType.c[al.bn] = (new en(al.bn - 256)).a("stoneSlab");
        ItemType.c[z.bn] = (new mr(z.bn - 256)).a("sapling");
        ItemType.c[L.bn] = (new uy(L.bn - 256)).a("leaves");
        ItemType.c[aa.bn] = (ItemType) new qr(aa.bn - 256);
        ItemType.c[W.bn] = (ItemType) new qr(W.bn - 256);
        for (int i = 0; i < 256; i++) {
            if (m[i] != null && ItemType.c[i] == null) {
                ItemType.c[i] = (ItemType) new ck(i - 256);
                m[i].k();
            }
        }
        r[0] = true;
        jl.b();
    }

    public final int bn;
    public final Material bA;
    public TileSounds by = d;
    public float bz = 1.0F;
    public float bB = 0.6F;
    public int textureNum = 0;
    public int bm;
    public double bs;
    public double bt;
    public double bu;
    public double bv;
    public double bw;
    public double bx;
    protected boolean bq = true;
    protected boolean br = true;
    protected float bo;
    protected float bp;
    private String a;

    protected MixinTile(int i, Material material) {
        if (m[i] != null)
            throw new IllegalArgumentException("Slot " + i + " is already occupied by " + m[i] + " when adding " + this);
        this.bA = material;
        m[i] = this;
        this.bn = i;
        a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        o[i] = c();
        q[i] = c() ? 255 : 0;
        r[i] = !material.b();
        p[i] = false;
    }

    protected MixinTile(int i, int j, Material material) {
        this(i, material);
        this.bm = j;
    }

    public static void resetArea(Level world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        boolean oldResetActive = resetActive;
        resetActive = true;
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    int blockID = world.a(x, y, z);
                    if (blockID != 0)
                        m[blockID].reset(world, x, y, z, false);
                }
            }
        }
        resetActive = oldResetActive;
    }

    static Class _mthclass$(String s) {
        try {
            return Class.forName(s);
        } catch (ClassNotFoundException classnotfoundexception) {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    protected MixinTile j() {
        t[this.bn] = true;
        return this;
    }

    protected void k() {
    }

    protected MixinTile a(TileSounds stepsound) {
        this.by = stepsound;
        return this;
    }

    protected MixinTile g(int i) {
        q[this.bn] = i;
        return this;
    }

    protected MixinTile setSubTypes(int i) {
        subTypes[this.bn] = i;
        return this;
    }

    protected MixinTile a(float f) {
        s[this.bn] = (int) (15.0F * f);
        return this;
    }

    protected MixinTile b(float f) {
        this.bp = f * 3.0F;
        return this;
    }

    public boolean d() {
        return true;
    }

    public int b() {
        return 0;
    }

    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return true;
    }

    protected MixinTile c(float f) {
        this.bo = f;
        if (this.bp < f * 5.0F)
            this.bp = f * 5.0F;
        return this;
    }

    protected MixinTile l() {
        c(-1.0F);
        return this;
    }

    public float m() {
        return this.bo;
    }

    protected MixinTile b(boolean flag) {
        n[this.bn] = flag;
        return this;
    }

    public void a(float f, float f1, float f2, float f3, float f4, float f5) {
        this.bs = f;
        this.bt = f1;
        this.bu = f2;
        this.bv = f3;
        this.bw = f4;
        this.bx = f5;
    }

    public float d(TileView iblockaccess, int i, int j, int k) {
        return iblockaccess.a(i, j, k, getBlockLightValue(iblockaccess, i, j, k));
    }

    public int getBlockLightValue(TileView iblockaccess, int i, int j, int k) {
        return s[this.bn];
    }

    public boolean b(TileView iblockaccess, int i, int j, int k, int l) {
        if (l == 0 && this.bt > 0.0D)
            return true;
        if (l == 1 && this.bw < 1.0D)
            return true;
        if (l == 2 && this.bu > 0.0D)
            return true;
        if (l == 3 && this.bx < 1.0D)
            return true;
        if (l == 4 && this.bs > 0.0D)
            return true;
        if (l == 5 && this.bv < 1.0D)
            return true;
        return !iblockaccess.g(i, j, k);
    }

    public boolean d(TileView iblockaccess, int i, int j, int k, int l) {
        return iblockaccess.f(i, j, k).a();
    }

    public int a(TileView iblockaccess, int i, int j, int k, int l) {
        return a(l, iblockaccess.e(i, j, k));
    }

    public int a(int i, int j) {
        return a(i);
    }

    public int a(int i) {
        return this.bm;
    }

    public Box f(Level world, int i, int j, int k) {
        return Box.b(i + this.bs, j + this.bt, k + this.bu, i + this.bv, j + this.bw, k + this.bx);
    }

    public void a(Level world, int i, int j, int k, Box axisalignedbb, ArrayList<Box> arraylist) {
        Box axisalignedbb1 = e(world, i, j, k);
        if (axisalignedbb1 != null && axisalignedbb.a(axisalignedbb1))
            arraylist.add(axisalignedbb1);
    }

    public Box e(Level world, int i, int j, int k) {
        return Box.b(i + this.bs, j + this.bt, k + this.bu, i + this.bv, j + this.bw, k + this.bx);
    }

    public boolean c() {
        return true;
    }

    public boolean a(int i, boolean flag) {
        return v_();
    }

    public boolean v_() {
        return true;
    }

    public void a(Level world, int i, int j, int k, Random random) {
    }

    public void b(Level world, int i, int j, int k, Random random) {
    }

    public void c(Level world, int i, int j, int k, int l) {
    }

    public void b(Level world, int i, int j, int k, int l) {
    }

    public int e() {
        return 10;
    }

    public void c(Level world, int i, int j, int k) {
    }

    public void b(Level world, int i, int j, int k) {
        world.triggerManager.removeArea(i, j, k);
    }

    public int a(Random random) {
        return 1;
    }

    public int a(int i, Random random) {
        return this.bn;
    }

    public float a(Player entityplayer) {
        if (this.bo < 0.0F)
            return 0.0F;
        if (!entityplayer.b(this))
            return 1.0F / this.bo / 100.0F;
        return entityplayer.a(this) / this.bo / 30.0F;
    }

    public void g(Level world, int i, int j, int k, int l) {
        a(world, i, j, k, l, 1.0F);
    }

    public void a(Level world, int i, int j, int k, int l, float f) {
    }

    protected void a(Level world, int i, int j, int k, ItemInstance itemstack) {
    }

    protected int b_(int i) {
        return 0;
    }

    public float a(net.minecraft.entity.Entity entity) {
        return this.bp / 5.0F;
    }

    public HitResult a(Level world, int i, int j, int k, bt vec3d, bt vec3d1) {
        a((TileView) world, i, j, k);
        vec3d = vec3d.c(-i, -j, -k);
        vec3d1 = vec3d1.c(-i, -j, -k);
        bt vec3d2 = vec3d.a(vec3d1, this.bs);
        bt vec3d3 = vec3d.a(vec3d1, this.bv);
        bt vec3d4 = vec3d.b(vec3d1, this.bt);
        bt vec3d5 = vec3d.b(vec3d1, this.bw);
        bt vec3d6 = vec3d.c(vec3d1, this.bu);
        bt vec3d7 = vec3d.c(vec3d1, this.bx);
        if (!a(vec3d2))
            vec3d2 = null;
        if (!a(vec3d3))
            vec3d3 = null;
        if (!b(vec3d4))
            vec3d4 = null;
        if (!b(vec3d5))
            vec3d5 = null;
        if (!c(vec3d6))
            vec3d6 = null;
        if (!c(vec3d7))
            vec3d7 = null;
        bt vec3d8 = null;
        if (vec3d2 != null && (vec3d8 == null || vec3d.c(vec3d2) < vec3d.c(vec3d8)))
            vec3d8 = vec3d2;
        if (vec3d3 != null && (vec3d8 == null || vec3d.c(vec3d3) < vec3d.c(vec3d8)))
            vec3d8 = vec3d3;
        if (vec3d4 != null && (vec3d8 == null || vec3d.c(vec3d4) < vec3d.c(vec3d8)))
            vec3d8 = vec3d4;
        if (vec3d5 != null && (vec3d8 == null || vec3d.c(vec3d5) < vec3d.c(vec3d8)))
            vec3d8 = vec3d5;
        if (vec3d6 != null && (vec3d8 == null || vec3d.c(vec3d6) < vec3d.c(vec3d8)))
            vec3d8 = vec3d6;
        if (vec3d7 != null && (vec3d8 == null || vec3d.c(vec3d7) < vec3d.c(vec3d8)))
            vec3d8 = vec3d7;
        if (vec3d8 == null)
            return null;
        byte byte0 = -1;
        if (vec3d8 == vec3d2)
            byte0 = 4;
        if (vec3d8 == vec3d3)
            byte0 = 5;
        if (vec3d8 == vec3d4)
            byte0 = 0;
        if (vec3d8 == vec3d5)
            byte0 = 1;
        if (vec3d8 == vec3d6)
            byte0 = 2;
        if (vec3d8 == vec3d7)
            byte0 = 3;
        return new HitResult(i, j, k, byte0, vec3d8.c(i, j, k));
    }

    protected boolean a(bt vec3d) {
        if (vec3d == null)
            return false;
        return (vec3d.b >= this.bt && vec3d.b <= this.bw && vec3d.c >= this.bu && vec3d.c <= this.bx);
    }

    protected boolean b(bt vec3d) {
        if (vec3d == null)
            return false;
        return (vec3d.a >= this.bs && vec3d.a <= this.bv && vec3d.c >= this.bu && vec3d.c <= this.bx);
    }

    protected boolean c(bt vec3d) {
        if (vec3d == null)
            return false;
        return (vec3d.a >= this.bs && vec3d.a <= this.bv && vec3d.b >= this.bt && vec3d.b <= this.bw);
    }

    public void d(Level world, int i, int j, int k) {
    }

    public int b_() {
        return 0;
    }

    public boolean a(Level world, int i, int j, int k, int l) {
        return a(world, i, j, k);
    }

    public boolean a(Level world, int i, int j, int k) {
        int l = world.a(i, j, k);
        return (l == 0 || (m[l]).bA.g());
    }

    public boolean a(Level world, int i, int j, int k, Player entityplayer) {
        return false;
    }

    public void b(Level world, int i, int j, int k, Entity entity) {
    }

    public void e(Level world, int i, int j, int k, int l) {
    }

    public void b(Level world, int i, int j, int k, Player entityplayer) {
    }

    public void a(Level world, int i, int j, int k, Entity entity, bt vec3d) {
    }

    public void a(TileView iblockaccess, int i, int j, int k) {
    }

    public int b(int i) {
        return 16777215;
    }

    public int b(TileView iblockaccess, int i, int j, int k) {
        return 16777215;
    }

    public boolean c(TileView iblockaccess, int i, int j, int k, int l) {
        return false;
    }

    public boolean f() {
        return false;
    }

    public void a(Level world, int i, int j, int k, Entity entity) {
    }

    public boolean d(Level world, int i, int j, int k, int l) {
        return false;
    }

    public void g() {
    }

    public void a(Level world, Player entityplayer, int i, int j, int k, int l) {
        entityplayer.a(Stats.mineBlock[this.bn], 1);
        g(world, i, j, k, l);
    }

    public boolean g(Level world, int i, int j, int k) {
        return true;
    }

    public void a(Level world, int i, int j, int k, LivingEntity entityliving) {
    }

    public MixinTile a(String s) {
        this.a = "tile." + s;
        return this;
    }

    public String n() {
        return I18n.translate(o() + ".name");
    }

    public String o() {
        return this.a;
    }

    public void a(Level world, int i, int j, int k, int l, int i1) {
    }

    public boolean p() {
        return this.br;
    }

    protected MixinTile q() {
        this.br = false;
        return this;
    }

    public int h() {
        return this.bA.j();
    }

    public boolean canBeTriggered() {
        return false;
    }

    public void addTriggerActivation(Level world, int i, int j, int k) {
        if (canBeTriggered()) {
            int metadata = Math.min(world.e(i, j, k) + 1, 15);
            world.e(i, j, k, metadata);
            if (metadata == 1)
                onTriggerActivated(world, i, j, k);
        }
    }

    public void removeTriggerActivation(Level world, int i, int j, int k) {
        if (canBeTriggered()) {
            int metadata = world.e(i, j, k) - 1;
            world.e(i, j, k, Math.max(metadata, 0));
            if (metadata == 0)
                onTriggerDeactivated(world, i, j, k);
        }
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
    }

    public void reset(Level world, int i, int j, int k, boolean death) {
    }

    public int alwaysUseClick(Level world, int i, int j, int k) {
        return -1;
    }

    public int getTextureNum() {
        return this.textureNum;
    }

    public MixinTile setTextureNum(int t) {
        this.textureNum = t;
        return this;
    }
}