package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.util.CutsceneCamera;
import io.github.ryuu.adventurecraft.util.CutsceneCameraPoint;
import net.minecraft.client.Minecraft;
import net.minecraft.tile.entity.TileEntity;

public class TileEntityCamera extends TileEntity {
    public String message;

    public String sound;

    public CutsceneCamera camera = new CutsceneCamera();

    public void loadCamera() {
        copyCamera(this.camera, Minecraft.minecraftInstance.cutsceneCamera);
        Minecraft.minecraftInstance.cutsceneCamera.startType = this.type;
    }

    public void saveCamera() {
        copyCamera(Minecraft.minecraftInstance.cutsceneCamera, this.camera);
    }

    private void copyCamera(CutsceneCamera src, CutsceneCamera tgt) {
        tgt.clearPoints();
        for (CutsceneCameraPoint p : src.cameraPoints)
            tgt.addCameraPoint(p.time, p.posX, p.posY, p.posZ, p.rotYaw, p.rotPitch, p.cameraBlendType);
    }

    public void a(nu nbttagcompound) {
        super.a(nbttagcompound);
        int numPoints = nbttagcompound.e("numPoints");
        for (int i = 0; i < numPoints; i++) {
            readPointTag(nbttagcompound.k(String.format("point%d", new Object[]{Integer.valueOf(i)})));
        }
        if (nbttagcompound.b("type"))
            this.type = nbttagcompound.c("type");
        if (nbttagcompound.b("pauseGame"))
            this.pauseGame = nbttagcompound.m("pauseGame");
    }

    public void b(nu nbttagcompound) {
        super.b(nbttagcompound);
        int numPoints = 0;
        for (CutsceneCameraPoint p : this.camera.cameraPoints) {
            nbttagcompound.a(String.format("point%d", new Object[]{Integer.valueOf(numPoints)}), getPointTag(p));
            numPoints++;
        }
        nbttagcompound.a("numPoints", numPoints);
        nbttagcompound.a("type", (byte) this.type);
        nbttagcompound.a("pauseGame", this.pauseGame);
    }

    private nu getPointTag(CutsceneCameraPoint point) {
        nu nbttagcompound = new nu();
        nbttagcompound.a("time", point.time);
        nbttagcompound.a("posX", point.posX);
        nbttagcompound.a("posY", point.posY);
        nbttagcompound.a("posZ", point.posZ);
        nbttagcompound.a("yaw", point.rotYaw);
        nbttagcompound.a("pitch", point.rotPitch);
        nbttagcompound.a("type", (byte) point.cameraBlendType);
        return nbttagcompound;
    }

    private void readPointTag(nu nbttagcompound) {
        float time = nbttagcompound.g("time");
        float posX = nbttagcompound.g("posX");
        float posY = nbttagcompound.g("posY");
        float posZ = nbttagcompound.g("posZ");
        float yaw = nbttagcompound.g("yaw");
        float pitch = nbttagcompound.g("pitch");
        int type = 2;
        if (nbttagcompound.b("type"))
            type = nbttagcompound.c("type");
        this.camera.addCameraPoint(time, posX, posY, posZ, yaw, pitch, type);
    }

    int type = 2;

    public boolean pauseGame = true;
}
