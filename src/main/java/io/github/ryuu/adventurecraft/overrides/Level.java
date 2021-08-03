package io.github.ryuu.adventurecraft.overrides;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import javax.imageio.ImageIO;

import io.github.ryuu.adventurecraft.blocks.BlockStairMulti;
import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityNpcPath;
import io.github.ryuu.adventurecraft.items.ItemCustom;
import io.github.ryuu.adventurecraft.scripting.EntityDescriptions;
import io.github.ryuu.adventurecraft.scripting.ScopeTag;
import io.github.ryuu.adventurecraft.scripting.Script;
import io.github.ryuu.adventurecraft.scripting.ScriptModel;
import io.github.ryuu.adventurecraft.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.FlowingLavaTextureBinder2;
import net.minecraft.entity.Entity;
import org.mozilla.javascript.Scriptable;

public class Level implements xp {
    public boolean a;

    private List C;

    public List<Entity> b;

    private List D;

    private TreeSet E;

    private Set F;

    public List c;

    private List G;

    public List d;

    public List e;

    private long H;

    public int f;

    protected int g;

    protected final int h = 1013904223;

    protected float i;

    protected float j;

    protected float k;

    protected float l;

    protected int m;

    public int n;

    public boolean o;

    private long I;

    protected int p;

    public int q;

    public Random r;

    public boolean s;

    public final xa t;

    protected List u;

    protected cl v;

    protected final wt w;

    public ei x;

    public boolean y;

    private boolean J;

    public hc z;

    private ArrayList K;

    private boolean L;

    private int M;

    private boolean N;

    private boolean O;

    public xv a() {
        return this.t.b;
    }

    public Level(wt isavehandler, String s, xa worldprovider, long l) {
        this.h = 1013904223;
        this.fogColorOverridden = false;
        this.fogDensityOverridden = false;
        this.firstTick = true;
        this.newSave = false;
        this.triggerManager = new TriggerManager(this);
        this.a = false;
        this.C = new ArrayList();
        this.b = new ArrayList<>();
        this.D = new ArrayList();
        this.E = new TreeSet();
        this.F = new HashSet();
        this.c = new ArrayList();
        this.G = new ArrayList();
        this.d = new ArrayList();
        this.e = new ArrayList();
        this.H = 16777215L;
        this.f = 0;
        this.g = (new Random()).nextInt();
        this.m = 0;
        this.n = 0;
        this.o = false;
        this.I = System.currentTimeMillis();
        this.p = 40;
        this.r = new Random();
        this.s = false;
        this.u = new ArrayList();
        this.K = new ArrayList();
        this.M = 0;
        this.N = true;
        this.O = true;
        this.Q = this.r.nextInt(12000);
        this.R = new ArrayList();
        this.B = false;
        this.w = isavehandler;
        this.x = new ei(l, s);
        this.t = worldprovider;
        this.z = new hc(isavehandler);
        worldprovider.a(this);
        this.v = b();
        k();
        E();
        this.mapHandler = null;
        this.script = new Script(this);
    }

    public Level(Level world, xa worldprovider) {
        this.h = 1013904223;
        this.fogColorOverridden = false;
        this.fogDensityOverridden = false;
        this.firstTick = true;
        this.newSave = false;
        this.triggerManager = new TriggerManager(this);
        this.a = false;
        this.C = new ArrayList();
        this.b = new ArrayList<>();
        this.D = new ArrayList();
        this.E = new TreeSet();
        this.F = new HashSet();
        this.c = new ArrayList();
        this.G = new ArrayList();
        this.d = new ArrayList();
        this.e = new ArrayList();
        this.H = 16777215L;
        this.f = 0;
        this.g = (new Random()).nextInt();
        this.m = 0;
        this.n = 0;
        this.o = false;
        this.I = System.currentTimeMillis();
        this.p = 40;
        this.r = new Random();
        this.s = false;
        this.u = new ArrayList();
        this.K = new ArrayList();
        this.M = 0;
        this.N = true;
        this.O = true;
        this.Q = this.r.nextInt(12000);
        this.R = new ArrayList();
        this.B = false;
        this.I = world.I;
        this.w = world.w;
        this.x = new ei(world.x);
        this.z = new hc(this.w);
        this.t = worldprovider;
        worldprovider.a(this);
        this.v = b();
        k();
        E();
        this.mapHandler = null;
        this.script = new Script(this);
    }

    public Level(wt isavehandler, String s, long l) {
        this(null, isavehandler, s, l, (xa)null);
    }

    public Level(String levelName, wt isavehandler, String s, long l) {
        this(levelName, isavehandler, s, l, (xa)null);
    }

    public Level(String levelName, wt isavehandler, String s, long l, xa worldprovider) {
        this.h = 1013904223;
        this.fogColorOverridden = false;
        this.fogDensityOverridden = false;
        this.firstTick = true;
        this.newSave = false;
        this.triggerManager = new TriggerManager(this);
        File mcDir = Minecraft.b();
        File mapDir = new File(mcDir, "../maps");
        File levelFile = new File(mapDir, levelName);
        nh.a().loadMapTranslation(levelFile);
        this.mapHandler = (wt)new mx(mapDir, levelName, false);
        this.levelDir = levelFile;
        this.a = false;
        this.C = new ArrayList();
        this.b = new ArrayList<Entity>();
        this.D = new ArrayList();
        this.E = new TreeSet();
        this.F = new HashSet();
        this.c = new ArrayList();
        this.G = new ArrayList();
        this.d = new ArrayList();
        this.e = new ArrayList();
        this.H = 16777215L;
        this.f = 0;
        this.g = (new Random()).nextInt();
        this.m = 0;
        this.n = 0;
        this.o = false;
        this.I = System.currentTimeMillis();
        this.p = 40;
        this.r = new Random();
        this.s = false;
        this.u = new ArrayList();
        this.K = new ArrayList();
        this.M = 0;
        this.N = true;
        this.O = true;
        this.Q = this.r.nextInt(12000);
        this.R = new ArrayList();
        this.B = false;
        this.w = isavehandler;
        if (isavehandler != null) {
            this.z = new hc(isavehandler);
            this.x = isavehandler.c();
        } else {
            this.z = new hc(this.mapHandler);
        }
        if (this.x == null) {
            this.newSave = true;
            this.x = this.mapHandler.c();
        }
        if (!TerrainImage.loadMap(levelFile))
            TerrainImage.loadMap(new File(new File(mcDir, "saves"), s));
        this.s = (this.x == null);
        if (worldprovider != null) {
            this.t = worldprovider;
        } else if (this.x != null && this.x.i() == -1) {
            this.t = xa.a(-1);
        } else {
            this.t = xa.a(0);
        }
        boolean flag = false;
        if (this.x == null) {
            this.x = new ei(l, s);
            flag = true;
        } else {
            this.x.a(s);
        }
        this.x.useImages = TerrainImage.isLoaded;
        if (this.x.triggerData != null)
            this.triggerManager.loadFromTagCompound(this.x.triggerData);
        this.t.a(this);
        loadBrightness();
        this.v = b();
        if (flag) {
            c();
            this.y = true;
            int i = 0;
            int j;
            for (j = 0; !this.t.a(i, j); j += this.r.nextInt(64) - this.r.nextInt(64))
                i += this.r.nextInt(64) - this.r.nextInt(64);
            this.x.a(i, a(i, j), j);
            this.y = false;
        }
        k();
        E();
        loadMapMusic();
        loadMapSounds();
        this.script = new Script(this);
        if (this.x.globalScope != null)
            ScopeTag.loadScopeFromTag(this.script.globalScope, this.x.globalScope);
        this.scriptHandler = new JScriptHandler(this, levelFile);
        this.musicScripts = new MusicScripts(this.script, levelFile, this.scriptHandler);
        if (this.x.musicScope != null)
            ScopeTag.loadScopeFromTag(this.musicScripts.scope, this.x.musicScope);
        this.scope = this.script.getNewScope();
        if (this.x.worldScope != null)
            ScopeTag.loadScopeFromTag(this.scope, this.x.worldScope);
        loadSoundOverrides();
        EntityDescriptions.loadDescriptions(new File(levelFile, "entitys"));
        ItemCustom.loadItems(new File(levelFile, "items"));
        this.undoStack = new UndoStack();
        TileEntityNpcPath.lastEntity = null;
    }

    public void loadMapTextures() {
        Minecraft.minecraftInstance.p.b();
        for (Object obj : Minecraft.minecraftInstance.p.b.entrySet()) {
            Map.Entry entry = (Map.Entry)obj;
            String texName = (String)entry.getKey();
            int texID = ((Integer)entry.getValue()).intValue();
            try {
                Minecraft.minecraftInstance.p.loadTexture(texID, texName);
            } catch (IllegalArgumentException ignoreNulls) {}
        }
        loadTextureAnimations();
        TextureFanFX.loadImage();
        sd.loadImage();
        cg.loadImage();
        FlowingLavaTextureBinder2.loadImage();
        hs.loadImage();
        vs.loadImage();
        oh.loadImage();
        ia.loadGrass("/misc/grasscolor.png");
        jh.loadFoliage("/misc/foliagecolor.png");
        this.x.loadTextureReplacements(this);
    }

    private void loadTextureAnimations() {
        Minecraft.minecraftInstance.p.clearTextureAnimations();
        File animationFile = new File(this.levelDir, "animations.txt");
        if (animationFile.exists())
            try {
                BufferedReader reader = new BufferedReader(new FileReader(animationFile));
                try {
                    while (reader.ready()) {
                        String line = reader.readLine();
                        String[] parts = line.split(",", 7);
                        if (parts.length == 7)
                            try {
                                String texName = parts[1].trim();
                                String animTex = parts[2].trim();
                                int x = Integer.parseInt(parts[3].trim());
                                int y = Integer.parseInt(parts[4].trim());
                                int w = Integer.parseInt(parts[5].trim());
                                int h = Integer.parseInt(parts[6].trim());
                                TextureAnimated t = new TextureAnimated(texName, animTex, x, y, w, h);
                                Minecraft.minecraftInstance.p.registerTextureAnimation(parts[0].trim(), t);
                            } catch (Exception e) {}
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
    }

    public BufferedImage loadMapTexture(String texName) {
        File terrainTexture = new File(this.levelDir, texName);
        if (terrainTexture.exists())
            try {
                BufferedImage bufferedimage = ImageIO.read(terrainTexture);
                return bufferedimage;
            } catch (Exception exception) {}
        return null;
    }

    public void updateChunkProvider() {
        this.v = b();
    }

    protected cl b() {
        MapChunkLoader mapChunkLoader;
        if (this.w == null) {
            bf ichunkloader = this.mapHandler.a(this.t);
        } else {
            bf ichunkloader = this.w.a(this.t);
            if (this.mapHandler != null)
                mapChunkLoader = new MapChunkLoader(this.mapHandler.a(this.t), ichunkloader);
        }
        return new kx(this, (bf)mapChunkLoader, this.t.b());
    }

    protected void c() {
        this.y = true;
        int i = 0;
        byte byte0 = 64;
        int j;
        for (j = 0; !this.t.a(i, j); j += this.r.nextInt(64) - this.r.nextInt(64))
            i += this.r.nextInt(64) - this.r.nextInt(64);
        this.x.a(i, getFirstUncoveredBlockY(i, j), j);
        this.y = false;
    }

    public void d() {
        if (this.x.d() <= 0)
            this.x.b(64);
        int i = this.x.c();
        int j;
        for (j = this.x.e(); a(i, j) == 0; j += this.r.nextInt(8) - this.r.nextInt(8))
            i += this.r.nextInt(8) - this.r.nextInt(8);
        this.x.a(i);
        this.x.b(getFirstUncoveredBlockY(i, j));
        this.x.c(j);
    }

    public int getFirstUncoveredBlockY(int i, int j) {
        int k;
        for (k = 127; d(i, k, j) && k > 0; k--);
        return k;
    }

    public int a(int i, int j) {
        int k;
        for (k = 127; d(i, k, j) && k > 0; k--);
        return a(i, k, j);
    }

    public void e() {}

    public void a(gs entityplayer) {
        try {
            nu nbttagcompound = this.x.h();
            if (nbttagcompound != null) {
                entityplayer.e(nbttagcompound);
                this.x.a(null);
            }
            if (this.v instanceof kx) {
                kx chunkproviderloadorgenerate = (kx)this.v;
                int i = in.d((int)entityplayer.aM) >> 4;
                int j = in.d((int)entityplayer.aO) >> 4;
                chunkproviderloadorgenerate.d(i, j);
            }
            b(entityplayer);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void a(boolean flag, yb iprogressupdate) {
        if (!this.v.b())
            return;
        if (iprogressupdate != null)
            iprogressupdate.b("Saving level");
        D();
        if (iprogressupdate != null)
            iprogressupdate.d("Saving chunks");
        this.v.a(flag, iprogressupdate);
    }

    private void D() {
        r();
        this.x.globalScope = ScopeTag.getTagFromScope(this.script.globalScope);
        this.x.worldScope = ScopeTag.getTagFromScope(this.scope);
        this.x.musicScope = ScopeTag.getTagFromScope(this.musicScripts.scope);
        if (this.w != null)
            this.w.a(this.x, this.d);
        if (DebugMode.levelEditing || this.w == null)
            this.mapHandler.a(this.x, this.d);
        this.z.a();
    }

    public boolean a(int i) {
        if (!this.v.b())
            return true;
        if (i == 0)
            D();
        return this.v.a(false, null);
    }

    public int a(int i, int j, int k) {
        if (i < -32000000 || k < -32000000 || i >= 32000000 || k > 32000000)
            return 0;
        if (j < 0)
            return 0;
        if (j >= 128)
            return 0;
        return c(i >> 4, k >> 4).a(i & 0xF, j, k & 0xF);
    }

    public boolean d(int i, int j, int k) {
        return (a(i, j, k) == 0);
    }

    public boolean i(int i, int j, int k) {
        if (j < 0 || j >= 128)
            return false;
        return f(i >> 4, k >> 4);
    }

    public boolean b(int i, int j, int k, int l) {
        return a(i - l, j - l, k - l, i + l, j + l, k + l);
    }

    public boolean a(int i, int j, int k, int l, int i1, int j1) {
        if (i1 < 0 || j >= 128)
            return false;
        i >>= 4;
        j >>= 4;
        k >>= 4;
        l >>= 4;
        i1 >>= 4;
        j1 >>= 4;
        for (int k1 = i; k1 <= l; k1++) {
            for (int l1 = k; l1 <= j1; l1++) {
                if (!f(k1, l1))
                    return false;
            }
        }
        return true;
    }

    private boolean f(int i, int j) {
        return this.v.a(i, j);
    }

    public lm b(int i, int j) {
        return c(i >> 4, j >> 4);
    }

    public lm c(int i, int j) {
        return this.v.b(i, j);
    }

    public boolean a(int i, int j, int k, int l, int i1) {
        if (i < -32000000 || k < -32000000 || i >= 32000000 || k > 32000000)
            return false;
        if (j < 0)
            return false;
        if (j >= 128)
            return false;
        lm chunk = c(i >> 4, k >> 4);
        return chunk.a(i & 0xF, j, k & 0xF, l, i1);
    }

    public boolean setBlockAndMetadataTemp(int i, int j, int k, int l, int i1) {
        if (i < -32000000 || k < -32000000 || i >= 32000000 || k > 32000000)
            return false;
        if (j < 0)
            return false;
        if (j >= 128)
            return false;
        lm chunk = c(i >> 4, k >> 4);
        return chunk.setBlockIDWithMetadataTemp(i & 0xF, j, k & 0xF, l, i1);
    }

    public boolean c(int i, int j, int k, int l) {
        if (i < -32000000 || k < -32000000 || i >= 32000000 || k > 32000000)
            return false;
        if (j < 0)
            return false;
        if (j >= 128)
            return false;
        lm chunk = c(i >> 4, k >> 4);
        return chunk.a(i & 0xF, j, k & 0xF, l);
    }

    public ln f(int i, int j, int k) {
        int l = a(i, j, k);
        if (l == 0)
            return ln.a;
        return (Tile.m[l]).bA;
    }

    public int e(int i, int j, int k) {
        if (i < -32000000 || k < -32000000 || i >= 32000000 || k > 32000000)
            return 0;
        if (j < 0)
            return 0;
        if (j >= 128)
            return 0;
        lm chunk = c(i >> 4, k >> 4);
        i &= 0xF;
        k &= 0xF;
        return chunk.b(i, j, k);
    }

    public void d(int i, int j, int k, int l) {
        if (e(i, j, k, l)) {
            int i1 = a(i, j, k);
            if (Tile.t[i1 & 0xFF]) {
                g(i, j, k, i1);
            } else {
                i(i, j, k, i1);
            }
        }
    }

    public boolean e(int i, int j, int k, int l) {
        if (i < -32000000 || k < -32000000 || i >= 32000000 || k > 32000000)
            return false;
        if (j < 0)
            return false;
        if (j >= 128)
            return false;
        lm chunk = c(i >> 4, k >> 4);
        i &= 0xF;
        k &= 0xF;
        chunk.b(i, j, k, l);
        return true;
    }

    public boolean f(int i, int j, int k, int l) {
        if (c(i, j, k, l)) {
            g(i, j, k, l);
            return true;
        }
        return false;
    }

    public boolean b(int i, int j, int k, int l, int i1) {
        if (a(i, j, k, l, i1)) {
            g(i, j, k, l);
            return true;
        }
        return false;
    }

    public void j(int i, int j, int k) {
        for (int l = 0; l < this.u.size(); l++)
            ((pm)this.u.get(l)).a(i, j, k);
    }

    protected void g(int i, int j, int k, int l) {
        j(i, j, k);
        i(i, j, k, l);
    }

    public void h(int i, int j, int k, int l) {
        if (k > l) {
            int i1 = l;
            l = k;
            k = i1;
        }
        b(i, k, j, i, l, j);
    }

    public void k(int i, int j, int k) {
        for (int l = 0; l < this.u.size(); l++)
            ((pm)this.u.get(l)).b(i, j, k, i, j, k);
    }

    public void b(int i, int j, int k, int l, int i1, int j1) {
        for (int k1 = 0; k1 < this.u.size(); k1++)
            ((pm)this.u.get(k1)).b(i, j, k, l, i1, j1);
    }

    public void i(int i, int j, int k, int l) {
        l(i - 1, j, k, l);
        l(i + 1, j, k, l);
        l(i, j - 1, k, l);
        l(i, j + 1, k, l);
        l(i, j, k - 1, l);
        l(i, j, k + 1, l);
    }

    private void l(int i, int j, int k, int l) {
        if (this.o || this.B)
            return;
        Tile block = Tile.m[a(i, j, k)];
        if (block != null)
            block.b(this, i, j, k, l);
    }

    public boolean l(int i, int j, int k) {
        return c(i >> 4, k >> 4).c(i & 0xF, j, k & 0xF);
    }

    public int m(int i, int j, int k) {
        if (j < 0)
            return 0;
        if (j >= 128)
            j = 127;
        return c(i >> 4, k >> 4).c(i & 0xF, j, k & 0xF, 0);
    }

    public int n(int i, int j, int k) {
        return a(i, j, k, true);
    }

    public int a(int i, int j, int k, boolean flag) {
        if (i < -32000000 || k < -32000000 || i >= 32000000 || k > 32000000)
            return 15;
        if (flag) {
            int l = a(i, j, k);
            if (l != 0 && (l == Tile.al.bn || l == Tile.aB.bn || l == Tile.aI.bn || l == Tile.au.bn || Tile.m[l] instanceof BlockStairMulti)) {
                int i1 = a(i, j + 1, k, false);
                int j1 = a(i + 1, j, k, false);
                int k1 = a(i - 1, j, k, false);
                int l1 = a(i, j, k + 1, false);
                int i2 = a(i, j, k - 1, false);
                if (j1 > i1)
                    i1 = j1;
                if (k1 > i1)
                    i1 = k1;
                if (l1 > i1)
                    i1 = l1;
                if (i2 > i1)
                    i1 = i2;
                return i1;
            }
        }
        if (j < 0)
            return 0;
        if (j >= 128)
            j = 127;
        lm chunk = c(i >> 4, k >> 4);
        i &= 0xF;
        k &= 0xF;
        return chunk.c(i, j, k, this.f);
    }

    public boolean o(int i, int j, int k) {
        if (i < -32000000 || k < -32000000 || i >= 32000000 || k > 32000000)
            return false;
        if (j < 0)
            return false;
        if (j >= 128)
            return true;
        if (!f(i >> 4, k >> 4))
            return false;
        lm chunk = c(i >> 4, k >> 4);
        i &= 0xF;
        k &= 0xF;
        return chunk.c(i, j, k);
    }

    public int d(int i, int j) {
        if (i < -32000000 || j < -32000000 || i >= 32000000 || j > 32000000)
            return 0;
        if (!f(i >> 4, j >> 4))
            return 0;
        lm chunk = c(i >> 4, j >> 4);
        return chunk.b(i & 0xF, j & 0xF);
    }

    public void a(eb enumskyblock, int i, int j, int k, int l) {
        if (this.t.e && enumskyblock == eb.a)
            return;
        if (!i(i, j, k))
            return;
        if (enumskyblock == eb.a) {
            if (o(i, j, k))
                l = 15;
        } else if (enumskyblock == eb.b) {
            int i1 = a(i, j, k);
            if (Tile.m[i1] != null && Tile.m[i1].getBlockLightValue(this, i, j, k) < l)
                l = Tile.m[i1].getBlockLightValue(this, i, j, k);
        }
        if (a(enumskyblock, i, j, k) != l)
            a(enumskyblock, i, j, k, i, j, k);
    }

    public int a(eb enumskyblock, int i, int j, int k) {
        if (j < 0)
            j = 0;
        if (j >= 128)
            j = 127;
        if (j < 0 || j >= 128 || i < -32000000 || k < -32000000 || i >= 32000000 || k > 32000000)
            return enumskyblock.c;
        int l = i >> 4;
        int i1 = k >> 4;
        if (!f(l, i1))
            return 0;
        lm chunk = c(l, i1);
        return chunk.a(enumskyblock, i & 0xF, j, k & 0xF);
    }

    public void b(eb enumskyblock, int i, int j, int k, int l) {
        if (i < -32000000 || k < -32000000 || i >= 32000000 || k > 32000000)
            return;
        if (j < 0)
            return;
        if (j >= 128)
            return;
        if (!f(i >> 4, k >> 4))
            return;
        lm chunk = c(i >> 4, k >> 4);
        chunk.a(enumskyblock, i & 0xF, j, k & 0xF, l);
        for (int i1 = 0; i1 < this.u.size(); i1++)
            ((pm)this.u.get(i1)).a(i, j, k);
    }

    public float getLightValue(int i, int j, int k) {
        int lightValue = n(i, j, k);
        float torchLight = PlayerTorch.getTorchLight(this, i, j, k);
        if (lightValue < torchLight)
            return Math.min(torchLight, 15.0F);
        return lightValue;
    }

    private float getBrightnessLevel(float lightValue) {
        int floorValue = (int)Math.floor(lightValue);
        if (floorValue != lightValue) {
            int ceilValue = (int)Math.ceil(lightValue);
            float lerpValue = lightValue - floorValue;
            return (1.0F - lerpValue) * this.t.f[floorValue] + lerpValue * this.t.f[ceilValue];
        }
        return this.t.f[floorValue];
    }

    public float a(int i, int j, int k, int l) {
        float lightValue = getLightValue(i, j, k);
        if (lightValue < l)
            lightValue = l;
        return getBrightnessLevel(lightValue);
    }

    public float c(int i, int j, int k) {
        float lightValue = getLightValue(i, j, k);
        return getBrightnessLevel(lightValue);
    }

    public float getDayLight() {
        int lightValue = 15 - this.f;
        return this.t.f[lightValue];
    }

    public boolean f() {
        return (this.f < 4);
    }

    public vf a(bt vec3d, bt vec3d1) {
        return a(vec3d, vec3d1, false, false);
    }

    public vf a(bt vec3d, bt vec3d1, boolean flag) {
        return a(vec3d, vec3d1, flag, false);
    }

    public vf a(bt vec3d, bt vec3d1, boolean flag, boolean flag1) {
        return rayTraceBlocks2(vec3d, vec3d1, flag, flag1, true);
    }

    public vf rayTraceBlocks2(bt vec3d, bt vec3d1, boolean flag, boolean flag1, boolean collideWithClip) {
        if (Double.isNaN(vec3d.a) || Double.isNaN(vec3d.b) || Double.isNaN(vec3d.c))
            return null;
        if (Double.isNaN(vec3d1.a) || Double.isNaN(vec3d1.b) || Double.isNaN(vec3d1.c))
            return null;
        int i = in.b(vec3d1.a);
        int j = in.b(vec3d1.b);
        int k = in.b(vec3d1.c);
        int l = in.b(vec3d.a);
        int i1 = in.b(vec3d.b);
        int j1 = in.b(vec3d.c);
        int k1 = a(l, i1, j1);
        int i2 = e(l, i1, j1);
        Tile block = Tile.m[k1];
        if ((!flag1 || block == null || block.e(this, l, i1, j1) != null) && k1 > 0 && block.a(i2, flag) && (collideWithClip || (k1 != AC_Blocks.clipBlock.bn && !dp.isLadderID(k1)))) {
            vf movingobjectposition = block.a(this, l, i1, j1, vec3d, vec3d1);
            if (movingobjectposition != null)
                return movingobjectposition;
        }
        for (int l1 = 200; l1-- >= 0; ) {
            if (Double.isNaN(vec3d.a) || Double.isNaN(vec3d.b) || Double.isNaN(vec3d.c))
                return null;
            if (l == i && i1 == j && j1 == k)
                return null;
            boolean flag2 = true;
            boolean flag3 = true;
            boolean flag4 = true;
            double d = 999.0D;
            double d1 = 999.0D;
            double d2 = 999.0D;
            if (i > l) {
                d = l + 1.0D;
            } else if (i < l) {
                d = l + 0.0D;
            } else {
                flag2 = false;
            }
            if (j > i1) {
                d1 = i1 + 1.0D;
            } else if (j < i1) {
                d1 = i1 + 0.0D;
            } else {
                flag3 = false;
            }
            if (k > j1) {
                d2 = j1 + 1.0D;
            } else if (k < j1) {
                d2 = j1 + 0.0D;
            } else {
                flag4 = false;
            }
            double d3 = 999.0D;
            double d4 = 999.0D;
            double d5 = 999.0D;
            double d6 = vec3d1.a - vec3d.a;
            double d7 = vec3d1.b - vec3d.b;
            double d8 = vec3d1.c - vec3d.c;
            if (flag2)
                d3 = (d - vec3d.a) / d6;
            if (flag3)
                d4 = (d1 - vec3d.b) / d7;
            if (flag4)
                d5 = (d2 - vec3d.c) / d8;
            byte byte0 = 0;
            if (d3 < d4 && d3 < d5) {
                if (i > l) {
                    byte0 = 4;
                } else {
                    byte0 = 5;
                }
                vec3d.a = d;
                vec3d.b += d7 * d3;
                vec3d.c += d8 * d3;
            } else if (d4 < d5) {
                if (j > i1) {
                    byte0 = 0;
                } else {
                    byte0 = 1;
                }
                vec3d.a += d6 * d4;
                vec3d.b = d1;
                vec3d.c += d8 * d4;
            } else {
                if (k > j1) {
                    byte0 = 2;
                } else {
                    byte0 = 3;
                }
                vec3d.a += d6 * d5;
                vec3d.b += d7 * d5;
                vec3d.c = d2;
            }
            bt vec3d2 = bt.b(vec3d.a, vec3d.b, vec3d.c);
            l = (int)(vec3d2.a = in.b(vec3d.a));
            if (byte0 == 5) {
                l--;
                vec3d2.a++;
            }
            i1 = (int)(vec3d2.b = in.b(vec3d.b));
            if (byte0 == 1) {
                i1--;
                vec3d2.b++;
            }
            j1 = (int)(vec3d2.c = in.b(vec3d.c));
            if (byte0 == 3) {
                j1--;
                vec3d2.c++;
            }
            int j2 = a(l, i1, j1);
            int k2 = e(l, i1, j1);
            Tile block1 = Tile.m[j2];
            if ((!flag1 || block1 == null || block1.e(this, l, i1, j1) != null) && j2 > 0 && block1.a(k2, flag) && block1.shouldRender(this, l, i1, j1)) {
                vf movingobjectposition1 = block1.a(this, l, i1, j1, vec3d, vec3d1);
                if (movingobjectposition1 != null && (collideWithClip || (block1.bn != Blocks.clipBlock.bn && !dp.isLadderID(block1.bn))))
                    return movingobjectposition1;
            }
        }
        return null;
    }

    public void a(sn entity, String s, float f, float f1) {
        for (int i = 0; i < this.u.size(); i++)
            ((pm)this.u.get(i)).a(s, entity.aM, entity.aN - entity.bf, entity.aO, f, f1);
    }

    public void a(double d, double d1, double d2, String s, float f, float f1) {
        for (int i = 0; i < this.u.size(); i++)
            ((pm)this.u.get(i)).a(s, d, d1, d2, f, f1);
    }

    public void a(String s, int i, int j, int k) {
        for (int l = 0; l < this.u.size(); l++)
            ((pm)this.u.get(l)).a(s, i, j, k);
    }

    public void a(String s, double d, double d1, double d2, double d3, double d4, double d5) {
        for (int i = 0; i < this.u.size(); i++)
            ((pm)this.u.get(i)).a(s, d, d1, d2, d3, d4, d5);
    }

    public boolean a(sn entity) {
        this.e.add(entity);
        return true;
    }

    public boolean b(sn entity) {
        int i = in.b(entity.aM / 16.0D);
        int j = in.b(entity.aO / 16.0D);
        boolean flag = false;
        if (entity instanceof gs)
            flag = true;
        if (flag || f(i, j)) {
            if (entity instanceof gs) {
                gs entityplayer = (gs)entity;
                this.d.add(entityplayer);
                y();
            }
            c(i, j).a(entity);
            if (!this.b.contains(entity))
                this.b.add(entity);
            c(entity);
            return true;
        }
        return false;
    }

    protected void c(sn entity) {
        for (int i = 0; i < this.u.size(); i++)
            ((pm)this.u.get(i)).a(entity);
    }

    protected void d(sn entity) {
        for (int i = 0; i < this.u.size(); i++)
            ((pm)this.u.get(i)).b(entity);
    }

    public void e(sn entity) {
        if (entity.aG != null)
            entity.aG.i(null);
        if (entity.aH != null)
            entity.i(null);
        entity.K();
        if (entity instanceof gs) {
            this.d.remove(entity);
            y();
        }
    }

    public void a(pm iworldaccess) {
        this.u.add(iworldaccess);
    }

    public void b(pm iworldaccess) {
        this.u.remove(iworldaccess);
    }

    public List a(sn entity, eq axisalignedbb) {
        this.K.clear();
        int i = in.b(axisalignedbb.a);
        int j = in.b(axisalignedbb.d + 1.0D);
        int k = in.b(axisalignedbb.b);
        int l = in.b(axisalignedbb.e + 1.0D);
        int i1 = in.b(axisalignedbb.c);
        int j1 = in.b(axisalignedbb.f + 1.0D);
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = i1; l1 < j1; l1++) {
                if (i(k1, 64, l1))
                    for (int i2 = k - 1; i2 < l; i2++) {
                        Tile block = Tile.m[a(k1, i2, l1)];
                        if (block != null && (entity.collidesWithClipBlocks || (block.bn != Blocks.clipBlock.bn && !dp.isLadderID(block.bn))))
                            block.a(this, k1, i2, l1, axisalignedbb, this.K);
                    }
            }
        }
        double d = 0.25D;
        List<sn> list = b(entity, axisalignedbb.b(d, d, d));
        for (int j2 = 0; j2 < list.size(); j2++) {
            eq axisalignedbb1 = ((sn)list.get(j2)).f();
            if (axisalignedbb1 != null && axisalignedbb1.a(axisalignedbb))
                this.K.add(axisalignedbb1);
            axisalignedbb1 = entity.a(list.get(j2));
            if (axisalignedbb1 != null && axisalignedbb1.a(axisalignedbb))
                this.K.add(axisalignedbb1);
        }
        return this.K;
    }

    public int a(float f) {
        float f1 = b(f);
        float f2 = 1.0F - in.b(f1 * 3.141593F * 2.0F) * 2.0F + 0.5F;
        if (f2 < 0.0F)
            f2 = 0.0F;
        if (f2 > 1.0F)
            f2 = 1.0F;
        f2 = 1.0F - f2;
        f2 = (float)(f2 * (1.0D - (g(f) * 5.0F) / 16.0D));
        f2 = (float)(f2 * (1.0D - (f(f) * 5.0F) / 16.0D));
        f2 = 1.0F - f2;
        return (int)(f2 * 11.0F);
    }

    public bt a(sn entity, float f) {
        float f1 = b(f);
        float f2 = in.b(f1 * 3.141593F * 2.0F) * 2.0F + 0.5F;
        if (f2 < 0.0F)
            f2 = 0.0F;
        if (f2 > 1.0F)
            f2 = 1.0F;
        int i = in.b(entity.aM);
        int j = in.b(entity.aO);
        float f3 = (float)a().b(i, j);
        int k = a().a(i, j).a(f3);
        float f4 = (k >> 16 & 0xFF) / 255.0F;
        float f5 = (k >> 8 & 0xFF) / 255.0F;
        float f6 = (k & 0xFF) / 255.0F;
        f4 *= f2;
        f5 *= f2;
        f6 *= f2;
        float f7 = g(f);
        if (f7 > 0.0F) {
            float f8 = (f4 * 0.3F + f5 * 0.59F + f6 * 0.11F) * 0.6F;
            float f10 = 1.0F - f7 * 0.75F;
            f4 = f4 * f10 + f8 * (1.0F - f10);
            f5 = f5 * f10 + f8 * (1.0F - f10);
            f6 = f6 * f10 + f8 * (1.0F - f10);
        }
        float f9 = f(f);
        if (f9 > 0.0F) {
            float f11 = (f4 * 0.3F + f5 * 0.59F + f6 * 0.11F) * 0.2F;
            float f13 = 1.0F - f9 * 0.75F;
            f4 = f4 * f13 + f11 * (1.0F - f13);
            f5 = f5 * f13 + f11 * (1.0F - f13);
            f6 = f6 * f13 + f11 * (1.0F - f13);
        }
        if (this.n > 0) {
            float f12 = this.n - f;
            if (f12 > 1.0F)
                f12 = 1.0F;
            f12 *= 0.45F;
            f4 = f4 * (1.0F - f12) + 0.8F * f12;
            f5 = f5 * (1.0F - f12) + 0.8F * f12;
            f6 = f6 * (1.0F - f12) + 1.0F * f12;
        }
        return bt.b(f4, f5, f6);
    }

    public float b(float f) {
        return this.t.a(this.x.getTimeOfDay(), f * this.x.getTimeRate());
    }

    public bt c(float f) {
        float f1 = b(f);
        float f2 = in.b(f1 * 3.141593F * 2.0F) * 2.0F + 0.5F;
        if (f2 < 0.0F)
            f2 = 0.0F;
        if (f2 > 1.0F)
            f2 = 1.0F;
        float f3 = (float)(this.H >> 16L & 0xFFL) / 255.0F;
        float f4 = (float)(this.H >> 8L & 0xFFL) / 255.0F;
        float f5 = (float)(this.H & 0xFFL) / 255.0F;
        float f6 = g(f);
        if (f6 > 0.0F) {
            float f7 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.6F;
            float f9 = 1.0F - f6 * 0.95F;
            f3 = f3 * f9 + f7 * (1.0F - f9);
            f4 = f4 * f9 + f7 * (1.0F - f9);
            f5 = f5 * f9 + f7 * (1.0F - f9);
        }
        f3 *= f2 * 0.9F + 0.1F;
        f4 *= f2 * 0.9F + 0.1F;
        f5 *= f2 * 0.85F + 0.15F;
        float f8 = f(f);
        if (f8 > 0.0F) {
            float f10 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.2F;
            float f11 = 1.0F - f8 * 0.95F;
            f3 = f3 * f11 + f10 * (1.0F - f11);
            f4 = f4 * f11 + f10 * (1.0F - f11);
            f5 = f5 * f11 + f10 * (1.0F - f11);
        }
        return bt.b(f3, f4, f5);
    }

    public bt d(float f) {
        float f1 = b(f);
        bt returnColor = this.t.b(f1, f);
        if (this.x.overrideFogColor)
            if (this.fogColorOverridden) {
                returnColor.a = this.x.fogR;
                returnColor.b = this.x.fogG;
                returnColor.c = this.x.fogB;
            } else {
                returnColor.a = (1.0F - f) * returnColor.a + (f * this.x.fogR);
                returnColor.b = (1.0F - f) * returnColor.b + (f * this.x.fogG);
                returnColor.c = (1.0F - f) * returnColor.c + (f * this.x.fogB);
            }
        return returnColor;
    }

    public float getFogStart(float start, float f) {
        if (this.x.overrideFogDensity) {
            if (this.fogDensityOverridden)
                return this.x.fogStart;
            return f * this.x.fogStart + (1.0F - f) * start;
        }
        return start;
    }

    public float getFogEnd(float end, float f) {
        if (this.x.overrideFogDensity) {
            if (this.fogDensityOverridden)
                return this.x.fogEnd;
            return f * this.x.fogEnd + (1.0F - f) * end;
        }
        return end;
    }

    public int e(int i, int j) {
        lm chunk = b(i, j);
        int k;
        for (k = 127; f(i, k, j).c() && k > 0; k--);
        i &= 0xF;
        j &= 0xF;
        while (k > 0) {
            int l = chunk.a(i, k, j);
            ln material = (l != 0) ? (Tile.m[l]).bA : ln.a;
            if (!material.c() && !material.d()) {
                k--;
                continue;
            }
            return k + 1;
        }
        return -1;
    }

    public float e(float f) {
        float f1 = b(f);
        float f2 = 1.0F - in.b(f1 * 3.141593F * 2.0F) * 2.0F + 0.75F;
        if (f2 < 0.0F)
            f2 = 0.0F;
        if (f2 > 1.0F)
            f2 = 1.0F;
        return f2 * f2 * 0.5F;
    }

    public void c(int i, int j, int k, int l, int i1) {
        qy nextticklistentry = new qy(i, j, k, l);
        byte byte0 = 8;
        if (this.a) {
            if (a(nextticklistentry.a - byte0, nextticklistentry.b - byte0, nextticklistentry.c - byte0, nextticklistentry.a + byte0, nextticklistentry.b + byte0, nextticklistentry.c + byte0)) {
                int j1 = a(nextticklistentry.a, nextticklistentry.b, nextticklistentry.c);
                if (j1 == nextticklistentry.d && j1 > 0)
                    Tile.m[j1].a(this, nextticklistentry.a, nextticklistentry.b, nextticklistentry.c, this.r);
            }
            return;
        }
        if (a(i - byte0, j - byte0, k - byte0, i + byte0, j + byte0, k + byte0)) {
            if (l > 0)
                nextticklistentry.a(i1 + this.x.f());
            if (!this.F.contains(nextticklistentry)) {
                this.F.add(nextticklistentry);
                this.E.add(nextticklistentry);
            }
        }
    }

    public void cancelBlockUpdate(int i, int j, int k, int l) {
        qy nextticklistentry = new qy(i, j, k, l);
        if (this.F.contains(nextticklistentry))
            this.F.remove(nextticklistentry);
    }

    public void g() {
        for (int i = 0; i < this.e.size(); i++) {
            sn entity = this.e.get(i);
            entity.w_();
            if (entity.be)
                this.e.remove(i--);
        }
        this.b.removeAll(this.D);
        for (int j = 0; j < this.D.size(); j++) {
            sn entity1 = this.D.get(j);
            int i1 = entity1.bG;
            int k1 = entity1.bI;
            if (entity1.bF && f(i1, k1))
                c(i1, k1).b(entity1);
        }
        for (int k = 0; k < this.D.size(); k++)
            d(this.D.get(k));
        this.D.clear();
        for (int l = 0; l < this.b.size(); l++) {
            sn entity2 = (sn)this.b.get(l);
            if (entity2.aH != null) {
                if (!entity2.aH.be && entity2.aH.aG == entity2)
                    continue;
                entity2.aH.aG = null;
                entity2.aH = null;
            }
            if (!entity2.be && (!Minecraft.minecraftInstance.cameraActive || !Minecraft.minecraftInstance.cameraPause))
                if (!DebugMode.active || entity2 instanceof gs) {
                    f(entity2);
                    eq.b();
                }
            if (entity2.be) {
                int j1 = entity2.bG;
                int l1 = entity2.bI;
                if (entity2.bF && f(j1, l1))
                    c(j1, l1).b(entity2);
                this.b.remove(l--);
                d(entity2);
            }
            continue;
        }
        this.L = true;
        Iterator<ow> iterator = this.c.iterator();
        while (iterator.hasNext()) {
            ow tileentity = iterator.next();
            if (!tileentity.g())
                tileentity.n_();
            if (tileentity.g()) {
                iterator.remove();
                if (!tileentity.killedFromSaving) {
                    lm chunk = c(tileentity.e >> 4, tileentity.g >> 4);
                    if (chunk != null)
                        chunk.e(tileentity.e & 0xF, tileentity.f, tileentity.g & 0xF);
                }
            }
        }
        this.L = false;
        if (!this.G.isEmpty()) {
            Iterator<ow> iterator1 = this.G.iterator();
            while (iterator1.hasNext()) {
                ow tileentity1 = iterator1.next();
                if (!tileentity1.g()) {
                    if (!this.c.contains(tileentity1))
                        this.c.add(tileentity1);
                    lm chunk1 = c(tileentity1.e >> 4, tileentity1.g >> 4);
                    if (chunk1 != null)
                        chunk1.a(tileentity1.e & 0xF, tileentity1.f, tileentity1.g & 0xF, tileentity1);
                    j(tileentity1.e, tileentity1.f, tileentity1.g);
                }
            }
            this.G.clear();
        }
    }

    public void a(Collection collection) {
        if (this.L) {
            this.G.addAll(collection);
        } else {
            this.c.addAll(collection);
        }
    }

    public void f(sn entity) {
        a(entity, true);
    }

    public void a(sn entity, boolean flag) {
        int i = in.b(entity.aM);
        int j = in.b(entity.aO);
        byte byte0 = 32;
        if (flag && !a(i - byte0, 0, j - byte0, i + byte0, 128, j + byte0))
            return;
        entity.bl = entity.aM;
        entity.bm = entity.aN;
        entity.bn = entity.aO;
        entity.aU = entity.aS;
        entity.aV = entity.aT;
        if (flag && entity.bF)
            if (entity.stunned > 0) {
                entity.stunned--;
            } else if (entity.aH != null) {
                entity.s_();
            } else {
                entity.w_();
            }
        if (Double.isNaN(entity.aM) || Double.isInfinite(entity.aM))
            entity.aM = entity.bl;
        if (Double.isNaN(entity.aN) || Double.isInfinite(entity.aN))
            entity.aN = entity.bm;
        if (Double.isNaN(entity.aO) || Double.isInfinite(entity.aO))
            entity.aO = entity.bn;
        if (Double.isNaN(entity.aT) || Double.isInfinite(entity.aT))
            entity.aT = entity.aV;
        if (Double.isNaN(entity.aS) || Double.isInfinite(entity.aS))
            entity.aS = entity.aU;
        int k = in.b(entity.aM / 16.0D);
        int l = in.b(entity.aN / 16.0D);
        int i1 = in.b(entity.aO / 16.0D);
        if (!entity.bF || entity.bG != k || entity.bH != l || entity.bI != i1) {
            if (entity.bF && f(entity.bG, entity.bI))
                c(entity.bG, entity.bI).a(entity, entity.bH);
            if (f(k, i1)) {
                entity.bF = true;
                c(k, i1).a(entity);
            } else {
                entity.bF = false;
            }
        }
        if (flag && entity.bF && entity.aG != null)
            if (entity.aG.be || entity.aG.aH != entity) {
                entity.aG.aH = null;
                entity.aG = null;
            } else {
                f(entity.aG);
            }
    }

    public boolean a(eq axisalignedbb) {
        List<sn> list = b((sn)null, axisalignedbb);
        for (int i = 0; i < list.size(); i++) {
            sn entity = list.get(i);
            if (!entity.be && entity.aF)
                return false;
        }
        return true;
    }

    public boolean b(eq axisalignedbb) {
        int i = in.b(axisalignedbb.a);
        int j = in.b(axisalignedbb.d + 1.0D);
        int k = in.b(axisalignedbb.b);
        int l = in.b(axisalignedbb.e + 1.0D);
        int i1 = in.b(axisalignedbb.c);
        int j1 = in.b(axisalignedbb.f + 1.0D);
        if (axisalignedbb.a < 0.0D)
            i--;
        if (axisalignedbb.b < 0.0D)
            k--;
        if (axisalignedbb.c < 0.0D)
            i1--;
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    Tile block = Tile.m[a(k1, l1, i2)];
                    if (block != null && block.bA.d())
                        return true;
                }
            }
        }
        return false;
    }

    public boolean c(eq axisalignedbb) {
        int i = in.b(axisalignedbb.a);
        int j = in.b(axisalignedbb.d + 1.0D);
        int k = in.b(axisalignedbb.b);
        int l = in.b(axisalignedbb.e + 1.0D);
        int i1 = in.b(axisalignedbb.c);
        int j1 = in.b(axisalignedbb.f + 1.0D);
        if (a(i, k, i1, j, l, j1))
            for (int k1 = i; k1 < j; k1++) {
                for (int l1 = k; l1 < l; l1++) {
                    for (int i2 = i1; i2 < j1; i2++) {
                        int j2 = a(k1, l1, i2);
                        if (j2 == Tile.as.bn || j2 == Tile.D.bn || j2 == Tile.E.bn)
                            return true;
                    }
                }
            }
        return false;
    }

    public boolean a(eq axisalignedbb, ln material, sn entity) {
        int i = in.b(axisalignedbb.a);
        int j = in.b(axisalignedbb.d + 1.0D);
        int k = in.b(axisalignedbb.b);
        int l = in.b(axisalignedbb.e + 1.0D);
        int i1 = in.b(axisalignedbb.c);
        int j1 = in.b(axisalignedbb.f + 1.0D);
        if (!a(i, k, i1, j, l, j1))
            return false;
        boolean flag = false;
        bt vec3d = bt.b(0.0D, 0.0D, 0.0D);
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    Tile block = Tile.m[a(k1, l1, i2)];
                    if (block != null && block.bA == material) {
                        double d1 = ((l1 + 1) - rp.d(e(k1, l1, i2)));
                        if (l >= d1) {
                            flag = true;
                            block.a(this, k1, l1, i2, entity, vec3d);
                        }
                    }
                }
            }
        }
        if (vec3d.d() > 0.0D) {
            vec3d = vec3d.c();
            double d = 0.014D;
            entity.aP += vec3d.a * d;
            entity.aQ += vec3d.b * d;
            entity.aR += vec3d.c * d;
        }
        return flag;
    }

    public boolean a(eq axisalignedbb, ln material) {
        int i = in.b(axisalignedbb.a);
        int j = in.b(axisalignedbb.d + 1.0D);
        int k = in.b(axisalignedbb.b);
        int l = in.b(axisalignedbb.e + 1.0D);
        int i1 = in.b(axisalignedbb.c);
        int j1 = in.b(axisalignedbb.f + 1.0D);
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    Tile block = Tile.m[a(k1, l1, i2)];
                    if (block != null && block.bA == material)
                        return true;
                }
            }
        }
        return false;
    }

    public boolean b(eq axisalignedbb, ln material) {
        int i = in.b(axisalignedbb.a);
        int j = in.b(axisalignedbb.d + 1.0D);
        int k = in.b(axisalignedbb.b);
        int l = in.b(axisalignedbb.e + 1.0D);
        int i1 = in.b(axisalignedbb.c);
        int j1 = in.b(axisalignedbb.f + 1.0D);
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    Tile block = Tile.m[a(k1, l1, i2)];
                    if (block != null && block.bA == material) {
                        int j2 = e(k1, l1, i2);
                        double d = (l1 + 1);
                        if (j2 < 8)
                            d = (l1 + 1) - j2 / 8.0D;
                        if (d >= axisalignedbb.b)
                            return true;
                    }
                }
            }
        }
        return false;
    }

    public qx a(sn entity, double d, double d1, double d2, float f) {
        return a(entity, d, d1, d2, f, false);
    }

    public qx a(sn entity, double d, double d1, double d2, float f, boolean flag) {
        qx explosion = new qx(this, entity, d, d1, d2, f);
        explosion.a = flag;
        explosion.a();
        explosion.a(true);
        return explosion;
    }

    public float a(bt vec3d, eq axisalignedbb) {
        double d = 1.0D / ((axisalignedbb.d - axisalignedbb.a) * 2.0D + 1.0D);
        double d1 = 1.0D / ((axisalignedbb.e - axisalignedbb.b) * 2.0D + 1.0D);
        double d2 = 1.0D / ((axisalignedbb.f - axisalignedbb.c) * 2.0D + 1.0D);
        int i = 0;
        int j = 0;
        float f;
        for (f = 0.0F; f <= 1.0F; f = (float)(f + d)) {
            float f1;
            for (f1 = 0.0F; f1 <= 1.0F; f1 = (float)(f1 + d1)) {
                float f2;
                for (f2 = 0.0F; f2 <= 1.0F; f2 = (float)(f2 + d2)) {
                    double d3 = axisalignedbb.a + (axisalignedbb.d - axisalignedbb.a) * f;
                    double d4 = axisalignedbb.b + (axisalignedbb.e - axisalignedbb.b) * f1;
                    double d5 = axisalignedbb.c + (axisalignedbb.f - axisalignedbb.c) * f2;
                    if (a(bt.b(d3, d4, d5), vec3d) == null)
                        i++;
                    j++;
                }
            }
        }
        return i / j;
    }

    public void a(gs entityplayer, int i, int j, int k, int l) {
        if (l == 0)
            j--;
        if (l == 1)
            j++;
        if (l == 2)
            k--;
        if (l == 3)
            k++;
        if (l == 4)
            i--;
        if (l == 5)
            i++;
        if (a(i, j, k) == Tile.as.bn) {
            a(entityplayer, 1004, i, j, k, 0);
            f(i, j, k, 0);
        }
    }

    public sn a(Class class1) {
        return null;
    }

    public String h() {
        return "All: " + this.b.size();
    }

    public String i() {
        return this.v.c();
    }

    public ow b(int i, int j, int k) {
        lm chunk = c(i >> 4, k >> 4);
        if (chunk != null)
            return chunk.d(i & 0xF, j, k & 0xF);
        return null;
    }

    public ow getBlockTileEntityDontCreate(int i, int j, int k) {
        lm chunk = c(i >> 4, k >> 4);
        if (chunk != null)
            return chunk.getChunkBlockTileEntityDontCreate(i & 0xF, j, k & 0xF);
        return null;
    }

    public void a(int i, int j, int k, ow tileentity) {
        if (!tileentity.g()) {
            p(i, j, k);
            if (this.L) {
                tileentity.e = i;
                tileentity.f = j;
                tileentity.g = k;
                this.G.add(tileentity);
            } else {
                this.c.add(tileentity);
                lm chunk = c(i >> 4, k >> 4);
                if (chunk != null)
                    chunk.a(i & 0xF, j, k & 0xF, tileentity);
            }
        }
    }

    public void p(int i, int j, int k) {
        ow tileentity = getBlockTileEntityDontCreate(i, j, k);
        if (tileentity != null && this.L) {
            tileentity.i();
        } else {
            if (tileentity != null)
                this.c.remove(tileentity);
            lm chunk = c(i >> 4, k >> 4);
            if (chunk != null)
                chunk.e(i & 0xF, j, k & 0xF);
        }
    }

    public boolean g(int i, int j, int k) {
        Tile block = Tile.m[a(i, j, k)];
        if (block == null)
            return false;
        return block.c();
    }

    public boolean h(int i, int j, int k) {
        Tile block = Tile.m[a(i, j, k)];
        if (block == null)
            return false;
        return (block.bA.h() && block.d());
    }

    public void a(yb iprogressupdate) {
        a(true, iprogressupdate);
    }

    public boolean j() {
        if (this.M >= 50)
            return false;
        this.M++;
        try {
            int i = 500;
            for (; this.C.size() > 0; ((st)this.C.remove(this.C.size() - 1)).a(this)) {
                if (--i <= 0) {
                    boolean flag = true;
                    return flag;
                }
            }
            boolean flag1 = false;
            return flag1;
        } finally {
            this.M--;
        }
    }

    public void a(eb enumskyblock, int i, int j, int k, int l, int i1, int j1) {
        a(enumskyblock, i, j, k, l, i1, j1, true);
    }

    public void a(eb enumskyblock, int i, int j, int k, int l, int i1, int j1, boolean flag) {
        if (this.t.e && enumskyblock == eb.a)
            return;
        A++;
        try {
            if (A == 50)
                return;
            int k1 = (l + i) / 2;
            int l1 = (j1 + k) / 2;
            if (!i(k1, 64, l1))
                return;
            if (b(k1, l1).h())
                return;
            int i2 = this.C.size();
            if (flag) {
                int j2 = 5;
                if (j2 > i2)
                    j2 = i2;
                for (int l2 = 0; l2 < j2; l2++) {
                    st metadatachunkblock = this.C.get(this.C.size() - l2 - 1);
                    if (metadatachunkblock.a == enumskyblock && metadatachunkblock.a(i, j, k, l, i1, j1))
                        return;
                }
            }
            this.C.add(new st(enumskyblock, i, j, k, l, i1, j1));
            int k2 = 1000000;
            if (this.C.size() > 1000000) {
                System.out.println("More than " + k2 + " updates, aborting lighting updates");
                this.C.clear();
            }
        } finally {
            A--;
        }
    }

    public void k() {
        int i = a(1.0F);
        if (i != this.f)
            this.f = i;
    }

    public void a(boolean flag, boolean flag1) {
        this.N = flag;
        this.O = flag1;
    }

    public void l() {
        if (this.firstTick) {
            if (this.newSave && !this.x.onNewSaveScript.equals(""))
                this.scriptHandler.runScript(this.x.onNewSaveScript, this.scope);
            if (!this.x.onLoadScript.equals(""))
                this.scriptHandler.runScript(this.x.onLoadScript, this.scope);
            this.firstTick = false;
        }
        ScriptModel.updateAll();
        if (!this.x.onUpdateScript.equals(""))
            this.scriptHandler.runScript(this.x.onUpdateScript, this.scope);
        this.fogColorOverridden = this.x.overrideFogColor;
        this.fogDensityOverridden = this.x.overrideFogDensity;
        m();
        this.v.a();
        int i = a(1.0F);
        if (i != this.f) {
            this.f = i;
            for (int j = 0; j < this.u.size(); j++)
                ((pm)this.u.get(j)).e();
        }
        long l1 = this.x.f() + 1L;
        if (l1 % this.p == 0L)
            a(false, (yb)null);
        this.x.a(l1);
        this.x.addToTimeOfDay(this.x.getTimeRate());
        a(false);
        n();
        if (this.x.o())
            DoSnowModUpdate();
        this.script.wakeupScripts(l1);
    }

    private void E() {
        if (this.x.o()) {
            this.j = 1.0F;
            if (this.x.m())
                this.l = 1.0F;
        }
    }

    protected void m() {
        if (this.t.e)
            return;
        if (this.m > 0)
            this.m--;
        this.i = this.j;
        if (this.x.o()) {
            this.j = (float)(this.j + 0.01D);
        } else {
            this.j = (float)(this.j - 0.01D);
        }
        if (this.j < 0.0F)
            this.j = 0.0F;
        if (this.j > 1.0F)
            this.j = 1.0F;
        this.k = this.l;
        if (this.x.m()) {
            this.l = (float)(this.l + 0.01D);
        } else {
            this.l = (float)(this.l - 0.01D);
        }
        if (this.l < 0.0F)
            this.l = 0.0F;
        if (this.l > 1.0F)
            this.l = 1.0F;
    }

    private void F() {
        this.x.f(0);
        this.x.b(false);
        this.x.e(0);
        this.x.a(false);
    }

    private void DoSnowModUpdate() {
        if (this.B)
            return;
        int cw = 0;
        if (this.coordOrder == null)
            initCoordOrder();
        for (int i = 0; i < this.d.size(); i++) {
            gs entityplayer = this.d.get(i);
            int pcx = in.b(entityplayer.aM / 16.0D);
            int pcz = in.b(entityplayer.aO / 16.0D);
            int radius = 9;
            for (int cx = -radius; cx <= radius; cx++) {
                for (int cz = -radius; cz <= radius; cz++) {
                    long iteration = (cx + cz * 2) + t();
                    if (iteration % 14L == 0L)
                        if (f(cx + pcx, cz + pcz)) {
                            iteration /= 14L;
                            int chunkX = cx + pcx;
                            int chunkZ = cz + pcz;
                            iteration += (chunkX * chunkX * 3121 + chunkX * 45238971 + chunkZ * chunkZ * 418711 + chunkZ * 13761);
                            iteration = Math.abs(iteration);
                            int x = chunkX * 16 + this.coordOrder[(int)(iteration % 256L)] % 16;
                            int z = chunkZ * 16 + this.coordOrder[(int)(iteration % 256L)] / 16;
                            SnowModUpdate(x, z);
                        }
                }
            }
        }
    }

    protected void n() {
        for (int i = 0; i < this.d.size(); i++) {
            gs entityplayer = this.d.get(i);
            int j = in.b(entityplayer.aM / 16.0D);
            int l = in.b(entityplayer.aO / 16.0D);
            byte byte0 = 9;
            for (int j1 = -byte0; j1 <= byte0; j1++) {
                for (int k2 = -byte0; k2 <= byte0; k2++)
                    updateChunk(j1 + j, k2 + l);
            }
        }
        if (this.Q > 0)
            this.Q--;
    }

    protected void updateChunk(int chunkX, int chunkZ) {
        lm chunk = c(chunkX, chunkZ);
        if (chunk.lastUpdated == t())
            return;
        int coordX = chunkX * 16;
        int coordZ = chunkZ * 16;
        chunk.lastUpdated = t();
        if (this.r.nextInt(100000) == 0 && C() && B()) {
            this.g = this.g * 3 + 1013904223;
            int l1 = this.g >> 2;
            int i3 = coordX + (l1 & 0xF);
            int i4 = coordZ + (l1 >> 8 & 0xF);
            int i5 = e(i3, i4);
            if (t(i3, i5, i4)) {
                a((sn)new c(this, i3, i5, i4));
                this.m = 2;
            }
        }
        int j2 = 0;
        while (j2 < 80) {
            this.g = this.g * 3 + 1013904223;
            int k3 = this.g >> 2;
            int k4 = k3 & 0xF;
            int k5 = k3 >> 8 & 0xF;
            int j6 = k3 >> 16 & 0x7F;
            int l6 = chunk.b[k4 << 11 | k5 << 7 | j6] & 0xFF;
            if (Tile.n[l6])
                Tile.m[l6].a(this, k4 + coordX, j6, k5 + coordZ, this.r);
            j2++;
        }
    }

    public boolean a(boolean flag) {
        int i = this.E.size();
        if (i > 1000)
            i = 1000;
        for (int j = 0; j < i; j++) {
            qy nextticklistentry = this.E.first();
            if (!flag && nextticklistentry.e > this.x.f())
                break;
            this.E.remove(nextticklistentry);
            if (this.F.remove(nextticklistentry)) {
                byte byte0 = 8;
                if (a(nextticklistentry.a - byte0, nextticklistentry.b - byte0, nextticklistentry.c - byte0, nextticklistentry.a + byte0, nextticklistentry.b + byte0, nextticklistentry.c + byte0)) {
                    int k = a(nextticklistentry.a, nextticklistentry.b, nextticklistentry.c);
                    if (k == nextticklistentry.d && k > 0) {
                        Tile.m[k].a(this, nextticklistentry.a, nextticklistentry.b, nextticklistentry.c, this.r);
                        eq.b();
                    }
                }
            }
        }
        return (this.E.size() != 0);
    }

    public void q(int i, int j, int k) {
        byte byte0 = 16;
        Random random = new Random();
        for (int l = 0; l < 1000; l++) {
            int i1 = i + this.r.nextInt(byte0) - this.r.nextInt(byte0);
            int j1 = j + this.r.nextInt(byte0) - this.r.nextInt(byte0);
            int k1 = k + this.r.nextInt(byte0) - this.r.nextInt(byte0);
            int l1 = a(i1, j1, k1);
            if (l1 > 0)
                Tile.m[l1].b(this, i1, j1, k1, random);
        }
    }

    public List b(sn entity, eq axisalignedbb) {
        this.R.clear();
        int i = in.b((axisalignedbb.a - 2.0D) / 16.0D);
        int j = in.b((axisalignedbb.d + 2.0D) / 16.0D);
        int k = in.b((axisalignedbb.c - 2.0D) / 16.0D);
        int l = in.b((axisalignedbb.f + 2.0D) / 16.0D);
        for (int i1 = i; i1 <= j; i1++) {
            for (int j1 = k; j1 <= l; j1++) {
                if (f(i1, j1))
                    c(i1, j1).a(entity, axisalignedbb, this.R);
            }
        }
        return this.R;
    }

    public List a(Class class1, eq axisalignedbb) {
        int i = in.b((axisalignedbb.a - 2.0D) / 16.0D);
        int j = in.b((axisalignedbb.d + 2.0D) / 16.0D);
        int k = in.b((axisalignedbb.c - 2.0D) / 16.0D);
        int l = in.b((axisalignedbb.f + 2.0D) / 16.0D);
        ArrayList arraylist = new ArrayList();
        for (int i1 = i; i1 <= j; i1++) {
            for (int j1 = k; j1 <= l; j1++) {
                if (f(i1, j1))
                    c(i1, j1).a(class1, axisalignedbb, arraylist);
            }
        }
        return arraylist;
    }

    public List o() {
        return this.b;
    }

    public void b(int i, int j, int k, ow tileentity) {
        if (i(i, j, k))
            b(i, k).g();
        for (int l = 0; l < this.u.size(); l++)
            ((pm)this.u.get(l)).a(i, j, k, tileentity);
    }

    public int b(Class class1) {
        int i = 0;
        for (int j = 0; j < this.b.size(); j++) {
            sn entity = (sn)this.b.get(j);
            if (class1.isAssignableFrom(entity.getClass()))
                i++;
        }
        return i;
    }

    public void a(List<? extends Entity> list) {
        this.b.addAll(list);
        for (int i = 0; i < list.size(); i++)
            c((sn)list.get(i));
    }

    public void b(List list) {
        this.D.addAll(list);
    }

    public void p() {
        while (this.v.a());
    }

    public boolean a(int i, int j, int k, int l, boolean flag, int i1) {
        int j1 = a(j, k, l);
        Tile block = Tile.m[j1];
        Tile block1 = Tile.m[i];
        eq axisalignedbb = block1.e(this, j, k, l);
        if (flag)
            axisalignedbb = null;
        if (axisalignedbb != null && !a(axisalignedbb))
            return false;
        if (block == Tile.B || block == Tile.C || block == Tile.D || block == Tile.E || block == Tile.as || block == Tile.aT)
            block = null;
        return (i > 0 && block == null && block1.a(this, j, k, l, i1));
    }

    public dh a(sn entity, sn entity1, float f) {
        int i = in.b(entity.aM);
        int j = in.b(entity.aN);
        int k = in.b(entity.aO);
        int l = (int)(f + 16.0F);
        int i1 = i - l;
        int j1 = j - l;
        int k1 = k - l;
        int l1 = i + l;
        int i2 = j + l;
        int j2 = k + l;
        ew chunkcache = new ew(this, i1, j1, k1, l1, i2, j2);
        return (new fw((xp)chunkcache)).a(entity, entity1, f);
    }

    public dh a(sn entity, int i, int j, int k, float f) {
        int l = in.b(entity.aM);
        int i1 = in.b(entity.aN);
        int j1 = in.b(entity.aO);
        int k1 = (int)(f + 8.0F);
        int l1 = l - k1;
        int i2 = i1 - k1;
        int j2 = j1 - k1;
        int k2 = l + k1;
        int l2 = i1 + k1;
        int i3 = j1 + k1;
        ew chunkcache = new ew(this, l1, i2, j2, k2, l2, i3);
        return (new fw((xp)chunkcache)).a(entity, i, j, k, f);
    }

    public boolean j(int i, int j, int k, int l) {
        int i1 = a(i, j, k);
        if (i1 == 0)
            return false;
        return Tile.m[i1].d(this, i, j, k, l);
    }

    public boolean r(int i, int j, int k) {
        if (j(i, j - 1, k, 0))
            return true;
        if (j(i, j + 1, k, 1))
            return true;
        if (j(i, j, k - 1, 2))
            return true;
        if (j(i, j, k + 1, 3))
            return true;
        if (j(i - 1, j, k, 4))
            return true;
        return j(i + 1, j, k, 5);
    }

    public boolean k(int i, int j, int k, int l) {
        if (h(i, j, k))
            return r(i, j, k);
        int i1 = a(i, j, k);
        if (i1 == 0)
            return false;
        return Tile.m[i1].c(this, i, j, k, l);
    }

    public boolean s(int i, int j, int k) {
        if (k(i, j - 1, k, 0))
            return true;
        if (k(i, j + 1, k, 1))
            return true;
        if (k(i, j, k - 1, 2))
            return true;
        if (k(i, j, k + 1, 3))
            return true;
        if (k(i - 1, j, k, 4))
            return true;
        return k(i + 1, j, k, 5);
    }

    public gs a(sn entity, double d) {
        return a(entity.aM, entity.aN, entity.aO, d);
    }

    public gs a(double d, double d1, double d2, double d3) {
        double d4 = -1.0D;
        gs entityplayer = null;
        for (int i = 0; i < this.d.size(); i++) {
            gs entityplayer1 = this.d.get(i);
            double d5 = entityplayer1.g(d, d1, d2);
            if ((d3 < 0.0D || d5 < d3 * d3) && (d4 == -1.0D || d5 < d4)) {
                d4 = d5;
                entityplayer = entityplayer1;
            }
        }
        return entityplayer;
    }

    public gs a(String s) {
        for (int i = 0; i < this.d.size(); i++) {
            if (s.equals(((gs)this.d.get(i)).l))
                return this.d.get(i);
        }
        return null;
    }

    public void a(int i, int j, int k, int l, int i1, int j1, byte[] abyte0) {
        int k1 = i >> 4;
        int l1 = k >> 4;
        int i2 = i + l - 1 >> 4;
        int j2 = k + j1 - 1 >> 4;
        int k2 = 0;
        int l2 = j;
        int i3 = j + i1;
        if (l2 < 0)
            l2 = 0;
        if (i3 > 128)
            i3 = 128;
        for (int j3 = k1; j3 <= i2; j3++) {
            int k3 = i - j3 * 16;
            int l3 = i + l - j3 * 16;
            if (k3 < 0)
                k3 = 0;
            if (l3 > 16)
                l3 = 16;
            for (int i4 = l1; i4 <= j2; i4++) {
                int j4 = k - i4 * 16;
                int k4 = k + j1 - i4 * 16;
                if (j4 < 0)
                    j4 = 0;
                if (k4 > 16)
                    k4 = 16;
                k2 = c(j3, i4).a(abyte0, k3, l2, j4, l3, i3, k4, k2);
                b(j3 * 16 + k3, l2, i4 * 16 + j4, j3 * 16 + l3, i3, i4 * 16 + k4);
            }
        }
    }

    public void q() {}

    public void r() {
        if (this.w != null) {
            this.w.b();
        } else {
            this.mapHandler.b();
        }
    }

    public void a(long l) {
        this.x.a(l);
    }

    public void setTimeOfDay(long l) {
        this.x.setTimeOfDay((float)l);
    }

    public float getTimeOfDay() {
        return (float)this.x.getTimeOfDay();
    }

    public long s() {
        return this.x.b();
    }

    public long t() {
        return this.x.f();
    }

    public br u() {
        return new br(this.x.c(), this.x.d(), this.x.e());
    }

    public void a(br chunkcoordinates) {
        this.x.a(chunkcoordinates.a, chunkcoordinates.b, chunkcoordinates.c);
    }

    public float getSpawnYaw() {
        return this.x.spawnYaw;
    }

    public void setSpawnYaw(float y) {
        this.x.spawnYaw = y;
    }

    public void g(sn entity) {
        int i = in.b(entity.aM / 16.0D);
        int j = in.b(entity.aO / 16.0D);
        byte byte0 = 2;
        for (int k = i - byte0; k <= i + byte0; k++) {
            for (int l = j - byte0; l <= j + byte0; l++)
                c(k, l);
        }
        if (!this.b.contains(entity))
            this.b.add(entity);
    }

    public boolean a(gs entityplayer, int i, int j, int k) {
        return true;
    }

    public void a(sn entity, byte byte0) {}

    public void v() {
        this.b.removeAll(this.D);
        for (int i = 0; i < this.D.size(); i++) {
            sn entity = this.D.get(i);
            int l = entity.bG;
            int j1 = entity.bI;
            if (entity.bF && f(l, j1))
                c(l, j1).b(entity);
        }
        for (int j = 0; j < this.D.size(); j++)
            d(this.D.get(j));
        this.D.clear();
        for (int k = 0; k < this.b.size(); k++) {
            sn entity1 = (sn)this.b.get(k);
            if (entity1.aH != null) {
                if (!entity1.aH.be && entity1.aH.aG == entity1)
                    continue;
                entity1.aH.aG = null;
                entity1.aH = null;
            }
            if (entity1.be) {
                int i1 = entity1.bG;
                int k1 = entity1.bI;
                if (entity1.bF && f(i1, k1))
                    c(i1, k1).b(entity1);
                this.b.remove(k--);
                d(entity1);
            }
            continue;
        }
    }

    public cl w() {
        return this.v;
    }

    public void d(int i, int j, int k, int l, int i1) {
        int j1 = a(i, j, k);
        if (j1 > 0)
            Tile.m[j1].a(this, i, j, k, l, i1);
    }

    public ei x() {
        return this.x;
    }

    public void y() {
        this.J = !this.d.isEmpty();
        Iterator<gs> iterator = this.d.iterator();
        while (iterator.hasNext()) {
            gs entityplayer = iterator.next();
            if (entityplayer.N())
                continue;
            this.J = false;
            break;
        }
    }

    protected void z() {
        this.J = false;
        Iterator<gs> iterator = this.d.iterator();
        while (iterator.hasNext()) {
            gs entityplayer = iterator.next();
            if (entityplayer.N())
                entityplayer.a(false, false, true);
        }
        F();
    }

    public boolean A() {
        if (this.J && !this.B) {
            for (Iterator<gs> iterator = this.d.iterator(); iterator.hasNext(); ) {
                gs entityplayer = iterator.next();
                if (!entityplayer.O())
                    return false;
            }
            return true;
        }
        return false;
    }

    public float f(float f) {
        return (this.k + (this.l - this.k) * f) * g(f);
    }

    public float g(float f) {
        return this.i + (this.j - this.i) * f;
    }

    public void h(float f) {
        this.i = f;
        this.j = f;
    }

    public boolean B() {
        return (f(1.0F) > 0.9D);
    }

    public boolean C() {
        return (g(1.0F) > 0.2D);
    }

    public boolean t(int i, int j, int k) {
        if (!C())
            return false;
        if (!l(i, j, k))
            return false;
        if (e(i, k) > j)
            return false;
        Biome biomegenbase = a().a(i, k);
        if (biomegenbase.c())
            return false;
        return biomegenbase.d();
    }

    public void a(String s, hm mapdatabase) {
        this.z.a(s, mapdatabase);
    }

    public hm a(Class class1, String s) {
        return this.z.a(class1, s);
    }

    public int b(String s) {
        return this.z.a(s);
    }

    public void e(int i, int j, int k, int l, int i1) {
        a((gs)null, i, j, k, l, i1);
    }

    public void a(gs entityplayer, int i, int j, int k, int l, int i1) {
        for (int j1 = 0; j1 < this.u.size(); j1++)
            ((pm)this.u.get(j1)).a(entityplayer, i, j, k, l, i1);
    }

    public double getTemperatureValue(int x, int z) {
        if (x < -32000000 || z < -32000000 || x >= 32000000 || z > 32000000)
            return 0.0D;
        return c(x >> 4, z >> 4).getTemperatureValue(x & 0xF, z & 0xF) + this.x.tempOffset;
    }

    public void setTemperatureValue(int x, int z, double temp) {
        if (x < -32000000 || z < -32000000 || x >= 32000000 || z > 32000000)
            return;
        lm c = c(x >> 4, z >> 4);
        if (c.getTemperatureValue(x & 0xF, z & 0xF) == temp)
            return;
        c.setTemperatureValue(x & 0xF, z & 0xF, temp);
    }

    public void resetCoordOrder() {
        this.coordOrder = null;
    }

    private void initCoordOrder() {
        Random r = new Random();
        r.setSeed(t());
        this.coordOrder = new int[256];
        int i;
        for (i = 0; i < 256; i++)
            this.coordOrder[i] = i;
        for (i = 0; i < 255; i++) {
            int j = r.nextInt(256 - i);
            int t = this.coordOrder[i];
            this.coordOrder[i] = this.coordOrder[i + j];
            this.coordOrder[i + j] = t;
        }
    }

    public boolean SnowModUpdate(int x, int z) {
        int height = e(x, z);
        if (height < 0)
            height = 0;
        int topBlock = a(x, height - 1, z);
        if (getTemperatureValue(x, z) < 0.5D) {
            if (!d(x, height, z))
                return false;
            if (topBlock != 0 && Tile.m[topBlock].c()) {
                if (!f(x, height - 1, z).c())
                    return false;
                if (a(eb.b, x, height, z) > 11)
                    return false;
                f(x, height, z, Tile.aT.bn);
            } else if (topBlock == Tile.B.bn && e(x, height - 1, z) == 0) {
                if (a(eb.b, x, height, z) > 11)
                    return false;
                f(x, height - 1, z, Tile.aU.bn);
            }
            return true;
        }
        int aboveTopBlock = a(x, height, z);
        if (aboveTopBlock == Tile.aT.bn) {
            f(x, height, z, 0);
            return true;
        }
        if (topBlock == Tile.aV.bn) {
            f(x, height - 1, z, Tile.aT.bn);
            return true;
        }
        if (topBlock == Tile.aU.bn) {
            f(x, height - 1, z, Tile.B.bn);
            return true;
        }
        return false;
    }

    public void loadMapMusic() {
        File musicDir = new File(this.levelDir, "music");
        if (musicDir.exists() && musicDir.isDirectory()) {
            int musicCount = 0;
            File[] musicFiles = musicDir.listFiles();
            for (File musicFile : musicFiles) {
                if (musicFile.isFile() && musicFile.getName().endsWith(".ogg")) {
                    String streamName = String.format("music/%s", new Object[] { musicFile.getName().toLowerCase() });
                    Minecraft.minecraftInstance.B.b(streamName, musicFile);
                    musicCount++;
                }
            }
            this.musicList = new String[musicCount];
            musicCount = 0;
            for (File musicFile : musicFiles) {
                if (musicFile.isFile() && musicFile.getName().endsWith(".ogg")) {
                    String streamName = String.format("music.%s", new Object[] { musicFile.getName().toLowerCase().replace(".ogg", "") });
                    this.musicList[musicCount] = streamName;
                    musicCount++;
                }
            }
        } else {
            this.musicList = new String[0];
        }
        if (!this.x.playingMusic.equals(""))
            Minecraft.minecraftInstance.B.playMusicFromStreaming(this.x.playingMusic, 0, 0);
    }

    public void loadMapSounds() {
        File soundDir = new File(this.levelDir, "sound");
        if (soundDir.exists() && soundDir.isDirectory()) {
            int soundCount = 0;
            File[] soundFiles = soundDir.listFiles();
            for (File soundFile : soundFiles) {
                if (soundFile.isFile() && soundFile.getName().endsWith(".ogg")) {
                    String streamName = String.format("sound/%s", new Object[] { soundFile.getName().toLowerCase() });
                    Minecraft.minecraftInstance.B.a(streamName, soundFile);
                    soundCount++;
                }
            }
            this.soundList = new String[soundCount];
            soundCount = 0;
            for (File soundFile : soundFiles) {
                if (soundFile.isFile() && soundFile.getName().endsWith(".ogg")) {
                    String streamName = String.format("sound.%s", new Object[] { soundFile.getName().toLowerCase().replace(".ogg", "") });
                    this.soundList[soundCount] = streamName;
                    soundCount++;
                }
            }
        } else {
            this.soundList = new String[0];
        }
    }

    public void loadSoundOverrides() {
        Minecraft.minecraftInstance.U.a();
        File soundDir = new File(this.levelDir, "soundOverrides");
        if (soundDir.exists())
            Minecraft.minecraftInstance.U.a(soundDir, "");
    }

    public void loadBrightness() {
        for (int i = 0; i < 16; i++)
            this.t.f[i] = this.x.brightness[i];
    }

    public void undo() {
        this.undoStack.undo(this);
    }

    public void redo() {
        this.undoStack.redo(this);
    }

    public sn getEntityByID(int entityID) {
        for (sn e : this.b) {
            if (e.aD == entityID)
                return e;
        }
        return null;
    }

    static int A = 0;

    private int Q;

    private List R;

    public boolean B;

    public File levelDir;

    private int[] coordOrder;

    public String[] musicList;

    public String[] soundList;

    protected final wt mapHandler;

    public TriggerManager triggerManager;

    public Script script;

    public JScriptHandler scriptHandler;

    public MusicScripts musicScripts;

    public boolean fogColorOverridden;

    public boolean fogDensityOverridden;

    public Scriptable scope;

    boolean firstTick;

    boolean newSave;

    public UndoStack undoStack;
}
