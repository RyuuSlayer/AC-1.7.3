package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.util.CutsceneCamera;
import io.github.ryuu.adventurecraft.util.CutsceneCameraPoint;
import net.minecraft.client.Minecraft;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

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

    public void readIdentifyingData(CompoundTag nbttagcompound) {
        super.readIdentifyingData(nbttagcompound);
        int numPoints = nbttagcompound.getInt("numPoints");
        for (int i = 0; i < numPoints; i++) {
            readPointTag(nbttagcompound.getCompoundTag(String.format("point%d", i)));
        }
        if (nbttagcompound.containsKey("type"))
            this.type = nbttagcompound.getByte("type");
        if (nbttagcompound.containsKey("pauseGame"))
            this.pauseGame = nbttagcompound.getBoolean("pauseGame");
    }

    public void writeIdentifyingData(CompoundTag nbttagcompound) {
        super.writeIdentifyingData(nbttagcompound);
        int numPoints = 0;
        for (CutsceneCameraPoint p : this.camera.cameraPoints) {
            nbttagcompound.put(String.format("point%d", numPoints), getPointTag(p));
            numPoints++;
        }
        nbttagcompound.put("numPoints", numPoints);
        nbttagcompound.put("type", (byte) this.type);
        nbttagcompound.put("pauseGame", this.pauseGame);
    }

    private CompoundTag getPointTag(CutsceneCameraPoint point) {
        CompoundTag nbttagcompound = new CompoundTag();
        nbttagcompound.put("time", point.time);
        nbttagcompound.put("posX", point.posX);
        nbttagcompound.put("posY", point.posY);
        nbttagcompound.put("posZ", point.posZ);
        nbttagcompound.put("yaw", point.rotYaw);
        nbttagcompound.put("pitch", point.rotPitch);
        nbttagcompound.put("type", (byte) point.cameraBlendType);
        return nbttagcompound;
    }

    private void readPointTag(CompoundTag nbttagcompound) {
        float time = nbttagcompound.getFloat("time");
        float posX = nbttagcompound.getFloat("posX");
        float posY = nbttagcompound.getFloat("posY");
        float posZ = nbttagcompound.getFloat("posZ");
        float yaw = nbttagcompound.getFloat("yaw");
        float pitch = nbttagcompound.getFloat("pitch");
        int type = 2;
        if (nbttagcompound.containsKey("type"))
            type = nbttagcompound.getByte("type");
        this.camera.addCameraPoint(time, posX, posY, posZ, yaw, pitch, type);
    }

    public int type = 2;

    public boolean pauseGame = true;
}
