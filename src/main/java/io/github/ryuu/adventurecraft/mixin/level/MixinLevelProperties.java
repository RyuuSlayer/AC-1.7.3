package io.github.ryuu.adventurecraft.mixin.level;

import io.github.ryuu.adventurecraft.blocks.BlockEffect;
import io.github.ryuu.adventurecraft.extensions.level.ExLevelProperties;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.entity.Entity;
import net.minecraft.level.Level;
import net.minecraft.level.LevelProperties;
import net.minecraft.util.io.AbstractTag;
import net.minecraft.util.io.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;
import java.util.Map;

@Mixin(LevelProperties.class)
public class MixinLevelProperties implements ExLevelProperties {

    public double tempOffset;
    public boolean useImages = true;
    public double mapSize = 250.0;
    public int waterLevel = 64;
    public double fractureHorizontal = 1.0;
    public double fractureVertical = 1.0;
    public double maxAvgDepth = 0.0;
    public double maxAvgHeight = 0.0;
    public double volatility1 = 1.0;
    public double volatility2 = 1.0;
    public double volatilityWeight1 = 0.0;
    public double volatilityWeight2 = 1.0;
    public boolean iceMelts = true;
    public CompoundTag triggerData = null;
    public String playingMusic = "";
    public boolean mobsBurn = true;
    public boolean overrideFogColor = false;
    public float fogR;
    public float fogG;
    public float fogB;
    public boolean overrideFogDensity = false;
    public float fogStart;
    public float fogEnd;
    public String overlay = "";
    public HashMap<String, String> replacementTextures;
    public String onNewSaveScript = "";
    public String onLoadScript = "";
    public String onUpdateScript = "";
    public String playerName = "ACPlayer";
    public float[] brightness;
    public float spawnYaw;
    public CompoundTag globalScope = null;
    public CompoundTag worldScope = null;
    public CompoundTag musicScope = null;
    public boolean originallyFromAC = false;
    public boolean allowsInventoryCrafting = false;
    float timeOfDay;
    float timeRate;
    CompoundTag replacementTag = null;

    @Shadow
    private final long randomSeed;

    @Shadow
    private int spawnX;

    @Shadow
    private int spawnY;

    @Shadow
    private int spawnZ;

    @Shadow
    private long time;

    @Shadow
    private long lastPlayed;

    @Shadow
    private long sizeOnDisk;

    @Shadow
    private CompoundTag playerData;

    @Shadow
    private int dimensionId;

    @Shadow
    private String levelName;

    @Shadow
    private int version;

    @Shadow
    private boolean raining;

    @Shadow
    private int rainTime;

    @Shadow
    private boolean thundering;

    @Shadow
    private int thunderTime;

    public MixinLevelProperties(CompoundTag tag) {
        this.brightness = new float[16];
        this.randomSeed = tag.getLong("RandomSeed");
        this.spawnX = tag.getInt("SpawnX");
        this.spawnY = tag.getInt("SpawnY");
        this.spawnZ = tag.getInt("SpawnZ");
        this.time = tag.getLong("Time");
        this.lastPlayed = tag.getLong("LastPlayed");
        this.sizeOnDisk = tag.getLong("SizeOnDisk");
        this.levelName = tag.getString("LevelName");
        this.version = tag.getInt("version");
        this.rainTime = tag.getInt("rainTime");
        this.raining = tag.getBoolean("raining");
        this.thunderTime = tag.getInt("thunderTime");
        this.thundering = tag.getBoolean("thundering");
        if (tag.containsKey("Player")) {
            this.playerData = tag.getCompoundTag("Player");
            this.dimensionId = this.playerData.getInt("Dimension");
        }
        this.tempOffset = tag.getDouble("TemperatureOffset");
        if (tag.containsKey("IsPrecipitating")) {
            this.raining = tag.getBoolean("IsPrecipitating");
        }
        Entity.field_1590 = tag.getInt("nextEntityID");
        if (tag.containsKey("useImages")) {
            this.useImages = tag.getBoolean("useImages");
            this.mapSize = tag.getDouble("mapSize");
            this.waterLevel = tag.getShort("waterLevel");
            this.fractureHorizontal = tag.getDouble("fractureHorizontal");
            this.fractureVertical = tag.getDouble("fractureVertical");
            this.maxAvgDepth = tag.getDouble("maxAvgDepth");
            this.maxAvgHeight = tag.getDouble("maxAvgHeight");
            this.volatility1 = tag.getDouble("volatility1");
            this.volatility2 = tag.getDouble("volatility2");
            this.volatilityWeight1 = tag.getDouble("volatilityWeight1");
            this.volatilityWeight2 = tag.getDouble("volatilityWeight2");
        }
        if (tag.containsKey("iceMelts")) {
            this.iceMelts = tag.getBoolean("iceMelts");
        }
        if (tag.containsKey("triggerAreas")) {
            this.triggerData = tag.getCompoundTag("triggerAreas");
        }
        this.timeRate = tag.containsKey("timeRate") ? tag.getFloat("timeRate") : 1.0f;
        this.timeOfDay = tag.containsKey("timeOfDay") ? tag.getFloat("timeOfDay") : (float) this.time;
        this.playingMusic = tag.getString("playingMusic");
        if (tag.containsKey("mobsBurn")) {
            this.mobsBurn = tag.getBoolean("mobsBurn");
        }
        this.overlay = tag.getString("overlay");
        this.replacementTextures = new HashMap();
        if (tag.containsKey("textureReplacements")) {
            this.replacementTag = tag.getCompoundTag("textureReplacements");
        }
        this.onNewSaveScript = tag.getString("onNewSaveScript");
        this.onLoadScript = tag.getString("onLoadScript");
        this.onUpdateScript = tag.getString("onUpdateScript");
        if (tag.containsKey("playerName")) {
            this.playerName = tag.getString("playerName");
        }
        float f = 0.05f;
        for (int i = 0; i < 16; ++i) {
            String key = String.format("brightness%d", i);
            if (tag.containsKey(key)) {
                this.brightness[i] = tag.getFloat(key);
                continue;
            }
            float f1 = 1.0f - (float) i / 15.0f;
            this.brightness[i] = (1.0f - f1) / (f1 * 3.0f + 1.0f) * (1.0f - f) + f;
        }
        if (tag.containsKey("globalScope")) {
            this.globalScope = tag.getCompoundTag("globalScope");
        }
        if (tag.containsKey("worldScope")) {
            this.worldScope = tag.getCompoundTag("worldScope");
        }
        if (tag.containsKey("musicScope")) {
            this.musicScope = tag.getCompoundTag("musicScope");
        }
        this.originallyFromAC = tag.containsKey("originallyFromAC") ? tag.getBoolean("originallyFromAC") : tag.containsKey("TemperatureOffset");
        this.allowsInventoryCrafting = !tag.containsKey("allowsInventoryCrafting") || tag.getBoolean("allowsInventoryCrafting");
    }

    public MixinLevelProperties(long seed, String levelName) {
        this.brightness = new float[16];
        this.randomSeed = seed;
        this.levelName = levelName;
        this.timeRate = 1.0f;
        this.replacementTextures = new HashMap();
        float f = 0.05f;
        for (int i = 0; i < 16; ++i) {
            float f1 = 1.0f - (float) i / 15.0f;
            this.brightness[i] = (1.0f - f1) / (f1 * 3.0f + 1.0f) * (1.0f - f) + f;
        }
    }

    public MixinLevelProperties(LevelProperties properties) {
        this.randomSeed = properties.randomSeed;
        this.spawnX = properties.spawnX;
        this.spawnY = properties.spawnY;
        this.spawnZ = properties.spawnZ;
        this.time = properties.time;
        this.lastPlayed = properties.lastPlayed;
        this.sizeOnDisk = properties.sizeOnDisk;
        this.playerData = properties.playerData;
        this.dimensionId = properties.dimensionId;
        this.levelName = properties.levelName;
        this.version = properties.version;
        this.rainTime = properties.rainTime;
        this.raining = properties.raining;
        this.thunderTime = properties.thunderTime;
        this.thundering = properties.thundering;
        this.replacementTextures = new HashMap();
        this.brightness = new float[16];
        for (int i = 0; i < 16; ++i) {
            this.brightness[i] = properties.brightness[i];
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void updateProperties(CompoundTag tag, CompoundTag playerData) {
        tag.put("RandomSeed", this.randomSeed);
        tag.put("SpawnX", this.spawnX);
        tag.put("SpawnY", this.spawnY);
        tag.put("SpawnZ", this.spawnZ);
        tag.put("Time", this.time);
        tag.put("SizeOnDisk", this.sizeOnDisk);
        tag.put("LastPlayed", System.currentTimeMillis());
        tag.put("LevelName", this.levelName);
        tag.put("version", this.version);
        tag.put("rainTime", this.rainTime);
        tag.put("raining", this.raining);
        tag.put("thunderTime", this.thunderTime);
        tag.put("thundering", this.thundering);
        if (playerData != null) {
            tag.put("Player", playerData);
        }
        tag.put("TemperatureOffset", this.tempOffset);
        tag.put("nextEntityID", Entity.field_1590);
        tag.put("useImages", this.useImages);
        tag.put("mapSize", this.mapSize);
        tag.put("waterLevel", (short) this.waterLevel);
        tag.put("fractureHorizontal", this.fractureHorizontal);
        tag.put("fractureVertical", this.fractureVertical);
        tag.put("maxAvgDepth", this.maxAvgDepth);
        tag.put("maxAvgHeight", this.maxAvgHeight);
        tag.put("volatility1", this.volatility1);
        tag.put("volatility2", this.volatility2);
        tag.put("volatilityWeight1", this.volatilityWeight1);
        tag.put("volatilityWeight2", this.volatilityWeight2);
        tag.put("iceMelts", this.iceMelts);
        if (AccessMinecraft.getInstance().level != null && AccessMinecraft.getInstance().level.triggerManager != null) {
            tag.put("triggerAreas", (AbstractTag) AccessMinecraft.getInstance().level.triggerManager.getTagCompound());
        }
        tag.put("timeOfDay", this.timeOfDay);
        tag.put("timeRate", this.timeRate);
        if (!this.playingMusic.equals("")) {
            tag.put("playingMusic", this.playingMusic);
        }
        tag.put("mobsBurn", this.mobsBurn);
        if (!this.overlay.equals("")) {
            tag.put("overlay", this.overlay);
        }
        tag.put("textureReplacements", this.getTextureReplacementTags());
        if (!this.onNewSaveScript.equals("")) {
            tag.put("onNewSaveScript", this.onNewSaveScript);
        }
        if (!this.onLoadScript.equals("")) {
            tag.put("onLoadScript", this.onLoadScript);
        }
        if (!this.onUpdateScript.equals("")) {
            tag.put("onUpdateScript", this.onUpdateScript);
        }
        if (!this.playerName.equals("")) {
            tag.put("playerName", this.playerName);
        }
        for (int i = 0; i < 16; ++i) {
            String key = String.format("brightness%d", i);
            tag.put(key, this.brightness[i]);
        }
        if (this.globalScope != null) {
            tag.put("globalScope", (AbstractTag) this.globalScope);
        }
        if (this.worldScope != null) {
            tag.put("worldScope", (AbstractTag) this.worldScope);
        }
        if (this.musicScope != null) {
            tag.put("musicScope", (AbstractTag) this.musicScope);
        }
        tag.put("originallyFromAC", this.originallyFromAC);
        tag.put("allowsInventoryCrafting", this.allowsInventoryCrafting);
    }

    @Override
    public long getTimeOfDay() {
        return (long) this.timeOfDay;
    }

    @Override
    public void setTimeOfDay(float l) {
        this.timeOfDay = l;
        while (this.timeOfDay < 0.0f) {
            this.timeOfDay += 24000.0f;
        }
        while (this.timeOfDay > 24000.0f) {
            this.timeOfDay -= 24000.0f;
        }
    }

    @Override
    public void addToTimeOfDay(float t) {
        this.timeOfDay += t;
        while (this.timeOfDay < 0.0f) {
            this.timeOfDay += 24000.0f;
        }
        while (this.timeOfDay > 24000.0f) {
            this.timeOfDay -= 24000.0f;
        }
    }

    @Override
    public float getTimeRate() {
        return this.timeRate;
    }

    @Override
    public void setTimeRate(float t) {
        this.timeRate = t;
    }

    @Override
    public boolean addReplacementTexture(String replace, String newTexture) {
        String prevReplacement = this.replacementTextures.get(replace);
        if (prevReplacement != null && prevReplacement.equals(newTexture)) {
            return false;
        }
        this.replacementTextures.put(replace, newTexture);
        return true;
    }

    @Override
    public void revertTextures() {
        this.replacementTextures.clear();
    }

    public CompoundTag getTextureReplacementTags() {
        boolean i = false;
        CompoundTag t = new CompoundTag();
        for (Map.Entry<String, String> e : this.replacementTextures.entrySet()) {
            t.put(e.getKey(), e.getValue());
        }
        return t;
    }

    public void loadTextureReplacements(Level w) {
        if (this.replacementTag != null) {
            this.replacementTextures.clear();
            for (String key : this.replacementTag.getKeys()) {
                BlockEffect.replaceTexture(w, key, this.replacementTag.getString(key));
            }
        }
    }

    @Override
    public boolean isOriginallyFromAC() {
        return this.originallyFromAC;
    }

    @Override
    public String getOverlay() {
        return this.overlay;
    }

    @Override
    public void setOverlay(String overlay) {
        this.overlay = overlay;
    }

    @Override
    public boolean isUsingImages() {
        return this.useImages;
    }

    @Override
    public String getPlayerName() {
        return this.playerName;
    }

    @Override
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public float[] getBrightness() {
        return this.brightness;
    }

    @Override
    public float getSpawnYaw() {
        return this.spawnYaw;
    }

    @Override
    public void setSpawnYaw(float spawnYaw) {
        this.spawnYaw = spawnYaw;
    }

    @Override
    public double getTempOffset() {
        return this.tempOffset;
    }

    @Override
    public void setTempOffset(double tempOffset) {
        this.tempOffset = tempOffset;
    }

    @Override
    public boolean getOverrideFogColor() {
        return this.overrideFogColor;
    }

    @Override
    public void setOverrideFogColor(boolean overrideFogColor) {
        this.overrideFogColor = overrideFogColor;
    }

    @Override
    public boolean getOverrideFogDensity() {
        return this.overrideFogDensity;
    }

    @Override
    public void setOverrideFogDensity(boolean overrideFogDensity) {
        this.overrideFogDensity = overrideFogDensity;
    }

    @Override
    public float getFogR() {
        return this.fogR;
    }

    @Override
    public void setFogR(float fogR) {
        this.fogR = fogR;
    }

    @Override
    public float getFogG() {
        return this.fogG;
    }

    @Override
    public void setFogG(float fogG) {
        this.fogG = fogG;
    }

    @Override
    public float getFogB() {
        return this.fogB;
    }

    @Override
    public void setFogB(float fogB) {
        this.fogB = fogB;
    }

    @Override
    public float getFogStart() {
        return fogStart;
    }

    @Override
    public void setFogStart(float fogStart) {
        this.fogStart = fogStart;
    }

    @Override
    public float getFogEnd() {
        return fogEnd;
    }

    @Override
    public void setFogEnd(float fogEnd) {
        this.fogEnd = fogEnd;
    }

    @Override
    public String getPlayingMusic() {
        return playingMusic;
    }

    @Override
    public void setPlayingMusic(String music) {
        playingMusic = music;
    }
}
