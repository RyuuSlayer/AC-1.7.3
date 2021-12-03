package io.github.ryuu.adventurecraft.mixin.level;

import io.github.ryuu.adventurecraft.blocks.BlockEffect;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.level.ExLevelProperties;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.mixin.entity.AccessEntity;
import io.github.ryuu.adventurecraft.mixin.util.io.ExCompoundTag;
import net.minecraft.level.Level;
import net.minecraft.level.LevelProperties;
import net.minecraft.util.io.AbstractTag;
import net.minecraft.util.io.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(LevelProperties.class)
public abstract class MixinLevelProperties implements ExLevelProperties {

    public double tempOffset;
    public boolean useBiomeImages = true;

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
    private long time;

    @Shadow
    private boolean raining;

    @Inject(method = "<init>(Lnet/minecraft/util/io/CompoundTag;)V", at = @At("TAIL"))
    private void init(CompoundTag tag, CallbackInfo ci) {
        this.brightness = new float[16];

        this.tempOffset = tag.getDouble("TemperatureOffset");
        if (tag.containsKey("IsPrecipitating")) {
            this.raining = tag.getBoolean("IsPrecipitating");
        }
        AccessEntity.setField_1590(tag.getInt("nextEntityID"));
        if (tag.containsKey("useImages")) {
            this.useBiomeImages = tag.getBoolean("useImages");
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
        this.replacementTextures = new HashMap<>();
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

    @Inject(method = "<init>(JLjava/lang/String;)V", at = @At("TAIL"))
    private void init(long seed, String levelName, CallbackInfo ci) {
        this.replacementTextures = new HashMap<>();
        this.brightness = new float[16];
        this.timeRate = 1.0f;

        float f = 0.05f;
        for (int i = 0; i < 16; ++i) {
            float f1 = 1.0f - (float) i / 15.0f;
            this.brightness[i] = (1.0f - f1) / (f1 * 3.0f + 1.0f) * (1.0f - f) + f;
        }
    }

    @Inject(method = "<init>(Lnet/minecraft/level/LevelProperties;)V", at = @At("TAIL"))
    private void init(LevelProperties properties, CallbackInfo ci) {
        this.replacementTextures = new HashMap<>();
        this.brightness = new float[16];

        ExLevelProperties exProps = (ExLevelProperties) properties;
        for (int i = 0; i < 16; ++i) {
            this.brightness[i] = exProps.getBrightness()[i];
        }
    }

    @Inject(method = "updateProperties", at = @At("TAIL"))
    private void writeAcUpdateProperties(CompoundTag tag, CompoundTag playerData, CallbackInfo ci) {
        tag.put("TemperatureOffset", this.tempOffset);
        tag.put("nextEntityID", AccessEntity.getField_1590());
        tag.put("useImages", this.useBiomeImages);
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
        ExLevel level = (ExLevel) AccessMinecraft.getInstance().level;
        if (level != null && level.getTriggerManager() != null) {
            tag.put("triggerAreas", (AbstractTag) level.getTriggerManager().getTagCompound());
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
        CompoundTag t = new CompoundTag();
        for (Map.Entry<String, String> e : this.replacementTextures.entrySet()) {
            t.put(e.getKey(), e.getValue());
        }
        return t;
    }

    @Override
    public void loadTextureReplacements(Level w) {
        if (this.replacementTag != null) {
            this.replacementTextures.clear();
            for (String key : ((ExCompoundTag) this.replacementTag).getKeys()) {
                BlockEffect.replaceTexture(w, key, this.replacementTag.getString(key));
            }
        }
    }

    @Override
    public boolean isOriginallyFromAC() {
        return this.originallyFromAC;
    }

    @Override
    public boolean getAllowsInventoryCrafting() {
        return this.allowsInventoryCrafting;
    }

    @Override
    public void setAllowsInventoryCrafting(boolean allowsInventoryCrafting) {
        this.allowsInventoryCrafting = allowsInventoryCrafting;
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
    public boolean isUsingBiomeImages() {
        return this.useBiomeImages;
    }

    public void setUseBiomeImages(boolean useBiomeImages) {
        this.useBiomeImages = useBiomeImages;
    }

    @Override
    public Map<String, String> getReplacementTextures() {
        return this.replacementTextures;
    }

    @Override
    public double getMapSize() {
        return mapSize;
    }

    @Override
    public void setMapSize(double mapSize) {
        this.mapSize = mapSize;
    }

    @Override
    public int getWaterLevel() {
        return waterLevel;
    }

    @Override
    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    @Override
    public double getFractureHorizontal() {
        return fractureHorizontal;
    }

    @Override
    public void setFractureHorizontal(double fractureHorizontal) {
        this.fractureHorizontal = fractureHorizontal;
    }

    @Override
    public double getFractureVertical() {
        return fractureVertical;
    }

    @Override
    public void setFractureVertical(double fractureVertical) {
        this.fractureVertical = fractureVertical;
    }

    @Override
    public double getMaxAvgDepth() {
        return maxAvgDepth;
    }

    @Override
    public void setMaxAvgDepth(double maxAvgDepth) {
        this.maxAvgDepth = maxAvgDepth;
    }

    @Override
    public double getMaxAvgHeight() {
        return maxAvgHeight;
    }

    @Override
    public void setMaxAvgHeight(double maxAvgHeight) {
        this.maxAvgHeight = maxAvgHeight;
    }

    @Override
    public double getVolatility1() {
        return volatility1;
    }

    @Override
    public void setVolatility1(double volatility1) {
        this.volatility1 = volatility1;
    }

    @Override
    public double getVolatility2() {
        return volatility2;
    }

    @Override
    public void setVolatility2(double volatility2) {
        this.volatility2 = volatility2;
    }

    @Override
    public double getVolatilityWeight1() {
        return volatilityWeight1;
    }

    @Override
    public void setVolatilityWeight1(double volatilityWeight1) {
        this.volatilityWeight1 = volatilityWeight1;
    }

    @Override
    public double getVolatilityWeight2() {
        return volatilityWeight2;
    }

    @Override
    public void setVolatilityWeight2(double volatilityWeight2) {
        this.volatilityWeight2 = volatilityWeight2;
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
    public boolean getIceMelts() {
        return this.iceMelts;
    }

    @Override
    public void setIceMelts(boolean iceMelts) {
        this.iceMelts = iceMelts;
    }

    @Override
    public boolean getMobsBurn() {
        return this.mobsBurn;
    }

    @Override
    public void setMobsBurn(boolean mobsBurn) {
        this.mobsBurn = mobsBurn;
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

    @Override
    public String getOnNewSaveScript() {
        return onNewSaveScript;
    }

    @Override
    public void setOnNewSaveScript(String onNewSaveScript) {
        this.onNewSaveScript = onNewSaveScript;
    }

    @Override
    public String getOnLoadScript() {
        return onLoadScript;
    }

    @Override
    public void setOnLoadScript(String onLoadScript) {
        this.onLoadScript = onLoadScript;
    }

    @Override
    public String getOnUpdateScript() {
        return onUpdateScript;
    }

    @Override
    public void setOnUpdateScript(String onUpdateScript) {
        this.onUpdateScript = onUpdateScript;
    }

    @Override
    public CompoundTag getGlobalScope() {
        return globalScope;
    }

    @Override
    public void setGlobalScope(CompoundTag globalScope) {
        this.globalScope = globalScope;
    }

    @Override
    public CompoundTag getWorldScope() {
        return worldScope;
    }

    @Override
    public void setWorldScope(CompoundTag worldScope) {
        this.worldScope = worldScope;
    }

    @Override
    public CompoundTag getMusicScope() {
        return musicScope;
    }

    @Override
    public void setMusicScope(CompoundTag musicScope) {
        this.musicScope = musicScope;
    }

    @Override
    public CompoundTag getTriggerData() {
        return triggerData;
    }
}
