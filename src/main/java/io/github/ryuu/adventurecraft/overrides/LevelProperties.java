package io.github.ryuu.adventurecraft.overrides;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.ryuu.adventurecraft.blocks.BlockEffect;
import io.github.ryuu.adventurecraft.mixin.Level;
import net.minecraft.client.Minecraft;

public class LevelProperties {
    private final long a;

    private int b;

    private int c;

    private int d;

    private long e;

    private long f;

    private long g;

    private nu h;

    private int i;

    private String j;

    private int k;

    private boolean l;

    private int m;

    private boolean n;

    private int o;

    public double tempOffset;

    public boolean useImages;

    public double mapSize;

    public int waterLevel;

    public double fractureHorizontal;

    public double fractureVertical;

    public double maxAvgDepth;

    public double maxAvgHeight;

    public double volatility1;

    public double volatility2;

    public double volatilityWeight1;

    public double volatilityWeight2;

    public boolean iceMelts;

    public nu triggerData;

    float timeOfDay;

    float timeRate;

    public String playingMusic;

    public boolean mobsBurn;

    public boolean overrideFogColor;

    public float fogR;

    public float fogG;

    public float fogB;

    public boolean overrideFogDensity;

    public float fogStart;

    public float fogEnd;

    public String overlay;

    nu replacementTag;

    public HashMap<String, String> replacementTextures;

    public String onNewSaveScript;

    public String onLoadScript;

    public String onUpdateScript;

    public String playerName;

    public float[] brightness;

    public float spawnYaw;

    public nu globalScope;

    public nu worldScope;

    public nu musicScope;

    public boolean originallyFromAC;

    public boolean allowsInventoryCrafting;

    public LevelProperties(nu nbttagcompound) {
        this.useImages = true;
        this.mapSize = 250.0D;
        this.waterLevel = 64;
        this.fractureHorizontal = 1.0D;
        this.fractureVertical = 1.0D;
        this.maxAvgDepth = 0.0D;
        this.maxAvgHeight = 0.0D;
        this.volatility1 = 1.0D;
        this.volatility2 = 1.0D;
        this.volatilityWeight1 = 0.0D;
        this.volatilityWeight2 = 1.0D;
        this.iceMelts = true;
        this.triggerData = null;
        this.playingMusic = "";
        this.mobsBurn = true;
        this.overrideFogColor = false;
        this.overrideFogDensity = false;
        this.overlay = "";
        this.replacementTag = null;
        this.onNewSaveScript = "";
        this.onLoadScript = "";
        this.onUpdateScript = "";
        this.playerName = "ACPlayer";
        this.globalScope = null;
        this.worldScope = null;
        this.musicScope = null;
        this.originallyFromAC = false;
        this.allowsInventoryCrafting = false;
        this.brightness = new float[16];
        this.a = nbttagcompound.f("RandomSeed");
        this.b = nbttagcompound.e("SpawnX");
        this.c = nbttagcompound.e("SpawnY");
        this.d = nbttagcompound.e("SpawnZ");
        this.e = nbttagcompound.f("Time");
        this.f = nbttagcompound.f("LastPlayed");
        this.g = nbttagcompound.f("SizeOnDisk");
        this.j = nbttagcompound.i("LevelName");
        this.k = nbttagcompound.e("version");
        this.m = nbttagcompound.e("rainTime");
        this.l = nbttagcompound.m("raining");
        this.o = nbttagcompound.e("thunderTime");
        this.n = nbttagcompound.m("thundering");
        if (nbttagcompound.b("Player")) {
            this.h = nbttagcompound.k("Player");
            this.i = this.h.e("Dimension");
        }
        this.tempOffset = nbttagcompound.h("TemperatureOffset");
        if (nbttagcompound.b("IsPrecipitating"))
            this.l = nbttagcompound.m("IsPrecipitating");
        Entity.a = nbttagcompound.e("nextEntityID");
        if (nbttagcompound.b("useImages")) {
            this.useImages = nbttagcompound.m("useImages");
            this.mapSize = nbttagcompound.h("mapSize");
            this.waterLevel = nbttagcompound.d("waterLevel");
            this.fractureHorizontal = nbttagcompound.h("fractureHorizontal");
            this.fractureVertical = nbttagcompound.h("fractureVertical");
            this.maxAvgDepth = nbttagcompound.h("maxAvgDepth");
            this.maxAvgHeight = nbttagcompound.h("maxAvgHeight");
            this.volatility1 = nbttagcompound.h("volatility1");
            this.volatility2 = nbttagcompound.h("volatility2");
            this.volatilityWeight1 = nbttagcompound.h("volatilityWeight1");
            this.volatilityWeight2 = nbttagcompound.h("volatilityWeight2");
        }
        if (nbttagcompound.b("iceMelts"))
            this.iceMelts = nbttagcompound.m("iceMelts");
        if (nbttagcompound.b("triggerAreas"))
            this.triggerData = nbttagcompound.k("triggerAreas");
        if (nbttagcompound.b("timeRate")) {
            this.timeRate = nbttagcompound.g("timeRate");
        } else {
            this.timeRate = 1.0F;
        }
        if (nbttagcompound.b("timeOfDay")) {
            this.timeOfDay = nbttagcompound.g("timeOfDay");
        } else {
            this.timeOfDay = (float) this.e;
        }
        this.playingMusic = nbttagcompound.i("playingMusic");
        if (nbttagcompound.b("mobsBurn"))
            this.mobsBurn = nbttagcompound.m("mobsBurn");
        this.overlay = nbttagcompound.i("overlay");
        this.replacementTextures = new HashMap<>();
        if (nbttagcompound.b("textureReplacements"))
            this.replacementTag = nbttagcompound.k("textureReplacements");
        this.onNewSaveScript = nbttagcompound.i("onNewSaveScript");
        this.onLoadScript = nbttagcompound.i("onLoadScript");
        this.onUpdateScript = nbttagcompound.i("onUpdateScript");
        if (nbttagcompound.b("playerName"))
            this.playerName = nbttagcompound.i("playerName");
        float f = 0.05F;
        for (int i = 0; i < 16; i++) {
            String key = String.format("brightness%d", Integer.valueOf(i));
            if (nbttagcompound.b(key)) {
                this.brightness[i] = nbttagcompound.g(key);
            } else {
                float f1 = 1.0F - i / 15.0F;
                this.brightness[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
            }
        }
        if (nbttagcompound.b("globalScope"))
            this.globalScope = nbttagcompound.k("globalScope");
        if (nbttagcompound.b("worldScope"))
            this.worldScope = nbttagcompound.k("worldScope");
        if (nbttagcompound.b("musicScope"))
            this.musicScope = nbttagcompound.k("musicScope");
        if (nbttagcompound.b("originallyFromAC")) {
            this.originallyFromAC = nbttagcompound.m("originallyFromAC");
        } else {
            this.originallyFromAC = nbttagcompound.b("TemperatureOffset");
        }
        if (nbttagcompound.b("allowsInventoryCrafting")) {
            this.allowsInventoryCrafting = nbttagcompound.m("allowsInventoryCrafting");
        } else {
            this.allowsInventoryCrafting = true;
        }
    }

    public LevelProperties(long l, String s) {
        this.useImages = true;
        this.mapSize = 250.0D;
        this.waterLevel = 64;
        this.fractureHorizontal = 1.0D;
        this.fractureVertical = 1.0D;
        this.maxAvgDepth = 0.0D;
        this.maxAvgHeight = 0.0D;
        this.volatility1 = 1.0D;
        this.volatility2 = 1.0D;
        this.volatilityWeight1 = 0.0D;
        this.volatilityWeight2 = 1.0D;
        this.iceMelts = true;
        this.triggerData = null;
        this.playingMusic = "";
        this.mobsBurn = true;
        this.overrideFogColor = false;
        this.overrideFogDensity = false;
        this.overlay = "";
        this.replacementTag = null;
        this.onNewSaveScript = "";
        this.onLoadScript = "";
        this.onUpdateScript = "";
        this.playerName = "ACPlayer";
        this.globalScope = null;
        this.worldScope = null;
        this.musicScope = null;
        this.originallyFromAC = false;
        this.allowsInventoryCrafting = false;
        this.brightness = new float[16];
        this.a = l;
        this.j = s;
        this.timeRate = 1.0F;
        this.replacementTextures = new HashMap<>();
        float f = 0.05F;
        for (int i = 0; i < 16; i++) {
            float f1 = 1.0F - i / 15.0F;
            this.brightness[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
        }
    }

    public LevelProperties(LevelProperties worldinfo) {
        this.useImages = true;
        this.mapSize = 250.0D;
        this.waterLevel = 64;
        this.fractureHorizontal = 1.0D;
        this.fractureVertical = 1.0D;
        this.maxAvgDepth = 0.0D;
        this.maxAvgHeight = 0.0D;
        this.volatility1 = 1.0D;
        this.volatility2 = 1.0D;
        this.volatilityWeight1 = 0.0D;
        this.volatilityWeight2 = 1.0D;
        this.iceMelts = true;
        this.triggerData = null;
        this.playingMusic = "";
        this.mobsBurn = true;
        this.overrideFogColor = false;
        this.overrideFogDensity = false;
        this.overlay = "";
        this.replacementTag = null;
        this.onNewSaveScript = "";
        this.onLoadScript = "";
        this.onUpdateScript = "";
        this.playerName = "ACPlayer";
        this.globalScope = null;
        this.worldScope = null;
        this.musicScope = null;
        this.originallyFromAC = false;
        this.allowsInventoryCrafting = false;
        this.a = worldinfo.a;
        this.b = worldinfo.b;
        this.c = worldinfo.c;
        this.d = worldinfo.d;
        this.e = worldinfo.e;
        this.f = worldinfo.f;
        this.g = worldinfo.g;
        this.h = worldinfo.h;
        this.i = worldinfo.i;
        this.j = worldinfo.j;
        this.k = worldinfo.k;
        this.m = worldinfo.m;
        this.l = worldinfo.l;
        this.o = worldinfo.o;
        this.n = worldinfo.n;
        this.replacementTextures = new HashMap<>();
        this.brightness = new float[16];
        for (int i = 0; i < 16; i++)
            this.brightness[i] = worldinfo.brightness[i];
    }

    public nu a() {
        nu nbttagcompound = new nu();
        a(nbttagcompound, this.h);
        return nbttagcompound;
    }

    public nu a(List<gs> list) {
        nu nbttagcompound = new nu();
        gs entityplayer = null;
        nu nbttagcompound1 = null;
        if (list.size() > 0)
            entityplayer = list.get(0);
        if (entityplayer != null) {
            nbttagcompound1 = new nu();
            entityplayer.d(nbttagcompound1);
        }
        a(nbttagcompound, nbttagcompound1);
        return nbttagcompound;
    }

    private void a(nu nbttagcompound, nu nbttagcompound1) {
        nbttagcompound.a("RandomSeed", this.a);
        nbttagcompound.a("SpawnX", this.b);
        nbttagcompound.a("SpawnY", this.c);
        nbttagcompound.a("SpawnZ", this.d);
        nbttagcompound.a("Time", this.e);
        nbttagcompound.a("SizeOnDisk", this.g);
        nbttagcompound.a("LastPlayed", System.currentTimeMillis());
        nbttagcompound.a("LevelName", this.j);
        nbttagcompound.a("version", this.k);
        nbttagcompound.a("rainTime", this.m);
        nbttagcompound.a("raining", this.l);
        nbttagcompound.a("thunderTime", this.o);
        nbttagcompound.a("thundering", this.n);
        if (nbttagcompound1 != null)
            nbttagcompound.a("Player", nbttagcompound1);
        nbttagcompound.a("TemperatureOffset", this.tempOffset);
        nbttagcompound.a("nextEntityID", Entity.a);
        nbttagcompound.a("useImages", this.useImages);
        nbttagcompound.a("mapSize", this.mapSize);
        nbttagcompound.a("waterLevel", (short) this.waterLevel);
        nbttagcompound.a("fractureHorizontal", this.fractureHorizontal);
        nbttagcompound.a("fractureVertical", this.fractureVertical);
        nbttagcompound.a("maxAvgDepth", this.maxAvgDepth);
        nbttagcompound.a("maxAvgHeight", this.maxAvgHeight);
        nbttagcompound.a("volatility1", this.volatility1);
        nbttagcompound.a("volatility2", this.volatility2);
        nbttagcompound.a("volatilityWeight1", this.volatilityWeight1);
        nbttagcompound.a("volatilityWeight2", this.volatilityWeight2);
        nbttagcompound.a("iceMelts", this.iceMelts);
        if (Minecraft.minecraftInstance.f != null && Minecraft.minecraftInstance.f.triggerManager != null)
            nbttagcompound.a("triggerAreas", Minecraft.minecraftInstance.f.triggerManager.getTagCompound());
        nbttagcompound.a("timeOfDay", this.timeOfDay);
        nbttagcompound.a("timeRate", this.timeRate);
        if (!this.playingMusic.equals(""))
            nbttagcompound.a("playingMusic", this.playingMusic);
        nbttagcompound.a("mobsBurn", this.mobsBurn);
        if (!this.overlay.equals(""))
            nbttagcompound.a("overlay", this.overlay);
        nbttagcompound.a("textureReplacements", getTextureReplacementTags());
        if (!this.onNewSaveScript.equals(""))
            nbttagcompound.a("onNewSaveScript", this.onNewSaveScript);
        if (!this.onLoadScript.equals(""))
            nbttagcompound.a("onLoadScript", this.onLoadScript);
        if (!this.onUpdateScript.equals(""))
            nbttagcompound.a("onUpdateScript", this.onUpdateScript);
        if (!this.playerName.equals(""))
            nbttagcompound.a("playerName", this.playerName);
        for (int i = 0; i < 16; i++) {
            String key = String.format("brightness%d", Integer.valueOf(i));
            nbttagcompound.a(key, this.brightness[i]);
        }
        if (this.globalScope != null)
            nbttagcompound.a("globalScope", this.globalScope);
        if (this.worldScope != null)
            nbttagcompound.a("worldScope", this.worldScope);
        if (this.musicScope != null)
            nbttagcompound.a("musicScope", this.musicScope);
        nbttagcompound.a("originallyFromAC", this.originallyFromAC);
        nbttagcompound.a("allowsInventoryCrafting", this.allowsInventoryCrafting);
    }

    public long b() {
        return this.a;
    }

    public int c() {
        return this.b;
    }

    public int d() {
        return this.c;
    }

    public int e() {
        return this.d;
    }

    public long f() {
        return this.e;
    }

    public long getTimeOfDay() {
        return (long) this.timeOfDay;
    }

    public void addToTimeOfDay(float t) {
        this.timeOfDay += t;
        while (this.timeOfDay < 0.0F)
            this.timeOfDay += 24000.0F;
        while (this.timeOfDay > 24000.0F)
            this.timeOfDay -= 24000.0F;
    }

    public void setTimeOfDay(float l) {
        this.timeOfDay = l;
        while (this.timeOfDay < 0.0F)
            this.timeOfDay += 24000.0F;
        while (this.timeOfDay > 24000.0F)
            this.timeOfDay -= 24000.0F;
    }

    public float getTimeRate() {
        return this.timeRate;
    }

    public void setTimeRate(float t) {
        this.timeRate = t;
    }

    public long g() {
        return this.g;
    }

    public nu h() {
        return this.h;
    }

    public int i() {
        return this.i;
    }

    public void a(int i) {
        this.b = i;
    }

    public void b(int i) {
        this.c = i;
    }

    public void c(int i) {
        this.d = i;
    }

    public void a(long l) {
        this.e = l;
    }

    public void b(long l) {
        this.g = l;
    }

    public void a(nu nbttagcompound) {
        this.h = nbttagcompound;
    }

    public void a(int i, int j, int k) {
        this.b = i;
        this.c = j;
        this.d = k;
    }

    public String j() {
        return this.j;
    }

    public void a(String s) {
        this.j = s;
    }

    public int k() {
        return this.k;
    }

    public void d(int i) {
        this.k = i;
    }

    public long l() {
        return this.f;
    }

    public boolean m() {
        return this.n;
    }

    public void a(boolean flag) {
        this.n = flag;
    }

    public int n() {
        return this.o;
    }

    public void e(int i) {
        this.o = i;
    }

    public boolean o() {
        return this.l;
    }

    public void b(boolean flag) {
        this.l = flag;
    }

    public int p() {
        return this.m;
    }

    public void f(int i) {
        this.m = i;
    }

    boolean addReplacementTexture(String replace, String newTexture) {
        String prevReplacement = this.replacementTextures.get(replace);
        if (prevReplacement != null && prevReplacement.equals(newTexture))
            return false;
        this.replacementTextures.put(replace, newTexture);
        return true;
    }

    void revertTextures() {
        this.replacementTextures.clear();
    }

    nu getTextureReplacementTags() {
        int i = 0;
        nu t = new nu();
        for (Map.Entry<String, String> e : this.replacementTextures.entrySet())
            t.a(e.getKey(), e.getValue());
        return t;
    }

    void loadTextureReplacements(Level w) {
        if (this.replacementTag != null) {
            this.replacementTextures.clear();
            for (String key : this.replacementTag.getKeys())
                BlockEffect.replaceTexture(w, key, this.replacementTag.i(key));
        }
    }
}