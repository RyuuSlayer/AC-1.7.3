package io.github.ryuu.adventurecraft.entities;

import io.github.ryuu.adventurecraft.Minecraft;
import io.github.ryuu.adventurecraft.gui.GuiCamera;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;

public class EntityCamera extends LivingEntity {
    float time;

    int type;

    int cameraID;

    EntityCamera(Level world, float t, int ty, int id) {
        super(world);
        this.time = t;
        this.cameraID = id;
        this.type = ty;
    }

    protected void b() {
    }

    public void deleteCameraPoint() {
        Minecraft.minecraftInstance.activeCutsceneCamera.deletePoint(this.cameraID);
        Minecraft.minecraftInstance.activeCutsceneCamera.loadCameraEntities();
    }

    public void a(CompoundTag nbttagcompound) {
    }

    public void b(CompoundTag nbttagcompound) {
    }

    public void U() {
    }

    public void o() {
    }

    public void w_() {
    }

    public boolean h_() {
        return true;
    }

    public boolean i_() {
        return false;
    }

    public boolean a(Player entityplayer) {
        GuiCamera.showUI(this);
        return true;
    }
}
