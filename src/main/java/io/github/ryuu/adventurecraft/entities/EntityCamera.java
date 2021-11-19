package io.github.ryuu.adventurecraft.entities;

import net.minecraft.client.Minecraft;

public class EntityCamera extends MixinLivingEntity {

    float time;

    int type;

    int cameraID;

    EntityCamera(MixinLevel world, float t, int ty, int id) {
        super(world);
        this.time = t;
        this.cameraID = id;
        this.type = ty;
    }

    @Override
    protected void initDataTracker() {
    }

    public void deleteCameraPoint() {
        Minecraft.minecraftInstance.activeCutsceneCamera.deletePoint(this.cameraID);
        Minecraft.minecraftInstance.activeCutsceneCamera.loadCameraEntities();
    }

    @Override
    public void readCustomDataFromTag(MixinCompoundTag tag) {
    }

    @Override
    public void writeCustomDataToTag(MixinCompoundTag tag) {
    }

    @Override
    public void baseTick() {
    }

    @Override
    public void updateDespawnCounter() {
    }

    @Override
    public void tick() {
    }

    @Override
    public boolean method_1356() {
        return true;
    }

    @Override
    public boolean method_1380() {
        return false;
    }

    @Override
    public boolean interact(MixinPlayer entityplayer) {
        GuiCamera.showUI(this);
        return true;
    }
}
