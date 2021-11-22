package io.github.ryuu.adventurecraft.entities.tile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

public class TileEntityCamera extends TileEntity {

    public String message;

    public String sound;

    public CutsceneCamera camera = new CutsceneCamera();

    int type = 2;

    public boolean pauseGame = true;

    TileEntityCamera() {
    }

    public void loadCamera() {
        this.copyCamera(this.camera, Minecraft.minecraftInstance.cutsceneCamera);
        Minecraft.minecraftInstance.cutsceneCamera.startType = this.type;
    }

    public void saveCamera() {
        this.copyCamera(Minecraft.minecraftInstance.cutsceneCamera, this.camera);
    }

    private void copyCamera(CutsceneCamera src, CutsceneCamera tgt) {
        tgt.clearPoints();
        for (CutsceneCameraPoint p : src.cameraPoints) {
            tgt.addCameraPoint(p.time, p.posX, p.posY, p.posZ, p.rotYaw, p.rotPitch, p.cameraBlendType);
        }
    }

    @Override
    public void readIdentifyingData(CompoundTag tag) {
        super.readIdentifyingData(tag);
        int numPoints = tag.getInt("numPoints");
        for (int i = 0; i < numPoints; ++i) {
            this.readPointTag(tag.getCompoundTag(String.format((String) "point%d", (Object[]) new Object[] { i })));
        }
        if (tag.containsKey("type")) {
            this.type = tag.getByte("type");
        }
        if (tag.containsKey("pauseGame")) {
            this.pauseGame = tag.getBoolean("pauseGame");
        }
    }

    @Override
    public void writeIdentifyingData(CompoundTag tag) {
        super.writeIdentifyingData(tag);
        int numPoints = 0;
        for (CutsceneCameraPoint p : this.camera.cameraPoints) {
            tag.put(String.format((String) "point%d", (Object[]) new Object[] { numPoints }), this.getPointTag(p));
            ++numPoints;
        }
        tag.put("numPoints", numPoints);
        tag.put("type", (byte) this.type);
        tag.put("pauseGame", this.pauseGame);
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
        if (nbttagcompound.containsKey("type")) {
            type = nbttagcompound.getByte("type");
        }
        this.camera.addCameraPoint(time, posX, posY, posZ, yaw, pitch, type);
    }
}
