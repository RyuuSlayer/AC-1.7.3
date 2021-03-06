package io.github.ryuu.adventurecraft.extensions.client;

import io.github.ryuu.adventurecraft.gui.GuiStore;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.util.CutsceneCamera;
import io.github.ryuu.adventurecraft.util.MapList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.level.Level;

public interface ExMinecraft {

    static ExMinecraft getInstance() {
        return (ExMinecraft) AccessMinecraft.getInstance();
    }

    GuiStore getStoreGUI();

    boolean isCameraActive();

    void setCameraActive(boolean cameraActive);

    CutsceneCamera getCutsceneCamera();

    CutsceneCamera getActiveCutsceneCamera();

    void setActiveCutsceneCamera(CutsceneCamera cutsceneCamera);

    LivingEntity getCutsceneCameraEntity();

    boolean isCameraPause();

    void setCameraPause(boolean cameraPause);

    MapList getMapList();

    long getAvgFrameTime();

    void saveMapUsed(String s, String mapName);

    Level getWorld(String saveName, long l, String mapName);

    void startWorld(String s, String s1, long l, String mapName);

    void updateStoreGUI();
}
