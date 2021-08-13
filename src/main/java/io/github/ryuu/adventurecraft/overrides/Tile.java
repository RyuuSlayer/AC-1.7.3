package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.blocks.BlockColor;
import io.github.ryuu.adventurecraft.items.ItemSubtypes;
import net.minecraft.level.TileView;
import net.minecraft.stat.Stats;
import net.minecraft.tile.*;
import net.minecraft.tile.material.Material;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.maths.Box;

import java.util.ArrayList;
import java.util.Random;

public class Tile {
    protected boolean bq = true;

    protected boolean br = true;

    public TileSounds by = d;

    public float bz = 1.0F;

    public float bB = 0.6F;

    protected Tile(int i, Material material) {
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

    protected Tile j() {
        t[this.bn] = true;
        return this;
    }

    protected void k() {
    }

    protected Tile(int i, int j, Material material) {
        this(i, material);
        this.bm = j;
    }

    protected Tile a(TileSounds stepsound) {
        this.by = stepsound;
        return this;
    }

    protected Tile g(int i) {
        q[this.bn] = i;
        return this;
    }

    protected Tile setSubTypes(int i) {
        subTypes[this.bn] = i;
        return this;
    }

    protected Tile a(float f) {
        s[this.bn] = (int) (15.0F * f);
        return this;
    }

    protected Tile b(float f) {
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

    protected Tile c(float f) {
        this.bo = f;
        if (this.bp < f * 5.0F)
            this.bp = f * 5.0F;
        return this;
    }

    protected Tile l() {
        c(-1.0F);
        return this;
    }

    public float m() {
        return this.bo;
    }

    protected Tile b(boolean flag) {
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

    protected void a(Level world, int i, int j, int k, iz itemstack) {
    }

    protected int b_(int i) {
        return 0;
    }

    public float a(sn entity) {
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

    public void b(Level world, int i, int j, int k, sn entity) {
    }

    public void e(Level world, int i, int j, int k, int l) {
    }

    public void b(Level world, int i, int j, int k, Player entityplayer) {
    }

    public void a(Level world, int i, int j, int k, sn entity, bt vec3d) {
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

    public void a(Level world, int i, int j, int k, sn entity) {
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

    public void a(Level world, int i, int j, int k, ls entityliving) {
    }

    public Tile a(String s) {
        this.a = "tile." + s;
        return this;
    }

    public String n() {
        return do.a(o() + ".name");
    }

    public String o() {
        return this.a;
    }

    public void a(Level world, int i, int j, int k, int l, int i1) {
    }

    public boolean p() {
        return this.br;
    }

    protected Tile q() {
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

    public Tile setTextureNum(int t) {
        this.textureNum = t;
        return this;
    }

    static Class _mthclass$(String s) {
        try {
            return Class.forName(s);
        } catch (ClassNotFoundException classnotfoundexception) {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    public static final boolean[] n = new boolean[256];

    public static final boolean[] o = new boolean[256];

    public static final boolean[] p = new boolean[256];

    public static final int[] q = new int[256];

    public static final int[] subTypes = new int[256];

    public static final int[] s = new int[256];

    public static final boolean[] t = new boolean[256];

    static {
        d = new TileSounds("stone", 1.0F, 1.0F);
        e = new TileSounds("wood", 1.0F, 1.0F);
        f = new TileSounds("gravel", 1.0F, 1.0F);
        g = new TileSounds("grass", 1.0F, 1.0F);
        h = new TileSounds("stone", 1.0F, 1.0F);
        Tile.i = new TileSounds("stone", 1.0F, 1.5F);
        j = (TileSounds) new al("stone", 1.0F, 1.0F);
        k = new TileSounds("cloth", 1.0F, 1.0F);
        l = (TileSounds) new aj("sand", 1.0F, 1.0F);
        m = new Tile[256];
        r = new boolean[256];
        u = (new StoneTile(1, 215)).c(1.5F).b(10.0F).a(h).a("stone");
        v = (GrassTile) (new GrassTile(2)).c(0.6F).a(g).a("grass").setSubTypes(5);
        w = (new DirtTile(3, 2)).c(0.5F).a(f).a("dirt");
        x = (new BlockColor(4, 214, Material.e)).c(2.0F).b(10.0F).a(h).a("stonebrick");
        y = (new Tile(5, 4, Material.d)).c(2.0F).b(5.0F).a(e).a("wood").j();
        z = (new SaplingTile(6, 15)).c(0.0F).a(g).a("sapling").j();
        A = (new Tile(7, 17, Material.e)).l().b(6000000.0F).a(h).a("bedrock").q();
        B = (new om(8, Material.g)).c(0.5F).g(3).a("water").q().j();
        C = (new nx(9, Material.g)).c(0.5F).g(3).a("water").q().j();
        D = (new om(10, Material.h)).c(0.5F).a(1.0F).g(255).a("lava").q().j();
        E = (new nx(11, Material.h)).c(0.5F).a(1.0F).g(255).a("lava").q().j();
        F = (new gk(12, 18)).c(0.5F).a(l).a("sand").setSubTypes(4);
        G = (new ne(13, 19)).c(0.6F).a(f).a("gravel");
        H = (new mt(14, 32)).c(3.0F).b(5.0F).a(h).a("oreGold");
        I = (new mt(15, 33)).c(3.0F).b(5.0F).a(h).a("oreIron");
        J = (new mt(16, 34)).c(3.0F).b(5.0F).a(h).a("oreCoal");
        K = (new vg(17)).c(2.0F).a(e).a("log").j();
        L = (bk) (new bk(18, 52)).c(0.2F).g(1).a(g).a("leaves").q().j();
        M = (new xf(19)).c(0.6F).a(g).a("sponge");
        N = (new GlassTile(20, 49, Material.p, false)).c(0.3F).a(j).a("glass");
        O = (new mt(21, 160)).c(3.0F).b(5.0F).a(h).a("oreLapis");
        P = (new Tile(22, 144, Material.e)).c(3.0F).b(5.0F).a(h).a("blockLapis");
        Q = (new xq(23)).c(3.5F).a(h).a("dispenser").j();
        R = (new rd(24)).a(h).c(0.8F).a("sandStone");
        S = (new pt(25)).c(0.8F).a("musicBlock").j();
        T = (new ve(26)).c(0.2F).a("bed").q().j();
        U = (new pc(27, 179, true)).c(0.7F).a(Tile.i).a("goldenRail").j();
        V = (new ph(28, 195)).c(0.7F).a(Tile.i).a("detectorRail").j();
        W = (new jq(29, 106, true)).a("pistonStickyBase").j();
        X = (new rn(30, 11)).g(1).c(4.0F).a("web");
        Y = (TallGrassTile) (new TallGrassTile(31, 39)).c(0.0F).a(g).a("tallgrass");
        Z = (DeadBushTile) (new DeadBushTile(32, 55)).c(0.0F).a(g).a("deadbush");
        aa = (new jq(33, 107, false)).a("pistonBase").j();
        ab = (h) (new h(34, 107)).j();
        ac = (new ee()).c(0.8F).a(k).a("cloth").j();
        ad = new ut(36);
        ae = (wb) (new wb(37, 13)).c(0.0F).a(g).a("flower");
        af = (wb) (new wb(38, 12)).c(0.0F).a(g).a("rose");
        ag = (wb) (new tl(39, 29)).c(0.0F).a(g).a(0.125F).a("mushroom");
        ah = (wb) (new tl(40, 28)).c(0.0F).a(g).a("mushroom");
        ai = (new l(41, 23)).c(3.0F).b(10.0F).a(Tile.i).a("blockGold");
        aj = (new l(42, 22)).c(5.0F).b(10.0F).a(Tile.i).a("blockIron");
        ak = (new StoneSlabTile(43, true)).c(2.0F).b(10.0F).a(h).a("stoneSlab");
        al = (new StoneSlabTile(44, false)).c(2.0F).b(10.0F).a(h).a("stoneSlab");
        am = (new Tile(45, 7, Material.e)).c(2.0F).b(10.0F).a(h).a("brick");
        an = (new ah(46, 8)).c(0.0F).a(g).a("tnt");
        ao = (new hb(47, 35)).c(1.5F).a(e).a("bookshelf");
        ap = (new Tile(48, 36, Material.e)).c(2.0F).b(10.0F).a(h).a("stoneMoss");
        aq = (new fb(49, 37)).c(10.0F).b(2000.0F).a(h).a("obsidian");
        ar = (new vm(50, 80)).c(0.0F).a(0.9375F).a(e).a("torch").j();
        as = (FireTile) (new FireTile(51, 31)).c(0.0F).a(1.0F).a(e).a("fire").q().j();
        at = (new dd(52, 65)).c(5.0F).a(Tile.i).a("mobSpawner").q();
        au = (new ss(53, y)).a("stairsWood").j();
        av = (new e(54)).c(2.5F).a(e).a("chest").j();
        aw = (new sm(55, 164)).c(0.0F).a(d).a("redstoneDust").q().j();
        ax = (new mt(56, 50)).c(3.0F).b(5.0F).a(h).a("oreDiamond");
        ay = (new l(57, 24)).c(5.0F).b(10.0F).a(Tile.i).a("blockDiamond");
        az = (new fi(58)).c(2.5F).a(e).a("workbench");
        aA = (new ni(59, 88)).c(0.0F).a(g).a("crops").q().j();
        aB = (new vl(60)).c(0.6F).a(f).a("farmland");
        aC = (new tc(61, false)).c(3.5F).a(h).a("furnace").j();
        aD = (new tc(62, true)).c(3.5F).a(h).a(0.875F).a("furnace").j();
        aE = (new uj(63, yk.class, true)).c(1.0F).a(e).a("sign").q().j();
        aF = (new le(64, Material.d)).c(3.0F).a(e).a("doorWood").q().j();
        aG = (new LadderTile(65, 83)).c(0.4F).a(e).a("ladder").j();
        aH = (new pc(66, 128, false)).c(0.7F).a(Tile.i).a("rail").j();
        aI = (new ss(67, x)).a("stairsStone").j();
        aJ = (new uj(68, yk.class, false)).c(1.0F).a(e).a("sign").q().j();
        aK = (new xr(69, 96)).c(0.5F).a(e).a("lever").j();
        aL = (new bv(70, u.bm, rt.b, Material.e)).c(0.5F).a(h).a("pressurePlate").j();
        aM = (new le(71, Material.f)).c(5.0F).a(Tile.i).a("doorIron").q().j();
        aN = (new bv(72, y.bm, rt.a, Material.d)).c(0.5F).a(e).a("pressurePlate").j();
        aO = (new bs(73, 51, false)).c(3.0F).b(5.0F).a(h).a("oreRedstone").j();
        aP = (new bs(74, 51, true)).a(0.625F).c(3.0F).b(5.0F).a(h).a("oreRedstone").j();
        aQ = (new db(75, 115, false)).c(0.0F).a(e).a("notGate").j();
        aR = (new db(76, 99, true)).c(0.0F).a(0.5F).a(e).a("notGate").j();
        aS = (new oi(77, u.bm)).c(0.5F).a(h).a("button").j();
        aT = (new jr(78, 66)).c(0.1F).a(k).a("snow");
        aU = (new nk(79, 67)).c(0.5F).g(3).a(j).a("ice");
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

    public int textureNum = 0;

    public static boolean resetActive;

    public static final TileSounds d;

    public static final TileSounds e;

    public static final TileSounds f;

    public static final TileSounds g;

    public static final TileSounds h;

    public static final TileSounds i;

    public static final TileSounds j;

    public static final TileSounds k;

    public static final TileSounds l;

    public static final Tile[] m;

    public static final boolean[] r;

    public static final Tile u;

    public static final GrassTile v;

    public static final Tile w;

    public static final Tile x;

    public static final Tile y;

    public static final Tile z;

    public static final Tile A;

    public static final Tile B;

    public static final Tile C;

    public static final Tile D;

    public static final Tile E;

    public static final Tile F;

    public static final Tile G;

    public static final Tile H;

    public static final Tile I;

    public static final Tile J;

    public static final Tile K;

    public static final bk L;

    public static final Tile M;

    public static final Tile N;

    public static final Tile O;

    public static final Tile P;

    public static final Tile Q;

    public static final Tile R;

    public static final Tile S;

    public static final Tile T;

    public static final Tile U;

    public static final Tile V;

    public static final Tile W;

    public static final Tile X;

    public static final TallGrassTile Y;

    public static final DeadBushTile Z;

    public static final Tile aa;

    public static final h ab;

    public static final Tile ac;

    public static final ut ad;

    public static final wb ae;

    public static final wb af;

    public static final wb ag;

    public static final wb ah;

    public static final Tile ai;

    public static final Tile aj;

    public static final Tile ak;

    public static final Tile al;

    public static final Tile am;

    public static final Tile an;

    public static final Tile ao;

    public static final Tile ap;

    public static final Tile aq;

    public static final Tile ar;

    public static final FireTile as;

    public static final Tile at;

    public static final Tile au;

    public static final Tile av;

    public static final Tile aw;

    public static final Tile ax;

    public static final Tile ay;

    public static final Tile az;

    public static final Tile aA;

    public static final Tile aB;

    public static final Tile aC;

    public static final Tile aD;

    public static final Tile aE;

    public static final Tile aF;

    public static final Tile aG;

    public static final Tile aH;

    public static final Tile aI;

    public static final Tile aJ;

    public static final Tile aK;

    public static final Tile aL;

    public static final Tile aM;

    public static final Tile aN;

    public static final Tile aO;

    public static final Tile aP;

    public static final Tile aQ;

    public static final Tile aR;

    public static final Tile aS;

    public static final Tile aT;

    public static final Tile aU;

    public static final Tile aV;

    public static final Tile aW;

    public static final Tile aX;

    public static final Tile aY;

    public static final Tile aZ;

    public static final Tile ba;

    public static final Tile bb;

    public static final Tile bc;

    public static final Tile bd;

    public static final Tile be;

    public static final PortalTile bf;

    public static final Tile bg;

    public static final Tile bh;

    public static final Tile bi;

    public static final Tile bj;

    public static final Tile bk;

    public static final Tile bl;

    public int bm;

    public final int bn;

    protected float bo;

    protected float bp;

    public double bs;

    public double bt;

    public double bu;

    public double bv;

    public double bw;

    public double bx;

    public final Material bA;

    private String a;
}