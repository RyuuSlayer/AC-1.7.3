package io.github.ryuu.adventurecraft.extensions.level;

import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;

public interface ExLevelProperties {

    double getMapSize();

    void setMapSize(double mapSize);

    int getWaterLevel();

    void setWaterLevel(int waterLevel);

    double getFractureHorizontal();

    void setFractureHorizontal(double fractureHorizontal);

    double getFractureVertical();

    void setFractureVertical(double fractureVertical);

    double getMaxAvgDepth();

    void setMaxAvgDepth(double maxAvgDepth);

    double getMaxAvgHeight();

    void setMaxAvgHeight(double maxAvgHeight);

    double getVolatility1();

    void setVolatility1(double volatility1);

    double getVolatility2();

    void setVolatility2(double volatility2);

    double getVolatilityWeight1();

    void setVolatilityWeight1(double volatilityWeight1);

    double getVolatilityWeight2();

    void setVolatilityWeight2(double volatilityWeight2);

    boolean isOriginallyFromAC();

    String getOverlay();

    void setOverlay(String overlay);

    boolean isUsingBiomeImages();

    void setUseBiomeImages(boolean useBiomeImages);

    String getPlayerName();

    void setPlayerName(String playerName);

    long getTimeOfDay();

    void setTimeOfDay(float l);

    void addToTimeOfDay(float t);

    float getTimeRate();

    void setTimeRate(float t);

    float[] getBrightness();

    float getSpawnYaw();

    void setSpawnYaw(float spawnYaw);

    double getTempOffset();

    void setTempOffset(double tempOffset);

    boolean getOverrideFogColor();

    void setOverrideFogColor(boolean overrideFogColor);

    boolean getOverrideFogDensity();

    void setOverrideFogDensity(boolean overrideFogDensity);

    float getFogR();

    void setFogR(float fogR);

    float getFogG();

    void setFogG(float fogG);

    float getFogB();

    void setFogB(float fogB);

    float getFogStart();

    void setFogStart(float fogStart);

    float getFogEnd();

    void setFogEnd(float fogEnd);

    void revertTextures();

    boolean addReplacementTexture(String replace, String newTexture);

    String getPlayingMusic();

    void setPlayingMusic(String music);

    String getOnNewSaveScript();

    String getOnLoadScript();

    String getOnUpdateScript();

    CompoundTag getGlobalScope();

    void setGlobalScope(CompoundTag globalScope);

    CompoundTag getWorldScope();

    void setWorldScope(CompoundTag worldScope);

    CompoundTag getMusicScope();

    void setMusicScope(CompoundTag musicScope);

    CompoundTag getTriggerData();

    void loadTextureReplacements(Level w);
}
