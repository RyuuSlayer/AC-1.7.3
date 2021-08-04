package io.github.ryuu.adventurecraft.util;

public class CutsceneCameraPoint {
    public float time;

    public float posX;

    public float posY;

    public float posZ;

    public float rotYaw;

    public float rotPitch;

    public int cameraBlendType;

    public int cameraID;

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

    static int startCameraID = 0;

    static final int NONE = 0;

    static final int LINEAR = 1;

    static final int QUADRATIC = 2;
}
