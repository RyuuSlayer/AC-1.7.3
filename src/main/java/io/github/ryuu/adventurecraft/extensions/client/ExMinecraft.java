package io.github.ryuu.adventurecraft.extensions.client;

import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.util.CutsceneCamera;
import io.github.ryuu.adventurecraft.util.MapList;
import net.minecraft.entity.LivingEntity;

public interface ExMinecraft {

    static ExMinecraft getInstance() {
        return (ExMinecraft)AccessMinecraft.getInstance();
    }

    boolean isCameraActive();

    CutsceneCamera getCutsceneCamera();

    CutsceneCamera getActiveCutsceneCamera();

    LivingEntity getCutsceneCameraEntity();

    void setActiveCutsceneCamera(CutsceneCamera cutsceneCamera);

    void setCameraActive(boolean cameraActive);

    boolean isCameraPause();

    void setCameraPause(boolean cameraPause);

    MapList getMapList();

    long getAvgFrameTime();
}
