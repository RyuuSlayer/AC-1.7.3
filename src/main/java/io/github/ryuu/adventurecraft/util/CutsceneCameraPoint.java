package io.github.ryuu.adventurecraft.util;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

class CutsceneCameraPoint {

    float time;

    float posX;

    float posY;

    float posZ;

    float rotYaw;

    float rotPitch;

    int cameraBlendType;

    int cameraID;

    static int startCameraID = 0;

    static final int NONE = 0;

    static final int LINEAR = 1;

    static final int QUADRATIC = 2;

    CutsceneCameraPoint(float t, float x, float y, float z, float ya, float p, int i) {
        this.time = t;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.rotYaw = ya;
        this.rotPitch = p;
        this.cameraBlendType = i;
        this.cameraID = startCameraID++;
    }
}
