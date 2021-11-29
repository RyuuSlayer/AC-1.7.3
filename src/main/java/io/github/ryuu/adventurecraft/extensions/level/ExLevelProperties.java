package io.github.ryuu.adventurecraft.extensions.level;

public interface ExLevelProperties {

    boolean isOriginallyFromAC();

    String getOverlay();

    void setOverlay(String overlay);

    boolean isUsingImages();

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
}
