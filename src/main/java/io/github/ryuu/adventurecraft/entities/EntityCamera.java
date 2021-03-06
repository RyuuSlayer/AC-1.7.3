package io.github.ryuu.adventurecraft.entities;

import io.github.ryuu.adventurecraft.extensions.client.ExMinecraft;
import io.github.ryuu.adventurecraft.gui.GuiCamera;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;

public class EntityCamera extends LivingEntity {

    public float time;
    public int type;
    public int cameraID;

    public EntityCamera(Level world, float t, int ty, int id) {
        super(world);
        this.time = t;
        this.cameraID = id;
        this.type = ty;
    }

    @Override
    protected void initDataTracker() {
    }

    public void deleteCameraPoint() {
        ExMinecraft.getInstance().getActiveCutsceneCamera().deletePoint(this.cameraID);
        ExMinecraft.getInstance().getActiveCutsceneCamera().loadCameraEntities();
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
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
    public boolean interact(Player entityplayer) {
        GuiCamera.showUI(this);
        return true;
    }
}
