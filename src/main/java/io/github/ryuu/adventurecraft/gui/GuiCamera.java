package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.EntityCamera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.entity.Entity;

public class GuiCamera extends Screen {
    private EntityCamera cam;

    private ro timerText;

    public GuiCamera(EntityCamera c) {
        this.cam = c;
    }

    public void b() {
        ke b = new ke(0, 4, 4, 160, 18, "Delete Camera Point");
        this.e.add(b);
        b = new ke(1, 4, 24, 160, 18, "No Interpolation");
        if (this.cam.type == 1) {
            b.e = "Linear Interpolation";
        } else if (this.cam.type == 2) {
            b.e = "Quadratic Interpolation";
        }
        this.e.add(b);
        this.timerText = new ro(this, this.g, 80, 46, 70, 16, String.format("%.2f", new Object[] { Float.valueOf(this.cam.time) }));
    }

    public void a() {
        this.timerText.b();
    }

    protected void a(char c, int i) {
        if (this.timerText.a && (i == 14 || (c >= '0' && c <= '9') || c == '.' || c == '\t'))
            this.timerText.a(c, i);
        super.a(c, i);
    }

    protected void a(int i, int j, int k) {
        this.timerText.a(i, j, k);
        super.a(i, j, k);
    }

    protected void a(ke guibutton) {
        if (guibutton.f == 0) {
            this.cam.deleteCameraPoint();
            Minecraft.minecraftInstance.a(null);
        } else if (guibutton.f == 1) {
            this.cam.type = (this.cam.type + 1) % 3;
            this.b.activeCutsceneCamera.setPointType(this.cam.cameraID, this.cam.type);
            for (Entity obj : this.b.f.b) {
                if (obj instanceof EntityCamera) {
                    EntityCamera c = (EntityCamera)obj;
                    if (c.W() && c.cameraID == this.cam.cameraID) {
                        this.cam = c;
                        break;
                    }
                }
            }
            if (this.cam.type == 1) {
                guibutton.e = "Linear Interpolation";
            } else if (this.cam.type == 2) {
                guibutton.e = "Quadratic Interpolation";
            } else {
                guibutton.e = "No Interpolation";
            }
        }
    }

    public void a(int i, int j, float f) {
        i();
        try {
            Float value = Float.valueOf(this.timerText.a());
            this.cam.time = value.floatValue();
            this.b.activeCutsceneCamera.setTime(this.cam.cameraID, value.floatValue());
        } catch (NumberFormatException e) {}
        b(this.g, "Active At:", 4, 49, 14737632);
        this.timerText.c();
        super.a(i, j, f);
    }

    public static void showUI(EntityCamera c) {
        Minecraft.minecraftInstance.a(new GuiCamera(c));
    }
}
