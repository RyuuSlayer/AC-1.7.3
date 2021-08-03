package io.github.ryuu.adventurecraft.entities;

import io.github.ryuu.adventurecraft.Minecraft;
import io.github.ryuu.adventurecraft.gui.GuiCamera;

public class EntityCamera extends ls {
    float time;

    int type;

    int cameraID;

    EntityCamera(fd world, float t, int ty, int id) {
        super(world);
        this.time = t;
        this.cameraID = id;
        this.type = ty;
    }

    protected void b() {}

    public void deleteCameraPoint() {
        Minecraft.minecraftInstance.activeCutsceneCamera.deletePoint(this.cameraID);
        Minecraft.minecraftInstance.activeCutsceneCamera.loadCameraEntities();
    }

    public void a(nu nbttagcompound) {}

    public void b(nu nbttagcompound) {}

    public void U() {}

    public void o() {}

    public void w_() {}

    public boolean h_() {
        return true;
    }

    public boolean i_() {
        return false;
    }

    public boolean a(gs entityplayer) {
        GuiCamera.showUI(this);
        return true;
    }
}
