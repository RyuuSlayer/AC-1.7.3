package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.EntityCamera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.Textbox;
import net.minecraft.entity.Entity;

public class GuiCamera extends Screen {
    private EntityCamera cam;

    private Textbox timerText;

    public GuiCamera(EntityCamera c) {
        this.cam = c;
    }

    public static void showUI(EntityCamera c) {
        Minecraft.minecraftInstance.a(new GuiCamera(c));
    }

    @Override
    public void init() {
        Button b = new Button(0, 4, 4, 160, 18, "Delete Camera Point");
        this.buttons.add(b);
        b = new Button(1, 4, 24, 160, 18, "No Interpolation");
        if (this.cam.type == 1) {
            b.text = "Linear Interpolation";
        } else if (this.cam.type == 2) {
            b.text = "Quadratic Interpolation";
        }
        this.buttons.add(b);
        this.timerText = new Textbox(this, this.textManager, 80, 46, 70, 16, String.format("%.2f", this.cam.time));
    }

    @Override
    public void tick() {
        this.timerText.tick();
    }

    @Override
    protected void keyPressed(char c, int i) {
        if (this.timerText.field_2420 && (i == 14 || (c >= '0' && c <= '9') || c == '.' || c == '\t'))
            this.timerText.method_1877(c, i);
        super.keyPressed(c, i);
    }

    @Override
    protected void mouseClicked(int i, int j, int k) {
        this.timerText.method_1879(i, j, k);
        super.mouseClicked(i, j, k);
    }

    @Override
    protected void buttonClicked(Button guibutton) {
        if (guibutton.id == 0) {
            this.cam.deleteCameraPoint();
            Minecraft.minecraftInstance.a(null);
        } else if (guibutton.id == 1) {
            this.cam.type = (this.cam.type + 1) % 3;
            this.minecraft.activeCutsceneCamera.setPointType(this.cam.cameraID, this.cam.type);
            for (Entity obj : this.minecraft.level.entities) {
                if (obj instanceof EntityCamera) {
                    EntityCamera c = (EntityCamera) obj;
                    if (c.isAlive() && c.cameraID == this.cam.cameraID) {
                        this.cam = c;
                        break;
                    }
                }
            }
            if (this.cam.type == 1) {
                guibutton.text = "Linear Interpolation";
            } else if (this.cam.type == 2) {
                guibutton.text = "Quadratic Interpolation";
            } else {
                guibutton.text = "No Interpolation";
            }
        }
    }

    @Override
    public void render(int i, int j, float f) {
        renderBackground();
        try {
            Float value = Float.valueOf(this.timerText.method_1876());
            this.cam.time = value.floatValue();
            this.minecraft.activeCutsceneCamera.setTime(this.cam.cameraID, value.floatValue());
        } catch (NumberFormatException e) {
        }
        drawTextWithShadow(this.textManager, "Active At:", 4, 49, 14737632);
        this.timerText.method_1883();
        super.render(i, j, f);
    }
}
